package co.uk.owenrumney.awsdiagram.mxgraph.core.costfunction;

import co.uk.owenrumney.awsdiagram.mxgraph.core.view.mxCellState;

/**
 * @author Mate
 * A constant cost function that returns <b>const</b> regardless of edge value
 */
public class mxConstCostFunction extends mxCostFunction
{
	private double cost;
	
	public mxConstCostFunction(double cost)
	{
		this.cost = cost;
	};
	
	public double getCost(mxCellState state)
	{
		return cost;
	};
}
