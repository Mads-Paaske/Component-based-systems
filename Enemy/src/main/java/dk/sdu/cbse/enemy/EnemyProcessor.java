package dk.sdu.cbse.enemy;

import dk.sdu.cbse.bullet.BulletTag;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.RenderComponent;
import dk.sdu.cbse.engine.TransformComponent;
import dk.sdu.cbse.engine.VelocityComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyProcessor implements IEntityProcessingService {
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

                    GameObject bullet = new GameObject();

                    TransformComponent t = new TransformComponent();
                    t.x = transform.x;
                    t.y = transform.y;
                    t.rotation = transform.rotation;

                    VelocityComponent v = new VelocityComponent();
                    double speed = 400;

                    double angleRad = Math.toRadians(transform.rotation);
                    v.dx = Math.sin(angleRad) * speed;
                    v.dy = -Math.cos(angleRad) * speed;

                    RenderComponent r = new RenderComponent();
                    r.size = 5;
                    r.color = "BLUE";

                    bullet.addComponent(t);
                    bullet.addComponent(v);
                    bullet.addComponent(r);
                    bullet.addComponent(new BulletTag());

                    toAdd.add(bullet);
                }

                // screen wrap (Asteroids style)
                if (transform.x < 0) transform.x = gameData.getDisplayWidth();
                if (transform.x > gameData.getDisplayWidth()) transform.x = 0;

                if (transform.y < 0) transform.y = gameData.getDisplayHeight();
                if (transform.y > gameData.getDisplayHeight()) transform.y = 0;

                // update position
                transform.x += velocity.dx * gameData.getDelta();
                transform.y += velocity.dy * gameData.getDelta();

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
