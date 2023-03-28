package org.eclipse.ecf.bndtools.grpc.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bndtools.api.BndProjectResource;
import org.bndtools.api.BndtoolsConstants;
import org.bndtools.api.ILogger;
import org.bndtools.api.Logger;
import org.bndtools.api.ProjectPaths;
import org.bndtools.build.api.BuildErrorDetailsHandler;
import org.bndtools.headless.build.manager.api.HeadlessBuildManager;
import org.bndtools.templating.Resource;
import org.bndtools.templating.ResourceMap;
import org.bndtools.templating.Template;
import org.bndtools.versioncontrol.ignores.manager.api.VersionControlIgnoresManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ecf.bndtools.grpc.ui.shared.JavaProjectUtils;
import org.eclipse.ecf.bndtools.grpc.ui.utils.FileUtils;
import org.eclipse.ecf.bndtools.grpc.ui.utils.ProjectTemplateParam;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.wizards.JavaProjectWizard;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import aQute.bnd.build.Project;
import aQute.bnd.build.model.BndEditModel;
import aQute.bnd.properties.Document;
import bndtools.Plugin;
import bndtools.central.Central;
import bndtools.editor.model.BndProject;
import bndtools.preferences.BndPreferences;

@SuppressWarnings("restriction")
abstract class AbstractNewBndServiceWizard extends JavaProjectWizard {
	private static final ILogger logger = Logger.getLogger(AbstractNewBndServiceWizard.class);

	protected NewBndServiceWizardPageOne pageOne;
	protected NewJavaProjectWizardPageTwo pageTwo;

	AbstractNewBndServiceWizard(NewBndServiceWizardPageOne pageOne, NewJavaProjectWizardPageTwo pageTwo) {
		super(pageOne, pageTwo);
		setWindowTitle("New Remote Service");
		setNeedsProgressMonitor(true);

		this.pageOne = pageOne;
		this.pageTwo = pageTwo;
	}

	@Override
	public void addPages() {
		addPage(pageOne);
		addPage(pageTwo);
	}

	@SuppressWarnings("unused")
	protected BndEditModel generateBndModel(IProgressMonitor monitor) {
		try {
			return new BndEditModel(Central.getWorkspace());
		} catch (Exception e) {
			logger.logInfo("Unable to create BndEditModel with Workspace, defaulting to without Workspace", e);
			return new BndEditModel();
		}
	}

	/**
	 * Allows for an IProjectTemplate to modify the new Bnd project
	 *
	 * @param monitor
	 */
	@SuppressWarnings("unused")
	protected BndProject generateBndProject(IProject project, IProgressMonitor monitor) {
		return new BndProject(project);
	}

	/**
	 * Modify the newly generated Java project; this method is executed from within
	 * a workspace operation so is free to make workspace resource modifications.
	 *
	 * @throws CoreException
	 */
	protected void processGeneratedProject(ProjectPaths projectPaths, BndEditModel bndModel, IJavaProject project,
			IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 3);

		Document document = new Document("");
		bndModel.saveChangesTo(document);
		progress.worked(1);

