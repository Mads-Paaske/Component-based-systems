package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;

public interface IEntityProcessingService {

    /**
     *
     *
     *
     * @param gameData
     * @param gameWorld
     * @throws
     */
    void process(GameData gameData, GameWorld gameWorld);
}