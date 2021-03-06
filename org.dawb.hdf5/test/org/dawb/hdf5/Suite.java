/*
 * Copyright (c) 2012 European Synchrotron Radiation Facility,
 *                    Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 
package org.dawb.hdf5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(org.junit.runners.Suite.class)
@SuiteClasses( { 
	
	org.dawb.hdf5.Hdf5Test.class, 
	org.dawb.hdf5.Hdf5ThreadTest.class 
	
} )
public class Suite {
}
