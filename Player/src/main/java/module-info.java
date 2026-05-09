module Player {
    uses dk.sdu.cbse.common.services.BulletSPI;
    requires Common;
    requires Engine;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.player.PlayerPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.player.PlayerProcessor;
}