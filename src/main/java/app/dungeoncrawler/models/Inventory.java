package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import app.dungeoncrawler.utils.Wallet;

import java.util.HashMap;

public class Inventory {
    HashMap<String, InventoryItem> items = new HashMap<>();
    
    public HashMap<String, InventoryItem> getInventoryItems() {
        return items;
    }
    
    public void addItem(InventoryItem inventoryItem, String itemType) {
        InventoryItem item = items.get(itemType);
        if (item != null) {
            item.increaseLevel(item.getLevels());
            return;
        }

        items.put(itemType, inventoryItem);
    }
    
    public void removeItem(String itemType) {
        InventoryItem item = items.get(itemType);
        if (item != null) {
            items.remove(itemType);
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
            
            System.out.println("Item not available or player doesnt have enough funds");
            return null;
        }
        
        return null;
    }
}
