package dk.sdu.cbse.splitpackage;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.engine.*;
import dk.sdu.cbse.engine.components.RenderComponent;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.VelocityComponent;
import dk.sdu.cbse.engine.components.WrapComponent;
import dk.sdu.cbse.engine.tags.PlayerTag;

public class SpaceshipPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, GameWorld gameWorld) {

        GameObject player = new GameObject();

        TransformComponent transform = new TransformComponent();
        transform.x = (double) gameData.getDisplayWidth() / 2;
        transform.y = (double) gameData.getDisplayHeight() / 2;

        player.addComponent(transform);
        player.addComponent(new VelocityComponent());
        player.addComponent(new PlayerTag());
        player.addComponent(new WrapComponent());
        RenderComponent render = new RenderComponent();
        render.shape = RenderComponent.Shape.TRIANGLE;
        render.size = 20;
        render.color = "GREEN";
        player.addComponent(render);

        gameWorld.addObject(player);
    }

    @Override
    public void stop(GameData gameData, GameWorld gameWorld) {
        // remove player if needed
    }
}