package dk.sdu.cbse.game;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ModuleConfig.class);

        // Debug: lets you verify beans are registered
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        Game game = context.getBean(Game.class);
        game.start(primaryStage);
        game.render();
    }
}