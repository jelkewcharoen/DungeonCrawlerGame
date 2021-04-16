package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import app.dungeoncrawler.utils.Wallet;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, InventoryItem> items = new HashMap<>();
    
    public HashMap<String, InventoryItem> getInventoryItems() {
        return items;
    }
    
    public void addItem(InventoryItem inventoryItem, String itemName) {
        InventoryItem item = items.get(itemName);
        if (item != null) {
            item.increaseLevel(item.getLevels());
            return;
        }
        items.put(itemName, inventoryItem);
    }
    
    public void removeItem(String itemName) {
        InventoryItem item = items.get(itemName);
        if (item != null) {
            items.remove(itemName);
        }
    }
    
    public AttachableItems purchaseItem(String itemType, Wallet playerWallet) {
        InventoryItem item = items.get(itemType);
        if (item != null) {
            if (item.getLevels() > 0 && playerWallet.getGold() > item.getPrice()) {
                playerWallet.reduceGold(item.getPrice());
                item.increaseLevel(-1);
                return item.getItem();
            }
            
            System.out.println("Item not available or player doesn't have enough funds");
            return null;
        }
        
        return null;
    }
}
