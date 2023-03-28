package org.eclipse.ecf.bndtools.grpc.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bndtools.templating.Template;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ecf.bndtools.grpc.ui.shared.ISkippingWizard;
import org.eclipse.ecf.bndtools.grpc.ui.shared.JavaProjectUtils;
import org.eclipse.ecf.bndtools.grpc.ui.shared.TemplateParamsWizardPage;
import org.eclipse.ecf.bndtools.grpc.ui.utils.ProjectTemplateParam;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;

import bndtools.Plugin;

class NewBndServicetWizard extends AbstractNewBndServiceWizard implements ISkippingWizard {

	public static final String			DEFAULT_TEMPLATE_ENGINE	= "stringtemplate";		//$NON-NLS-1$
	public static final String			DEFAULT_BUNDLE_VERSION	= "0.0.0.${tstamp}";	//$NON-NLS-1$
	public static final String			EMPTY_TEMPLATE_NAME		= "\u00abEmpty\u00bb";

	private NewBndServiceWizardPageOne pageOne;
	private TemplateParamsWizardPage paramsPage;
	
	private NewBndServiceWizardNonApiProjectPage implPage;
	private NewBndServiceWizardNonApiProjectPage consumerPage;
	
	NewBndServicetWizard(final NewBndServiceWizardPageOne pageOne, final NewJavaProjectWizardPageTwo pageTwo) {
		super(pageOne, pageTwo);
		this.pageOne = pageOne;
		// setup params page
		this.paramsPage = new TemplateParamsWizardPage(ProjectTemplateParam.valueStrings());
		// setup impl page to use .impl project suffix
		this.implPage = NewBndServiceWizardNonApiProjectPage.createNonApiProjectPage(pageOne, ServiceProjectInfo.DEFAULT_IMPL_SUFFIX);
		this.consumerPage = NewBndServiceWizardNonApiProjectPage.createNonApiProjectPage(pageOne, ServiceProjectInfo.DEFAULT_CONSUMER_SUFFIX);
	}

	@SuppressWarnings("restriction")
	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		setDefaultPageImageDescriptor(ImageDescriptor.createFromURL(Plugin.getDefault()
			.getBundle()
			.getEntry("icons/bndtools-wizban.png")));
	}

	@SuppressWarnings("restriction")
	@Override
	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {

		try {
			SubMonitor submonitor = SubMonitor.convert(monitor,"Creating service projects", 100);
			super.finishPage(submonitor.split(33)); 
			implPage.performFinish(submonitor.split(33));
			consumerPage.performFinish(submonitor.split(33));
		} finally {
			monitor.done();
		}
	}

	@Override
	public void addPages() {
		addPage(pageOne);
		addPage(paramsPage);
	}

	@Override
	protected Map<String, String> getProjectTemplateParams() {
		Map<ProjectTemplateParam, String> params = new HashMap<>();
		// Service Name
		params.put(ProjectTemplateParam.SERVICE_NAME, pageOne.getServiceName());
		// Package Name
		String packageName = pageOne.getPackageName();
		params.put(ProjectTemplateParam.BASE_PACKAGE_NAME, packageName);
		// 
		params.put(ProjectTemplateParam.API_PACKAGE, packageName);
		// Package Dir
		String packageDir = packageName.replace('.', '/');
		params.put(ProjectTemplateParam.BASE_PACKAGE_DIR, packageDir);

		// Version
		params.put(ProjectTemplateParam.VERSION, DEFAULT_BUNDLE_VERSION);

		// Source Folders
		IJavaProject javaProject = pageTwo.getJavaProject();
		Map<String, String> sourceOutputLocations = JavaProjectUtils.getSourceOutputLocations(javaProject);
		int nr = 1;
		for (Map.Entry<String, String> entry : sourceOutputLocations.entrySet()) {
			String src = entry.getKey();
			String bin = entry.getValue();

			if (nr == 1) {
				params.put(ProjectTemplateParam.SRC_DIR, src);
				params.put(ProjectTemplateParam.BIN_DIR, bin);
				nr = 2;
			} else if (nr == 2) {
				params.put(ProjectTemplateParam.TEST_SRC_DIR, src);
				params.put(ProjectTemplateParam.TEST_BIN_DIR, bin);
				nr = 2;
			} else {
				// if for some crazy reason we end up with more than 2 paths, we
				// log them in
				// extension properties (we cannot write comments) but this
				// should never happen
				// anyway since the second page will not complete if there are
				// not exactly 2 paths
				// so this could only happen if someone adds another page (that
				// changes them again)

				// TODO
				// model.genericSet("X-WARN-" + nr, "Ignoring source path " +
				// src + " -> " + bin);
				nr++;
			}
		}

		try {
			String javaLevel = JavaProjectUtils.getJavaLevel(javaProject);
			if (javaLevel != null)
				params.put(ProjectTemplateParam.JAVA_LEVEL, javaLevel);
		} catch (Exception e) {
			Plugin.getDefault()
				.getLog()
				.log(new Status(IStatus.ERROR, Plugin.PLUGIN_ID, 0,
					String.format("Unable to get Java level for project %s", javaProject.getProject()
						.getName()),
					e));
		}

		Map<String, String> params_ = new HashMap<>();
		for (Entry<ProjectTemplateParam, String> entry : params.entrySet())
			params_.put(entry.getKey()
				.getString(), entry.getValue());

		Map<String, String> editedParams = paramsPage.getValues();
		for (Entry<String, String> editedEntry : editedParams.entrySet()) {
			params_.put(editedEntry.getKey(), editedEntry.getValue());
		}
		return params_;
	}


	@Override
	public IWizardPage getUnfilteredPreviousPage(IWizardPage page) {
		return super.getPreviousPage(page);
	}

	@Override
	public IWizardPage getUnfilteredNextPage(IWizardPage page) {
		return super.getNextPage(page);
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		return ISkippingWizard.super.getPreviousPage(page);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return ISkippingWizard.super.getNextPage(page);
	}

	@Override
	protected Template getServiceTemplate() {
		return pageOne.getTemplate();
	}

	@Override
	protected Template getImplTemplate() {
		return pageOne.getImplTemplate();
	}

	@Override
	protected IJavaProject getImplJavaProject() {
		return implPage.getJavaProject();
	}

	@Override
	protected Template getConsumerTemplate() {
		return pageOne.getConsumerTemplate();
	}

	@Override
	protected IJavaProject getConsumerJavaProject() {
		return consumerPage.getJavaProject();
	}

}
