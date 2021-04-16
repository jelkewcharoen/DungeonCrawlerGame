package app.dungeoncrawler.Components.PotionInv;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PotionInvController implements Initializable {
    @FXML private AnchorPane image;
    @FXML private Label text;
    @FXML private Button use;
    @FXML private Label count;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("potion item");
    }

    public void setData(InventoryItem inventoryItem) {
        AttachableItems wp = inventoryItem.getItem();
        this.text.setText(wp.getName());
        this.text.getStyleClass().add("listitem");
        this.image.getStyleClass().add(wp.getImage());
        this.count.setText("Count: " + inventoryItem.getLevels());
        this.count.getStyleClass().add("listitem");
        use.setId(wp.getName());
    }

    public Button getUsePotion() {
        return use;
    }
}
