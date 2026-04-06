package dk.sdu.cbse.engine;

import javafx.scene.canvas.GraphicsContext;

public interface Component {

    void start(GameObject gameObject);

    void update(double deltaTime);
}
