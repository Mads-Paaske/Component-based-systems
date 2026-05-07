module Player {
    requires Common;
    requires Engine;
    requires Bullet;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.player.PlayerPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.player.PlayerProcessor;
}