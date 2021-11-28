package GraphAlgorithms;

import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Collection.Triple;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

import java.util.*;
import java.util.stream.Collectors;

public class GraphTools {

	private static int _DEBBUG = 0;
	private static int INF = Integer.MAX_VALUE;
    private static int compt = 0;

	public GraphTools() {

	}


    // TODO
    public List<Triple<UndirectedNode, UndirectedNode, Integer>> prim(UndirectedValuedGraph undirectedGraph) {
        List edges = new ArrayList();
        List visitedNodes = new ArrayList();
        BinaryHeapEdge binaryHeapEdge = new BinaryHeapEdge();
        List<UndirectedNode> nodes = undirectedGraph.getNodes();
        primIteration(nodes.get(0), edges, binaryHeapEdge);
        visitedNodes.add(nodes.get(0));
        return edges;
	}

	private void primIteration(UndirectedNode undirectedNode,
			List<Triple<UndirectedNode, UndirectedNode, Integer>> edges, BinaryHeapEdge binaryHeapEdge) {
		List<Triple<UndirectedNode, UndirectedNode, Integer>> adj = getAllAdjacent(undirectedNode);
		for (Triple<UndirectedNode, UndirectedNode, Integer> triple : adj) {
			binaryHeapEdge.insert(triple.getFirst(), triple.getSecond(), triple.getThird());
		}
	}


    private List<Triple<UndirectedNode, UndirectedNode, Integer>> getAllAdjacent(UndirectedNode undirectedNode) {
        ArrayList<Triple<UndirectedNode, UndirectedNode, Integer>> adj = new ArrayList<>();
        Map<UndirectedNode, Integer> ng = undirectedNode.getNeighbours();
        for (Map.Entry<UndirectedNode, Integer> entry : ng.entrySet()) {
            adj.add(new Triple<>(undirectedNode, entry.getKey(), entry.getValue()));
        }
        return adj;
    }

	/**
	 * @param n,     the number of vertices
	 * @param multi, at true if we want a multi-graph
	 * @param s,     at true if the graph is symmetric
	 * @param c,     at true if the graph is connected
	 * @param seed,  the unique seed giving a unique random graph
	 * @return the generated matrix
	 */
	public static int[][] generateGraphData(int n, boolean multi, boolean s, boolean c, int seed) {
		if (_DEBBUG > 0) {
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateGraphData en aléatoire complet>>");
		}

		Random rand = new Random(seed);
		int m = (rand.nextInt(n) + 1) * (n - 1) / 2;
		if (_DEBBUG > 0) {
			System.out.println("m = " + m);
		}
		int[][] matrix = new int[n][n];
		if (c) {
			List<Integer> vis = new ArrayList<>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while (vis.size() < n) {
				if (!vis.contains(from)) {
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);
					if (s) {
						matrix[dest][from] = 1;
					}
					matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);
			}
			m -= n - 1;
		}

