package dk.sdu.cbse.game;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.data.GameObject;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import dk.sdu.cbse.engine.components.HealthComponent;
import dk.sdu.cbse.engine.tags.EnemyTag;
import dk.sdu.cbse.engine.tags.PlayerTag;

public class RenderProcessor {

    public void render(GameData gameData, GameWorld world) {
        GraphicsContext gc = gameData.getGraphicsContext();
        gc.clearRect(0, 0, gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc.setFill(Paint.valueOf("BLACK"));
        gc.setFont(javafx.scene.text.Font.font("Arial", 20));
        gc.fillText("Score: " + gameData.getScore(), 10, 25);

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

                if (entity.getComponent(EnemyTag.class) != null) {
                    HealthComponent health = entity.getComponent(HealthComponent.class);
                    if (health != null && transform != null) {
                        double barWidth = 40;
                        double barHeight = 6;
                        double barX = transform.x - barWidth / 2;
                        double barY = transform.y - render.size - 12;
                        double healthRatio = (double) health.currentHealth / health.maxHealth;

                        // background bar
                        gc.setFill(Paint.valueOf("RED"));
                        gc.fillRect(barX, barY, barWidth, barHeight);

                        // foreground bar
                        gc.setFill(Paint.valueOf("GREEN"));
                        gc.fillRect(barX, barY, barWidth * healthRatio, barHeight);
                    }
                }
            }
        }

        // Draw player health bar near score
        for (GameObject entity : world.getObjects()) {
            if (entity.getComponent(PlayerTag.class) != null) {
                HealthComponent health = entity.getComponent(HealthComponent.class);
                if (health != null) {
                    double barWidth = 100;
                    double barHeight = 10;
                    double barX = 10;
                    double barY = 35;
                    double healthRatio = (double) health.currentHealth / health.maxHealth;

                    gc.setFill(Paint.valueOf("RED"));
                    gc.fillRect(barX, barY, barWidth, barHeight);

                    gc.setFill(Paint.valueOf("GREEN"));
                    gc.fillRect(barX, barY, barWidth * healthRatio, barHeight);

                    gc.setFill(Paint.valueOf("WHITE"));
                    gc.fillText("Health", barX, barY - 2);
                }
                break;
            }
        }

    }
}