package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.SpriteElement;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Player extends SpriteElement {
    private final static String positionX = "positionX";
    private final static String positionY = "positionY";
    
    private static final Map<Difficulties, Integer> playerLevelsMultiplier = new HashMap<>() {{
            put(Difficulties.EASY, 10);
            put(Difficulties.MEDIUM, 20);
            put(Difficulties.HARD, 30);
        }
    };
    
    private final int defaultHealth = 10;
    private final int defaultGold = 10;
    private final int health;
    private final Weapon weapon;
    private final int gold;
    private final String name;
    public static int PLAYER_SPEED = 15;

    /**
     * Creates a new player.
     * @param name name of the player.
     * @param weapons the selected Weapon.
     * @param difficulties the selected difficulty.
     */
    public Player(String name, DefaultWeapons weapons, Difficulties difficulties) {
        super("/app/assets/player.png", 85, 100);
        int multiplier = Player.playerLevelsMultiplier.get(difficulties);

        this.name = name;
        this.weapon = Weapon.getWeaponsWeaponMap().get(weapons);
        this.gold = defaultGold * multiplier;
        this.health = defaultHealth * multiplier;
    }
    
    public int getX() {
        return this.getPositionAtX();
    }

    public int getY() {
        return this.getPositionAtY();
    }
    
    public void movePlayer(int x, int y, GraphicsContext c) {
        if (
            Game.getDungeon()
                .getActiveRoom()
                .getRoomMap()
                .isCoordinateInsideTheMap(x, y)
        ) {
            this.setPosition(x, y);
            this.draw(c);
        }
    }    
    
    public void movePlayer(int x, int y) {
        this.movePlayer(x, y, this.getGraphicsContext());
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
