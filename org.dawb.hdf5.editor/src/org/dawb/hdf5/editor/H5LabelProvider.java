/*
 * Copyright (c) 2012 European Synchrotron Radiation Facility,
 *                    Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 
package org.dawb.hdf5.editor;

import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;
import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Group;
import ncsa.hdf.object.HObject;
import ncsa.hdf.object.h5.H5Datatype;

import org.dawb.hdf5.HierarchicalDataFactory;
import org.dawb.hdf5.HierarchicalDataUtils;
import org.dawb.hdf5.IHierarchicalDataFile;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H5LabelProvider extends ColumnLabelProvider implements ITableLabelProvider {

	private static final Logger logger = LoggerFactory.getLogger(H5LabelProvider.class);
	
	private IHierarchicalDataFile file;

	public H5LabelProvider(IHierarchicalDataFile file) {
		this.file = file;
	}
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * { "Name", "Class", "Dims", "Type", "Size" };
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		
		if (!(element instanceof TreeNode)) return null;
		final TreeNode node   = (TreeNode)element;
		
		HObject  object = (HObject)((DefaultMutableTreeNode)node).getUserObject();
		
		if (file.isClosed()) {
			try {
				this.file = HierarchicalDataFactory.getReader(file.getPath());
				
			} catch (Exception e) {
				logger.error("Cannot re-create link to "+file.getPath());
			}
		}
		
		try {
			object = file.getData(object.getFullName());
		} catch (Exception e) {
			logger.error("Cannot re-create link to recorde "+object.getFullName());
		}

		switch(columnIndex) {
		case 0:
			return object.getName();
		case 1:
			if (object instanceof Group) {
				return "Group";
			} else if (object instanceof Dataset) {
				return "Dataset";
			} else {
				return object.getClass().getConstructors()[0].getName();
			}
		case 2:
			if (object instanceof Dataset) {
				long[] shape;
				try {
					shape = HierarchicalDataUtils.getDims((Dataset)object);
				} catch (Exception e) {
					return "";
				}
			    if (shape==null) return "";
				return Arrays.toString(shape);
			}
			return "";
		case 3:
			if (object instanceof Dataset) {
				return ""+((Dataset)object).getDatatype().getDatatypeDescription();
			}
			return "";
		case 4:
			if (object instanceof Dataset) {
				long memSize;
				try {
					memSize = HierarchicalDataUtils.getSize((Dataset)object);
				} catch (Exception e) {
					return "";
				}
				if (memSize<0) return "";
				return formatSize(memSize);
			}
			return "";
		default:
			return null;
		}
	}

    private static final double BASE = 1024, KB = BASE, MB = KB*BASE, GB = MB*BASE;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static String formatSize(double size) {
        if(size >= GB) {
            return df.format(size/GB) + " GB";
        }
        if(size >= MB) {
            return df.format(size/MB) + " MB";
        }
        if(size >= KB) {
            return df.format(size/KB) + " KB";
        }
        return "" + (int)size + " bytes";
    }
    
    @Override
    public void dispose() {
    	try {
    	    this.file.close();
    	} catch (Exception ne) {
    		logger.error("Cannot close file "+file, ne);
    	}
    }

}
