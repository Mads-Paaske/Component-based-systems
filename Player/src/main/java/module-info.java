import dk.sdu.cbse.splitpackage.SpaceshipPlugin;
import dk.sdu.cbse.splitpackage.SpaceshipProcessor;

module Player {
    uses dk.sdu.cbse.common.services.BulletSPI;
    requires Common;
    requires Engine;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with SpaceshipPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with SpaceshipProcessor;
}