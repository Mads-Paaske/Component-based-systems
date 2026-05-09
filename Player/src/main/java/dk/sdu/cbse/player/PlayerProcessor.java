package dk.sdu.cbse.player;

import dk.sdu.cbse.common.services.BulletSPI;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.TransformComponent;
import dk.sdu.cbse.engine.VelocityComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PlayerProcessor implements IEntityProcessingService {
    private double shotCooldown = 0;

    @Override
    public void process(GameData gameData, GameWorld gameWorld) {

        List<GameObject> toAdd = new ArrayList<>();


        for (GameObject entity : gameWorld.getObjects()) {

            if (entity.getComponent(dk.sdu.cbse.engine.PlayerTag.class) != null) {

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

                // shooting
                shotCooldown -= gameData.getDelta();

                if (gameData.getKeys().isDown("SPACE") && shotCooldown <= 0) {

                    shotCooldown = 0.3;

                    ServiceLoader.load(BulletSPI.class)
                            .findFirst()
                            .ifPresent(spi -> {

                                GameObject bullet =
                                        spi.createBullet(entity, gameData);

                                if (bullet != null) {
                                    toAdd.add(bullet);
                                }
                            });
                }

                // optional friction to slow down over time
                velocity.dx *= friction;
                velocity.dy *= friction;
            }
        }
        for (GameObject obj : toAdd) {
            gameWorld.addObject(obj);
        }
    }
}
