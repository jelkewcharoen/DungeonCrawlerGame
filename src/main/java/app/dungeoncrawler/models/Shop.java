package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.InventoryItem;
import app.dungeoncrawler.utils.Wallet;

public class Shop {
    public Wallet wallet;
    public Inventory shopInventory;

    public Shop() {
        wallet = new Wallet();
        shopInventory = Shop.generateDefaultInventory();
    }

    public Shop(int initialGold) {
        wallet = new Wallet(initialGold);
        shopInventory = Shop.generateDefaultInventory();
    }    
    
    public Shop(int initialGold, Inventory inv) {
        wallet = new Wallet(initialGold);
        shopInventory = inv;
    }

    public Inventory getShopInventory() {
        return shopInventory;
    }

    public static Inventory generateDefaultInventory() {
        Inventory inventory = new Inventory();

        Weapon.WEAPONS_WEAPON_MAP.forEach((defaultWeapons, weapon) -> {
            InventoryItem inventoryItem = new InventoryItem(weapon, 10, 10);
            inventory.addItem(inventoryItem, weapon.getImage());
        });
        
        Potion.ALL_POTION.forEach((name, potion) -> {
            InventoryItem inventoryItem = new InventoryItem(potion, 50, 10);
            inventory.addItem(inventoryItem, potion.getImage());
        });
        
        return inventory;
    }
}
