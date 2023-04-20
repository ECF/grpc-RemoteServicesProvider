package org.eclipse.ecf.bndtools.grpc.ui;

import org.bndtools.core.ui.wizards.service.NewBndServiceWizard;
import org.bndtools.core.ui.wizards.service.NewBndServiceWizardPageOne;
import org.bndtools.core.ui.wizards.service.NewBndServiceWizardPageTwo;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class NewBndGrpcServiceWizardFactory implements IExecutableExtension, IExecutableExtensionFactory {

	private IConfigurationElement	config;
	private String					propertyName;
	private Object					data;

	@SuppressWarnings("restriction")
	@Override
	public NewBndServiceWizard create() throws CoreException {
		String templateName = null;
		if (this.data instanceof String) {
			templateName = (String) this.data;
		}
		NewBndServiceWizardPageOne pageOne = new NewBndServiceWizardPageOne(templateName);
		NewBndServiceWizard wizard = new NewBndServiceWizard(pageOne, new NewBndServiceWizardPageTwo(pageOne),
			templateName);
		wizard.setInitializationData(config, propertyName, data);
		return wizard;
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
		throws CoreException {
		this.config = config;
		this.propertyName = propertyName;
		this.data = data;
	}
}
