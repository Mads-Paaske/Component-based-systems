module Game {

    requires javafx.controls;
    requires javafx.graphics;

    requires Common;
    requires Engine;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;

    exports dk.sdu.cbse.game;
}