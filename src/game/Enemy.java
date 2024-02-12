package game;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Enemy extends Entity {

    //A* Variables
    ArrayList<Node> openList = new ArrayList<>();
    Node[][] nodes;
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;

    public Enemy(int col, int row, double size, Node[][] nodes, Node player, Color color) {
        //Initializing game variables
        this.col = col;
        this.row = row;
        health = (int)(Math.random() * 3) + 3;
        armor = 0;

        //Initializing A* variables
        this.nodes = nodes;
        setStartNode(col, row);
        setGoalNode(player.col, player.row);
        setCosts();

        circle = new Circle((col * size) + size /2, (row * size) + size/2, 19.5, color);
        circle.setOnMouseClicked(this::onClick);
        circle.setFocusTraversable(false);
    }

    private void onClick(MouseEvent e) {
        notifyObservers(this, null);
    }
    public void move(int col, int row, Entity target) {
        nodes[this.col][this.row].hasEnemy = false;
        setStartNode(this.col, this.row);
        setGoalNode(col, row);
        setCosts();

        Node nextNode = autoSearch();


        if (nextNode != goalNode) {
            this.col = nextNode.col;
            this.row = nextNode.row;
        } else {
        //Add function call to damage player here
        }
        nodes[this.col][this.row].hasEnemy = true;

        circle.setCenterX((this.col * nodeSize) + nodeSize / 2);
        circle.setCenterY((this.row * nodeSize) + nodeSize / 2);


    }

    @Override
    public void die() {

    }

    public Node autoSearch() {
        //Used as a failsafe preventing an infinite loop in case no path can be drawn
        int step = 0;
        Node nextNode = null;
        currentNode = startNode;
        while (!goalReached && step < 300) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            openList.remove(currentNode);

            //Open top node
            if (row - 1 >= 0 && !nodes[col][row - 1].solid)
                openNode(nodes[col][row - 1]);
            //Open left node
            if (col - 1 >= 0 && !nodes[col - 1][row].solid)
                openNode(nodes[col - 1][row]);
            //Open bottom node
            if (row + 1 < nodes[0].length && !nodes[col][row + 1].solid)
                openNode(nodes[col][row + 1]);
            //Open right node
            if (col + 1 < nodes.length && !nodes[col + 1][row].solid)
                openNode(nodes[col + 1][row]);

            int bestIndex = 0;
            int bestFCost = 999;

            //Finds the node with the best cost in the list
            for (int i = 0; i < openList.size(); i++) {
                //Finds the lowest fCost of all the nodes in the openList
                if (openList.get(i).fCost < bestFCost) {
                    bestIndex = i;
                    bestFCost = openList.get(i).fCost;
                }
                //Check gCost if fCost is equal
                else if (openList.get(i).fCost == bestFCost) {
                    if (openList.get(i).gCost < openList.get(bestIndex).gCost)
                        bestIndex = i;
                }
            }
            currentNode = openList.get(bestIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                nextNode = trackPath();
            }
            step++;
        }
        goalReached = false;
        int col = 0;
        int row = 0;
        while (col < nodes.length && row < nodes[0].length) {
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            if (!nodes[col][row].solid)
                nodes[col][row].rec.setFill(Color.WHITE);
            col++;
            if (col == nodes.length) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        return nextNode;
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Used for debugging, shows the path from the startNode to the goalNode
     * @return the next node in the path
     */
    private Node trackPath() {
        Node current = goalNode;
        Node ret = null;
        while (current != startNode) {
            if (current.parent == startNode) {
                ret = current;
            }
            current = current.parent;
            if (current != startNode) {
                current.setAsPath();
            }
        }
        return ret;
    }
    private void setCosts() {
        int col = 0;
        int row = 0;

        while (col < nodes.length && row < nodes[0].length) {
            getCost(nodes[col][row]);
            col++;
            if (col == nodes.length) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Calculates the cost between the passed in node and the startNode
     * @param node used
     */
    private void getCost(Node node) {
        //Calculate gCost
        int xDist = Math.abs(node.col - startNode.col);
        int yDist = Math.abs(node.row - startNode.row);
        node.gCost = xDist + yDist;

        //Calculate hCost
        xDist = Math.abs(node.col - goalNode.col);
        yDist = Math.abs(node.row - goalNode.row);
        node.hCost = xDist + yDist;

        //Calculate fCost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Sets the start node for A*
     * @param col of the node
     * @param row of the node
     */
    private void setStartNode(int col, int row) {
        nodes[col][row].setAsStart();
        startNode = nodes[col][row];
        currentNode = startNode;
    }

    /**
     * Sets the goal node for A*
     * @param col of the node
     * @param row of the node
     */
    private void setGoalNode(int col, int row) {
        nodes[col][row].setAsGoal();
        goalNode = nodes[col][row];
    }
}
