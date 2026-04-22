package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.engine.*;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private final Random random = new Random();

    @Override
    public void start(GameData gameData, GameWorld world) {

        for (int i = 0; i < 5; i++) {

            GameObject asteroid = new GameObject();

            TransformComponent transform = new TransformComponent();
            transform.x = random.nextInt(gameData.getDisplayWidth());
            transform.y = random.nextInt(gameData.getDisplayHeight());

            VelocityComponent velocity = new VelocityComponent();
            velocity.dx = random.nextDouble() * 100 - 50;
            velocity.dy = random.nextDouble() * 100 - 50;

            RenderComponent render = new RenderComponent();
            render.shape = RenderComponent.Shape.CIRCLE;
            render.size = 30;
            render.color = "GRAY";

            asteroid.addComponent(transform);
            asteroid.addComponent(velocity);
            asteroid.addComponent(render);
            asteroid.addComponent(new AsteroidTag());

            world.addObject(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, GameWorld world) {
        // optional cleanup
    }
}