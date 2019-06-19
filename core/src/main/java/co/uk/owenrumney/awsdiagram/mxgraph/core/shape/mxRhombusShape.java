package co.uk.owenrumney.awsdiagram.mxgraph.core.shape;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxGraphics2DCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

public class mxRhombusShape extends mxBasicShape
{

	/**
	 * 
	 */
	public Shape createShape(mxGraphics2DCanvas canvas, mxCellState state)
	{
		Rectangle temp = state.getRectangle();
		int x = temp.x;
		int y = temp.y;
		int w = temp.width;
		int h = temp.height;
		int halfWidth = w / 2;
		int halfHeight = h / 2;

		Polygon rhombus = new Polygon();
		rhombus.addPoint(x + halfWidth, y);
		rhombus.addPoint(x + w, y + halfHeight);
		rhombus.addPoint(x + halfWidth, y + h);
		rhombus.addPoint(x, y + halfHeight);

		return rhombus;
	}

}
