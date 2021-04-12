package app.dungeoncrawler.Components.ShopItem;

import app.dungeoncrawler.utils.InventoryItem;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShopItemView {
    private ShopItemController controller;

    /**
     * initialize the configuration screen
     * @param pane the stage for the screen
     */
    public ShopItemView(GridPane pane, InventoryItem inventoryItem, int column, int row) {
        this.buildScene(pane, inventoryItem, column, row);
    }
    public void buildScene(GridPane pane, InventoryItem inventoryItem, int column, int row) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ShopItem.fxml"));
            SplitPane box = loader.load();
            this.controller = loader.getController();
            this.controller.setData(inventoryItem);
            pane.add(box, column, row);
            pane.getChildren().add(box);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ShopItemController getController() {
        return controller;
    }
}
