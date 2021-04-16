package app.dungeoncrawler.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Fighter extends SpriteElement {
    private final IntegerProperty health = new SimpleIntegerProperty(10);
    private int power;

    /**
     * constructor for fighter
     *
     * @param imageurl image
     * @param health   health
     * @param width    width of object
     * @param height   height of object
     */
    public Fighter(String imageurl, int health, int width, int height) {
        super(imageurl, width, height);
        this.health.set(health);
    }

    /**
     * get monster's health
     *
     * @return monster's health
     */
    public IntegerProperty getHealth() {
        return this.health;
    }

    /**
     * set the health of the monster
     *
     * @param health new health
     */
    public void setHealth(int health) {
        this.health.set(health);
    }

    /**
     * getter for power
     *
     * @return power
     */
    public int getPower() {
        return power;
    }

    /**
     * setter for power
     *
     * @param power power
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * reduces the health of input object
     *
     * @param obj2 another fighter whose health to be removed
     */
    public void reduceHealth(Fighter obj2) {
        if (this.collides(obj2)) {
            this.setHealth(this.getHealth().getValue() - obj2.getPower());
        }
    }

    public int getPlayerSpeed() {
        return 0;
    }

    public void setSpeed(int sp) {
    }

}
