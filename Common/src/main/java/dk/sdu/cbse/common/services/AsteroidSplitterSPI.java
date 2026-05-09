package dk.sdu.cbse.common.services;

import dk.sdu.cbse.engine.GameObject;

import java.util.List;

public interface AsteroidSplitterSPI {

    List<GameObject> splitAsteroid(GameObject asteroid);
}