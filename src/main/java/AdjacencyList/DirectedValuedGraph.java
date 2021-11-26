package AdjacencyList;

import static GraphAlgorithms.GraphTools.bellman;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class DirectedValuedGraph extends DirectedGraph {

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public DirectedValuedGraph(int[][] matrixVal) {
		super();
		this.order = matrixVal.length;
		this.nodes = new ArrayList<DirectedNode>();
		for (int i = 0; i < this.order; i++) {
			this.nodes.add(i, this.makeNode(i));
		}
		for (DirectedNode n : this.getNodes()) {
			for (int j = 0; j < matrixVal[n.getLabel()].length; j++) {
				DirectedNode nn = this.getNodes().get(j);
				if (matrixVal[n.getLabel()][j] != 0) {
					n.getSuccs().put(nn, matrixVal[n.getLabel()][j]);
					nn.getPreds().put(n, matrixVal[n.getLabel()][j]);
					this.m++;
				}
			}
		}
	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	/**
	 * Adds the arc (from,to) with cost if it is not already present in the graph
	 */
	public void addArc(DirectedNode from, DirectedNode to, int cost) {
		if (!isArc(from, to)) {
			DirectedNode xElement = this.getNodeOfList(from);
			DirectedNode yElement = this.getNodeOfList(to);
			xElement.getSuccs().put(yElement, cost);
			yElement.getPreds().put(xElement, cost);
			this.m++;
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (DirectedNode n : nodes) {
			s.append("successors of ").append(n).append(" : ");
			for (DirectedNode sn : n.getSuccs().keySet()) {
				s.append("(").append(sn).append(",").append(n.getSuccs().get(sn)).append(")  ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		GraphTools.afficherMatrix(matrix);
		GraphTools.afficherMatrix(matrixValued);
		DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
		// A completer
		System.out.println(al.toString());
		System.out.println(al.getNbArcs());
		DirectedNode xElement = new DirectedNode(5);
		DirectedNode yElement = new DirectedNode(15);
		if (al.isArc(xElement, yElement)) {
			al.removeArc(xElement, yElement);
		} else {
			al.addArc(xElement, yElement, 8);
		}
		GraphTools.afficherMatrix(al.toAdjacencyMatrix());
		System.out.println(al.toString());
		System.out.println(al.getNbArcs());
		// Test Bellman
		int[][] testBellman = new int[][] { { 0, 3, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, -2, 1, 0 },

				{ 0, 0, 0, -2, -2, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0 }, { 0, 0, 0, 0, 0, 6, 4 }, { 0, 0, 0, 0, -2, 0, -3 },
				{ 4, 2, 0, 0, 0, 0, 0 } };

		DirectedValuedGraph al2 = new DirectedValuedGraph(testBellman);
		System.out.println(al2);
		int[][] dist = bellman(al2, al2.getNodeOfList(al2.makeNode(0)));
		for (int i = 0; i < dist.length; ++i) {
			String s = "";
			for (int j = 0; j < dist[i].length; ++j) {
				s += dist[i][j] + ",";
			}
			System.out.println(s);
		}
	}

}
