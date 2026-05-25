package dk.sdu.cbse.engine.components;

import dk.sdu.cbse.common.services.Component;

public class RenderComponent implements Component {

    public enum Shape { CIRCLE, TRIANGLE, SQUARE }

    public Shape shape = Shape.CIRCLE;
    public double size = 10;
    public String color = "BLACK";

}