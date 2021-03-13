package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.Sprite;
import app.dungeoncrawler.utils.SpriteElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Player extends SpriteElement {
    private static Map<Difficulties, Integer> playerLevelsMultiplier = new HashMap<>() {{
            put(Difficulties.EASY, 10);
            put(Difficulties.MEDIUM, 20);
            put(Difficulties.HARD, 30);
        }
    };
    
    private final int defaultHealth = 10;
    private final int defaultGold = 10;
    private final int health;
    private Weapon weapon;
    private int gold;
    private String name;
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

    public void setPlayerPosition(int x, int y) {
        this.setPosition(x, y);
    }
    
    public void movePlayer(int x, int y, GraphicsContext c) {
        
        System.out.println(String.format("x: %d, y: %d", x, y));
        if (
                Game.getDungeon()
                        .getActiveRoom()
                        .getRoomMap()
                        .isCoordinateInsideTheMap(x, y)
        ) {
            this.setPlayerPosition(x, y);
            this.draw(c);
        }
        
        
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
