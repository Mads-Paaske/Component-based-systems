package dk.sdu.cbse.engine;

import javafx.scene.canvas.GraphicsContext;

public abstract class Component {

    protected GameObject gameObject;

    // Called when component is added to a GameObject
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    // Called every frame (logic)
    public void update() {
        // default = do nothing
    }

    // Called every frame (rendering)
    public void render(GraphicsContext gc) {
        // default = do nothing
    }
}
