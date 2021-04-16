package app.dungeoncrawler.utils;

public class InventoryItem {
    private int price = 0;
    private int levels = 0;
    private AttachableItems item;

    private InventoryItem() { }

    public InventoryItem(AttachableItems item, int price, int levels) {
        this.item = item;
        this.price = price;
        this.levels = levels;
    }
    
    public void increaseLevel(int level) {
        this.levels = this.levels + level;
    }

    public int getLevels() {
        return levels;
    }
    
    public String getType() {
        return item.getType();
    }

    public int getPrice() {
        return price;
    }

    public AttachableItems getItem() {
        return item;
    }
}
