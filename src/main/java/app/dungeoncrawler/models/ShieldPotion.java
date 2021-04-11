package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.AttachableItems;
import app.dungeoncrawler.utils.Fighter;

public class ShieldPotion extends Potion {

    public ShieldPotion(String name) {
        this.setName(name);
        this.setImage("shield");
        this.setType("potion");
    }

    @Override
    public void addToPlayer(Fighter fighter) {
        System.out.println("Adding Shield Potion");
    }

    @Override
    public void removeFromPlayer(Fighter fighter) {
        System.out.println("removing Shield Potion");
    }
}
