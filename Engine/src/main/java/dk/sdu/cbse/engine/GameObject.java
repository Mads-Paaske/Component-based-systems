package dk.sdu.cbse.engine;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private final List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
        component.start(this);
    }

    public void update(double deltaTime) {
        for (Component c : components) {
            c.update(deltaTime);
        }
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component c : components) {
            if (type.isAssignableFrom(c.getClass())) {
                return type.cast(c);
            }
        }
        return null;
    }
}