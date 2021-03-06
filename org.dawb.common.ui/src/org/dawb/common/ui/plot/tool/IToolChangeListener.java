package org.dawb.common.ui.plot.tool;

import java.util.EventListener;

public interface IToolChangeListener extends EventListener {

	/**
	 * If the user selects a different tool, this will be called.
	 * @param evt
	 */
	public void toolChanged(ToolChangeEvent evt);
}
