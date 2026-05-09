package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.engine.*;
import dk.sdu.cbse.common.services.AsteroidSplitterSPI;
import dk.sdu.cbse.engine.components.Component;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.tags.AsteroidTag;
import dk.sdu.cbse.engine.tags.BulletTag;
import dk.sdu.cbse.engine.tags.EnemyTag;
import dk.sdu.cbse.engine.tags.PlayerTag;

import java.util.ServiceLoader;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, GameWorld world) {

        List<GameObject> toRemove = new ArrayList<>();
        List<GameObject> toAdd = new ArrayList<>();

        List<GameObject> players = getEntities(world, PlayerTag.class);
        List<GameObject> bullets = getEntities(world, BulletTag.class);
        List<GameObject> asteroids = getEntities(world, AsteroidTag.class);
        List<GameObject> enemies = getEntities(world, EnemyTag.class);

        // Player vs Asteroid
        for (GameObject player : players) {
            for (GameObject asteroid : asteroids) {

                if (collides(player, asteroid)) {
                    toRemove.add(player);
                    System.out.println("Player died!");
                }
            }
        }

        // Bullet vs Asteroid
        for (GameObject bullet : bullets) {
            for (GameObject asteroid : asteroids) {

                if (collides(bullet, asteroid)) {

                    toRemove.add(bullet);

                    ServiceLoader.load(AsteroidSplitterSPI.class)
                            .findFirst()
                            .ifPresent(spi -> {
                                toAdd.addAll(spi.splitAsteroid(asteroid));
                            });

                    toRemove.add(asteroid);

                    System.out.println("Asteroid destroyed!");
                }
            }
        }

        // Bullet vs Enemy
        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {

                if (collides(bullet, enemy)) {

                    toRemove.add(bullet);
                    toRemove.add(enemy);

                    System.out.println("Enemy destroyed!");
                }
            }
        }

        // Bullet vs Player
        for (GameObject bullet : bullets) {
            for (GameObject player : players) {

                if (collides(bullet, player)) {

                    toRemove.add(bullet);
                    toRemove.add(player);

                    System.out.println("Player destroyed!");
                }
            }
        }

        // Player vs Enemy
        for (GameObject player : players) {
            for (GameObject enemy : enemies) {

                if (collides(player, enemy)) {
                    toRemove.add(player);
                    System.out.println("Player died!");
                }
            }
        }

        // Asteroid vs Enemy
        for (GameObject asteroid : asteroids) {
            for (GameObject enemy : enemies) {

                if (collides(asteroid, enemy)) {
                    toRemove.add(asteroid);
                    System.out.println("Player died!");
                }
            }
        }


        // remove entities
        for (GameObject e : toRemove) {
            world.removeObject(e);
        }

        // add new entities
        for (GameObject e : toAdd) {
            world.addObject(e);
        }
    }

    private boolean collides(GameObject a, GameObject b) {

        TransformComponent aT = a.getComponent(TransformComponent.class);
        TransformComponent bT = b.getComponent(TransformComponent.class);

        RenderComponent aR = a.getComponent(RenderComponent.class);
        RenderComponent bR = b.getComponent(RenderComponent.class);

        if (aT == null || bT == null || aR == null || bR == null) {
            return false;
        }

        double dx = aT.x - bT.x;
        double dy = aT.y - bT.y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (aR.size + bR.size) / 2;
    }

    private List<GameObject> getEntities(
            GameWorld world,
            Class<? extends Component> componentClass
    ) {

        List<GameObject> result = new ArrayList<>();

        for (GameObject entity : world.getObjects()) {

            if (entity.getComponent(componentClass) != null) {
                result.add(entity);
            }
        }

        return result;
    }
}