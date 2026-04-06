package dk.sdu.cbse.common.data;

import javafx.scene.canvas.GraphicsContext;

public class GameData {

    private double delta;

    private int displayWidth = 800;
    private int displayHeight = 600;

    private Keys keys = new Keys();

    private GraphicsContext gc;

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setGraphicsContext(GraphicsContext graphics)
    {
        gc = graphics;
    }
    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public Keys getKeys() { return keys; }
}
