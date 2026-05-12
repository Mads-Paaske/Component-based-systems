module Game {

    requires javafx.controls;
    requires javafx.graphics;

    requires Common;
    requires Engine;
    requires spring.context;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;

    exports dk.sdu.cbse.game;

    opens dk.sdu.cbse.game to spring.core, spring.beans, spring.context;
}