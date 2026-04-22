package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.engine.*;
import dk.sdu.cbse.player.PlayerTag;
import dk.sdu.cbse.asteroid.AsteroidTag;

public class CollisionProcessor implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        GameObject player = null;

        // find player
        for (GameObject e : world.getObjects()) {
            if (e.getComponent(PlayerTag.class) != null) {
                player = e;
                break;
            }
        }

        if (player == null) return;

        TransformComponent pT = player.getComponent(TransformComponent.class);
        RenderComponent pR = player.getComponent(RenderComponent.class);

        for (GameObject asteroid : world.getObjects()) {

            if (asteroid.getComponent(AsteroidTag.class) != null) {

                TransformComponent aT = asteroid.getComponent(TransformComponent.class);
                RenderComponent aR = asteroid.getComponent(RenderComponent.class);

                double dx = pT.x - aT.x;
                double dy = pT.y - aT.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < (pR.size + aR.size) / 2) {
                    world.removeObject(player);
                    System.out.println("Player died!");
                    return;
                }
            }
        }
    }
}