package co.uk.owenrumney.awsdiagram.mxgraph.core.shape;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxGraphics2DCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

public class mxCloudShape extends mxBasicShape
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
		GeneralPath path = new GeneralPath();

		path.moveTo((float) (x + 0.25 * w), (float) (y + 0.25 * h));
		path.curveTo((float) (x + 0.05 * w), (float) (y + 0.25 * h), x,
				(float) (y + 0.5 * h), (float) (x + 0.16 * w),
				(float) (y + 0.55 * h));
		path.curveTo(x, (float) (y + 0.66 * h), (float) (x + 0.18 * w),
				(float) (y + 0.9 * h), (float) (x + 0.31 * w),
				(float) (y + 0.8 * h));
		path.curveTo((float) (x + 0.4 * w), (y + h), (float) (x + 0.7 * w),
				(y + h), (float) (x + 0.8 * w), (float) (y + 0.8 * h));
		path.curveTo((x + w), (float) (y + 0.8 * h), (x + w),
				(float) (y + 0.6 * h), (float) (x + 0.875 * w),
				(float) (y + 0.5 * h));
		path.curveTo((x + w), (float) (y + 0.3 * h), (float) (x + 0.8 * w),
				(float) (y + 0.1 * h), (float) (x + 0.625 * w),
				(float) (y + 0.2 * h));
		path.curveTo((float) (x + 0.5 * w), (float) (y + 0.05 * h),
				(float) (x + 0.3 * w), (float) (y + 0.05 * h),
				(float) (x + 0.25 * w), (float) (y + 0.25 * h));
		path.closePath();

		return path;
	}

}
