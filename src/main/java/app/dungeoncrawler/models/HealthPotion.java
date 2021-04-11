package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Fighter;

public class HealthPotion extends Potion {
    public HealthPotion(String name) {
        super();
        this.setName(name);
        this.setImage("healthpotion");
        this.setType("potion");
    }

    @Override
    public void addToPlayer(Fighter fighter) {
        System.out.println("Adding Health POtion");
    }

    @Override
    public void removeFromPlayer(Fighter fighter) {
        System.out.println("removing Health POtion");
    }
}
