module Bullet {
    requires Engine;
    requires Common;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.bullet.BulletProcessor;

    provides dk.sdu.cbse.common.services.BulletSPI
            with dk.sdu.cbse.bullet.BulletPlugin;
}