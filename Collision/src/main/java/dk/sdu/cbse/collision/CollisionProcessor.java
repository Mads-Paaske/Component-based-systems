package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.engine.*;
import dk.sdu.cbse.player.PlayerTag;
import dk.sdu.cbse.asteroid.AsteroidTag;
import dk.sdu.cbse.bullet.BulletTag;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        List<GameObject> toRemove = new ArrayList<>();

        GameObject player = null;

        // Find player
        for (GameObject e : world.getObjects()) {
            if (e.getComponent(PlayerTag.class) != null) {
                player = e;
                break;
            }
        }

        // Player vs Asteroid
        if (player != null) {

            TransformComponent pT = player.getComponent(TransformComponent.class);
            RenderComponent pR = player.getComponent(RenderComponent.class);

            for (GameObject asteroid : world.getObjects()) {

                if (asteroid.getComponent(AsteroidTag.class) == null) continue;

                TransformComponent aT = asteroid.getComponent(TransformComponent.class);
                RenderComponent aR = asteroid.getComponent(RenderComponent.class);

                double dx = pT.x - aT.x;
                double dy = pT.y - aT.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < (pR.size + aR.size) / 2) {
                    toRemove.add(player);
                    System.out.println("Player died!");
                }
            }
        }

        // Bullet vs Asteroid
        for (GameObject bullet : world.getObjects()) {

            if (bullet.getComponent(BulletTag.class) == null) continue;

            TransformComponent bT = bullet.getComponent(TransformComponent.class);
            RenderComponent bR = bullet.getComponent(RenderComponent.class);

            for (GameObject asteroid : world.getObjects()) {

                if (asteroid.getComponent(AsteroidTag.class) == null) continue;

                TransformComponent aT = asteroid.getComponent(TransformComponent.class);
                RenderComponent aR = asteroid.getComponent(RenderComponent.class);

                double dx = bT.x - aT.x;
                double dy = bT.y - aT.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < (bR.size + aR.size) / 2) {
                    toRemove.add(bullet);
                    toRemove.add(asteroid);
                    System.out.println("Asteroid destroyed!");
                }
            }
        }

        // Remove after loop
        for (GameObject e : toRemove) {
            world.removeObject(e);
        }
    }
}