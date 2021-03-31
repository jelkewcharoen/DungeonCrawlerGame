package app.dungeoncrawler;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.views.AppScenes;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(Game.WINDOW_WIDTH);
        stage.setHeight(Game.WINDOW_HEIGHT);
        stage.setTitle("Team 39: 404 Not Found");
        stage.setResizable(false);
        AppScenes scenes = new AppScenes(stage);
        stage.setScene(scenes.getMainPage());
        stage.show();
        //applyAnimation(rootGroup);
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }

    @Override
    public void init() {
        System.out.println("init");
    }

    /**
     * Main class for App.java
     * @param args in order to launch application
     */
    public static void main(String... args) {
        Application.launch(args);
    }
}
