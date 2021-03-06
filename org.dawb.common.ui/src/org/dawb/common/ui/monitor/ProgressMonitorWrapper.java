/*
 * Copyright (c) 2012 European Synchrotron Radiation Facility,
 *                    Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 

package org.dawb.common.ui.monitor;

import org.eclipse.core.runtime.IProgressMonitor;
import uk.ac.gda.monitor.IMonitor;


/**
 *
 */
public class ProgressMonitorWrapper implements IMonitor {

	private IProgressMonitor monitor;

	/**
	 * @param monitor
	 */
	public ProgressMonitorWrapper(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public boolean isCancelled() {
		if (monitor!=null) return monitor.isCanceled();
		return false;
	}

	@Override
	public void worked(int amount) {
		if (monitor!=null) monitor.worked(amount);
	}

}
