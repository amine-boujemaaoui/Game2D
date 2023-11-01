package ai;

public class Node {

    Node parent;
    public int col, row;
    public int f, g, h;
    boolean solid;
    boolean open;
    boolean closed;

    public Node(int col, int row) {

        this.col = col;
        this.row = row;
    }
}
