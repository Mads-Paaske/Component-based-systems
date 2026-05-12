package dk.sdu.cbse.game;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.List;

public class Game {

    private final GameData gameData = new GameData();
    private final GameWorld gameWorld = new GameWorld();

    private final List<IGamePluginService> plugins;
    private final List<IEntityProcessingService> processors;
    private final List<IPostEntityProcessingService> postProcessors;

    // Spring calls this constructor and injects the lists
    public Game(List<IGamePluginService> plugins,
                List<IEntityProcessingService> processors,
                List<IPostEntityProcessingService> postProcessors) {
        this.plugins = plugins;
        this.processors = processors;
        this.postProcessors = postProcessors;
    }

    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Group(canvas));
        primaryStage.setTitle("AsteroidsFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameData.setGraphicsContext(gc);

        scene.setOnKeyPressed(e -> gameData.getKeys().press(e.getCode().toString()));
        scene.setOnKeyReleased(e -> gameData.getKeys().release(e.getCode().toString()));

        for (IGamePluginService plugin : plugins) {
            plugin.start(gameData, gameWorld);
        }
    }

    public void render() {
        RenderProcessor renderer = new RenderProcessor();

        new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { last = now; return; }

                double delta = (now - last) / 1_000_000_000.0;
                last = now;
                gameData.setDelta(delta);

                // gc needs to be accessible here — store it as a field in Game
                for (IEntityProcessingService p : processors) {
                    p.process(gameData, gameWorld);
                }
                for (IPostEntityProcessingService p : postProcessors) {
                    p.process(gameData, gameWorld);
                }
                renderer.render(gameData, gameWorld);
            }
        }.start();
    }
}