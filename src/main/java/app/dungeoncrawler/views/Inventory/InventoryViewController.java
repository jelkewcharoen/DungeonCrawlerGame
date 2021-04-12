package app.dungeoncrawler.views.Inventory;

import app.dungeoncrawler.Components.PotionInv.PotionInvView;
import app.dungeoncrawler.Components.ShopItem.ShopItemView;
import app.dungeoncrawler.Components.WeaponInv.WeaponInvView;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Inventory;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Shop;
import app.dungeoncrawler.utils.InventoryItem;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class InventoryViewController implements Initializable {
    @FXML GridPane potions;
    @FXML GridPane weapon;
    @FXML GridPane shop;
    @FXML Button backButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Shop shops = Game.gameSingleInstance().getShop();
        if (shops == null) {
            return;
        }

        Inventory inventory = shops.getShopInventory();
        this.renderShop(inventory);
        
        Player pl = Game.gameSingleInstance().getPlayerI();
        Inventory inventory1 = pl.getPlayerInventory();
        this.renderPotion(inventory1);
        this.renderWeapons(inventory1);
        
        this.backButton.setOnMouseClicked(this::onBackClick);
    }
    
    public void onBackClick(MouseEvent e) {
        Node node = (Node) e.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        AppScenes.navigateTo(thisStage, SceneNames.INITIAL_GAME);
    }
    
   public void onBuyItem(MouseEvent e) {
        System.out.println("sdsdsdsdsds"); 
   }

   public void renderShop(Inventory inventory) {
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();
        int row = 0;
        int column = 0;
        for (InventoryItem inventoryItem: inventoryItems) {
            ShopItemView shopItemView = new ShopItemView(this.shop, inventoryItem, column, row);
            shopItemView.getController().getBuy_potion().setOnMouseClicked(this::onBuyItem);
    
            if (column == 3) {
                row++;
                column = 0;
            } else {
                column++;
            }
        }
   }    
    
    public void renderPotion(Inventory inventory) {
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();
        int row = 0;
        int column = 0;
        for (InventoryItem inventoryItem: inventoryItems) {
            if (inventoryItem.getType().equals("potion")) {
                new PotionInvView(this.potions, inventoryItem, column, row);
                if (column == 3) {
                    row++;
                    column = 0;
                } else {
                    column++;
                }
            }
        }
    }    
    
    public void renderWeapons(Inventory inventory) {
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();
        int row = 0;
        int column = 0;
        for (InventoryItem inventoryItem: inventoryItems) {
            if (inventoryItem.getType().equals("weapon")) {
                new WeaponInvView(this.weapon, inventoryItem, column, row);
                if (column == 3) {
                    row++;
                    column = 0;
                } else {
                    column++;
                }
            }
        }
    }
}
