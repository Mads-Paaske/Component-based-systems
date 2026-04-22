module Game {
    requires javafx.controls;
    requires javafx.graphics;
    requires Engine;
    requires Common;
    requires Player;
    requires Asteroids;
    requires Collision;
    requires Bullet;

    exports dk.sdu.cbse.game;
}