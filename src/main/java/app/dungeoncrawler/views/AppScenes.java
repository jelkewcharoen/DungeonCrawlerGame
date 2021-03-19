package app.dungeoncrawler.views;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.Configuration.ConfigurationView;
import app.dungeoncrawler.views.InitialGame.InitialGame;
import app.dungeoncrawler.views.Welcome.WelcomeView;
import app.dungeoncrawler.views.Win.WinView;
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
        AppScenes.navigateTo(stage, SceneNames.WELCOME);
    }
    
    public ViewBase getScreen(SceneNames sceneNames) {
        return AppScenes.uiViews.get(sceneNames);
    }

    /**
     * Access the scene of the main page
     * @return the scene that corresponds to the main page
     */
    public Scene getMainPage() {
        return this.getScreen(SceneNames.WELCOME).getScene();
    }
    
    public static ViewBase buildScreen(SceneNames scene, Stage stage) {
        
        if (AppScenes.uiViews.get(scene) != null) {
            return AppScenes.uiViews.get(scene);
        } else if (SceneNames.CONFIGURATION == scene) {
            AppScenes.uiViews.put(SceneNames.CONFIGURATION, new ConfigurationView(stage));
            
        } else if (SceneNames.INITIAL_GAME == scene) {
            AppScenes.uiViews.put(SceneNames.INITIAL_GAME, new InitialGame(stage));
            
        } else if (SceneNames.WIN == scene) {
            AppScenes.uiViews.put(SceneNames.WIN, new WinView(stage));

        } else {
            AppScenes.uiViews.put(SceneNames.WELCOME, new WelcomeView(stage));

        }
        
        return AppScenes.uiViews.get(scene);
    }

    /**
     * Helps to navigate between scenes
     * @param stage current stage of the game
     * @param name scene that it needs to be navigated to
     */
    public static void navigateTo(Stage stage, SceneNames name) {
        if (AppScenes.currentView != null) {
            AppScenes.currentView.unMountingScene();
        }
        
        AppScenes.currentView = AppScenes.buildScreen(name, stage);
        System.out.println(uiViews);
        AppScenes.currentView.mountingScene();
        stage.setScene(AppScenes.currentView.getScene());
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
