/*******************************************************************************
 * Copyright (c) 2018 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xtext.generator

import org.eclipse.xtend2.lib.StringConcatenation
import org.eclipse.xtext.xtext.generator.AbstractGeneratorFragmentTests
import org.junit.Test

/**
 * @author Holger Schill - Initial contribution and API
 * @since 2.14
 */
class ResourceDescriptionStrategyFragmentTests extends AbstractGeneratorFragmentTests {

	@Test
	def testGenerateNothing() {
		val fragment = initializeFragmentWithGrammarFromString('''
			grammar org.xtext.Foo with org.eclipse.xtext.common.Terminals
			generate foo "http://org.xtext/foo"
			Model: rules+=Rule;
			Rule: name=ID;
		''')
		val exportedRules = fragment.getExportedRulesFromGrammar
		assertTrue(exportedRules.empty)
		assertFalse(fragment.shouldGenerate(exportedRules))
	}

	@Test
	def testGenerate() {
		val fragment = initializeFragmentWithGrammarFromString('''
			grammar org.xtext.Foo with org.eclipse.xtext.common.Terminals
			generate foo "http://org.xtext/foo"
			Model: rules+=Rule;
			@Exported
			Rule: name=ID;
		''')
	
		val exportedRules = fragment.getExportedRulesFromGrammar
		assertFalse(exportedRules.empty)
		assertTrue(fragment.shouldGenerate(exportedRules))
		val result = fragment.generateSuperResourceDescriptionStrategyContent(exportedRules)
		val stringConcat = new StringConcatenation("\n")
		stringConcat.append(result)
		assertEquals('''
			public class FooDefaultResourceDescriptionStrategy extends org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy {
				public boolean createEObjectDescriptions(interface org.eclipse.emf.ecore.EObject eObject, interface org.eclipse.xtext.util.IAcceptor<interface org.eclipse.xtext.resource.IEObjectDescription> acceptor) {
					if(eObject instanceof org.xtext.foo.Rule) {
						return createEObjectDescriptionsForRule(eObject, acceptor);
					}
					return true;
				}
			
				protected boolean createEObjectDescriptionsForRule(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
					return super.createEObjectDescriptions(eObject, acceptor);
				}
			}
		'''.toString, stringConcat.toString)
	}
	
	

}
