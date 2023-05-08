package at.fhtw.swen2.presentation;

import at.fhtw.swen2.Swen2Application;
import at.fhtw.swen2.presentation.events.ApplicationStartupEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Swen2ApplicationFX extends Application {

    private Logger logger = LoggerFactory.getLogger(Swen2Application.class);

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new ApplicationStartupEvent(this, primaryStage));
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder()
                .sources(Swen2Application.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
