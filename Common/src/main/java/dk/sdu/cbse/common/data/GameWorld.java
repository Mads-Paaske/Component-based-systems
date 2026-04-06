package dk.sdu.cbse.common.data;


import dk.sdu.cbse.engine.GameObject;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private List<GameObject> objects = new ArrayList<>();

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

}