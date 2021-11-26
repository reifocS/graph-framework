package Abstraction;

import Nodes.AbstractNode;

public interface IGraph {
	/**
	 * @return the number of nodes in the graph (referred to as the order of the
	 *         graph)
	 */
	int getNbNodes();

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	int[][] toAdjacencyMatrix();

	/**
	 * Does the given Abstract node is already in the graph
	 * 
	 * @param node
	 * @return
	 */
	boolean isIncluded(AbstractNode node);

}
