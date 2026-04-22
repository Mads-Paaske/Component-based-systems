package dk.sdu.cbse.bullet;

import dk.sdu.cbse.asteroid.AsteroidTag;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.*;

import java.util.ArrayList;
import java.util.List;

public class BulletProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        List<GameObject> toRemove = new ArrayList<>();

        for (GameObject entity : world.getObjects()) {

            if (entity.getComponent(BulletTag.class) != null) {

                TransformComponent t = entity.getComponent(TransformComponent.class);
                VelocityComponent v = entity.getComponent(VelocityComponent.class);

                if (t == null || v == null) continue;

                // move bullet
                t.x += v.dx * gameData.getDelta();
                t.y += v.dy * gameData.getDelta();

                // remove if off screen
                if (t.x < 0 || t.x > gameData.getDisplayWidth()
                        || t.y < 0 || t.y > gameData.getDisplayHeight()) {

                    toRemove.add(entity);
                }
            }
        }

        for (GameObject e : toRemove) {
            world.removeObject(e);
        }
    }
}