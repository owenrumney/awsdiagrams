package co.uk.owenrumney.awsdiagram.backend;

import java.io.IOException;

import javax.swing.JFrame;

import org.w3c.dom.Document;

import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxICanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.canvas.mxSvgCanvas;
import co.uk.owenrumney.awsdiagram.mxgraph.core.io.mxCodec;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxCellRenderer;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxCellRenderer.CanvasFactory;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxDomUtils;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxUtils;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxXmlUtils;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxGraph;

/**
 * Usage: Xml2Svg infile outfile where infile is the path to the input XML file
 * (with an mxGraphModel) and outfile is the path to the output SVG file.
 */
public class Xml2Svg extends JFrame
{
	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			System.out.println("Usage: Xml2Svg infile outfile");
		}
		else
		{
			try
			{
				mxGraph graph = new mxGraph();
	
				// Parses XML into graph
				Document doc = mxXmlUtils.parseXml(mxUtils.readFile(args[0]));
				mxCodec codec = new mxCodec(doc);
				codec.decode(doc.getDocumentElement(), graph.getModel());
	
				// Renders graph to SVG
				mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer.drawCells(graph,
						null, 1, null, new CanvasFactory()
						{
							public mxICanvas createCanvas(int width, int height)
							{
								return new mxSvgCanvas(mxDomUtils
										.createSvgDocument(width, height));
							}
						});
	
				mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()), args[1]);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
