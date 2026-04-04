package dk.sdu.cbse.engine;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        component.setGameObject(this);
        components.add(component);
    }

    public void update() {
        for (Component c : components) {
            c.update();
        }
    }

    public void render(GraphicsContext gc) {
        for (Component c : components) {
            c.render(gc);
        }
    }
}