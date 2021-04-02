package app.dungeoncrawler.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Fighter extends SpriteElement {
    IntegerProperty health = new SimpleIntegerProperty(10);
    private int power;

    public Fighter(String imageurl, int health, int width, int height) {
        super(imageurl, width, height);
        this.health.set(health);
    }
    /**
     * set the health of the monster
     * @param health new health
     */
    public void setHealth(int health) { this.health.set(health); }

    /**
     * get monster's health
     * @return monster's health
     */
    public IntegerProperty getHealth() { return this.health; }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void reduceHealth(Fighter obj2) {
        if (this.collides(obj2)) {
            this.setHealth(this.getHealth().getValue() - obj2.getPower());
        }
    }
}
