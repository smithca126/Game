package game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Player extends Entity {
    public Player(int col, int row, double size) {
        this.col = col;
        this.row = row;

        this.health = 10;
        this.armor = 0;

        circle = new Circle((col * size) + size /2, (row * size) + size/2, 19.5, Color.BLUE);
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
}
