package dk.sdu.cbse.game;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class GameApp extends Application {

    private final GameData gameData = new GameData();
    private final GameWorld gameWorld = new GameWorld();

    private final List<IEntityProcessingService> processors = new ArrayList<>();
    private final List<IPostEntityProcessingService> postProcessors = new ArrayList<>();
    private final List<IGamePluginService> plugins = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("AsteroidsFX");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Group(canvas));

        primaryStage.setScene(scene);
        primaryStage.show();

        gameData.setGraphicsContext(gc);

        // keyboard input
        scene.setOnKeyPressed(
                e -> gameData.getKeys().press(e.getCode().toString())
        );

        scene.setOnKeyReleased(
                e -> gameData.getKeys().release(e.getCode().toString())
        );

        // LOAD GAME PLUGINS
        for (IGamePluginService plugin :
                ServiceLoader.load(IGamePluginService.class)) {

            plugins.add(plugin);
            plugin.start(gameData, gameWorld);
        }

        // LOAD ENTITY PROCESSORS
        for (IEntityProcessingService processor :
                ServiceLoader.load(IEntityProcessingService.class)) {

            processors.add(processor);
        }

        // LOAD POST PROCESSORS
        for (IPostEntityProcessingService postProcessor :
                ServiceLoader.load(IPostEntityProcessingService.class)) {

            postProcessors.add(postProcessor);
        }

        RenderProcessor renderer = new RenderProcessor();

        new AnimationTimer() {

            private long last = 0;

            @Override
            public void handle(long now) {

                if (last == 0) {
                    last = now;
                    return;
                }

                double delta =
                        (now - last) / 1_000_000_000.0;

                last = now;

                gameData.setDelta(delta);

                gc.clearRect(
                        0,
                        0,
                        gameData.getDisplayWidth(),
                        gameData.getDisplayHeight()
                );

                // processors
                for (IEntityProcessingService processor : processors) {
                    processor.process(gameData, gameWorld);
                }

                // post processors
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