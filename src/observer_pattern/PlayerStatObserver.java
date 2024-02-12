package observer_pattern;

import game.Entity;
import javafx.scene.text.Text;

public class PlayerStatObserver implements Observer {
    private Text playerHealthDisplay;
    private Text playerArmorDisplay;
    public PlayerStatObserver(Text playerHealthDisplay, Text playerArmorDisplay) {
        this.playerHealthDisplay = playerHealthDisplay;
        this.playerArmorDisplay = playerArmorDisplay;
    }
    @Override
    public void update(Entity entity, String message) {
        if (entity != null) {
            playerHealthDisplay.setText("H: " + entity.health);
            playerArmorDisplay.setText("A: " + entity.armor);
        }
    }
}
