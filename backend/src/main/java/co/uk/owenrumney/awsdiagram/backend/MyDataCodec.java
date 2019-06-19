package co.uk.owenrumney.awsdiagram.backend;

import java.lang.reflect.Field;

import javax.swing.JFrame;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import co.uk.owenrumney.awsdiagram.backend.MyData.MyEnum;
import co.uk.owenrumney.awsdiagram.mxgraph.core.io.mxCodec;
import co.uk.owenrumney.awsdiagram.mxgraph.core.io.mxCodecRegistry;
import co.uk.owenrumney.awsdiagram.mxgraph.core.io.mxObjectCodec;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxDomUtils;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxUtils;
import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxGraph;

public class MyDataCodec extends JFrame
{

	public static void main(String[] args)
	{
		// Overriden methods are required to support enums
		mxCodecRegistry.addPackage("com.mxgraph.examples");
		mxCodecRegistry.register(new mxObjectCodec(new MyData())
		{
			protected boolean isPrimitiveValue(Object value)
			{
				System.out.println("value.getClass().isEnum()="
						+ value.getClass().isEnum());

				return super.isPrimitiveValue(value)
						|| value.getClass().isEnum();
			}

			protected void setFieldValue(Object obj, String fieldname,
					Object value)
			{
				System.out.println("setFieldValue: " + value);
				Field field = getField(obj, fieldname);

				if (field.getType().isEnum())
				{
					Object[] c = field.getType().getEnumConstants();

					for (int i = 0; i < c.length; i++)
					{
						if (c[i].toString().equals(value))
						{
							value = c[i];
							break;
						}
					}
				}

				super.setFieldValue(obj, fieldname, value);
			}
		});

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.insertVertex(parent, null, new MyData(MyEnum.TWO), 20, 20, 80, 30);

		mxCodec codec = new mxCodec();
		Node node = codec.encode(graph.getModel());
		System.out.println("xml=" + mxUtils.getPrettyXml(node));

		Document doc = mxDomUtils.createDocument();
		doc.appendChild(doc.importNode(node, true));
		codec = new mxCodec(doc);
		Object model = codec.decode(node);

		codec = new mxCodec();
		node = codec.encode(model);

		System.out.println("xml2=" + mxUtils.getPrettyXml(node));
	}

}
