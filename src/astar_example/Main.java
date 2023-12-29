/*
 * Course: CS 1011
 * Fall 2022
 * Lab 8 - Parking lot
 * Name: Carson Smith
 * Created: 10/31/2022
 */
package astar_example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * main
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BackGround.fxml")));
        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

