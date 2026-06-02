package dk.sdu.cbse.engine.components;

import dk.sdu.cbse.common.services.Component;

public class HealthComponent implements Component {
    public int currentHealth;
    public int maxHealth;
    public float iframeTimer; // counts down in seconds

    public HealthComponent(int health) {
        this.currentHealth = health;
        this.maxHealth = health;
        this.iframeTimer = 0;
    }
}
