package app.dungeoncrawler.views;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.Configuration.ConfigurationView;
import app.dungeoncrawler.views.InitialGame.InitialGame;
import app.dungeoncrawler.views.Welcome.WelcomeView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AppScenes {
    private static Map<SceneNames, ViewBase> uiViews = new HashMap<>();
    
    private static ViewBase currentView;
    private Stage stage;
    
    public AppScenes(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("stage can not be null");
        }

        AppScenes.uiViews.put(SceneNames.WELCOME, new WelcomeView(stage));
        AppScenes.uiViews.put(SceneNames.CONFIGURATION, new ConfigurationView(stage));
        AppScenes.uiViews.put(SceneNames.INITIAL_GAME, new InitialGame(stage));
    }
    
    public Scene getMainPage() {
        AppScenes.currentView = AppScenes.uiViews.get(SceneNames.WELCOME);
        return AppScenes.uiViews.get(SceneNames.WELCOME).getScene();
    }

    public static void navigateTo(Stage stage, SceneNames name) {
            ViewBase prevScene = AppScenes.currentView;
            ViewBase nextScene = AppScenes.uiViews.get(name);
            if(prevScene != null) {
                prevScene.unMountingScene();
            }
            nextScene.mountingScene();
            AppScenes.currentView = nextScene;

            stage.setScene(nextScene.getScene());
            stage.toFront();
    }

    public static Map<SceneNames, ViewBase> getUiViews() {
        return uiViews;
    }

    public static void setUiViews(Map<SceneNames, ViewBase> uiViews) {
        AppScenes.uiViews = uiViews;
    }
}
