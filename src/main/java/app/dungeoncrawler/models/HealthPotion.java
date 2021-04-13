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
        Player player = Game.getPlayer();
        player.setHealth(player.getHealth().getValue() + 5);
        System.out.println("Adding Health Potion");
    }

    @Override
    public void removeFromPlayer(Fighter fighter) {
        System.out.println("removing Health POtion");
    }
}
