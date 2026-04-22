package dk.sdu.cbse.game;

import dk.sdu.cbse.asteroid.AsteroidPlugin;
import dk.sdu.cbse.asteroid.AsteroidProcessor;
import dk.sdu.cbse.bullet.BulletProcessor;
import dk.sdu.cbse.collision.CollisionProcessor;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.player.PlayerPlugin;
import dk.sdu.cbse.player.PlayerProcessor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AsteroidsFX");
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Group(canvas));

        primaryStage.setScene(scene);
        primaryStage.show();

        GameData gameData = new GameData();
        GameWorld gameWorld = new GameWorld();

        // create plugin instance
        PlayerPlugin playerPlugin = new PlayerPlugin();
        playerPlugin.start(gameData, gameWorld);   // adds player to world

        AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
        asteroidPlugin.start(gameData,gameWorld);


        // add processors (movement, rendering, etc.) manually
        List<IEntityProcessingService> processors = new ArrayList<>();
        processors.add(new PlayerProcessor());      // handles input
        processors.add(new AsteroidProcessor());
        processors.add(new BulletProcessor());

        RenderProcessor renderer = new RenderProcessor();

        scene.setOnKeyPressed(e -> gameData.getKeys().press(e.getCode().toString()));
        scene.setOnKeyReleased(e -> gameData.getKeys().release(e.getCode().toString()));

        gameData.setGraphicsContext(gc);

        List<IPostEntityProcessingService> postProcessors = new ArrayList<>();
        List<IGamePluginService> plugins = new ArrayList<>();

        postProcessors.add(new CollisionProcessor());

        for (IGamePluginService plugin : plugins) {
            plugin.start(gameData, gameWorld);
        }

        new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight());

                if (last == 0) {
                    last = now;
                    return;
                }

                double delta = (now - last) / 1_000_000_000.0;
                last = now;

                gameData.setDelta(delta);

                // update processors
                for (IEntityProcessingService processor : processors) {
                    processor.process(gameData, gameWorld);
                }

                // run postprocessors
                for (IPostEntityProcessingService postProcessor : postProcessors) {
                    postProcessor.process(gameData, gameWorld);
                }

                // render
                renderer.render(gameData, gameWorld);
            }

        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}