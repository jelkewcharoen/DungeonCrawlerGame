package app.dungeoncrawler.Components.ShopItem;

import app.dungeoncrawler.utils.InventoryItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;

public class ShopItemView {
    private ShopItemController controller;

    /**
     * initialize the configuration screen
     * @param pane the stage for the screen
     * @param inventoryItem the inventory item
     * @param column the column to add in the GridPane
     * @param row the row to add in the GridPane
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
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("ERROR ShopItemView");
        }
    }

    public ShopItemController getController() {
        return controller;
    }
}
