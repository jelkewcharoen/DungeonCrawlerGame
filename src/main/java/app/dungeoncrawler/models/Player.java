package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.Fighter;
import app.dungeoncrawler.utils.SpriteElement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Player extends Fighter {
    private SimpleObjectProperty<ArrayList<Integer>> cordinates = new SimpleObjectProperty();
    
    private static Map<Difficulties, Integer> playerLevelsMultiplier = new HashMap<>() {{
            put(Difficulties.EASY, 10);
            put(Difficulties.MEDIUM, 20);
            put(Difficulties.HARD, 30);
        }
    };
    
    public static final int defaultHealth = 10;
    private final int defaultGold = 10;
    private final Weapon weapon;
    private final int gold;
    private final String name;
    public static final int PLAYER_SPEED = 15;

    /**
     * Creates a new player.
     * @param name name of the player.
     * @param weapons the selected Weapon.
     * @param difficulties the selected difficulty.
     */
    public Player(String name, DefaultWeapons weapons, Difficulties difficulties) {
        super("/app/assets/player.png", 10, 85, 100);
        int multiplier = Player.playerLevelsMultiplier.get(difficulties);

        this.name = name;
        this.weapon = Weapon.getWeaponsWeaponMap().get(weapons);
        this.gold = defaultGold * multiplier;
        this.setHealth((Player.defaultHealth * Player.playerLevelsMultiplier.get(difficulties)));
        this.setPower(this.weapon.getPower());
    }

    /**
     * gets position at x
     * @return position at x
     */
    public int getX() {
        return this.getPositionAtX();
    }

    public ArrayList<Integer> getCordinates() {
        return cordinates.get();
    }

    public SimpleObjectProperty<ArrayList<Integer>> cordinatesProperty() {
        return cordinates;
    }
    
    /**
     * gets position at y
     * @return position at y
     */
    public int getY() {
        return this.getPositionAtY();
    }
    
    /**
     * moves player
     * @param x x where we want to move player to
     * @param y y where we want to move player to
     */
    public void move(int x, int y) {
        this.setPosition(x, y);

        ArrayList<Integer> cord = new ArrayList<>();
        cord.add(x);
        cord.add(y);
        cordinates.set(cord);
    }

    /**
     * isPlayerNameValid checks if the name of the player 
     * is valid (checks if it is not Null and a string containing 
     * something other than empty spaces).
     * @param name name of the player, or any string.
     * @return true or false depending if the name is valid.
     */
    public static boolean isPlayerNameValid(String name) {
        Pattern matchAllWhiteSpaces = Pattern.compile("^[\\s]+$");
        return name != null && !matchAllWhiteSpaces.matcher(name).find();
    }

    /**
     * returns the amount of gold of the player.
     * @return the integer amount of gold.
     */
    public int getGold() {
        return gold;
    }
}
