package dk.sdu.cbse.game;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class RenderProcessor {

    public void render(GameData gameData, GameWorld world) {
        GraphicsContext gc = gameData.getGraphicsContext();
        gc.clearRect(0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight());

        for (GameObject entity : world.getObjects()) {

            TransformComponent transform = entity.getComponent(TransformComponent.class);
            RenderComponent render = entity.getComponent(RenderComponent.class);

            if (transform != null && render != null) {

                gc.save();
                gc.translate(transform.x, transform.y);
                gc.rotate(transform.rotation);
                gc.setFill(Paint.valueOf(render.color));

                switch (render.shape) {
                    case CIRCLE -> gc.fillOval(-render.size/2, -render.size/2, render.size, render.size);
                    case TRIANGLE -> {
                        double r = render.size;
                        gc.beginPath();
                        gc.moveTo(0, -r);       // tip
                        gc.lineTo(-r/2, r/2);   // bottom left
                        gc.lineTo(r/2, r/2);    // bottom right
                        gc.closePath();
                        gc.fill();
                    }
                    case SQUARE -> gc.fillRect(-render.size/2, -render.size/2, render.size, render.size);
                }

                gc.restore();
            }
        }
    }
}