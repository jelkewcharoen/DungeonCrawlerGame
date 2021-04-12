package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class InitialGame extends ViewBase {
    private Scene scene;
    private InitialGameController controller;

    /**
     * initialize the scene with the passed in stage
     * @param stage the stage for the scene
     */
    public InitialGame(Stage stage) {
        super(stage, SceneNames.INITIAL_GAME);
        this.buildScene();
    }
    
    /**
     * build the scene
     */
    public void buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("InitialGame.fxml"));
            Pane root = loader.load();
            InitialGameController controller = loader.getController();
            this.controller = controller;
            this.controller.setStage(this.stage);
            this.scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
            this.scene.setOnKeyPressed(this.controller::handleOnKeyPressed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mountingScene() {
        if (this.controller != null) {
            this.controller.mounting();
        }
    }
    
    @Override
    public void unMountingScene() {
        if (this.controller != null) {
            this.controller.unmount();
        }
    }

    /**
     * getter for the scene
     * @return the scene object
     */
    public Scene getScene() {
        return this.scene;
    }
    public InitialGameController getController() {
        return controller;
    }
}
