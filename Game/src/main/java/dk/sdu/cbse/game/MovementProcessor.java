package dk.sdu.cbse.game;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.MovementComponent;

public class MovementProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld gameWorld) {
        for (GameObject entity : gameWorld.getObjects()) {

            MovementComponent movement =
                    entity.getComponent(MovementComponent.class);

            if (movement != null) {
                movement.update(gameData.getDelta());
            }
        }
    }
}