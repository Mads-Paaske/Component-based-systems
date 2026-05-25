package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameObject;

public interface BulletSPI {
    GameObject createBullet(GameObject shooter, GameData gameData);
}