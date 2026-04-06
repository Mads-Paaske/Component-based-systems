package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.TransformComponent;
import dk.sdu.cbse.engine.VelocityComponent;

public class PlayerProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld gameWorld) {

        for (GameObject entity : gameWorld.getObjects()) {

            if (entity.getComponent(PlayerTag.class) != null) {

                TransformComponent transform = entity.getComponent(TransformComponent.class);
                VelocityComponent velocity = entity.getComponent(VelocityComponent.class);

                double rotationSpeed = 180; // degrees/sec
                double thrust = 200;        // pixels/sec^2
                double friction = 0.99;

                if (gameData.getKeys().isDown("LEFT")) transform.rotation -= rotationSpeed * gameData.getDelta();
                if (gameData.getKeys().isDown("RIGHT")) transform.rotation += rotationSpeed * gameData.getDelta();
                if (gameData.getKeys().isDown("UP")) {
                    double angleRad = Math.toRadians(transform.rotation);
                    velocity.dx += Math.sin(angleRad) * thrust * gameData.getDelta();
                    velocity.dy -= Math.cos(angleRad) * thrust * gameData.getDelta();
                }

                // update position
                transform.x += velocity.dx * gameData.getDelta();
                transform.y += velocity.dy * gameData.getDelta();

                // optional friction to slow down over time
                velocity.dx *= friction;
                velocity.dy *= friction;
            }
        }
    }
}
