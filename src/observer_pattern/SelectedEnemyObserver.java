package observer_pattern;

import game.Entity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class SelectedEnemyObserver implements Observer{
    private Text enemyHealthDisplay;
    private Text enemyArmorDisplay;
    private Pane selectedEnemyPane;
    public SelectedEnemyObserver(Text enemyHealthDisplay, Text enemyArmorDisplay, Pane selectedEnemyPane) {
        this.enemyHealthDisplay = enemyHealthDisplay;
        this.enemyArmorDisplay = enemyArmorDisplay;
        this.selectedEnemyPane = selectedEnemyPane;
    }
    @Override
    public void update(Entity entity, String message) {
        if (entity != null) {
            if (entity.health < 0) {
                enemyHealthDisplay.setText("H: " + 0);
            } else {
                enemyHealthDisplay.setText("H: " + entity.health);
            }
            enemyArmorDisplay.setText("A: " + entity.armor);
            selectedEnemyPane.getChildren().removeAll();
            selectedEnemyPane.getChildren().add(new Circle(75, 50, 50, entity.circle.getFill()));
        }
    }
}
