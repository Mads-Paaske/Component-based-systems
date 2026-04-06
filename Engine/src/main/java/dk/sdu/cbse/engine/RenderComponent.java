package dk.sdu.cbse.engine;

public class RenderComponent implements Component {

    public enum Shape { CIRCLE, TRIANGLE, SQUARE }

    public Shape shape = Shape.CIRCLE;
    public double size = 10;
    public String color = "BLACK";

    @Override
    public void start(GameObject gameObject) {}
    @Override
    public void update(double deltaTime) {}
}