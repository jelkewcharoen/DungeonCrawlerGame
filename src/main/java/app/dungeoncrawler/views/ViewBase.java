package app.dungeoncrawler.views;

import app.dungeoncrawler.utils.SceneNames;
import javafx.stage.Stage;

public abstract class ViewBase implements ViewMaker {
    protected Stage stage;
    /**
     * Instance of the SceneNames class
     */
    protected SceneNames sceneName;

    /**
     * ViewBase class
     * @param stage needed to create game screen
     * @param name needed to identify the game screen
     */
    public ViewBase(Stage stage, SceneNames name) {
        if (name == null) {
            throw new IllegalArgumentException("ViewBase: sceneName cant be null");
        }
        
        if (stage == null) {
            throw new IllegalArgumentException("ViewBase: stage cant be null");
        }
        this.sceneName = name;
        this.stage = stage;
    }
    
    /**
     * Helps build a scene of the game
     */
    public abstract void buildScene();

    /**
     * Helps mount the Scene of the game
     */
    public void mountingScene() {
        //System.out.println(String.format("%s: mounting", this.sceneName));
    }

    /**
     * Helps unmount the scene of the game
     */
    public void unMountingScene() {
        //System.out.println(String.format("%s: unmounting", this.sceneName));
    }
}
