package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import observer_pattern.EventBoxObserver;
import observer_pattern.Observer;
import observer_pattern.PlayerStatObserver;
import observer_pattern.SelectedEnemyObserver;

import java.util.ArrayList;

public class BoardManager {
    private final int nodeSize;
    private final Pane field;
    public Player player;
    ArrayList<Entity> enemies = new ArrayList<>();
    private Timeline timeline;


    private Node[][] grid;

    public BoardManager(int nodeSize, Pane field) {
        this.nodeSize = nodeSize;
        this.field = field;
    }

    public void createRoom(int col, int row, TextArea eventBox, Text playerHealthDisplay, Text playerArmorDisplay, Text enemyHealthDisplay, Text enemyArmorDisplay, Pane selectedEnemyPane) {
        grid = new Node[col][row];
        createFrame(col, row);
        addWalls();

        addPlayer(eventBox);

//        addEnemies(1, eventBox, enemyHealthDisplay, enemyArmorDisplay, selectedEnemyPane, playerHealthDisplay, playerArmorDisplay);

    }

    private void createFrame(int maxColumns, int maxRows) {
        //Iterate through each element in the 2d array and add a node to it
        int col = 0;
        int row = 0;
        while (col < maxColumns && row < maxRows) {
            Node node = new Node(col, row, nodeSize);
            grid[col][row] = node;
            field.getChildren().addAll(node.rec);
            col++;
            if (col == maxColumns) {
                col = 0;
                row++;
            }
        }
    }

    private void addWalls() {
        //Creates the top and bottom walls
        for (int i = 0; i < grid.length; i++) {
            setSolidNode(i, 0);
            setSolidNode(i, grid[0].length - 1);
        }
        //Creates the side walls
        for (int i = 0; i < grid[0].length; i++) {
            setSolidNode(0, i);
            setSolidNode(grid.length - 1, i);
        }
    }

    private void setSolidNode(int col, int row) {
        grid[col][row].setAsSolid();
    }

    private void addPlayer(TextArea eventBox) {
        //Spawn the player
        player = new Player(3, 6, nodeSize, grid);
        field.getChildren().add(player.circle);
        attachObserverToPlayer(new EventBoxObserver(eventBox));
        player.notifyObservers(player, null);
    }

//    private void addEnemies(int num, TextArea eventBox, Text enemyHealthDisplay, Text enemyArmorDisplay,
//                            Pane selectedEnemyPane, Text playerHealthDisplay, Text playerArmorDisplay) {
//        for (int i = 0; i < num; i++) {
//            Enemy enemy = new Enemy(6, i + 6, nodeSize, grid, grid[player.col][player.row], Color.RED);
//            field.getChildren().add(enemy.circle);
//            enemies.add(enemy);
//        }
//        enemy = new Enemy(6, 3, nodeSize, grid, grid[player.col][player.row], Color.ORANGE);
//        field.getChildren().add(enemy.circle);
//        enemies.add(enemy);
//
//        attachObserverToEnemies(new EventBoxObserver(eventBox));
//        attachObserverToEnemies(new SelectedEnemyObserver(enemyHealthDisplay, enemyArmorDisplay, selectedEnemyPane));
//        attachObserverToEnemies(new PlayerStatObserver(playerHealthDisplay, playerArmorDisplay));
//
//    }

    /**
     * Finds a tile to spawn an enemy on when generating a room
     * @return [x,y] coordinate of a tile
     */
    private int[] generateCoordinate() {
        int[] coordinates = new int[2];

        return null;
    }

    public void attachObserverToPlayer(Observer o) {
        player.attach(o);
    }

    public void attachObserverToEnemies(Observer o) {
        for (Entity enemy : enemies) {
            enemy.attach(o);
        }
    }

    public void movePlayer(int col, int row) {
        if (!grid[col][row].solid && !grid[col][row].hasEnemy)
            player.move(col, row, null, field);
        else if (grid[col][row].hasEnemy) {
            for (Entity enemy : enemies) {
                if (enemy.col == col && enemy.row == row) {
                    player.move(col, row, enemy, field);
                    break;
                }
            }
        }
    }

    /**
     * Loops through the entities in the enemies list and moves each entity,
     * removing any entities that are dead
     * @param actionEvent unused
     */
    private void moveEnemies(ActionEvent actionEvent) {
        Entity removed = null;
        for (Entity enemy : enemies) {
            if (enemy.health > 0) {
                enemy.move(player.col, player.row, player, field);
            } else {
                //Removes dead enemies from the list
                removed = enemy;
            }
        }
        enemies.remove(removed);
    }

    public void initializeEnemies() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), this::moveEnemies));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}

