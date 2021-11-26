package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import Abstraction.AbstractListGraph;
import Abstraction.IUndirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

public class UndirectedGraph extends AbstractListGraph<UndirectedNode> implements IUndirectedGraph {

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public UndirectedGraph() {
		this.nodes = new ArrayList<>();
	}

	public UndirectedGraph(List<UndirectedNode> nodes) {
		super(nodes);
		for (UndirectedNode i : nodes) {
			this.m += i.getNbNeigh();
		}
	}

	public UndirectedGraph(int[][] matrix) {
		this.order = matrix.length;
		this.nodes = new ArrayList<>();
		for (int i = 0; i < this.order; i++) {
			this.nodes.add(this.makeNode(i));
		}
		for (UndirectedNode n : this.getNodes()) {
			/**
			 * we do not want to do this : int j = n.getLabel(); for (int i = 0; i <
			 * matrix[n.getLabel()].length; ++i) { .. }; as we would build i->j nodes twice
			 */

			for (int j = n.getLabel(); j < matrix[n.getLabel()].length; j++) {
				UndirectedNode nn = this.getNodes().get(j);
				if (matrix[n.getLabel()][j] != 0) {
					// build edges from both sides
					// we add 0 as the graph is not valued
					n.getNeighbours().put(nn, 0);
					nn.getNeighbours().put(n, 0);
					this.m++;
				}
			}
		}
	}

	public UndirectedGraph(UndirectedGraph g) {
		super();
		this.order = g.getNbNodes();
		this.m = g.getNbEdges();
		this.nodes = new ArrayList<>();
		for (UndirectedNode n : g.getNodes()) {
			this.nodes.add(makeNode(n.getLabel()));
		}
		for (UndirectedNode n : g.getNodes()) {
			UndirectedNode nn = this.getNodes().get(n.getLabel());
			for (UndirectedNode sn : n.getNeighbours().keySet()) {
				UndirectedNode snn = this.getNodes().get(sn.getLabel());
				nn.getNeighbours().put(snn, 0);
				snn.getNeighbours().put(nn, 0);
			}
		}
	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	@Override
	public int getNbEdges() {
		return this.m;
	}

	@Override
	public boolean isEdge(UndirectedNode x, UndirectedNode y) {
		UndirectedNode xElement = this.getNodeOfList(x);
		UndirectedNode yElement = this.getNodeOfList(y);
		return xElement.getNeighbours().containsKey(yElement) && yElement.getNeighbours().containsKey(xElement);
	}

	@Override
	public void removeEdge(UndirectedNode x, UndirectedNode y) {
		if (isEdge(x, y)) {
			UndirectedNode xElement = this.getNodeOfList(x);
			UndirectedNode yElement = this.getNodeOfList(y);
			xElement.getNeighbours().remove(yElement);
			yElement.getNeighbours().remove(xElement);
			this.m--;
		}
	}

	@Override
	public void addEdge(UndirectedNode x, UndirectedNode y) {
		if (!isEdge(x, y)) {
			UndirectedNode xElement = this.getNodeOfList(x);
			UndirectedNode yElement = this.getNodeOfList(y);
			xElement.getNeighbours().put(yElement, 0);
			yElement.getNeighbours().put(xElement, 0);
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
	 * @return a node typed by A extends UndirectedNode
	 */
	@Override
	public UndirectedNode makeNode(int label) {
		return new UndirectedNode(label);
	}

	/**
	 * @return the corresponding nodes in the list this.nodes
	 */
	public UndirectedNode getNodeOfList(UndirectedNode src) {
		return this.getNodes().get(src.getLabel());
	}

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	@Override
	public int[][] toAdjacencyMatrix() {
		int[][] matrix = new int[order][order];
		// A completer
		for (UndirectedNode undirectedNode : this.getNodes()) {
			for (UndirectedNode sn : undirectedNode.getNeighbours().keySet()) {
				matrix[undirectedNode.getLabel()][sn.getLabel()] = 1;
			}
		}
		return matrix;
	}

	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	public int[][] toAdjacencyMatrix2() {
		int myOrder = this.getNbNodes();
		int[][] matrix = new int[myOrder][myOrder];
		this.getNodes().forEach(n -> {
			n.getNeighbours().forEach((nn, i) -> {
				matrix[n.getLabel()][nn.getLabel()] = 1;
				matrix[nn.getLabel()][n.getLabel()] = 1;
			});
		});
		return matrix;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (UndirectedNode n : nodes) {
			s.append("neighbours of ").append(n).append(" : ");
			for (UndirectedNode sn : n.getNeighbours().keySet()) {
				s.append(sn).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
		GraphTools.afficherMatrix(mat);

		UndirectedGraph al = new UndirectedGraph(mat);
		System.out.println(al);
		System.out.println(al.isEdge(new UndirectedNode(5), new UndirectedNode(9)));
		// A completer
		System.out.println(al.getNbEdges());
		UndirectedNode xElement = new UndirectedNode(5);
		UndirectedNode yElement = new UndirectedNode(15);
		if (al.isEdge(xElement, yElement)) {
			al.removeEdge(xElement, yElement);
		} else {
			al.addEdge(xElement, yElement);
		}
		System.out.println(al);
		GraphTools.afficherMatrix(al.toAdjacencyMatrix());
		System.out.println(al.getNbEdges());
	}
}
