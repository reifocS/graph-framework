package AdjacencyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Abstraction.AbstractListGraph;
import Abstraction.IDirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

//TODO
//1) completer ces methodes et celles de directedValuedGraph
//2) directedNode
//3) Bellman dans Tools/
public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

	private static int _DEBBUG = 0;

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public DirectedGraph() {
		super();
		this.nodes = new ArrayList<DirectedNode>();
	}

	public DirectedGraph(int[][] matrix) {
		this.order = matrix.length;
		this.nodes = new ArrayList<DirectedNode>();
		for (int i = 0; i < this.order; i++) {
			this.nodes.add(i, this.makeNode(i));
		}
		for (DirectedNode n : this.getNodes()) {
			for (int j = 0; j < matrix[n.getLabel()].length; j++) {
				DirectedNode nn = this.getNodes().get(j);
				if (matrix[n.getLabel()][j] != 0) {
					n.getSuccs().put(nn, 0);
					nn.getPreds().put(n, 0);
					this.m++;
				}
			}
		}
	}

	public DirectedGraph(DirectedGraph g) {
		super();
		this.nodes = new ArrayList<>();
		this.order = g.getNbNodes();
		this.m = g.getNbArcs();
		for (DirectedNode n : g.getNodes()) {
			this.nodes.add(makeNode(n.getLabel()));
		}
		for (DirectedNode n : g.getNodes()) {
			DirectedNode nn = this.getNodes().get(n.getLabel());
			for (DirectedNode sn : n.getSuccs().keySet()) {
				DirectedNode snn = this.getNodes().get(sn.getLabel());
				nn.getSuccs().put(snn, 0);
				snn.getPreds().put(nn, 0);
			}
		}

	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	@Override
	public int getNbArcs() {
		return this.m;
	}

	@Override
	public boolean isArc(DirectedNode from, DirectedNode to) {
		if (this.isIncluded(from) && this.isIncluded(to)) {
			DirectedNode xElement = this.getNodeOfList(from);
			DirectedNode yElement = this.getNodeOfList(to);
			return xElement.getNbSuccs() > 0 && yElement.getNbPreds() > 0 && xElement.getSuccs().containsKey(yElement)
					&& yElement.getPreds().containsKey(xElement);
		}
		return false;

	}

	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		if (isArc(from, to)) {
			DirectedNode xElement = this.getNodeOfList(from);
			DirectedNode yElement = this.getNodeOfList(to);
			xElement.getSuccs().remove(yElement);
			yElement.getPreds().remove(xElement);
			this.m--;
		}
	}

	@Override
	public void addArc(DirectedNode from, DirectedNode to) {
		if (!isArc(from, to) && this.isIncluded(from) && this.isIncluded(to)) {
			DirectedNode xElement = this.getNodeOfList(from);
			DirectedNode yElement = this.getNodeOfList(to);
			xElement.getSuccs().put(yElement, 0);
			yElement.getPreds().put(xElement, 0);
			this.m++;
		}
	}

	// --------------------------------------------------
	// Methods
	// --------------------------------------------------

	/**
	 * Method to generify node creation
	 * 
	 * @param label of a node
	 * @return a node typed by A extends DirectedNode
	 */
	@Override
	public DirectedNode makeNode(int label) {
		return new DirectedNode(label);
	}

	/**
	 * @return the corresponding nodes in the list this.nodes
	 */
	public DirectedNode getNodeOfList(DirectedNode src) {
		if (this.isIncluded(src)) {
			return this.getNodes().get(src.getLabel());
		}
		return null;

	}

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	@Override
	public int[][] toAdjacencyMatrix() {
		int[][] matrix = new int[order][order];
		for (int i = 0; i < order; i++) {
			for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
				int IndSucc = j.getLabel();
				// incrementation, even if we do not have a multigraph
				matrix[i][IndSucc]++;
			}
		}
		return matrix;
	}

	/**
	 * @return the associated inverted directed graph, i.e : edge => no edge no edge
	 *         => edge
	 */
	@Override
	public DirectedGraph computeInverse() {
		DirectedGraph g = new DirectedGraph(this);
		List<DirectedNode> nodes = g.getNodes();
		for (DirectedNode n : nodes) {
			Map<DirectedNode, Integer> preds = n.getPreds();
			Map<DirectedNode, Integer> succs = n.getSuccs();
			n.setPreds(succs);
			n.setSuccs(preds);
		}
		// A completer
		return g;
	}

	public IDirectedGraph computeInverse2() {
		int[][] matrixI = new int[order][order];
		int[][] matrix = this.toAdjacencyMatrix();
		for (int i = 0; i < matrix.length; i++) {
			int[] n = matrix[i];
			for (int j = 0; j < n.length; j++) {
				int k = n[j];
				matrixI[i][j] = (k == 0 ? 1 : 0);
			}
		}
		return new DirectedGraph(matrixI);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (DirectedNode n : nodes) {
			s.append("successors of ").append(n).append(" : ");
			for (DirectedNode sn : n.getSuccs().keySet()) {
				s.append(sn).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		DirectedGraph al = new DirectedGraph(Matrix);
		System.out.println(al);
		GraphTools.afficherMatrix(al.toAdjacencyMatrix());
		// A completer
		al.addArc(al.getNodeOfList(al.makeNode(0)), al.getNodeOfList(al.makeNode(5)));
		System.out.println(al);
		al.removeArc(al.getNodeOfList(al.makeNode(0)), al.getNodeOfList(al.makeNode(5)));
		System.out.println(al);
		GraphTools.afficherMatrix(Matrix);

		DirectedGraph alI = (DirectedGraph) al.computeInverse();
		System.out.println(al);
		GraphTools.afficherMatrix(alI.toAdjacencyMatrix());

		System.out.println(al.toString());
		System.out.println(al.getNbArcs());
		DirectedNode xElement = new DirectedNode(5);
		DirectedNode yElement = new DirectedNode(15);
		if (al.isArc(xElement, yElement)) {
			al.removeArc(xElement, yElement);
		} else {
			al.addArc(xElement, yElement);
		}
		GraphTools.afficherMatrix(al.toAdjacencyMatrix());
		System.out.println(al.toString());
		System.out.println(al.getNbArcs());
	}
}
