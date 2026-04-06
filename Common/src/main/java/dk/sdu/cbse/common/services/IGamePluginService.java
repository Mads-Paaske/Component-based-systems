package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;

public interface IGamePluginService {

    void start(GameData gameData, GameWorld gameWorld);

    void stop(GameData gameData, GameWorld gameWorld);
}
