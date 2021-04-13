package app.dungeoncrawler.Components.PotionInv;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class PotionInvController implements Initializable {
    @FXML AnchorPane image;
    @FXML Label text;
    @FXML Button use;
    @FXML Label count;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("potion item");
    }

    public void setData(InventoryItem inventoryItem) {
        AttachableItems wp = inventoryItem.getItem();
        System.out.println(wp.getName());
        this.text.setText(wp.getName());
        this.text.getStyleClass().add("listitem");
        this.image.getStyleClass().add(wp.getImage());
        this.count.setText("Count: "+ inventoryItem.getLevels());
        this.count.getStyleClass().add("listitem");
    }

    public Button getUsePotion() {
        return use;
    }
}
