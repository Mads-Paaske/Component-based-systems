package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.*;

public class AsteroidProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        for (GameObject entity : world.getObjects()) {

            if (entity.getComponent(AsteroidTag.class) != null) {

                TransformComponent transform = entity.getComponent(TransformComponent.class);
                VelocityComponent velocity = entity.getComponent(VelocityComponent.class);

                if (transform == null || velocity == null) continue;

                // move
                transform.x += velocity.dx * gameData.getDelta();
                transform.y += velocity.dy * gameData.getDelta();

                // screen wrap (Asteroids style)
                if (transform.x < 0) transform.x = gameData.getDisplayWidth();
                if (transform.x > gameData.getDisplayWidth()) transform.x = 0;

                if (transform.y < 0) transform.y = gameData.getDisplayHeight();
                if (transform.y > gameData.getDisplayHeight()) transform.y = 0;
            }
        }
    }
}