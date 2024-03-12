package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class Player extends Entity {
    final private Node[][] grid;
    private Node target = null;
    Timeline timeline;

    public Player(int col, int row, double size, Node[][] grid) {
        this.col = col;
        this.row = row;
        this.grid = grid;

        this.health = 10;
        this.armor = 0;

        circle = new Circle((col * size) + size / 2, (row * size) + size / 2, 19.5, Color.BLUE);
        circle.setOnMouseClicked(this::onClick);
    }

    @Override
    public void die(Pane field) {

    }

    @Override
    public void damage(int damage) {

    }

    public void move(int col, int row, Entity target, Pane field) {
        if (target == null) {
            this.col = col;
            this.row = row;
            circle.setCenterX((col * nodeSize) + nodeSize / 2);
            circle.setCenterY((row * nodeSize) + nodeSize / 2);
        } else {
            if (!target.isDead()) {
                target.damage(4);
                notifyObservers(null, "Enemy attacked for 4hp\n");
                if (target.health < 0) {
                    target.die(field);
                    notifyObservers(null, "An enemy has been defeated!\n");
                }
            }
        }
    }

    private void onClick(MouseEvent e) {
        displayMovementRange();
        timeline = new Timeline(new KeyFrame(Duration.millis(10), this::checkForSelectedSquare));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void checkForSelectedSquare(ActionEvent actionEvent) {
        for (int col = 0; col < grid.length; col++) {
            for (int row = 0; row < grid[0].length; row++) {
                if (grid[col][row].moveTo && target == null && gridWithinMovementBounds(col, row)) {
                    //Initial Choice
                    notifyObservers(null, "Reselect square to confirm movement choice.\n");
                    target = grid[col][row];

                    grid[col][row].moveTo = false;
                } else if (grid[col][row].moveTo && target != null && gridWithinMovementBounds(col, row)) {
                    //Second Choice
                    if (grid[col][row].equals(target)) {
                        //Same Square
                        notifyObservers(null, "Moving unit.\n");

                        for (int i = 0; i <= 4; i++) {
                            for (int j = 0; j <= 4; j++) {
                                grid[this.col - 2 + i][this.row - 2 + j].rec.setFill(Color.WHITE);
                            }
                        }
                        grid[col][row].moveTo = false;
                        grid[col][row].confirmed = false;

                        move(col, row, null, null);
                        timeline.stop();
                        target = null;
                    } else {
                        //Different Square
                        notifyObservers(null, "Reselect square to confirm movement choice.\n");
                        target = grid[col][row];

                        grid[col][row].moveTo = false;
                        grid[col][row].rec.setFill(Color.WHITE);
                    }
                } else if (grid[col][row].moveTo && target == null && !gridWithinMovementBounds(col, row)) {
                    notifyObservers(null, "Selected square outside of range.\n");
                    grid[col][row].moveTo = false;
                }
            }
        }
    }


private boolean gridWithinMovementBounds(int col, int row) {
    boolean withinBounds = false;
    for (int i = 0; i <= 4; i++) {
        for (int j = 0; j <= 4; j++) {
            if (this.col - 2 + i == col && this.row - 2 + j == row) {
                //Checking the corners
                if (this.col - 2 == col && this.row + 2 == row || this.col - 2 == col && this.row - 2 == row
                        || this.col + 2 == col && this.row - 2 == row || this.col + 2 == col && this.row + 2 == row) {
                    withinBounds = false;
                } else {
                    withinBounds = true;
                }
            }
        }
    }
    return withinBounds;
}

/**
 * Displays the movement range of the unit by highlighting the squares yellow
 */
private void displayMovementRange() {
    for (int i = 0; i <= 4; i++) {
        for (int j = 0; j <= 4; j++) {
            grid[col - 2 + i][row - 2 + j].rec.setFill(Color.YELLOW);
        }
    }
    grid[col - 2][row + 2].rec.setFill(Color.WHITE);
    grid[col - 2][row - 2].rec.setFill(Color.WHITE);
    grid[col + 2][row - 2].rec.setFill(Color.WHITE);
    grid[col + 2][row + 2].rec.setFill(Color.WHITE);
}
}
