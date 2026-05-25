package dk.sdu.cbse.engine.components;

import dk.sdu.cbse.common.services.Component;

public class OwnerComponent implements Component{
    public enum OwnerType { PLAYER, ENEMY }
    public OwnerType ownerType;
}