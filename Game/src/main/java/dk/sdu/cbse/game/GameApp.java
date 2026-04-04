package dk.sdu.cbse.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AsteroidsFX");
        primaryStage.setScene(new Scene(new Label("Hello World"), 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}