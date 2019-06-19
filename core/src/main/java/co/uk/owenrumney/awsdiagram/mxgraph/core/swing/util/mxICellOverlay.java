package co.uk.owenrumney.awsdiagram.mxgraph.core.swing.util;

import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxRectangle;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

public interface mxICellOverlay
{

	/**
	 * 
	 */
	mxRectangle getBounds(mxCellState state);

}
