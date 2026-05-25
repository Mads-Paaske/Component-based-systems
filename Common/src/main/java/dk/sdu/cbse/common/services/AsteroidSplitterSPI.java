package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameObject;

import java.util.List;

public interface AsteroidSplitterSPI {

    List<GameObject> splitAsteroid(GameObject asteroid);
}