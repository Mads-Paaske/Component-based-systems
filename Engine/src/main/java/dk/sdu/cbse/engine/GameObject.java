package dk.sdu.cbse.engine;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private final List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
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