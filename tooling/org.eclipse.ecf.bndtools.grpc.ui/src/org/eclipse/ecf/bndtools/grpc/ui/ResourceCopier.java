package org.eclipse.ecf.bndtools.grpc.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;


public class ResourceCopier {
	
	public static void recurseCreate(IContainer container, IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		if (container == null || container.exists())
			return;

		recurseCreate(container.getParent(), progress.split(1, SubMonitor.SUPPRESS_NONE));

		if (container instanceof IFolder)
			((IFolder) container).create(false, true, progress.split(1, SubMonitor.SUPPRESS_NONE));
		else
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.ecf.bndtools.grpc.ui", 0,
				"Cannot create new projects or workspace roots automatically.", null));
	}


	public static IFile copy(URL url, IFile dst, Map<String, String> replaceRegularExpressions,
		IProgressMonitor monitor) throws IOException, CoreException {
		InputStream is = null;
		try {
			SubMonitor progress = SubMonitor.convert(monitor, 2);

			if (url.getPath()
				.endsWith("/")) {
				File file = dst.getProjectRelativePath()
					.toFile();

				if (file.isDirectory())
					return dst; // already done

				if (file.isFile())
					throw new IllegalArgumentException("Expected no file or a directory, but was a file: " + file);

				file.mkdirs();
				return dst; // already done
			}

			ResourceReplacer replacer = null;
			if ((replaceRegularExpressions == null) || replaceRegularExpressions.isEmpty()) {
				is = url.openStream();
			} else {
				replacer = new ResourceReplacer(replaceRegularExpressions, url);
				replacer.start();
				is = replacer.getStream();
			}

			if (dst.exists()) {
				dst.setContents(is, false, true, progress.split(2, SubMonitor.SUPPRESS_NONE));
			} else {
				recurseCreate(dst.getParent(), progress.split(1, SubMonitor.SUPPRESS_NONE));
				dst.create(is, false, progress.split(1, SubMonitor.SUPPRESS_NONE));
			}

			if (replacer != null) {
				try {
					replacer.join();
				} catch (InterruptedException e) {
					/* swallow */
				}
				if (replacer.getResult() != null) {
					throw replacer.getResult();
				}
			}
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				
			}
		}

		return dst;
	}
}
