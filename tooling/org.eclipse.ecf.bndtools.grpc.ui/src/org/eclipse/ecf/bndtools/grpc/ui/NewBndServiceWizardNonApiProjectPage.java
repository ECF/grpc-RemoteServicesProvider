package org.eclipse.ecf.bndtools.grpc.ui;

import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;

public class NewBndServiceWizardNonApiProjectPage extends NewBndProjectWizardPageTwo {

	NewBndServiceWizardNonApiProjectPage(NewJavaProjectWizardPageOne pageOne) {
		super(pageOne);
	}

	public static NewBndServiceWizardNonApiProjectPage createNonApiProjectPage(NewBndServiceWizardPageOne parentPageOne, String projectNameSuffix) {
		return new NewBndServiceWizardNonApiProjectPage(new NewBndServiceWizardPageOne(parentPageOne, projectNameSuffix));
	}
	
}
