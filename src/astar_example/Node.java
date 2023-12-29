package astar_example;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Node {
    //Acts as X and Y coordinate of the node
    public final int col, row;

    //Flags for the different node states
    public boolean start, goal, solid;

    //Flags used for the A* algorithm
    public boolean open, checked;

    //Costs used for calculating the closest path
    public int gCost, hCost, fCost;
    public Node parent;
    public Rectangle rec;
    public Text text;

    public Node(int col, int row, int size) {
        this.col = col;
        this.row = row;
        rec = new Rectangle(col * size + 0.5, row * size + 0.5, 39, 39);
        text = new Text(col * size + 2, row * size + 25, "");
        text.setFont(Font.font("Verdana", 8));
        rec.setFill(Color.WHITE);

        rec.setOnMouseClicked(this::onClick);
        text.setOnMouseClicked(this::onClick);

        rec.setFocusTraversable(false);
        text.setFocusTraversable(false);
    }

    private void onClick(MouseEvent e) {
//        Rectangle r = (Rectangle) e.getSource();
//        r.setFill(Color.ORANGE);
//        r.setStrokeWidth(10);
        if (solid) {
            solid = false;
            rec.setFill(Color.WHITE);
            text.setText("G:" + gCost + " F:" + fCost);
        } else {
            setAsSolid();
        }
    }

    /**
     * Sets a node as the starting node
     */
    public void setAsStart() {
        rec.setFill(Color.AQUA);
        text.setText("Start");
        start = true;
    }

    /**
     * Sets a node as the goal node
     */
    public void setAsGoal() {
        rec.setFill(Color.YELLOW);
        text.setText("Goal");
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

    /**
     * Sets a node green to show the path the algorithm ended on
     */
    public void setAsPath() {
        rec.setFill(Color.GREEN);
    }
}
