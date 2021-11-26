package AdjacencyList;

import Abstraction.AbstractListGraph;
import Abstraction.IDirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO
//1) compléter ces méthodes et celles de directedValuedGraph
//2) directedNode
//3) Bellman dans Tools/
public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

	private static int _DEBBUG = 0;

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public DirectedGraph() {
		super();
		this.nodes = new ArrayList<>();
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
		return from.getSuccs().containsValue(to);
	}

	@Override
	public void removeArc(DirectedNode from, DirectedNode to) {
		from.getSuccs().remove(to);
		to.getPreds().remove(from);
	}

	@Override
	public void addArc(DirectedNode from, DirectedNode to) {
		from.getSuccs().put(to, 0);
		to.getPreds().put(from, 0);
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
		return this.getNodes().get(src.getLabel());
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
				matrix[i][IndSucc] = 1;
			}
		}
		return matrix;
	}

	@Override
	public IDirectedGraph computeInverse() {
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
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph al = new DirectedGraph(Matrix);
		System.out.println(al);
		al.addArc(al.getNodeOfList(al.makeNode(0)), al.getNodeOfList(al.makeNode(5)));
		System.out.println(al);
		al.removeArc(al.getNodeOfList(al.makeNode(0)), al.getNodeOfList(al.makeNode(5)));
		System.out.println(al);
		// A completer
	}
}
