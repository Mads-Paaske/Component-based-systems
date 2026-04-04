package dk.sdu.cbse.game;

import dk.sdu.cbse.engine.GameObject;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private List<GameObject> objects = new ArrayList<>();

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public void update() {
        for (GameObject obj : objects) {
            obj.update();
        }
    }

    public void render(GraphicsContext gc) {
        for (GameObject obj : objects) {
            obj.render(gc);
        }
    }
}