package dk.sdu.cbse.collision;

import dk.sdu.cbse.asteroid.AsteroidComponent;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.enemy.EnemyTag;
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
        List<GameObject> toAdd = new ArrayList<>();

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
                    splitAsteroid(asteroid, toAdd, toRemove);
                    System.out.println("Asteroid destroyed!");
                }
            }
        }

        // Bullet vs Enemy
        for (GameObject bullet : world.getObjects()) {

            if (bullet.getComponent(BulletTag.class) == null) continue;

            TransformComponent bT = bullet.getComponent(TransformComponent.class);
            RenderComponent bR = bullet.getComponent(RenderComponent.class);

            for (GameObject enemy : world.getObjects()) {

                if (enemy.getComponent(EnemyTag.class) == null) continue;

                TransformComponent aT = enemy.getComponent(TransformComponent.class);
                RenderComponent aR = enemy.getComponent(RenderComponent.class);

                double dx = bT.x - aT.x;
                double dy = bT.y - aT.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < (bR.size + aR.size) / 2) {
                    toRemove.add(bullet);
                    toRemove.add(enemy);
                    System.out.println("Enemy destroyed!");
                }
            }
        }

        // Bullet vs Player
        for (GameObject bullet : world.getObjects()) {

            if (bullet.getComponent(BulletTag.class) == null) continue;

            TransformComponent bT = bullet.getComponent(TransformComponent.class);
            RenderComponent bR = bullet.getComponent(RenderComponent.class);

            for (GameObject players : world.getObjects()) {

                if (players.getComponent(PlayerTag.class) == null) continue;

                TransformComponent aT = players.getComponent(TransformComponent.class);
                RenderComponent aR = players.getComponent(RenderComponent.class);

                double dx = bT.x - aT.x;
                double dy = bT.y - aT.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < (bR.size + aR.size) / 2) {
                    toRemove.add(bullet);
                    toRemove.add(players);
                    System.out.println("Player destroyed!");
                }
            }
        }

        // Remove after loop
        for (GameObject e : toRemove) {
            world.removeObject(e);
        }
        // Add after loop
        for (GameObject obj : toAdd) {
            world.addObject(obj);
        }
    }
    private void splitAsteroid(GameObject asteroid,
                               List<GameObject> toAdd,
                               List<GameObject> toRemove) {

        AsteroidComponent ac = asteroid.getComponent(AsteroidComponent.class);

        // always remove original asteroid
        toRemove.add(asteroid);

        // if too small → just destroy
        if (ac == null || ac.level <= 1) {
            System.out.println("Asteroid destroyed!");
            return;
        }

        TransformComponent t = asteroid.getComponent(TransformComponent.class);
        VelocityComponent v = asteroid.getComponent(VelocityComponent.class);
        RenderComponent r = asteroid.getComponent(RenderComponent.class);

        for (int i = 0; i < 2; i++) {

            GameObject child = new GameObject();

            TransformComponent nt = new TransformComponent();
            nt.x = t.x;
            nt.y = t.y;

            VelocityComponent nv = new VelocityComponent();
            nv.dx = v.dx + (Math.random() - 0.5) * 100;
            nv.dy = v.dy + (Math.random() - 0.5) * 100;

            RenderComponent nr = new RenderComponent();
            nr.shape = RenderComponent.Shape.CIRCLE;
            nr.color = "GRAY";
            nr.size = r.size * 0.6;

            AsteroidComponent nac = new AsteroidComponent();
            nac.level = ac.level - 1;

            child.addComponent(nt);
            child.addComponent(nv);
            child.addComponent(nr);
            child.addComponent(nac);
            child.addComponent(new AsteroidTag());

            toAdd.add(child);
        }

        System.out.println("Asteroid split!");
    }
}