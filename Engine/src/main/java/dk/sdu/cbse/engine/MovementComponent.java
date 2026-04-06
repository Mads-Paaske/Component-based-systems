package dk.sdu.cbse.engine;

public class MovementComponent implements Component {

    private GameObject gameObject;

    @Override
    public void start(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void update(double deltaTime) {
        TransformComponent transform =
                gameObject.getComponent(TransformComponent.class);

        if (transform != null) {
            transform.x += 100 * deltaTime;
        }
    }
}