		while (m > 0) {
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			if (_DEBBUG > 0) {
				System.out.println("i = " + i);
				System.out.println("j = " + j);
			}
			if (!multi) {
				if (i != j && matrix[i][j] != 1) {
					if (s) {
						matrix[j][i] = 1;
					}
					matrix[i][j] = 1;
					m--;
				}
			} else {
				if (matrix[i][j] == 0) {
					int val = (i != j ? (m < 3 ? m : rand.nextInt(3) + 1) : 1);
					if (_DEBBUG > 0) {
						System.out.println("Pour multi, val = " + val);
					}
					if (s) {
						matrix[j][i] = val;
					}
					matrix[i][j] = val;
					m -= val;
				}
			}
		}
		return matrix;
	}

	/**
	 * @param n,     the number of vertices
	 * @param m,     the number of edges
	 * @param multi, at true if we want a multi-graph
	 * @param s,     at true if the graph is symmetric
	 * @param c,     at true if the graph is connexted
	 * @param seed,  the unique seed giving a unique random graph
	 * @return the generated matrix
	 */
	public static int[][] generateGraphData(int n, int m, boolean multi, boolean s, boolean c, int seed) {
		if (_DEBBUG > 0) {
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateGraphData >>");
		}
		int[][] matrix = new int[n][n];
		Random rand = new Random(seed);
		if (c) {
			List<Integer> vis = new ArrayList<>();
			int from = rand.nextInt(n);
			vis.add(from);
			from = rand.nextInt(n);
			while (vis.size() < n) {
				if (!vis.contains(from)) {
					int indDest = rand.nextInt(vis.size());
					int dest = vis.get(indDest);
					if (s) {
						matrix[dest][from] = 1;
					}
					matrix[from][dest] = 1;
					vis.add(from);
				}
				from = rand.nextInt(n);
			}
			m -= n - 1;
		}

		while (m > 0) {
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			if (_DEBBUG > 0) {
				System.out.println("i = " + i);
				System.out.println("j = " + j);
			}
			if (!multi) {
				if (i != j && matrix[i][j] != 1) {
					if (s) {
						matrix[j][i] = 1;
					}
					matrix[i][j] = 1;
					m--;
				}
			} else {
				if (matrix[i][j] == 0) {
					int val = (i != j ? (m < 3 ? m : rand.nextInt(3) + 1) : 1);
					if (_DEBBUG > 0) {
						System.out.println("Pour multi, val = " + val);
					}
					if (s) {
						matrix[j][i] = val;
					}
					matrix[i][j] = val;
					m -= val;
				}
			}
		}
		return matrix;
	}

	/**
	 * @param n,     the number of vertices
	 * @param multi, at true if we want a multi-graph
	 * @param s,     at true if the graph is symmetric
	 * @param c,     at true if the graph is connexted
	 * @param neg,   at true if the graph has negative weights
	 * @param seed,  the unique seed giving a unique random graph
	 */
	public static int[][] generateValuedGraphData(int n, boolean multi, boolean s, boolean c, boolean neg, int seed) {
		if (_DEBBUG > 0) {
			System.out.println("\n ------------------------------------------------");
			System.out.println("<< Lancement de la méthode generateValuedGraphData >>");
		}

		int[][] mat = generateGraphData(n, multi, s, c, seed);
		int[][] matValued = new int[mat.length][mat.length];
		Random rand = new Random(seed);
		int valNeg = 0;
		if (neg) {
			valNeg = -6;
		}

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (mat[i][j] > 0) {
					int val = rand.nextInt(15) + 1 + valNeg;
					matValued[i][j] = val;
					if (s) {
						matValued[j][i] = val;
					}
				}
			}
		}

		return matValued;
	}

	/**
	 * @param m a matrix
	 */
	public static void afficherMatrix(int[][] m) {
		for (int[] line : m) {
			for (int v : line) {
				System.out.print(v + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * @param mat, a matrix
	 * @return the symmetrical matrix
	 */
	public static int[][] matrixSym(int[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				if (mat[i][j] == 1) {
					mat[j][i] = 1;
				}
				mat[i][j] = 0;
			}
		}
		return mat;
	}

	public static List<DirectedNode> explorerSommet(DirectedNode sommet, Set<DirectedNode> nodeSet, int[] visite, int[] debut, int[] fin) {
        nodeSet.add(sommet);
        visite[sommet.getLabel()] = 1;
        debut[sommet.getLabel()] = compt++;
        List<DirectedNode> directedNodes = new ArrayList<>();
        directedNodes.add(sommet);
        for (DirectedNode voisin : sommet.getSuccs().keySet()) {
            if (!nodeSet.contains(voisin)) {
                directedNodes.addAll(explorerSommet(voisin, nodeSet, visite, debut, fin));
            }
        }
        visite[sommet.getLabel()] = 2;
        fin[sommet.getLabel()] = compt++;
        return directedNodes;
    }

    //Depth First Search
    public static int[][] explorerGraphe(DirectedGraph graph, List<DirectedNode> nodeList, boolean displayCFC) {
        Set<DirectedNode> nodes = new HashSet<>();
        int[] visite = new int[graph.getNbNodes()];
        int[] debut = new int[graph.getNbNodes()];
        int[] fin = new int[graph.getNbNodes()];
        compt = 0;
        for (DirectedNode directedNode : nodeList) {
            if (!nodes.contains(directedNode)) {
                if (displayCFC) {
                    System.out.println("CFC:");
                    System.out.println(explorerSommet(directedNode, nodes, visite, debut, fin));
                } else {
                    explorerSommet(directedNode, nodes, visite, debut, fin);
                }
            }
        }
        return new int[][]{debut, fin, visite};
    }

    public static void BFS(DirectedNode node) {
        Set<DirectedNode> marked = new HashSet<>();
        LinkedList<DirectedNode> toVisit = new LinkedList<>();
        toVisit.add(node);
        marked.add(node);
        while (!toVisit.isEmpty()) {
            DirectedNode n = toVisit.removeFirst();
            for (DirectedNode dn : n.getSuccs().keySet()) {
                if (!marked.contains(dn)) {
                    marked.add(dn);
                    toVisit.add(dn);
                }
            }
        }
    }


    private static boolean areAllTrue(boolean[] array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }

    public static int[] dijkstra(DirectedValuedGraph directedValuedGraph, DirectedNode node) {
        int n = directedValuedGraph.getNbNodes();

        int[] d = new int[n];
        int[] pred = new int[n];
        boolean[] b = new boolean[n];
        for (int i = 0; i < n; ++i) {
            d[i] = INF;
            b[i] = false;
        }
        d[node.getLabel()] = 0;
        while (!areAllTrue(b)) {
            int min = INF;
            DirectedNode s = null;
            for (DirectedNode no : directedValuedGraph.getNodes()) {
                if (!b[no.getLabel()] && d[no.getLabel()] < min) {
                    min = d[no.getLabel()];
                    s = no;
                }
            }
            if (min < INF) {
                assert s != null;
                b[s.getLabel()] = true;
                for (DirectedNode s2 : s.getSuccs().keySet()) {
                    if (d[s2.getLabel()] > d[s.getLabel()] + s.getSuccs().get(s2)) {
                        d[s2.getLabel()] = d[s.getLabel()] + s.getSuccs().get(s2);
                        pred[s2.getLabel()] = s.getLabel();
                    }
                }
            } else {
                return d;
            }
        }
        return d;
    }

    public static int[][] bellman(DirectedValuedGraph graph, DirectedNode s) {
        int n = graph.getNbNodes();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                dist[i][j] = INF;
            }
        }
        dist[0][s.getLabel()] = 0;
        for (int k = 1; k < n; ++k) {
            for (DirectedNode v : graph.getNodes()) {
                for (DirectedNode x : v.getPreds().keySet()) {
                    int bestValue = Math.min(dist[k - 1][v.getLabel()], dist[k][v.getLabel()]);
                    if (dist[k - 1][x.getLabel()] != Integer.MAX_VALUE) {
                        dist[k][v.getLabel()] = Math.min(bestValue, dist[k - 1][x.getLabel()] + v.getPreds().get(x));
                    } else {
                        dist[k][v.getLabel()] = bestValue;
                    }
                }
            }
        }
        return dist;
    }


    public static void CFC(DirectedGraph g) {
        int[] fin = explorerGraphe(g, g.getNodes(), false)[1];
        DirectedGraph inverse = g.computeInverse();
        TreeMap<Integer, Integer> map = new TreeMap();
        for (int i = 0; i < fin.length; ++i) {
            map.put(fin[i], i);
        }
        List<Integer> finDecroissant = map.values().stream().collect(Collectors.toList());
        Collections.reverse(finDecroissant);
        List<DirectedNode> nodesInverse = finDecroissant.stream().map(i -> inverse.getNodeOfList(inverse.makeNode(i))).collect(Collectors.toList());
        explorerGraphe(inverse, nodesInverse, true);
    }

    public static void main(String[] args) {
        int[][] mat = generateGraphData(8, 14, false, false, false, 13);
		afficherMatrix(mat);
		DirectedGraph g = new DirectedGraph(mat);
		CFC(g);

	}

}
