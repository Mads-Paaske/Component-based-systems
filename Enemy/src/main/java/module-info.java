import dk.sdu.cbse.splitpackage.SpaceshipPlugin;
import dk.sdu.cbse.splitpackage.SpaceshipProcessor;

module Enemy {
    requires java.desktop;
    requires Engine;
    requires Common;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with SpaceshipPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with SpaceshipProcessor;
}