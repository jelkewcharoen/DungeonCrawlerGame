package app.dungeoncrawler.Components.WeaponInv;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WeaponInvController implements Initializable {
    @FXML private AnchorPane weaponImage;
    @FXML private Label weaponLabel;
    @FXML private Button weaponButtonAttach;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println("Ivan");
    }

    public void setData(InventoryItem inventoryItem) {
        AttachableItems wp = inventoryItem.getItem();
        this.weaponLabel.setText(wp.getName());
        this.weaponLabel.getStyleClass().add("listitem");
        this.weaponImage.getStyleClass().add(wp.getImage());
        weaponButtonAttach.setId(wp.getName());
    }

    public Button getButton() {
        return weaponButtonAttach;
    }
}
