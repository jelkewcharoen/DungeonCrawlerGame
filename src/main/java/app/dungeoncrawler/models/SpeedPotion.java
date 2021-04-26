package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Fighter;

public class SpeedPotion extends Potion {
    public SpeedPotion(String name) {
        this.setName(name);
        this.setImage("attackpotion");
        this.setType("potion");
    }

    @Override
    public void addToPlayer(Fighter fighter) {
        System.out.println("Adding Speed Potion");
        fighter.setSpeed(5);
    }
}
