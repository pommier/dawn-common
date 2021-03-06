/*
 * Copyright (c) 2012 European Synchrotron Radiation Facility,
 *                    Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */ 
package org.dawb.common.ui.plot.trace;

import java.util.EventObject;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 * Note the source to this event can either be the ITrace
 * or the plotting system.
 * 
 * @author fcp94556
 *
 */
public class TraceWillPlotEvent extends EventObject {

	private AbstractDataset image=null;
	private boolean         newImageDataSet = false;
	
	private List<AbstractDataset> axes=null;
	private AbstractDataset xLineData=null, yLineData=null;
	private boolean         newLineDataSet = false;
	
	private final boolean applyStraightAway;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6103365099398209061L;

	public TraceWillPlotEvent(Object source, boolean applyStraightAway) {
		super(source);
		this.applyStraightAway = applyStraightAway;
		if (source instanceof IImageTrace) {
			IImageTrace it = (IImageTrace)source;
			image = it.getData();
			axes  = it.getAxes();
		}
		
		if (source instanceof ILineTrace) {
			ILineTrace lt = (ILineTrace)source;
			this.xLineData = lt.getXData();
			this.yLineData = lt.getYData();
		}

	}

	public AbstractDataset getImage() {
		return image;
	}

	public void setImageData(AbstractDataset image, List<AbstractDataset> axes) {
		this.image = image;
		this.axes = axes;
		newImageDataSet = true;
		
		if (applyStraightAway && source instanceof IImageTrace) {
			IImageTrace it = (IImageTrace)source;
            it.setData(image, axes, false);		
		}
	}

	public List<AbstractDataset> getAxes() {
		return axes;
	}

	public AbstractDataset getXData() {
		return xLineData;
	}
	public AbstractDataset getYData() {
		return yLineData;
	}

	public void setLineData(AbstractDataset xLineData, AbstractDataset yLineData) {
		this.xLineData = xLineData;
		this.yLineData = yLineData;
		newLineDataSet = true;
		
		if (applyStraightAway && source instanceof ILineTrace) {
			ILineTrace lt = (ILineTrace)source;
			lt.setData(xLineData, yLineData);
		}
	}

	public boolean isNewImageDataSet() {
		return newImageDataSet;
	}

	public boolean isNewLineDataSet() {
		return newLineDataSet;
	}
	

}
