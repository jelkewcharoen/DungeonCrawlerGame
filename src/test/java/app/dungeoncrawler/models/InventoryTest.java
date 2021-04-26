package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.InventoryItem;
import app.dungeoncrawler.utils.Wallet;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class InventoryTest {

    @Test
    public void getInventoryItems() {
        Inventory inventory = new Inventory();
        assertEquals(inventory.getInventoryItems(), new HashMap<>());
    }

    @Test
    public void addItem() {
        Inventory inventory = new Inventory();
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion");
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"speed potion");
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"shield");
        assertEquals(inventory.getInventoryItems().size(), 3);
    }

    @Test
    public void removeItem() {
        Inventory inventory = new Inventory();
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion1");
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion2");
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion3");
        inventory.removeItem("healthPotion1");
        assertEquals(inventory.getInventoryItems().size(), 2);
    }

    @Test
    public void purchaseItem() {
        Inventory inventory = new Inventory();
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion1");
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion2");
        inventory.addItem(new InventoryItem(new HealthPotion("health"), 10, 10),"healthPotion3");
        Wallet wallet = new Wallet(11);
        AttachableItems attachableItems = inventory.purchaseItem("healthPotion1", wallet);
        assertEquals(wallet.getGold(), 1);
        assertNotNull(attachableItems);

    }
}