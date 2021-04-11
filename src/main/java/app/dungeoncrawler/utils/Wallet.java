package app.dungeoncrawler.utils;

public class Wallet {
    int gold;
    
    public Wallet() {
        gold = 0;
    }

    public Wallet(int gold) {
        this.gold = gold;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    
    public void reduceGold(int gold) {
        this.gold = this.gold - gold;
    }
}
