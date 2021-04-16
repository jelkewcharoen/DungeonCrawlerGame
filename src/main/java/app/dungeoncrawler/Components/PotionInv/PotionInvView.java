package app.dungeoncrawler.Components.PotionInv;

import app.dungeoncrawler.utils.InventoryItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;

public class PotionInvView {
    private PotionInvController controller;

    /**
     * initialize the configuration screen
     * @param pane the stage for the screen
     * @param inventoryItem the inventory item being added
     * @param column the column to add in the GridPane
     * @param row the row to add in the GridPane
     */
    public PotionInvView(GridPane pane, InventoryItem inventoryItem, int column, int row) {
        this.buildScene(pane, inventoryItem, column, row);
    }
    public void buildScene(GridPane pane, InventoryItem inventoryItem, int column, int row) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PotionInv.fxml"));
            SplitPane box = loader.load();
            this.controller = loader.getController();
            this.controller.setData(inventoryItem);
            pane.add(box, column, row);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("ERROR PotionInvView");
        }
    }

    public PotionInvController getController() {
        return controller;
    }
}
