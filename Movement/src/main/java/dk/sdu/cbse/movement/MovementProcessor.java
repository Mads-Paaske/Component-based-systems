package dk.sdu.cbse.movement;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.VelocityComponent;

public class MovementProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        double delta = gameData.getDelta();

        for (GameObject obj : world.getObjects()) {

            TransformComponent t = obj.getComponent(TransformComponent.class);
            VelocityComponent v = obj.getComponent(VelocityComponent.class);

            if (t != null && v != null) {
                t.x += v.dx * delta;
                t.y += v.dy * delta;
            }
        }
    }
}