package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] nodes;
    public ArrayList<Node> openList = new ArrayList<Node>();
    public ArrayList<Node> pathList = new ArrayList<Node>();
    Node current, start, end;
    boolean found = false;
    int step = 0;

    public PathFinder(GamePanel gp) {

    	this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes() {

        nodes = new Node[gp.maxWorldCol][gp.maxWorldRow];
        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                nodes[col][row] = new Node(col, row);
            }
        }
    }
    public void resetNodes() {

        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                nodes[col][row].open = false;
                nodes[col][row].closed = false;
                nodes[col][row].solid = false;
                nodes[col][row].f = 0;
                nodes[col][row].g = 0;
                nodes[col][row].h = 0;
                nodes[col][row].parent = null;
            }
        }
        openList.clear();
        pathList.clear();
        found = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int endCol, int endRow) {

        resetNodes();

        start = nodes[startCol][startRow];
        current = start;
        end = nodes[endCol][endRow];
        openList.add(current);

        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {

                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
                if (gp.tileM.tiles[tileNum].collision) nodes[col][row].solid = true;

                for (int i = 0; i < gp.it[gp.currentMap].length; i++) {
                    if(gp.it[gp.currentMap][i] != null && gp.it[gp.currentMap][i].destructible) {
                        int itCol = gp.it[gp.currentMap][i].worldX / gp.tileSize;
                        int itRow = gp.it[gp.currentMap][i].worldY / gp.tileSize;
                        nodes[itCol][itRow].solid = true;
                    }
                }

                getCost(nodes[col][row]);
            }
        }
    }
    public void getCost(Node node) {

        int dx, dy;

        dx = Math.abs(node.col - start.col);
        dy = Math.abs(node.row - start.row);
        node.g = dx + dy;

        dx = Math.abs(node.col - end.col);
        dy = Math.abs(node.row - end.row);
        node.h = dx + dy;

        node.f = node.g + node.h;
    }
    public boolean search() {

        while (!found && step < 500) {
            int col = current.col;
            int row = current.row;
            current.closed = true;
            openList.remove(current);

            if (row - 1 >= 0)               openNode(nodes[col][row - 1]);
            if (row + 1 < gp.maxWorldRow)   openNode(nodes[col][row + 1]);
            if (col - 1 >= 0)               openNode(nodes[col - 1][row]);
            if (col + 1 < gp.maxWorldCol)   openNode(nodes[col + 1][row]);

            int lowestIndex = 0;
            int lowestF = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).f < lowestF) {
                    lowestF = openList.get(i).f;
                    lowestIndex = i;
                } else if (openList.get(i).f == lowestF) {
                    if (openList.get(i).g < openList.get(lowestIndex).g) {
                        lowestIndex = i;
                    }
                }
            }

            if (openList.size() == 0) break;

            current = openList.get(lowestIndex);
            if (current == end) {
                found = true;
                trackPath();
            }
            step++;
        }

        return found;
    }
    public void openNode(Node node) {

        if (!node.open && !node.closed && !node.solid) {
            node.open = true;
            node.parent = current;
            openList.add(node);
        }
    }
    public void trackPath() {

        Node node = end;
        while (node != start) {
            pathList.add(0, node);
            node = node.parent;
        }
    }
}
