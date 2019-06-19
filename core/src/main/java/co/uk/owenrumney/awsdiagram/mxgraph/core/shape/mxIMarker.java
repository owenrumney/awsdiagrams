package co.uk.owenrumney.awsdiagram.mxgraph.core.shape;

import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxGraphics2DCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxPoint;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

public interface mxIMarker
{
	/**
	 * 
	 */
	mxPoint paintMarker(mxGraphics2DCanvas canvas, mxCellState state, String type,
                        mxPoint pe, double nx, double ny, double size, boolean source);

}
