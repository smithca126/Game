package game;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Controller {
    public Text enemyHealthDisplay;
    public Text enemyArmorDisplay;
    public Text playerHealthDisplay;
    public Text playerArmorDisplay;
    public TextArea eventBox;
    public Pane selectedEnemyPane;
    @FXML
    private Pane field;
    private final int nodeSize = 40;
    DungeonManager dungeonGenerator;

    /**
     * initializes the window
     */
    @FXML
    public void initialize() {
        field.setFocusTraversable(true);
        enemyHealthDisplay.setFocusTraversable(false);
        enemyArmorDisplay.setFocusTraversable(false);
        playerHealthDisplay.setFocusTraversable(false);
        playerArmorDisplay.setFocusTraversable(false);
        selectedEnemyPane.setFocusTraversable(false);
        eventBox.setFocusTraversable(false);
        eventBox.setWrapText(true);

        dungeonGenerator = new DungeonManager(nodeSize, field);
        dungeonGenerator.createRoom(15, 10, eventBox, playerHealthDisplay, playerArmorDisplay, enemyHealthDisplay, enemyArmorDisplay, selectedEnemyPane);
        dungeonGenerator.initializeEnemies();

    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        System.out.printf(mouseEvent.toString());
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP) {
            dungeonGenerator.movePlayer(dungeonGenerator.player.col, dungeonGenerator.player.row - 1);
        } else if (keyEvent.getCode() == KeyCode.LEFT) {
            dungeonGenerator.movePlayer(dungeonGenerator.player.col - 1, dungeonGenerator.player.row);
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            dungeonGenerator.movePlayer(dungeonGenerator.player.col + 1, dungeonGenerator.player.row);
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            dungeonGenerator.movePlayer(dungeonGenerator.player.col, dungeonGenerator.player.row + 1);
        } else {
            System.out.println("unsupported input");
        }
    }

}
