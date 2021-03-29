package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.SpriteElement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Monster extends SpriteElement {
    IntegerProperty health = new SimpleIntegerProperty(0);
    public static final int MONSTER_SPEED = 5;
    
    public Monster(String imageurl, int health) {
        super(imageurl, 85, 100);
        this.health.set(10 * health);
    }

    public void setImage(String url) {
        super.setImage(url);
    }
    /**
     * gets position at x
     * @return position at x
     */
    public int getX() {
        return this.getPositionAtX();
    }

    /**
     * gets position at y
     * @return position at y
     */
    public int getY() {
        return this.getPositionAtY();
    }

    public void move(int x, int y) {
        this.setPosition(x, y);
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

    public static Monster getNewMonster() {
        int rand = (int) (Math.random() * (3 - 1 + 1) + 1); // generates 0, 1 or 2
        return new Monster(String.format("/app/assets/monster%s.png", rand), rand);
    }
}