package dk.sdu.cbse.screenwrap;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.WrapComponent;

public class WrapProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        for (dk.sdu.cbse.common.data.GameObject entity : world.getObjects()) {

            if (entity.getComponent(WrapComponent.class) == null) {
                continue;
            }

            TransformComponent t =
                    entity.getComponent(TransformComponent.class);

            if (t == null) continue;

            if (t.x < 0) {
                t.x = gameData.getDisplayWidth();
            }

            if (t.x > gameData.getDisplayWidth()) {
                t.x = 0;
            }

            if (t.y < 0) {
                t.y = gameData.getDisplayHeight();
            }

            if (t.y > gameData.getDisplayHeight()) {
                t.y = 0;
            }
        }
    }
}