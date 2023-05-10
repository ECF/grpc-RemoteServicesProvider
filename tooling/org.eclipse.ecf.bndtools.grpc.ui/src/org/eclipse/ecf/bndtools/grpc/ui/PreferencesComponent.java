package org.eclipse.ecf.bndtools.grpc.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import aQute.bnd.header.Parameters;

@Component(immediate = true)
public class PreferencesComponent {

	@Reference
	org.eclipse.core.runtime.preferences.IPreferencesService prefs;

	private Parameters getRepoParameters(InputStream ins) {
		Parameters result = new Parameters();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(ins))) {
			while (reader.ready()) {
				result.mergeWith(new Parameters(reader.readLine()), true);
			}
		} catch (Exception e) {
			// Ignore if this fails
		}
		return result;
	}
	
	@Activate
	void activate(BundleContext bundleContext) {
		Parameters bndtoolsRepos = new Parameters();
		List<Bundle> bundles = Arrays.asList(bundleContext.getBundles()).stream()
				.filter(b -> b.getSymbolicName().equals("org.bndtools.templating.gitrepo")).collect(Collectors.toList());
		if (bundles.size() > 0) {
			Bundle b = bundles.get(0);
			Enumeration<URL> entries = b.findEntries("org/bndtools/templating/jgit", "initialrepos.txt", false);
			if (entries != null && entries.hasMoreElements()) {
				try {
					bndtoolsRepos = getRepoParameters(entries.nextElement().openStream());
				} catch (IOException e) {
					// Ignore if can't read it
				}
			}
		}
		Parameters ecfRepos = getRepoParameters(Activator.class.getResourceAsStream("ecfrepos.txt"));
		if (ecfRepos.size() > 0) {
			ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.bndtools.templating.gitrepo");
			store.setDefault("githubRepos", bndtoolsRepos.toString());
			bndtoolsRepos.mergeWith(ecfRepos, true);
			store.setValue("githubRepos", bndtoolsRepos.toString());
		}
	}
}
