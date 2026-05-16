package dk.sdu.cbse.bullet;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.services.BulletSPI;
import dk.sdu.cbse.engine.*;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.VelocityComponent;
import dk.sdu.cbse.engine.components.OwnerComponent;
import dk.sdu.cbse.engine.tags.BulletTag;
import dk.sdu.cbse.engine.tags.EnemyTag;
import dk.sdu.cbse.engine.tags.PlayerTag;


public class BulletPlugin implements BulletSPI {

    @Override
    public GameObject createBullet(GameObject shooter, GameData gameData) {

        TransformComponent shooterTransform =
                shooter.getComponent(TransformComponent.class);

        if (shooterTransform == null) {
            return null;
        }

        GameObject bullet = new GameObject();

        TransformComponent t = new TransformComponent();
        VelocityComponent v = new VelocityComponent();
        RenderComponent r = new RenderComponent();

        double rad = Math.toRadians(shooterTransform.rotation);

        double dirX = Math.sin(rad);
        double dirY = -Math.cos(rad);

        double offset = 20;
        double speed = 400;

        // spawn position
        t.x = shooterTransform.x + dirX * offset;
        t.y = shooterTransform.y + dirY * offset;
        t.rotation = shooterTransform.rotation;

        // velocity
        v.dx = dirX * speed;
        v.dy = dirY * speed;

        // render
        r.size = 5;
        r.color = "BLUE";

        bullet.addComponent(t);
        bullet.addComponent(v);
        bullet.addComponent(r);
        bullet.addComponent(new BulletTag());

        //
        OwnerComponent owner = new OwnerComponent();
        if (shooter.getComponent(PlayerTag.class) != null) {
            owner.ownerType = OwnerComponent.OwnerType.PLAYER;
        } else if (shooter.getComponent(EnemyTag.class) != null) {
            owner.ownerType = OwnerComponent.OwnerType.ENEMY;
        }
        bullet.addComponent(owner);

        return bullet;
    }
}