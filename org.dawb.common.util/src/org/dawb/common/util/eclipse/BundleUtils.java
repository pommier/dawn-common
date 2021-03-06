/*
 * Copyright (c) 2012 European Synchrotron Radiation Facility,
 *                    Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 
package org.dawb.common.util.eclipse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 *   BundleUtils
 *   
 *   Assumes that this class can be used before the Logger is loaded, therefore do not put Logging in here.
 *
 *   @author gerring
 *   @date Aug 2, 2010
 *   @project org.dawb.common.util
 **/
public class BundleUtils {
	
	
	public static File getBundleLocation(final String bundleName) throws IOException {
		final Bundle bundle = Platform.getBundle(bundleName);
		return FileLocator.getBundleFile(bundle);
	}
	
	/**
	 * Get the java.io.File location of a bundle.
	 * @param bundleName
	 * @return
	 * @throws Exception 
	 */
	public static File getBundleLocation(final Bundle bundle) throws IOException {
		return FileLocator.getBundleFile(bundle);
	}

	/**
	 * Get the bundle path using eclipse.home.location not loading the bundle.
	 * @param bundleName
	 * @return
	 */
	public static File getBundlePathNoLoading(String bundleName) {
		return new File(new File(getEclipseHome(), "plugins"), bundleName);
	}
	
	/**
	 * Gets eclipse home in debug and in deployed application mode.
	 * @return
	 */
	public static String getEclipseHome() {
		File hDirectory;
		try {
			URI u = new URI(System.getProperty("eclipse.home.location"));
			hDirectory = new File(u);
		} catch (URISyntaxException e) {
			return null;
		}

		String path = hDirectory.getName();
		if (path.equals("plugins") || path.equals("bundles")) {
			path = hDirectory.getParentFile().getParentFile().getAbsolutePath();
		} else{
			path = hDirectory.getAbsolutePath();
		}
        return path;
	}
}
