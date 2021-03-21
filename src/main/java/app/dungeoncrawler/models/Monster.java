package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.SpriteElement;

public class Monster extends SpriteElement {

    public Monster() {

        super("/app/assets/monster.png", 85, 100);
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
}