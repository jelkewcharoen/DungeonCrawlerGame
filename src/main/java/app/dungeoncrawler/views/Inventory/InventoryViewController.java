package app.dungeoncrawler.views.Inventory;

import app.dungeoncrawler.Components.PotionInv.PotionInvController;
import app.dungeoncrawler.Components.PotionInv.PotionInvView;
import app.dungeoncrawler.Components.ShopItem.ShopItemView;
import app.dungeoncrawler.Components.WeaponInv.WeaponInvView;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Inventory;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Shop;
import app.dungeoncrawler.utils.AttachableItems;
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
import java.util.Collection;
import java.util.ResourceBundle;

public class InventoryViewController implements Initializable {
    @FXML
    private GridPane potions;
    @FXML
    private GridPane weapon;
    @FXML
    private GridPane shop;
    @FXML
    private Button backButton;
    private Inventory shopInventory;
    private Button potionButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Shop shops = Game.gameSingleInstance().getShop();
        if (shops == null) {
            return;
        }

        shopInventory = shops.getShopInventory();
        this.renderShop(shopInventory);

        Player pl = Game.gameSingleInstance().getPlayerI();
        Inventory playerInventory = pl.getPlayerInventory();
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
        String itemName = ((Button) e.getSource()).getId();
        Player pl = Game.gameSingleInstance().getPlayerI();
        Collection<InventoryItem> inventoryItems = shopInventory.getInventoryItems().values();

        for (InventoryItem inventoryItem : inventoryItems) {
            if (inventoryItem.getItem().getName().equals(itemName)) {
                AttachableItems newItem = shopInventory.purchaseItem(
                        inventoryItem.getItem().getImage(), 
                        pl.getWallet()
                );

                if (newItem != null) {
                    pl.getPlayerInventory().addItem(new InventoryItem(newItem, 0, 1), itemName);
                }

                Inventory inventory = pl.getPlayerInventory();
                renderPotion(inventory);
                renderWeapons(inventory);
            }
        }

        // counts how many items the user bought
        Game.incItemsBought();
        System.out.println("The number of items bought: " + Game.getItemsBought());
    }

    /**
     * calls the methods that perform of the action of the item
     *
     * @param e - event for buttons on items
     */
    public void onUseItem(MouseEvent e) {
        String itemName = ((Button) e.getSource()).getId();
        System.out.println("USE ITEM AKA ID: " + itemName);
        Player pl = Game.gameSingleInstance().getPlayerI();
        Inventory inventory = pl.getPlayerInventory();
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();

        for (InventoryItem inventoryItem : inventoryItems) {
            if (inventoryItem.getItem().getName().equals(itemName)) {
                inventoryItem.getItem().addToPlayer(pl);
                inventoryItem.increaseLevel(-1);

                if (inventoryItem.getLevels() == 0) {
                    inventory.removeItem(inventoryItem.getItem().getName());
                }

                break;
            }
        }

        renderPotion(inventory);
        Game.incItemsUsed();
        System.out.println("The number of items used: " + Game.getItemsUsed());
    }

    public void onAttachWeapon(MouseEvent e) {
        String itemName = ((Button) e.getSource()).getId();
        Player pl = Game.gameSingleInstance().getPlayerI();
        Inventory inventory = pl.getPlayerInventory();
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();

        for (InventoryItem inventoryItem : inventoryItems) {
            if (inventoryItem.getItem().getName().equals(itemName)) {
                inventoryItem.getItem().addToPlayer(pl);
                break;
            }
        }

        renderWeapons(inventory);
    }


    public void renderShop(Inventory inventory) {
        Collection<InventoryItem> inventoryItems = inventory.getInventoryItems().values();
        int row = 0;
        int column = 0;
        for (InventoryItem inventoryItem : inventoryItems) {
            ShopItemView shopItemView = new ShopItemView(this.shop, inventoryItem, column, row);
            shopItemView.getController().getBuyPotion().setOnMouseClicked(this::onBuyItem);
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
        for (InventoryItem inventoryItem : inventoryItems) {
            if (inventoryItem.getType().equals("potion")) {
                PotionInvView potionInvView = new PotionInvView(this.potions,
                        inventoryItem, column, row);
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
        for (InventoryItem inventoryItem : inventoryItems) {
            if (inventoryItem.getType().equals("weapon")) {
                WeaponInvView weaponInvView = new WeaponInvView(this.weapon,
                        inventoryItem, column, row);
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
