package astar_example;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {
    @FXML
    private Pane field;

    private final int windowWidth = 600;
    private final int windowHeight = 400;
    private final int maxColumns = 15;
    private final int maxRows = 10;
    private final int nodeSize = 40;

    Node[][] nodes = new Node[maxColumns][maxRows];
    private Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();

    private boolean goalReached = false;

    /**
     * initializes the window
     */
    @FXML
    public void initialize() {
        field.setFocusTraversable(true);

        //Add all the nodes to the window
        setBoard();
    }
    private void setStartNode(int col, int row) {
        nodes[col][row].setAsStart();
        startNode = nodes[col][row];
        currentNode = startNode;
    }
    private void setGoalNode(int col, int row) {
        nodes[col][row].setAsGoal();
        goalNode = nodes[col][row];
    }
    private void setSolidNode(int col, int row) {
        nodes[col][row].setAsSolid();
    }

    /**
     * Displays the gCost and fCost of all the nodes from the startNode
     */
    private void setCosts() {
        int col = 0;
        int row = 0;

        while (col < maxColumns && row < maxRows) {
            getCost(nodes[col][row]);
            col++;
            if (col == maxColumns) {
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

        //Display the gCost and fCost on the node
        if (node != startNode && node != goalNode && !node.solid) {
            node.text.setText("G:" + node.gCost + " F:" + node.fCost);
        }
    }

    /**
     * Does one iteration of searching for the path
     */
    public void search() {
        if (!goalReached) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            openList.remove(currentNode);

            //Open top node
            if (row - 1 >= 0)
                openNode(nodes[col][row - 1]);
            //Open left node
            if (col - 1 >= 0)
                openNode(nodes[col - 1][row]);
            //Open bottom node
            if (row + 1 < maxRows)
                openNode(nodes[col][row + 1]);
            //Open right node
            if (col + 1 < maxColumns)
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
                trackPath();
            }
        }
    }
    public void autoSearch() {
        //Used as a failsafe preventing an infinite loop in case no path can be drawn
        int step = 0;
        while (!goalReached && step < 300) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            openList.remove(currentNode);

            //Open top node
            if (row - 1 >= 0)
                openNode(nodes[col][row - 1]);
            //Open left node
            if (col - 1 >= 0)
                openNode(nodes[col - 1][row]);
            //Open bottom node
            if (row + 1 < maxRows)
                openNode(nodes[col][row + 1]);
            //Open right node
            if (col + 1 < maxColumns)
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
                trackPath();
            }
            step++;
        }
    }
    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.R) {
            setBoard();
        } else if (keyEvent.getCode() == KeyCode.K) {
            autoSearch();
        } else {
            search();
        }
    }
    private void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            current = current.parent;
            if (current != startNode) {
                current.setAsPath();
            }
        }
    }
    private void setBoard() {
        //Checks if the board has been set already and if it has clears
        //the node lists so that it can be regenerated
        if (nodes[0][0] != null) {
            nodes = new Node[maxColumns][maxRows];
            openList = new ArrayList<>();
        }

        //Regenerates all the nodes on the board
        goalReached = false;
        int col = 0;
        int row = 0;
        while (col < maxColumns && row < maxRows) {
            Node node = new Node(col, row, nodeSize);
            nodes[col][row] = node;
            field.getChildren().addAll(node.rec, node.text);

            col++;
            if (col == maxColumns) {
                col = 0;
                row++;
            }
        }
        setStartNode(3, 6);
        setGoalNode(11, 3);

        setSolidNode(10, 2);
        setSolidNode(10, 3);
        setSolidNode(10, 4);
        setSolidNode(10, 5);
        setSolidNode(10, 6);
        setSolidNode(10, 7);
        setSolidNode(2, 2);
        setSolidNode(3, 2);
        setSolidNode(4, 2);
        setSolidNode(5, 2);
        setSolidNode(6, 2);
        setSolidNode(7, 2);
        setSolidNode(8, 2);
        setSolidNode(9, 2);
        setSolidNode(7, 7);
        setSolidNode(8, 7);
        setSolidNode(9, 7);
        setSolidNode(10, 7);
        setSolidNode(11, 7);
        setSolidNode(12, 7);
        setSolidNode(6, 1);
        setSolidNode(6, 0);
        setCosts();
    }
}
