package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.SpriteElement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Monster extends SpriteElement {
    IntegerProperty health = new SimpleIntegerProperty(0);
    
    public Monster(String imageurl) {
        super(imageurl, 85, 100);
        System.out.println(imageurl);
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
    public void moveMonster() {
        System.out.println("move monster");
    }

    public boolean collides(int x, int y) {

        return ((x > (getX() - 90)) && (x < (getX() + 30)) && (y > (getY() - 90)) && (y < (getY() + 30)));
    }
}