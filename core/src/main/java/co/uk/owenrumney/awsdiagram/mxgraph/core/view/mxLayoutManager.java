package co.uk.owenrumney.awsdiagram.mxgraph.core.view;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import co.uk.owenrumney.awsdiagram.mxgraph.core.layout.mxIGraphLayout;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxGraphModel;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxIGraphModel;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxGraphModel.mxChildChange;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxGraphModel.mxGeometryChange;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxGraphModel.mxRootChange;
import co.uk.owenrumney.awsdiagram.mxgraph.core.model.mxGraphModel.mxTerminalChange;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxEvent;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxEventObject;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxEventSource;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxUndoableEdit;
import co.uk.owenrumney.awsdiagram.mxgraph.core.util.mxUtils;

/**
 * Implements a layout manager that updates the layout for a given transaction.
 * The following example installs an automatic tree layout in a graph:
 * 
 * <code>
 * new mxLayoutManager(graph) {
 * 
 *   mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
 *   
 *   public mxIGraphLayout getLayout(Object parent)
 *   {
 *     if (graph.getModel().getChildCount(parent) > 0) {
 *       return layout;
 *     }
 *     return null;
 *   }
 * };
 * </code>
 * 
 * This class fires the following event:
 * 
 * mxEvent.LAYOUT_CELLS fires between begin- and endUpdate after all cells have
 * been layouted in layoutCells. The <code>cells</code> property contains all
 * cells that have been passed to layoutCells.
 */
public class mxLayoutManager extends mxEventSource
{

	/**
	 * Defines the type of the source or target terminal. The type is a string
	 * passed to mxCell.is to check if the rule applies to a cell.
	 */
	protected mxGraph graph;

	/**
	 * Optional string that specifies the value of the attribute to be passed
	 * to mxCell.is to check if the rule applies to a cell. Default is true.
	 */
	protected boolean enabled = true;

	/**
	 * Optional string that specifies the attributename to be passed to
	 * mxCell.is to check if the rule applies to a cell. Default is true.
	 */
	protected boolean bubbling = true;

	/**
	 * 
	 */
	protected mxIEventListener undoHandler = new mxIEventListener()
	{
		public void invoke(Object source, mxEventObject evt)
		{
			if (isEnabled())
			{
				beforeUndo((mxUndoableEdit) evt.getProperty("edit"));
			}
		}
	};

	/**
	 * 
	 */
	protected mxIEventListener moveHandler = new mxIEventListener()
	{
		public void invoke(Object source, mxEventObject evt)
		{
			if (isEnabled())
			{
				cellsMoved((Object[]) evt.getProperty("cells"), (Point) evt
						.getProperty("location"));
			}
		}
	};

	/**
	 * 
	 */
	public mxLayoutManager(mxGraph graph)
	{
		setGraph(graph);
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * @param value the enabled to set
	 */
	public void setEnabled(boolean value)
	{
		enabled = value;
	}

	/**
	 * @return the bubbling
	 */
	public boolean isBubbling()
	{
		return bubbling;
	}

	/**
	 * @param value the bubbling to set
	 */
	public void setBubbling(boolean value)
	{
		bubbling = value;
	}

	/**
	 * @return the graph
	 */
	public mxGraph getGraph()
	{
		return graph;
	}

	/**
	 * @param value the graph to set
	 */
	public void setGraph(mxGraph value)
	{
		if (graph != null)
		{
			mxIGraphModel model = graph.getModel();
			model.removeListener(undoHandler);
			graph.removeListener(moveHandler);
		}

		graph = value;

		if (graph != null)
		{
			mxIGraphModel model = graph.getModel();
			model.addListener(mxEvent.BEFORE_UNDO, undoHandler);
			graph.addListener(mxEvent.MOVE_CELLS, moveHandler);
		}
	}

	/**
	 * 
	 */
	protected mxIGraphLayout getLayout(Object parent)
	{
		return null;
	}

	/**
	 * 
	 */
	protected void cellsMoved(Object[] cells, Point location)
	{
		if (cells != null && location != null)
		{
			mxIGraphModel model = getGraph().getModel();

			// Checks if a layout exists to take care of the moving
			for (int i = 0; i < cells.length; i++)
			{
				mxIGraphLayout layout = getLayout(model.getParent(cells[i]));

				if (layout != null)
				{
					layout.moveCell(cells[i], location.x, location.y);
				}
			}
		}
	}

	/**
	 * 
	 */
	protected void beforeUndo(mxUndoableEdit edit)
	{
		Collection<Object> cells = getCellsForChanges(edit.getChanges());
		mxIGraphModel model = getGraph().getModel();

		if (isBubbling())
		{
			Object[] tmp = mxGraphModel.getParents(model, cells.toArray());

			while (tmp.length > 0)
			{
				cells.addAll(Arrays.asList(tmp));
				tmp = mxGraphModel.getParents(model, tmp);
			}
		}

		layoutCells(mxUtils.sortCells(cells, false).toArray());
	}

	/**
	 * 
	 */
	protected Collection<Object> getCellsForChanges(
			List<mxUndoableEdit.mxUndoableChange> changes)
	{
		Set<Object> result = new HashSet<Object>();
		Iterator<mxUndoableEdit.mxUndoableChange> it = changes.iterator();

		while (it.hasNext())
		{
			mxUndoableEdit.mxUndoableChange change = it.next();

			if (change instanceof mxRootChange)
			{
				return new HashSet<Object>();
			}
			else
			{
				result.addAll(getCellsForChange(change));
			}
		}

		return result;
	}

	/**
	 * 
	 */
	protected Collection<Object> getCellsForChange(mxUndoableEdit.mxUndoableChange change)
	{
		mxIGraphModel model = getGraph().getModel();
		Set<Object> result = new HashSet<Object>();

		if (change instanceof mxChildChange)
		{
			mxChildChange cc = (mxChildChange) change;
			Object parent = model.getParent(cc.getChild());

			if (cc.getChild() != null)
			{
				result.add(cc.getChild());
			}

			if (parent != null)
			{
				result.add(parent);
			}

			if (cc.getPrevious() != null)
			{
				result.add(cc.getPrevious());
			}
		}
		else if (change instanceof mxTerminalChange
				|| change instanceof mxGeometryChange)
		{
			Object cell = (change instanceof mxTerminalChange) ? ((mxTerminalChange) change)
					.getCell()
					: ((mxGeometryChange) change).getCell();

			if (cell != null)
			{
				result.add(cell);
				Object parent = model.getParent(cell);

				if (parent != null)
				{
					result.add(parent);
				}
			}
		}

		return result;
	}

	/**
	 * 
	 */
	protected void layoutCells(Object[] cells)
	{
		if (cells.length > 0)
		{
			// Invokes the layouts while removing duplicates
			mxIGraphModel model = getGraph().getModel();

			model.beginUpdate();
			try
			{
				for (int i = 0; i < cells.length; i++)
				{
					if (cells[i] != model.getRoot())
					{
						executeLayout(getLayout(cells[i]), cells[i]);
					}
				}

				fireEvent(new mxEventObject(mxEvent.LAYOUT_CELLS, "cells",
						cells));
			}
			finally
			{
				model.endUpdate();
			}
		}
	}

	/**
	 * 
	 */
	protected void executeLayout(mxIGraphLayout layout, Object parent)
	{
		if (layout != null && parent != null)
		{
			layout.execute(parent);
		}
	}

	/**
	 * 
	 */
	public void destroy()
	{
		setGraph(null);
	}

}
