/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.builtin.conversion;

import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.AbstractAnnotationBasedValueConverterService;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.conversion.impl.AbstractToStringConverter;
import org.eclipse.xtext.parsetree.AbstractNode;
import org.eclipse.xtext.service.Inject;

public class XtextBuiltInConverters extends AbstractAnnotationBasedValueConverterService {

	private Grammar g;
	
	@Inject
	public void setGrammar(IGrammarAccess ga) {
		this.g = ga.getGrammar();
	}
	
	@ValueConverter(rule = "ID")
	public IValueConverter<String> ID() {
		return new AbstractNullSafeConverter<String>() {
			@Override
			protected String internalToValue(String string, AbstractNode node) {
				return string.startsWith("^") ? string.substring(1) : string;
			}

			@Override
			protected String internalToString(String value) {
				if (GrammarUtil.getAllKeywords(g).contains(value)) {
					return "^"+value;
				}
				return (String) value;
			}
		};
	}

	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return new AbstractNullSafeConverter<String>() {
			protected String internalToValue(String string, AbstractNode node) {
				return string.substring(1, string.length() - 1);
			}

			@Override
			protected String internalToString(String value) {
				String v = (String) value;
				return v.indexOf('\'') == -1 ? "'" + value + "'": "\"" + value + "\"";
			}
		};
	}

	@ValueConverter(rule = "INT")
	public IValueConverter<Integer> INT() {
		return new AbstractToStringConverter<Integer>() {
			public Integer internalToValue(String string, AbstractNode node) {
				return Integer.valueOf(string);
			}
		};
	}

}
