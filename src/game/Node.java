package game;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Node {
    //Acts as X and Y coordinate of the node
    public final int col, row;

    //Flags for the different node states
    public boolean start, goal, solid, hasEnemy, moveTo, confirmed;

    //Flags used for the A* algorithm
    public boolean open, checked;

    //Costs used for calculating the closest path
    public int gCost, hCost, fCost;
    public Node parent;
    public Rectangle rec;

    public Node(int col, int row, double size) {
        this.col = col;
        this.row = row;

        rec = new Rectangle(col * size + 0.5, row * size + 0.5, 39, 39);
        rec.setFill(Color.WHITE);
        rec.setOnMouseClicked(this::onClick);
        rec.setFocusTraversable(false);
    }

    private void onClick(MouseEvent e) {
        if (moveTo) {
//            rec.setFill(Color.WHITE);
            moveTo = false;
            confirmed = true;
        } else {
//            rec.setFill(Color.GREEN);
            moveTo = true;
        }
    }

    /**
     * Sets a node as the starting node
     */
    public void setAsStart() {
        start = true;
    }

    /**
     * Sets a node as the goal node
     */
    public void setAsGoal() {
        goal = true;
    }

    /**
     * Sets a node as solid
     */
    public void setAsSolid() {
        rec.setFill(Color.BLACK);
        solid = true;
    }

    /**
     * Sets a node as open to be checked
     */
    public void setAsOpen() {
        open = true;
    }

    /**
     * Sets a node as checked
     */
    public void setAsChecked() {
        if (!start && !goal) {
            rec.setFill(Color.ORANGE);
        }
        checked = true;
    }

    public void resetNode() {
        rec.setFill(Color.WHITE);
        start = false;
        goal = false;
        solid = false;
        open = false;
        checked = false;
    }

    /**
     * Sets a node green to show the path the algorithm ended on
     */
    public void setAsPath() {
        rec.setFill(Color.GREEN);
    }

}
