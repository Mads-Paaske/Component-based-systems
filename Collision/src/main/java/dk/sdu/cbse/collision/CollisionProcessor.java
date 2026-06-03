package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.services.AsteroidSplitterSPI;
import dk.sdu.cbse.common.services.Component;
import dk.sdu.cbse.engine.components.HealthComponent;
import dk.sdu.cbse.engine.components.OwnerComponent;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.tags.AsteroidTag;
import dk.sdu.cbse.engine.tags.BulletTag;
import dk.sdu.cbse.engine.tags.EnemyTag;
import dk.sdu.cbse.engine.tags.PlayerTag;
import dk.sdu.cbse.common.data.GameObject;


import java.util.ServiceLoader;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessingService {

    private final ScoringClient scoringClient = new ScoringClient();

    @Override
    public void process(GameData gameData, GameWorld world) {

        List<GameObject> toRemove = new ArrayList<>();
        List<GameObject> toAdd = new ArrayList<>();

        List<GameObject> players = getEntities(world, PlayerTag.class);
        List<GameObject> bullets = getEntities(world, BulletTag.class);
        List<GameObject> asteroids = getEntities(world, AsteroidTag.class);
        List<GameObject> enemies = getEntities(world, EnemyTag.class);

        for (GameObject entity : world.getObjects()) {
            HealthComponent health = entity.getComponent(HealthComponent.class);
            if (health != null && health.iframeTimer > 0) {
                health.iframeTimer -= (float) gameData.getDelta();
            }
        }

        // Player vs Asteroid
        for (GameObject player : players) {
            for (GameObject asteroid : asteroids) {

                if (collides(player, asteroid)) {

                    HealthComponent playerHealth = player.getComponent(HealthComponent.class);

                    if (playerHealth != null && playerHealth.iframeTimer <= 0) {
                        playerHealth.currentHealth -= 1;
                        playerHealth.iframeTimer = 1.5f; // 1.5 seconds of iframes

                        if (playerHealth.currentHealth <= 0) {
                            toRemove.add(player);
                            System.out.println("Player died!");

                            ServiceLoader.load(AsteroidSplitterSPI.class)
                                    .findFirst()
                                    .ifPresent(spi -> {
                                        toAdd.addAll(spi.splitAsteroid(asteroid));
                                    });
                        }
                    }

                    toRemove.add(asteroid);
                }
            }
        }

        //Bullet vs Asteroid
        for (GameObject bullet : bullets) {
            for (GameObject asteroid : asteroids) {

                if (collides(bullet, asteroid)) {

                    OwnerComponent owner = bullet.getComponent(OwnerComponent.class);
                    if (owner != null && owner.ownerType == OwnerComponent.OwnerType.PLAYER) {
                        gameData.addScore(100);
                        scoringClient.reportScore("Player", gameData.getScore());
                    }
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

                    OwnerComponent owner = bullet.getComponent(OwnerComponent.class);

                    toRemove.add(bullet);

                    HealthComponent enemyHealth = enemy.getComponent(HealthComponent.class);
                    enemyHealth.currentHealth = enemyHealth.currentHealth - 1;

                    if (enemyHealth.currentHealth == 0)
                    {
                        if (owner != null && owner.ownerType == OwnerComponent.OwnerType.PLAYER) {
                            gameData.addScore(250);
                            scoringClient.reportScore("Player", gameData.getScore());
                        }
                        toRemove.add(enemy);
                        System.out.println("Enemy destroyed!");
                    }
                }
            }
        }

        // Bullet vs Player
        for (GameObject bullet : bullets) {
            for (GameObject player : players) {

                if (collides(bullet, player)) {

                    toRemove.add(bullet);

                    HealthComponent playerHealth = player.getComponent(HealthComponent.class);

                    if (playerHealth != null && playerHealth.iframeTimer <= 0) {
                        playerHealth.currentHealth -= 1;
                        playerHealth.iframeTimer = 1.5f; // 1.5 seconds of iframes

                        if (playerHealth.currentHealth <= 0) {
                            toRemove.add(player);
                            System.out.println("Player died!");
                        }
                    }
                }
            }
        }

        // Player vs Enemy
        for (GameObject player : players) {
            for (GameObject enemy : enemies) {

                if (collides(player, enemy)) {
                    HealthComponent playerHealth = player.getComponent(HealthComponent.class);

                    if (playerHealth != null && playerHealth.iframeTimer <= 0) {
                        playerHealth.currentHealth -= 1;
                        playerHealth.iframeTimer = 1.5f; // 1.5 seconds of iframes

                        if (playerHealth.currentHealth <= 0) {
                            toRemove.add(player);
                        }
                    }
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