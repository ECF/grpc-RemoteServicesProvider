package org.eclipse.ecf.bndtools.grpc.ui;

import org.eclipse.jdt.core.IClasspathEntry;

public class ServiceProjectInfo {

	public static final String DEFAULT_IMPL_SUFFIX = ".impl";
	public static final String DEFAULT_CONSUMER_SUFFIX = ".consumer";
	
	private final NewBndServiceWizardPageOne apiProjectPage;
	private final String projectNameSuffix;
	
	ServiceProjectInfo(NewBndServiceWizardPageOne apiProjectPage, String projectNameSuffix) {
		this.apiProjectPage = apiProjectPage;
		this.projectNameSuffix = projectNameSuffix;
	}
	
	public static ServiceProjectInfo createInfo(NewBndServiceWizardPageOne apiProjectPageOne, String projectSuffix) {
		return new ServiceProjectInfo(apiProjectPageOne, projectSuffix);
	}
	
	public static ServiceProjectInfo createImplInfo(NewBndServiceWizardPageOne apiProjectPageOne) {
		return new ServiceProjectInfo(apiProjectPageOne, DEFAULT_IMPL_SUFFIX);
	}

	public static ServiceProjectInfo createConsumerInfo(NewBndServiceWizardPageOne apiProjectPageOne) {
		return new ServiceProjectInfo(apiProjectPageOne, DEFAULT_CONSUMER_SUFFIX);
	}

	public String getProjectName() {
		return this.apiProjectPage.getProjectName() + this.projectNameSuffix;
	}
	
	public IClasspathEntry[] getDefaultClasspathEntries() {
		return apiProjectPage.getDefaultClasspathEntries();
	}

	public String getCompilerCompliance() {
		return apiProjectPage.getCompilerCompliance();
	}

	public NewBndServiceWizardPageOne getBndServiceProjectPageOne() {
		return apiProjectPage;
	}

}
