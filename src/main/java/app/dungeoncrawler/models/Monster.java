package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Fighter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Monster extends Fighter {
    private IntegerProperty health = new SimpleIntegerProperty(0);

    public static int MONSTER_SPEED = 5;

    public Monster(String imageurl, int health, int width, int height) {
        super(imageurl, health, width, height);
        this.health.set(health);
        this.setPower(10);
    }

    public Monster(String imageurl, int health) {
        this(imageurl, health, 85, 100);
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

    public static Monster getNewMonster() {
        int rand = (int) (Math.random() * (3 - 1 + 1) + 1); // generates 0, 1 or 2
        return new Monster(String.format("/app/assets/monster%s.png", rand), 30 * rand);
    }

    public static Monster getBossMonster() {
        Monster boss = new Monster("/app/assets/bowser.png", 400, 120, 120);
        boss.MONSTER_SPEED = 20;
        boss.setPower(40);

        return boss;
    }
}