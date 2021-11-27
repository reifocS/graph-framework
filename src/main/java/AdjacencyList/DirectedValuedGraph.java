package AdjacencyList;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

import java.util.ArrayList;
import java.util.Arrays;

import static GraphAlgorithms.GraphTools.bellman;
import static GraphAlgorithms.GraphTools.dijkstra;

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
		// A completer
		from.getSuccs().put(to, cost);
		to.getPreds().put(from, cost);
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
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 5);
		int[][] testBellman = new int[][] { { 0, 3, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, -2, 1, 0 },
				{ 0, 0, 0, -2, -2, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0 }, { 0, 0, 0, 0, 0, 6, 4 }, { 0, 0, 0, 0, -2, 0, -3 },
				{ 4, 2, 0, 0, 0, 0, 0 } };
		DirectedValuedGraph al = new DirectedValuedGraph(testBellman);
		DirectedValuedGraph bel = new DirectedValuedGraph(matrixValued);
        // System.out.println(al);
        // int[][] dist = bellman(al, al.getNodeOfList(al.makeNode(0)));
        int[] d2 = dijkstra(bel, bel.getNodeOfList(bel.makeNode(0)));
        for(int i : d2) {
			System.out.println(i);
		}
		// A completer
	}

}
