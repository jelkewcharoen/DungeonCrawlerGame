package app.dungeoncrawler.views.Inventory;

import app.dungeoncrawler.Components.PotionInv.PotionInvController;
import app.dungeoncrawler.Components.PotionInv.PotionInvView;
import app.dungeoncrawler.Components.ShopItem.ShopItemView;
import app.dungeoncrawler.Components.WeaponInv.WeaponInvController;
import app.dungeoncrawler.Components.WeaponInv.WeaponInvView;
import app.dungeoncrawler.models.*;
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
    private InventoryItem item;
    private Inventory playerInventory;
    private Inventory shopInventory;
    private Player pl;
    private Button potionButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Shop shops = Game.gameSingleInstance().getShop();
        if (shops == null) {
            return;
        }

        shopInventory = shops.getShopInventory();
        this.renderShop(shopInventory);
        
        pl = Game.gameSingleInstance().getPlayerI();
        playerInventory = pl.getPlayerInventory();
        this.renderPotion(playerInventory);
        this.renderWeapons(playerInventory);
        
        this.backButton.setOnMouseClicked(this::onBackClick);
    }
    
    public void onBackClick(MouseEvent e) {
        Node node = (Node) e.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        AppScenes.navigateTo(thisStage, SceneNames.INITIAL_GAME);
    }
    
   public void onBuyItem(MouseEvent e) {
        String itemName = ((Button)e.getSource()).getId();
        Collection<InventoryItem> inventoryItems = shopInventory.getInventoryItems().values();
        for (InventoryItem inventoryItem: inventoryItems) {
            if (inventoryItem.getItem().getName().equals(itemName)) {
                item = inventoryItem;
                break;
            }
        }
        playerInventory.addItem(item, itemName);
        pl.reduceGold(item.getPrice());
        renderPotion(playerInventory);
        renderWeapons(playerInventory);
   }

    /**
     * calls the methods that perform of the action of the item
     * @param e - event for buttons on items
     */
   public void onUseItem(MouseEvent e) {
       String itemName = ((Button)e.getSource()).getId();
       Collection<InventoryItem> inventoryItems = playerInventory.getInventoryItems().values();
       for (InventoryItem inventoryItem: inventoryItems) {
           if (inventoryItem.getItem().getName().equals(itemName)) {
               item = inventoryItem;
               item.getItem().addToPlayer(pl);
               break;
           }
       }

       playerInventory.removeItem(itemName);
       renderPotion(playerInventory);

   }

    public void onAttachWeapon(MouseEvent e) {
        String itemName = ((Button)e.getSource()).getId();
        Collection<InventoryItem> inventoryItems = playerInventory.getInventoryItems().values();
        for (InventoryItem inventoryItem: inventoryItems) {
            if (inventoryItem.getItem().getName().equals(itemName)) {
                item = inventoryItem;
                ((Weapon)item.getItem()).addToPlayer(pl, itemName);
                break;
            }
        }
        renderWeapons(playerInventory);

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
        this.potions.getChildren().clear();
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();
        int row = 0;
        int column = 0;
        for (InventoryItem inventoryItem: inventoryItems) {
            if (inventoryItem.getType().equals("potion")) {
                PotionInvView potionInvView = new PotionInvView(this.potions, inventoryItem, column, row);
                PotionInvController potionInvController = potionInvView.getController();
                potionButton = potionInvController.getUsePotion();
                potionButton.setOnMouseClicked(this::onUseItem);
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
                WeaponInvView weaponInvView = new WeaponInvView(this.weapon, inventoryItem, column, row);
                weaponInvView.getController().getButton().setOnMouseClicked(this::onAttachWeapon);
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
