package app.dungeoncrawler.views.Inventory;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InventoryView extends ViewBase {
    private Scene scene;
    private InventoryViewController controller;

    /**
     * initialize the configuration screen
     * @param stage the stage for the screen
     */
    public InventoryView(Stage stage) {
        super(stage, SceneNames.SHOP);
        this.buildScene();
    }
    
    @Override
    public void buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("InventoryView.fxml"));
            Pane box = loader.load();

            this.scene = new Scene(box, this.stage.getWidth(), this.stage.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }
    public InventoryViewController getController() {
        return controller;
    }
}
