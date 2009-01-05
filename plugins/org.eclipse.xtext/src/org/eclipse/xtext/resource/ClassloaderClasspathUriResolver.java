/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.xtext.resource;

import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;

/**
 * A classpath URI resolver that looks up a resource in the classpath of the
 * context attribute.
 * 
 * @author Jan K�hnlein
 */
public class ClassloaderClasspathUriResolver implements IClasspathUriResolver {

    /**
     * Locates a resource using a classloader.
     * 
     * @param context
     *      the classloader to be used, or an object whose classloader is used.
     *      If null, the context classloader of the current thread is used.
     */
    public URI resolve(Object context, URI classpathUri) {
        if (context == null) {
            context = Thread.currentThread().getContextClassLoader();
        }
        if (context instanceof Class) {
            context = ((Class<?>)context).getClassLoader();
        }
        if (!(context instanceof ClassLoader)) {
        	context = context.getClass().getClassLoader();
        }
        ClassLoader classLoader = (ClassLoader) context;
        try {
            if (ClasspathUriUtil.isClasspathUri(classpathUri)) {
                return findResourceOnClasspath(classLoader, classpathUri);
            }
        } catch (Exception exc) {
            throw new ClasspathUriResolutionException(exc);
        }
        return classpathUri;
    }

    private URI findResourceOnClasspath(ClassLoader classLoader, URI classpathUri) throws URISyntaxException {
        String pathAsString = classpathUri.path();
        if (classpathUri.hasAbsolutePath()) {
            pathAsString = pathAsString.substring(1);
        }
        URL resource = classLoader.getResource(pathAsString);
        if (resource==null)
        	throw new IllegalStateException("Couldn't find resource on classpath. URI was '"+classpathUri+"'");
        URI fileUri = URI.createURI(resource.toURI().toString());
        return fileUri.appendFragment(classpathUri.fragment());
    }

}
