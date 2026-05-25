package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.tags.BulletTag;
import dk.sdu.cbse.common.data.GameObject;
import dk.sdu.cbse.engine.components.TransformComponent;

import java.util.ArrayList;
import java.util.List;

public class BulletProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        List<GameObject> toRemove = new ArrayList<>();

        for (GameObject entity : world.getObjects()) {

            if (entity.getComponent(BulletTag.class) == null) continue;

            TransformComponent t =
                    entity.getComponent(TransformComponent.class);

            if (t == null) continue;

            // ONLY removal logic
            if (t.x < 0 || t.x > gameData.getDisplayWidth()
                    || t.y < 0 || t.y > gameData.getDisplayHeight()) {

                toRemove.add(entity);
            }
        }

        for (GameObject e : toRemove) {
            world.removeObject(e);
        }
    }
}