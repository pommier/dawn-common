/*-
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dawb.common.services;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.RGB;
/**
 * Immutable HistogramBound class. Keep immutable so that static
 * bound defaults cannot be modified.
 */
public final class HistogramBound {

	public static final HistogramBound DEFAULT_MAXIMUM;
	public static final HistogramBound DEFAULT_MINIMUM;
	public static final HistogramBound DEFAULT_NAN;   

	static {
		/**
		 * DO NOT USE Display.getDefault().getSystemColor(..) here! This causes things to break because
		 * Display.getDefault() may be null at the point where statics are initiated.
		 */
		DEFAULT_MAXIMUM = new HistogramBound(Double.POSITIVE_INFINITY, ColorConstants.red.getRGB());
		DEFAULT_MINIMUM = new HistogramBound(Double.NEGATIVE_INFINITY, ColorConstants.blue.getRGB());
		DEFAULT_NAN     = new HistogramBound(Double.NaN,               ColorConstants.green.getRGB());
	}
	
	protected Number bound;
	protected RGB  color;
	
	/**
	 * RGB may be null. If it is the last three colours in the palette
	 * are used for the bound directly. For instance RGBs can be set to 
	 * null to avoid special cut bounds colors at all.
	 * 
	 * @param bound
	 * @param color
	 */
	public HistogramBound(Number bound, RGB color) {
		this.bound = bound;
		this.color = color;
	}
	public Number getBound() {
		return bound;
	}
	public RGB getColor() {
		return color;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bound == null) ? 0 : bound.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistogramBound other = (HistogramBound) obj;
		if (bound == null) {
			if (other.bound != null)
				return false;
		} else if (!bound.equals(other.bound))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final StringBuilder buf = new StringBuilder();
		buf.append(bound);
		buf.append(",");
		if (color!=null) {
			buf.append(color.red);
			buf.append(",");
			buf.append(color.green);
			buf.append(",");
			buf.append(color.blue);
			
		} else{
			buf.append("null");
		}
		return buf.toString();
	}

	public static HistogramBound fromString(String encoded) {
		
		if (encoded == null || "null".equals(encoded) || "null,null".equals(encoded) || "".equals(encoded)) {
			return null;
		}

		final String[] sa = encoded.split(",");
		
		Number bound = null;
		if (sa[0].equals("null")) {
			bound = null;
		} else {
			bound = Double.parseDouble(sa[0]);
		}
		
		RGB color = null;
		if (sa[1].equals("null")) {
			color = null;
		} else {
			color = new RGB(Integer.parseInt(sa[1]), Integer.parseInt(sa[2]), Integer.parseInt(sa[3]));
		}
		return new HistogramBound(bound, color);
	}
}

