package dk.sdu.cbse.splitpackage;

import dk.sdu.cbse.common.services.BulletSPI;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.VelocityComponent;
import dk.sdu.cbse.engine.tags.EnemyTag;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class SpaceshipProcessor implements IEntityProcessingService {
    private double moveCooldown = 0;
    private double shotCooldown = 0;
    private int action = 0;

    @Override
    public void process(GameData gameData, GameWorld gameWorld) {

        List<GameObject> toAdd = new ArrayList<>();


        for (GameObject entity : gameWorld.getObjects())
        {
            if (entity.getComponent(EnemyTag.class) != null)
            {
                double rotationSpeed = 180; // degrees/sec
                double thrust = 200;        // pixels/sec^2
                double friction = 0.99;

                moveCooldown -= gameData.getDelta();

                if (moveCooldown <= 0){
                    moveCooldown = 0.5;
                    action = (int)(Math.random() * 4); // generate one of three moves
                }

                TransformComponent transform = entity.getComponent(TransformComponent.class);
                VelocityComponent velocity = entity.getComponent(VelocityComponent.class);

                if (transform == null || velocity == null) continue;

                if (action == 0) transform.rotation -= rotationSpeed * gameData.getDelta();
                if (action == 1) transform.rotation += rotationSpeed * gameData.getDelta();
                if (action == 2){
                    double angleRad = Math.toRadians(transform.rotation);
                    velocity.dx += Math.sin(angleRad) * thrust * gameData.getDelta();
                    velocity.dy -= Math.cos(angleRad) * thrust * gameData.getDelta();
                }

                shotCooldown -= gameData.getDelta();

                if (action == 3 && shotCooldown <= 0)
                {
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
