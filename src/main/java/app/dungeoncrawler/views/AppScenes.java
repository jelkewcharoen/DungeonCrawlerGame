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

    /**
     * Helps set the stage of the App Scene
     * @param stage - creates the game view
     */
    public AppScenes(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("stage can not be null");
        }

        AppScenes.uiViews.put(SceneNames.WELCOME, new WelcomeView(stage));
        AppScenes.uiViews.put(SceneNames.CONFIGURATION, new ConfigurationView(stage));
        AppScenes.uiViews.put(SceneNames.INITIAL_GAME, new InitialGame(stage));
    }

    /**
     * Access the scene of the main page
     * @return the scene that corresponds to the main page
     */
    public Scene getMainPage() {
        AppScenes.currentView = AppScenes.uiViews.get(SceneNames.INITIAL_GAME);
        return AppScenes.uiViews.get(SceneNames.INITIAL_GAME).getScene();
    }

    /**
     * Helps to navigate between scenes
     * @param stage current stage of the game
     * @param name scene that it needs to be navigated to
     */
    public static void navigateTo(Stage stage, SceneNames name) {
        ViewBase prevScene = AppScenes.currentView;
        ViewBase nextScene = AppScenes.uiViews.get(name);
        if (prevScene != null) {
            prevScene.unMountingScene();
        }
        nextScene.mountingScene();
        AppScenes.currentView = nextScene;
        stage.setScene(nextScene.getScene());
        stage.toFront();
    }

    /**
     * Gets the changed UI View
     * @return a map of the scenenames and its view base
     */
    public static Map<SceneNames, ViewBase> getUiViews() {
        return uiViews;
    }

    /**
     * Sets the new UI of the View
     * @param uiViews map of the scene names and viewbase
     */
    public static void setUiViews(Map<SceneNames, ViewBase> uiViews) {
        AppScenes.uiViews = uiViews;
    }
}
