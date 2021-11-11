package GraphAlgorithms;


public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public void insert(int element) {
        this.nodes[pos] = element;
        percolateUp(pos);
        pos++;
    }


    public int[] getChildren(int elemIndex) {
        int child1 = this.nodes[2 * elemIndex + 1];
        int child2 = this.nodes[2 * elemIndex + 2];
        return new int[]{child1, child2};
    }

    public int remove() {
        int racine = this.nodes[0];
        this.nodes[0] = this.nodes[pos - 1];
        pos--;
        percolateDown(0);
        return racine;
    }

    private void percolateDown(int elemIndex) {

        int x = getBestChildPos(elemIndex);
        int elemValue = this.nodes[elemIndex];
        if (x >= pos) {
            return;
        }
        if (this.nodes[x] < elemValue) {
            swap(x, elemIndex);
            percolateDown(x);
        }
    }

    private void percolateUp(int elemIndex) {
        int fatherIndex = (elemIndex - 1) / 2;
        if (elemIndex != 0 && this.nodes[fatherIndex] > this.nodes[elemIndex]) {
            swap(fatherIndex, elemIndex);
            percolateUp(fatherIndex);
        }
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            if (2 * src + 2 >= pos) {
                return 2 * src + 1;
            }
            if (nodes[2 * src + 1] <= nodes[2 * src + 2]) return 2 * src + 1;
            return 2 * src + 2;
        }
    }


    /**
     * Test if the node is a leaf in the binary heap
     *
     * @returns true if it's a leaf or false else
     */
    private boolean isLeaf(int src) {
        return 2 * src + 1 >= pos;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
     * Recursive test to check the validity of the binary heap
     *
     * @returns a boolean equal to True if the binary tree is compact from left to right
     */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty() + "\n");
        int k = 20;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand);
            jarjarBin.insert(rand);
            k--;
        }
        // A completer
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());
        jarjarBin.remove();
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());
    }

}
