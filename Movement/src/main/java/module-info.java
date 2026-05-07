module Movement {
    requires Common;
    requires Engine;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.movement.MovementProcessor;
}