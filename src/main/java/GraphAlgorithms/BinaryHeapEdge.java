package GraphAlgorithms;

import Collection.Triple;
import Nodes.UndirectedNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 */
	private List<Triple<UndirectedNode, UndirectedNode, Integer>> binh;

	public BinaryHeapEdge() {
		this.binh = new ArrayList<>();
	}

	public boolean isEmpty() {
		return binh.isEmpty();
	}

	/**
	 * Insert a new edge in the binary heap
	 *
	 * @param from one node of the edge
	 * @param to   one node of the edge
	 * @param val  the edge weight
	 */
	public void insert(UndirectedNode from, UndirectedNode to, Integer val) {
		binh.add(new Triple<>(from, to, val));
		percolateUp(binh.size() - 1);
	}

	private void percolateUp(int index) {
		int fatherIndex = (index - 1) / 2;
		if (index != 0 && this.binh.get(fatherIndex).getThird() > this.binh.get(index).getThird()) {
			swap(fatherIndex, index);
			percolateUp(fatherIndex);
		}
	}

	/**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid
	 * binary heap
	 *
	 * @return the edge with the minimal value (root of the binary heap)
	 */
	public Triple<UndirectedNode, UndirectedNode, Integer> remove() {
		// To complete
		Triple<UndirectedNode, UndirectedNode, Integer> racine = this.binh.get(0);
		this.binh.set(0, this.binh.remove(this.binh.size() - 1));
		percolateDown(0);
		return racine;
	}

	private void percolateDown(int elemIndex) {

		int x = getBestChildPos(elemIndex);
		int elemValue = this.binh.get(elemIndex).getThird();
		if (x > this.binh.size()) {
			return;
		}
		if (this.binh.get(x).getThird() < elemValue) {
			swap(x, elemIndex);
			percolateDown(x);
		}
	}

	/**
	 * From an edge indexed by src, find the child having the least weight and
	 * return it
	 *
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
	private int getBestChildPos(int src) {
		int lastIndex = binh.size() - 1;
		if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
			return Integer.MAX_VALUE;
		} else {
			if (2 * src + 2 > lastIndex) {
				return 2 * src + 1;
			}
			if (this.binh.get(2 * src + 1).getThird() <= this.binh.get(2 * src + 2).getThird())
				return 2 * src + 1;
			return 2 * src + 2;
		}
	}

	private boolean isLeaf(int src) {
		// A completer
		return 2 * src + 1 >= this.binh.size();
	}

	/**
	 * Swap two edges in the binary heap
	 *
	 * @param father an index of the list edges
	 * @param child  an index of the list edges
	 */
	private void swap(int father, int child) {
		Triple<UndirectedNode, UndirectedNode, Integer> temp = new Triple<>(binh.get(father).getFirst(),
				binh.get(father).getSecond(), binh.get(father).getThird());
		binh.get(father).setTriple(binh.get(child));
		binh.get(child).setTriple(temp);
	}

	/**
	 * Create the string of the visualisation of a binary heap
	 *
	 * @return the string of the binary heap
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Triple<UndirectedNode, UndirectedNode, Integer> no : binh) {
			s.append(no).append(", ");
		}
		return s.toString();
	}

	private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < x; i++) {
			res.append(" ");
		}
		return res.toString();
	}

	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 */
	public void lovelyPrinting() {
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1 + (int) (Math.log(this.binh.size()) / Math.log(2));
		int index = 0;

		for (int h = 1; h <= depth; h++) {
			int left = ((int) (Math.pow(2, depth - h - 1))) * nodeWidth - nodeWidth / 2;
			int between = ((int) (Math.pow(2, depth - h)) - 1) * nodeWidth;
			int i = 0;
			System.out.print(space(left));
			while (i < Math.pow(2, h - 1) && index < binh.size()) {
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// ------------------------------------
	// TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 *
	 * @return a boolean equal to True if the binary tree is compact from left to
	 *         right
	 */
	private boolean test() {
		return this.isEmpty() || testRec(0);
	}

	private boolean testRec(int root) {
		int lastIndex = binh.size() - 1;
		if (isLeaf(root)) {
			return true;
		} else {
			int left = 2 * root + 1;
			int right = 2 * root + 2;
			if (right >= lastIndex) {
				return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left);
			} else {
				return binh.get(left).getThird() >= binh.get(root).getThird() && testRec(left)
						&& binh.get(right).getThird() >= binh.get(root).getThird() && testRec(right);
			}
		}
	}

	public static void main(String[] args) {
		BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
		System.out.println(jarjarBin.isEmpty() + "\n");
		int k = 20;
		int m = k;
		int min = 2;
		int max = 20;
		while (k > 0) {
			int rand = min + (int) (Math.random() * ((max - min) + 1));
			jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k + 30), rand);
			k--;
		}
		// A completer
		jarjarBin.lovelyPrinting();
		System.out.println(jarjarBin.test());
		jarjarBin.remove();
		System.out.println(jarjarBin.test());

		jarjarBin.remove();
		System.out.println(jarjarBin.test());

	}

}
