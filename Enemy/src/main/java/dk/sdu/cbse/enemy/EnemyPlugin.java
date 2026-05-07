package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.engine.GameObject;
import dk.sdu.cbse.engine.RenderComponent;
import dk.sdu.cbse.engine.TransformComponent;
import dk.sdu.cbse.engine.VelocityComponent;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService{

    private final Random random = new Random();
    @Override
    public void start(GameData gameData, GameWorld gameWorld) {
        GameObject enemy = new GameObject();

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.dx = random.nextDouble() * 100 - 50;
        velocityComponent.dy = random.nextDouble() * 100 - 50;


        TransformComponent transformComponent = new TransformComponent();

        transformComponent.x = random.nextInt(gameData.getDisplayWidth());
        transformComponent.y = random.nextInt(gameData.getDisplayHeight());

        RenderComponent renderComponent = new RenderComponent();
        renderComponent.shape = RenderComponent.Shape.TRIANGLE;
        renderComponent.color = "RED";
        renderComponent.size = 20;


        enemy.addComponent(velocityComponent);
        enemy.addComponent(transformComponent);
        enemy.addComponent(renderComponent);
        enemy.addComponent(new dk.sdu.cbse.engine.EnemyTag());

        gameWorld.addObject(enemy);
    }

    @Override
    public void stop(GameData gameData, GameWorld gameWorld) {
        // optional cleanup
    }
}
