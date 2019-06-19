package co.uk.owenrumney.awsdiagram.mxgraph.core.shape;

import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxGraphics2DCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

public interface mxIShape
{
	/**
	 * 
	 */
	void paintShape(mxGraphics2DCanvas canvas, mxCellState state);

}
