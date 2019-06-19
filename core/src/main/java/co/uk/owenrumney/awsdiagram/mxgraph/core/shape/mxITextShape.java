/**
 * Copyright (c) 2010, Gaudenz Alder, David Benson
 */
package co.uk.owenrumney.awsdiagram.mxgraph.core.shape;

import java.util.Map;

import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxGraphics2DCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

public interface mxITextShape
{
	/**
	 * 
	 */
	void paintShape(mxGraphics2DCanvas canvas, String text, mxCellState state,
                    Map<String, Object> style);

}
