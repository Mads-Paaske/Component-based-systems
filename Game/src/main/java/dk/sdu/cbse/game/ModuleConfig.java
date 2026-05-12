package dk.sdu.cbse.game;

import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;

@Configuration
public class ModuleConfig {

    @Bean
    public Game game() {
        // Spring intercepts these method calls and returns the managed bean instances
        return new Game(gamePlugins(), processors(), postProcessors());
    }

    @Bean
    public List<IGamePluginService> gamePlugins() {
        return ServiceLoader.load(IGamePluginService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    @Bean
    public List<IEntityProcessingService> processors() {
        return ServiceLoader.load(IEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    @Bean
    public List<IPostEntityProcessingService> postProcessors() {
        return ServiceLoader.load(IPostEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }
}