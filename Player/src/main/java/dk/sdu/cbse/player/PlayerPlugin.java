package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.engine.*;

public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld gameWorld) {

        GameObject player = new GameObject();

        TransformComponent transform = new TransformComponent();
        transform.x = gameData.getDisplayWidth() / 2;
        transform.y = gameData.getDisplayHeight() / 2;

        player.addComponent(transform);
        player.addComponent(new VelocityComponent());
        player.addComponent(new PlayerTag());
        RenderComponent render = new RenderComponent();
        render.shape = RenderComponent.Shape.TRIANGLE;
        render.size = 20;
        render.color = "RED";
        player.addComponent(render);

        gameWorld.addObject(player);
    }

    @Override
    public void stop(GameData gameData, GameWorld gameWorld) {
        // remove player if needed
    }
}