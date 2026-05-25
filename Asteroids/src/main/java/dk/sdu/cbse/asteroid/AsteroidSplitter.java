package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.services.AsteroidSplitterSPI;
import dk.sdu.cbse.engine.components.AsteroidComponent;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.VelocityComponent;
import dk.sdu.cbse.engine.tags.AsteroidTag;
import dk.sdu.cbse.common.data.GameObject;

import java.util.ArrayList;
import java.util.List;

public class AsteroidSplitter implements AsteroidSplitterSPI {

    @Override
    public List<GameObject> splitAsteroid(GameObject asteroid) {

        List<GameObject> result = new ArrayList<>();

        AsteroidComponent ac =
                asteroid.getComponent(AsteroidComponent.class);

        if (ac == null || ac.level <= 1) {
            return result;
        }

        TransformComponent t =
                asteroid.getComponent(TransformComponent.class);

        VelocityComponent v =
                asteroid.getComponent(VelocityComponent.class);

        RenderComponent r =
                asteroid.getComponent(RenderComponent.class);

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

            result.add(child);
        }

        return result;
    }
}