package co.uk.owenrumney.awsdiagram.backend;

import java.awt.Graphics2D;
import java.io.FileOutputStream;

import com.mxpdf.text.Document;
import com.mxpdf.text.Rectangle;
import com.mxpdf.text.pdf.PdfContentByte;
import com.mxpdf.text.pdf.PdfWriter;
import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxGraphics2DCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxICanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxCell;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxCellRenderer;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxRectangle;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxCellRenderer.CanvasFactory;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxGraph;

// This example requires iText from http://www.lowagie.com/iText/
public class PdfExport
{
	public PdfExport() throws Exception
	{
		// Creates graph with model
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try
		{
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			mxCell v2 = (mxCell) graph.insertVertex(parent, null, "World!",
					240, 150, 80, 30);
			graph.insertEdge(parent, null, "e1", v1, v2);
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxRectangle bounds = graph.getGraphBounds();
		Document document = new Document(new Rectangle((float) (bounds
				.getWidth()), (float) (bounds.getHeight())));
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("example.pdf"));
		document.open();
		final PdfContentByte cb = writer.getDirectContent();

		mxGraphics2DCanvas canvas = (mxGraphics2DCanvas) mxCellRenderer
				.drawCells(graph, null, 1, null, new CanvasFactory()
				{
					public mxICanvas createCanvas(int width, int height)
					{
						Graphics2D g2 = cb.createGraphics(width, height);
						return new mxGraphics2DCanvas(g2);
					}
				});

		canvas.getGraphics().dispose();
		document.close();
	}

	public static void main(String[] args)
	{
		try
		{
			new PdfExport();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
