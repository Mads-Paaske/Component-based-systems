package dk.sdu.cbse.engine.components;

public class RenderComponent implements Component {

    public enum Shape { CIRCLE, TRIANGLE, SQUARE }

    public Shape shape = Shape.CIRCLE;
    public double size = 10;
    public String color = "BLACK";

}