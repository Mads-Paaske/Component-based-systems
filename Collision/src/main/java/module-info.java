module Collision {

    requires Common;
    requires Engine;

    provides dk.sdu.cbse.common.services.IPostEntityProcessingService
            with dk.sdu.cbse.collision.CollisionProcessor;
}