package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.SpriteElement;

public class Monster extends SpriteElement {
    int health;
    public Monster(String imageurl) {

        super(imageurl, 85, 100);
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

    /**
     * set the health of the monster
     * @param health new health
     */
    public void setHealth(int health) { this.health = health; }

    /**
     * get monster's health
     * @return monster's health
     */
    public int getHealth() { return this.health; }
}