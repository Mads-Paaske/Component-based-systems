package dk.sdu.cbse.engine.components;

public class OwnerComponent implements Component{
    public enum OwnerType { PLAYER, ENEMY }
    public OwnerType ownerType;
}