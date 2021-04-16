package app.dungeoncrawler.Components.ShopItem;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopItemController implements Initializable {
    @FXML private AnchorPane image;
    @FXML private Label text;
    @FXML private Button buyPotion;
    @FXML private Label priceLabel;
    
    private InventoryItem inventoryItem;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("Shop item");
    }

    public void setData(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        AttachableItems wp = inventoryItem.getItem();
        this.text.setText(wp.getName());
        this.text.getStyleClass().add("listitem");
        this.image.getStyleClass().add(wp.getImage());
        this.priceLabel.setText("Price: " + inventoryItem.getPrice());
        this.priceLabel.getStyleClass().add("listitem");
        buyPotion.setId(wp.getName());
    }

    public Button getBuyPotion() {
        return buyPotion;
    }
}
