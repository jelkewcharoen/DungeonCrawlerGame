package app.dungeoncrawler.views.Lose;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoseView extends ViewBase {
    private Scene scene;
    private LoseViewController controller;

    /**
     * initialize the win screen
     * @param stage the stage for the screen
     */
    public LoseView(Stage stage) {
        super(stage, SceneNames.LOSE);
        this.buildScene();
    }
    @Override
    public void buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LoseView.fxml"));
            BorderPane box = loader.load();
            this.controller = loader.getController();

            this.scene = new Scene(box, this.stage.getWidth(), this.stage.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    /**
     * getter for the controller
     * @return the controller for the win screen
     */
    public LoseViewController getController() {
        return controller;
    }
}
