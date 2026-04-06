package dk.sdu.cbse.common.data;

import java.util.HashSet;
import java.util.Set;

public class Keys {

    private final Set<String> pressedKeys = new HashSet<>();

    public void press(String key) {
        pressedKeys.add(key);
    }

    public void release(String key) {
        pressedKeys.remove(key);
    }

    public boolean isDown(String key) {
        return pressedKeys.contains(key);
    }
}