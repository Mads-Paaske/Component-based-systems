package dk.sdu.cbse.player;

import dk.sdu.cbse.engine.BulletTag;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.RenderComponent;
import dk.sdu.cbse.engine.TransformComponent;
import dk.sdu.cbse.engine.VelocityComponent;

import java.util.ArrayList;
import java.util.List;

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

                    GameObject bullet = new GameObject();

                    TransformComponent t = new TransformComponent();
                    RenderComponent r = new RenderComponent();
                    VelocityComponent v = new VelocityComponent();

                    double rad = Math.toRadians(transform.rotation);

                    double dirX = Math.sin(rad);
                    double dirY = -Math.cos(rad);

                    double offset = 20;
                    double speed = 400;

                    // spawn in front of player
                    t.x = transform.x + dirX * offset;
                    t.y = transform.y + dirY * offset;
                    t.rotation = transform.rotation;

                    v.dx = dirX * speed;
                    v.dy = dirY * speed;

                    r.size = 5;
                    r.color = "BLUE";

                    bullet.addComponent(t);
                    bullet.addComponent(v);
                    bullet.addComponent(r);
                    bullet.addComponent(new BulletTag());

                    toAdd.add(bullet);
                }

                // optional friction to slow down over time
                velocity.dx *= friction;
                velocity.dy *= friction;

                // screen wrap (Asteroids style)
                if (transform.x < 0) transform.x = gameData.getDisplayWidth();
                if (transform.x > gameData.getDisplayWidth()) transform.x = 0;

                if (transform.y < 0) transform.y = gameData.getDisplayHeight();
                if (transform.y > gameData.getDisplayHeight()) transform.y = 0;
            }
        }
        for (GameObject obj : toAdd) {
            gameWorld.addObject(obj);
        }
    }
}
