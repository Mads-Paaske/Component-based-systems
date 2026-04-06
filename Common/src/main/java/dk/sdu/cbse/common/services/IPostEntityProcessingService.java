package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;

public interface IPostEntityProcessingService {

    void process(GameData gameData, GameWorld gameWorld);
}
