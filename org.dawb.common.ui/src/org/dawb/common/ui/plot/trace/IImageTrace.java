package org.dawb.common.ui.plot.trace;

import java.util.List;

import org.dawb.common.services.HistogramBound;
import org.dawb.common.services.ImageServiceBean;
import org.dawb.common.services.ImageServiceBean.HistoType;
import org.dawb.common.services.ImageServiceBean.ImageOrigin;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.roi.ROIBase;

/**
 * Interface used for the plotting system to plot images.
 * 
 * In the LightWeightPlotter this is called ImageTrace.
 * 
 * @author fcp94556
 * 
   Histogramming Explanation
   ---------------------------
   Image intensity distribution:

                ++----------------------**---------------
                +                      *  *              
                ++                    *    *             
                |                     *    *             
                ++                    *     *            
                *                    *       *            
                +*                   *       *            
                |*                  *        *            
                +*                  *        *           
                |                  *          *         
                ++                 *          *          
                |                  *           *        
                ++                 *           *        
                |                 *            *        
                ++                *            *       
                                 *              *      
        Min Cut           Min    *              *      Max                     Max cut
 Red <- |   (min colour)  |    (color range, palette)  |      (max color)      | -> Blue
                                *                 *  
                |              *        +         *  
----------------++------------**--------+----------**----+---------------**+---------------++
              
 */
public interface IImageTrace extends IPaletteTrace {

		
	public enum DownsampleType {
		
		POINT(0, "Point, top left of bin"),  // select corner point of bin
		MEAN(1, "Mean value of bin"),   // mean average over bin
		MAXIMUM(2, "Maximum value of bin"), // use maximum value in bin
		MINIMUM(3, "Minimum value of bin"); // use minimum value in bin
		
		private String label;
		private int index;
		
		DownsampleType(int index, String label) {
			this.index = index;
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
		public int getIndex() {
			return index;
		}
	}

	/**
	 * Default is TOP_LEFT unlike normal plotting
	 * @return
	 */
	public ImageOrigin getImageOrigin();
	
	/**
	 * Repaints the axes and the image to the new origin.
	 * @param origin
	 */
	public void setImageOrigin(final ImageOrigin origin);
	
	
	/**
	 * Call to set image data
	 * @param image
	 * @param axes - may be null
	 * @param performAutoScale - true to rescale to new selection, otherwise keeps last axis position.
	 */
	public void setData(final AbstractDataset image, List<AbstractDataset> axes, boolean performAutoScale);
	
	/**
	 * Change the axes without changing the underlying data.
	 * @param axes
	 * @param performAutoScale
	 */
	public void setAxes(List<AbstractDataset> axes, boolean performAutoScale);
	
	/**
	 * @return the axes if they were set - may be null
	 */
	public List<AbstractDataset> getAxes();

	/**
	 * The min intensity for generating the image
	 * @return
	 */
	public Number getMin();
	
	/**
	 * The min intensity for generating the image
	 * @return
	 */
	public void setMin(Number min);
	
	/**
	 * The max intensity for generating the image
	 * @return
	 */
	public Number getMax();
	
	/**
	 * The max intensity for generating the image
	 * @return
	 */
	public void setMax(Number max);
	
	
	/**
	 * 
	 * @return the down-sample type being used for plotting less data
	 * than received.
	 */
	public DownsampleType getDownsampleType();
	
	/**
	 * Change the down-sample type, will also refresh the UI.
	 * @param type
	 */
	public void setDownsampleType(DownsampleType type);
	
	/**
	 * @param rehisto image when run
	 */
	public void rehistogram();
	
	/**
	 * return the HistoType being used
	 * @return
	 */
	public HistoType getHistoType();
	
	/**
	 * Sets the histo type.
	 */
	public void setHistoType(HistoType type);

	/**
	 * You may set the image not to redraw images during updating a number of 
	 * settings for efficiency reasons. Do this in a try{} finally{} block to 
	 * avoid it being left off.
	 * 
	 * @param b
	 */
	void setImageUpdateActive(boolean b);
	
	/**
	 * Call to redraw the image, normally the same as repaint on Figure.
	 */
	public void repaint();
	
	
	/**
	 * 
	 * @return the current downsampled AbstractDataset being used to draw the image.
	 */
	public AbstractDataset getDownsampled();

	/**
	 * 
	 * @return the current downsampled mask or null if there is no mask.
	 */
	public AbstractDataset getDownsampledMask();

	
	/**
	 * @return the bin side in pixels which will be used when drawing the image. 
               The bin is a square of side = the return value.
	 */
	public int getDownsampleBin();
	
	/**
	 * Gets the min cut, a RGB and a bound.
	 * @return
	 */
	public HistogramBound getMinCut();
	
	/**
	 * Gets the min cut, a RGB and a bound.
	 * @return
	 */
	public void setMinCut(HistogramBound bound);

	/**
	 * Gets the max cut, a RGB and a bound.
	 * @return
	 */
	public HistogramBound getMaxCut();

	/**
	 * Gets the min cut, a RGB and a bound.
	 * @return
	 */
	public void setMaxCut(HistogramBound bound);
	
	/**
	 * Gets the Nan cut
	 * @return
	 */
	public HistogramBound getNanBound();
	
	/**
	 * Gets the Nan cut
	 * @return
	 */
	public void setNanBound(HistogramBound bound);

	/**
	 * The masking dataset of there is one, normally null.
	 * false to mask the pixel, true to leave as is.
	 * 
	 * @return
	 */
	public AbstractDataset getMask();
	
	/**
	 * The masking dataset of there is one, normally null.
	 * false to mask the pixel, true to leave as is.
	 * 
	 * If you don't send a BooleanDataset the system may attempt a cast
	 * and throw an exception.
	 * 
	 * @return
	 */
	public void setMask(AbstractDataset bd);

	
	/**
	 * If the axis data set has been set, this method will return 
	 * a selection region in the coordinates of the axes labels rather
	 * than the indices. If no axes are set, then the original roi is
	 * returned.
	 * 
	 * @return ROI in label coordinates. This roi is not that useful after it
	 *         is created. The data processing needs rois with indices.
	 *         
	 * @throws Exception if the roi could not be transformed or the roi type
	 *         is unknown.
	 */
	public ROIBase getRegionInAxisCoordinates(final ROIBase roi) throws Exception;
	
	
	/**
	 * If the axis data set has been set, this method will return 
	 * a point in the coordinates of the axes labels rather
	 * than the indices. If no axes are set, then the original point is
	 * returned.
	 * 
	 * @return point in label coordinates. 
	 * 
	 * @throws Exception if the point could not be transformed or the point type
	 *         is unknown.
	 */
	public double[] getPointInAxisCoordinates(final double[] point) throws Exception;

	/**
	 * Is the imagetrace set to rescale the histogram when a new image update occurs
	 * @return the current state.
	 */
	public boolean isRescaleHistogram();

	/**
	 * Set wheter an update to the image trace triggers a rehistogram
	 * @param rescaleHistogram
	 */
	public void setRescaleHistogram(boolean rescaleHistogram);
}