		ByteArrayInputStream bndInput;
		try {
			bndInput = new ByteArrayInputStream(document.get().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return;
		}
		IFile bndBndFile = project.getProject().getFile(Project.BNDFILE);
		if (bndBndFile.exists()) {
			bndBndFile.setContents(bndInput, false, false, progress.split(1));
		}

		BndProject proj = generateBndProject(project.getProject(), progress.split(1));

		progress.setWorkRemaining(proj.getResources().size());
		for (Map.Entry<String, BndProjectResource> resource : proj.getResources().entrySet()) {
			importResource(project.getProject(), resource.getKey(), resource.getValue(), progress.split(1));
		}

		if (!bndBndFile.exists()) {
			bndBndFile.create(bndInput, false, progress.split(1));
		}

		/* Version control ignores */
		VersionControlIgnoresManager versionControlIgnoresManager = Plugin.getDefault()
				.getVersionControlIgnoresManager();
		Set<String> enabledIgnorePlugins = new BndPreferences()
				.getVersionControlIgnoresPluginsEnabled(versionControlIgnoresManager, project, null);
		Map<String, String> sourceOutputLocations = JavaProjectUtils.getSourceOutputLocations(project);
		versionControlIgnoresManager.createProjectIgnores(enabledIgnorePlugins,
				project.getProject().getLocation().toFile(), sourceOutputLocations, projectPaths.getTargetDir());

		/* Headless build files */
		HeadlessBuildManager headlessBuildManager = Plugin.getDefault().getHeadlessBuildManager();
		Set<String> enabledPlugins = new BndPreferences().getHeadlessBuildPluginsEnabled(headlessBuildManager, null);
		headlessBuildManager.setup(enabledPlugins, false, project.getProject().getLocation().toFile(), true,
				enabledIgnorePlugins, new LinkedList<String>());

		/* refresh the project; files were created outside of Eclipse API */
		project.getProject().refreshLocal(IResource.DEPTH_INFINITE, progress);

		project.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, progress);
	}

	protected static IFile importResource(IProject project, String fullPath, BndProjectResource bndProjectResource,
			IProgressMonitor monitor) throws CoreException {
		URL url = bndProjectResource.getUrl();
		Map<String, String> replaceRegularExpressions = bndProjectResource.getReplaceRegularExpressions();
		IFile dst = project.getFile(fullPath);

		try {
			return ResourceCopier.copy(url, dst, replaceRegularExpressions, monitor);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Plugin.PLUGIN_ID, e.getMessage(), e));
		}
	}

	protected void generateProjectContent(IProject project, Template template, Map<String, String> params,
			IProgressMonitor monitor) throws IOException {
		// Set to current project name
		String projectName = project.getName();
		params.put(ProjectTemplateParam.PROJECT_NAME.getString(), projectName);
		params.put(ProjectTemplateParam.BASE_PACKAGE_DIR.getString(), projectName.replace('.', '/'));
		params.put(ProjectTemplateParam.BASE_PACKAGE_NAME.getString(), projectName);
		
		Map<String, List<Object>> templateParams = new HashMap<>();
		for (Entry<String, String> param : params.entrySet()) {
			templateParams.put(param.getKey(), Collections.<Object> singletonList(param.getValue()));
		}
		try {
			ResourceMap outputs;
			if (template != null) {
				outputs = template.generateOutputs(templateParams);
			} else {
				outputs = new ResourceMap(); // empty
			}

			SubMonitor progress = SubMonitor.convert(monitor, outputs.size() * 3);
			for (Entry<String, Resource> outputEntry : outputs.entries()) {
				String path = outputEntry.getKey();
				Resource resource = outputEntry.getValue();

				// Strip leading slashes from path
				while (path.startsWith("/"))
					path = path.substring(1);

				switch (resource.getType()) {
					case Folder :
						if (!path.isEmpty()) {
							IFolder folder = project.getFolder(path);
							FileUtils.mkdirs(folder, progress.split(1, SubMonitor.SUPPRESS_ALL_LABELS));
						}
						break;
					case File :
						IFile file = project.getFile(path);
						FileUtils.mkdirs(file.getParent(), progress.split(1, SubMonitor.SUPPRESS_ALL_LABELS));
						try (InputStream in = resource.getContent()) {
							if (file.exists())
								file.setContents(in, 0, progress.split(1, SubMonitor.SUPPRESS_NONE));
							else
								file.create(in, 0, progress.split(1, SubMonitor.SUPPRESS_NONE));
							file.setCharset(resource.getTextEncoding(), progress.split(1));
						}
						break;
					default :
						throw new IllegalArgumentException("Unknown resource type " + resource.getType());
				}
			}
		} catch (Exception e) {
			String message = MessageFormat.format("Error generating project contents from template \"{0}\": {1}",
				template != null ? template.getName() : "<null>", e.getMessage());
			throw new IOException(message);
		}
	}

	protected IJavaProject getServiceJavaProject() {
		return (IJavaProject) getCreatedElement();
	}

	protected abstract Map<String, String> getProjectTemplateParams();

	protected abstract Template getServiceTemplate();
	
	protected abstract Template getImplTemplate();
	
	protected abstract IJavaProject getImplJavaProject();

	protected abstract Template getConsumerTemplate();
	
	protected abstract IJavaProject getConsumerJavaProject();

	protected boolean generateProjectContent(IJavaProject javaProject, Template template,
			Map<String, String> templateParams) {
		boolean result = true;
		try {
			// Run using the progress bar from the wizard dialog
			getContainer().run(false, false, monitor -> {
				try {
					// Make changes to the project
					final IWorkspaceRunnable op = monitor1 -> {
						try {
							generateProjectContent(javaProject.getProject(), template, templateParams, monitor1);
						} catch (Exception e) {
							throw new CoreException(new Status(IStatus.ERROR, Plugin.PLUGIN_ID, 0,
									"Error generating project content from template", e));
						}
					};
					javaProject.getProject().getWorkspace().run(op, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				}
			});
			result = true;
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			final IStatus status;
			if (targetException instanceof CoreException) {
				status = ((CoreException) targetException).getStatus();
			} else {
				status = new Status(IStatus.ERROR, Plugin.PLUGIN_ID, 0, "Error creating bnd project contents",
						targetException);
			}
			logger.logStatus(status);
			ErrorDialog.openError(getShell(), "Error", "Error creating bnd project", status);
			result = false;
		} catch (InterruptedException e) {
			// Shouldn't happen
		}
		return result;
	}

	protected void configureServiceProject(IJavaProject serviceJavaProject, Map<String, String> templateParams) {
		// get bnd.bnd file
		IFile bndFile = serviceJavaProject.getProject().getFile(Project.BNDFILE);

		// check to see if we need to add marker about missing workspace
		try {
			if (!Central.hasWorkspaceDirectory()) {
				IResource markerTarget = bndFile;
				if (markerTarget == null || markerTarget.getType() != IResource.FILE || !markerTarget.exists())
					markerTarget = serviceJavaProject.getProject();
				IMarker marker = markerTarget.createMarker(BndtoolsConstants.MARKER_BND_MISSING_WORKSPACE);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				marker.setAttribute(IMarker.MESSAGE,
						"Missing Bnd Workspace. Create a new workspace with the 'New Bnd OSGi Workspace' wizard.");
				marker.setAttribute(BuildErrorDetailsHandler.PROP_HAS_RESOLUTIONS, true);
				marker.setAttribute("$bndType", BndtoolsConstants.MARKER_BND_MISSING_WORKSPACE);
			} else {
				Central.getWorkspace().refreshProjects();
			}
		} catch (Exception e1) {
			// ignore exceptions, this is best effort to help new users
		}

		// Open the protofile
		IFile protoFile = serviceJavaProject.getProject().getFile(
				"protofiles/" + templateParams.get(ProjectTemplateParam.SERVICE_NAME.getString()) + ".proto");
		try {
			if (protoFile.exists())
				IDE.openEditor(getWorkbench().getActiveWorkbenchWindow().getActivePage(), protoFile);
		} catch (PartInitException e) {
			ErrorDialog.openError(getShell(), "Error", null,
					new Status(IStatus.ERROR, Plugin.PLUGIN_ID, 0,
							MessageFormat.format("Failed to open project descriptor file {0} in the editor.",
									bndFile.getFullPath().toString()),
							e));
		}

	}
	@Override
	public boolean performFinish() {
		boolean result = super.performFinish();
		if (result) {
			final Map<String, String> templateParams = getProjectTemplateParams();
			final IJavaProject serviceJavaProject = getServiceJavaProject();
			result = generateProjectContent(getServiceJavaProject(), getServiceTemplate(), templateParams);
			if (result) {
				configureServiceProject(serviceJavaProject, templateParams);
			}
			// Now get impl Template
			Template implTemplate = getImplTemplate();
			IJavaProject implJavaProject = getImplJavaProject();
			if (implTemplate != null) {
				result = generateProjectContent(implJavaProject, implTemplate, templateParams);
				if (result) {
					configureImplProject(implJavaProject, templateParams);
				}
			}
			// Now get consumer Template
			Template consumerTemplate = getConsumerTemplate();
			IJavaProject consumerJavaProject = getConsumerJavaProject();
			if (consumerTemplate != null) {
				result = generateProjectContent(consumerJavaProject, consumerTemplate, templateParams);
				if (result) {
					configureConsumerProject(consumerJavaProject, templateParams);
				}
			}

		}
		return result;
	}

	protected void configureImplProject(IJavaProject implJavaProject, Map<String, String> templateParams) {
		// by default do nothing
	}
	
	protected void configureConsumerProject(IJavaProject implJavaProject, Map<String, String> templateParams) {
		// by default do nothing
	}

}
