package game;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import observer_pattern.Subject;

public abstract class Entity extends Subject {
    public Circle circle;
    public int col, row;
    public int health, armor;
    protected final double nodeSize = 40;
    private boolean isDead = false;


    abstract void move(int col, int row, Entity target);
    public abstract void die(Pane field);

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
