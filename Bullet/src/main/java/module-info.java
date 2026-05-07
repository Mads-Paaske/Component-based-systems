module Bullet {
    requires Engine;
    requires Common;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.bullet.BulletProcessor;
}