/*
 * generated by Xtext
 */
package org.eclipse.xtext.testlanguages.fileAware.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.testlanguages.fileAware.FileAwareTestLanguageRuntimeModule;
import org.eclipse.xtext.testlanguages.fileAware.FileAwareTestLanguageStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class FileAwareTestLanguageIdeSetup extends FileAwareTestLanguageStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new FileAwareTestLanguageRuntimeModule(), new FileAwareTestLanguageIdeModule()));
	}
	
}
