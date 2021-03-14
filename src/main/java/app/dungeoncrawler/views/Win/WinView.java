package app.dungeoncrawler.views.Win;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class WinView extends ViewBase{
    private Scene scene;
    private WinViewController controller;

    /**
     * initialize the win screen
     * @param stage the stage for the screen
     */
    public WinView(Stage stage) {
        super(stage, SceneNames.WIN);
        this.buildScene();
    }
    @Override
    public void buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("WinView.fxml"));
            GridPane box = loader.load();
            this.controller = loader.getController();

            this.scene = new Scene(box, this.stage.getWidth(), this.stage.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Scene getScene() { return scene; }

    /**
     * getter for the controller
     * @return the controller for the win screen
     */
    public WinViewController getController() { return controller; }
}
