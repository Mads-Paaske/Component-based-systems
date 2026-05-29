package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameWorld;
import dk.sdu.cbse.common.data.Keys;
import dk.sdu.cbse.common.data.GameObject;
import dk.sdu.cbse.engine.components.TransformComponent;
import dk.sdu.cbse.engine.components.VelocityComponent;
import dk.sdu.cbse.engine.tags.PlayerTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerTest {

    // Mocks
    @Mock GameData gameData;
    @Mock GameWorld gameWorld;
    @Mock Keys keys;
    // Create components & player
    PlayerProcessor processor;
    GameObject player;
    TransformComponent transform;
    VelocityComponent velocity;

    @BeforeEach
    void setUp() {
        processor = new PlayerProcessor();

        // Build a minimal player entity
        player = new GameObject();
        transform = new TransformComponent();
        velocity = new VelocityComponent();
        player.addComponent(transform);
        player.addComponent(velocity);
        player.addComponent(new PlayerTag());

        // Tell the mocks what to return when the processor calls them
        when(gameData.getKeys()).thenReturn(keys);
        when(gameData.getDelta()).thenReturn(0.016); // ~60fps frame
        when(gameWorld.getObjects()).thenReturn(List.of(player));
    }

    @Test
    void rotatesLeftWhenLeftKeyIsDown() {
        when(keys.isDown("LEFT")).thenReturn(true);

        processor.process(gameData, gameWorld);

        // rotation should have decreased (negative direction)
        assertTrue(transform.rotation < 0,
                "Rotation should decrease when LEFT is held");
    }

    @Test
    void rotatesRightWhenRightKeyIsDown() {
        when(keys.isDown("LEFT")).thenReturn(false);
        when(keys.isDown("RIGHT")).thenReturn(true);
        when(keys.isDown("UP")).thenReturn(false);

        processor.process(gameData, gameWorld);

        assertTrue(transform.rotation > 0);
    }

    @Test
    void thrustIncreasesVelocityInFacingDirection() {
        when(keys.isDown("LEFT")).thenReturn(false);
        when(keys.isDown("RIGHT")).thenReturn(false);
        when(keys.isDown("UP")).thenReturn(true);

        processor.process(gameData, gameWorld);

        assertTrue(velocity.dy < 0);
    }

    @Test
    void frictionSlowsShipDown() {
        velocity.dx = 100;
        velocity.dy = 100;

        // no keys pressed
        processor.process(gameData, gameWorld);

        assertTrue(velocity.dx < 100, "Friction should reduce dx");
        assertTrue(velocity.dy < 100, "Friction should reduce dy");
    }

    @Test
    void noMovementWhenNoKeysPressed() {
        // all keys return false by default in Mockito
        double startRotation = transform.rotation;

        processor.process(gameData, gameWorld);

        assertEquals(startRotation, transform.rotation,
                "Rotation should not change with no input");
    }
}