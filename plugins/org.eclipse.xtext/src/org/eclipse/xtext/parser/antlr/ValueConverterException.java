/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.parser.antlr;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.parsetree.AbstractNode;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings("serial")
public class ValueConverterException extends RecognitionException {

	private final AbstractNode node;
	
	public ValueConverterException(AbstractNode node, Exception cause) {
		super();
		this.node = node;
		initCause(cause);
	}

	public AbstractNode getNode() {
		return node;
	}
}
