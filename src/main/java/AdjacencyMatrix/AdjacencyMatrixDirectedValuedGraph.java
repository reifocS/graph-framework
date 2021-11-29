package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;

public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	// --------------------------------------------------
	// Class variables
	// --------------------------------------------------

	private int[][] matrixCosts; // The graph with Costs

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public AdjacencyMatrixDirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super();
		this.order = mat.length;
		this.matrix = new int[this.order][this.order];
		this.matrixCosts = new int[this.order][this.order];
		for (int i = 0; i < this.order; i++) {
			for (int j = 0; j < this.order; j++) {
				int val = mat[i][j];
				int cost = matrixVal[i][j];
				this.matrix[i][j] = val;
				this.matrixCosts[i][j] = cost;
				this.m += val;
			}
		}
	}

	// --------------------------------------------------
	// Accessors
	// --------------------------------------------------

	/**
	 * All edges which have the same vertex origin and destination are of the same
	 * cost
	 * 
	 * @return the matrix with costs of the graph
	 */
	public int[][] getMatrixCosts() {
		return matrixCosts;
	}

	// ------------------------------------------------
	// Methods
	// ------------------------------------------------

	/**
	 * removes the arc (from,to) if there exists at least one between these nodes in
	 * the graph. And if there remains no arc, removes the cost.
	 */
	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		super.removeArc(from, to);
		if (this.isIncluded(from) && this.isIncluded(to) && !isArc(from, to)) {
			int fL = from.getLabel();
			int tL = to.getLabel();
			this.matrixCosts[fL][tL] = 0;
			this.matrixCosts[tL][fL] = this.matrixCosts[fL][tL];
		}
	}

	/**
	 * adds the arc (from,to,cost), we allow the multi-graph. If there is already
	 * one initial cost, we keep it.
	 */
	public void addArc(DirectedNode from, DirectedNode to, int cost) {
		super.addArc(from, to);
		if (this.isIncluded(from) && this.isIncluded(to)) {
			int fL = from.getLabel();
			int tL = to.getLabel();
			this.matrixCosts[fL][tL] = this.matrixCosts[fL][tL] > 0 ? this.matrixCosts[fL][tL] : cost;
		}
	}

	public String toString() {
		StringBuilder s = new StringBuilder(super.toString() + "\n Matrix of Costs: \n");
		for (int[] matrixCost : this.matrixCosts) {
			for (int i : matrixCost) {
				s.append(i).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix = GraphTools.generateGraphData(10, 30, false, false, false, 100001);
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);
		// A completer
		System.out.println(am.getNbArcs());
		DirectedNode xElement = new DirectedNode(5);
		DirectedNode yElement = new DirectedNode(15);
		am.addArc(xElement, yElement, 8);
		am.addArc(xElement, yElement, 8);
		System.out.println(am);
		System.out.println(am.getNbArcs());
		while (am.isArc(xElement, yElement)) {
			am.removeArc(xElement, yElement);
		}
		System.out.println(am);
		System.out.println(am.getNbArcs());
	}
}
