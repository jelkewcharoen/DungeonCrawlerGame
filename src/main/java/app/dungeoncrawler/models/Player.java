package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Player {
    private static Map<Difficulties, Integer> playerLevelsMultiplier = new HashMap<>() {{
            put(Difficulties.EASY, 10);
            put(Difficulties.MEDIUM, 20);
            put(Difficulties.HARD, 30);
        }
    };
    private final Image playerImage = new Image(
            getClass().getResource("/app/assets/player.png").toExternalForm(),
            85, 
            100,
            false,
            false
    );
    
    private final int defaultHealth = 10;
    private final int defaultGold = 10;
    private final int health;
    private Weapon weapon;
    private int gold;
    private String name;
    private Sprite sprite;
    private int x;
    private int y;
    public static int PLAYER_SPEED = 15;

    /**
     * Creates a new player.
     * @param name name of the player.
     * @param weapons the selected Weapon.
     * @param difficulties the selected difficulty.
     */
    public Player(String name, DefaultWeapons weapons, Difficulties difficulties) {
        int multiplier = Player.playerLevelsMultiplier.get(difficulties);

        this.name = name;
        this.weapon = Weapon.getWeaponsWeaponMap().get(weapons);
        this.gold = defaultGold * multiplier;
        this.health = defaultHealth * multiplier;
        this.sprite = new Sprite();
        this.sprite.setImage(playerImage);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPlayerPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }
    
    public void movePlayer(int x, int y, GraphicsContext c) {
        System.out.println("" + x + ", " + y);
        c.restore();
        c.clearRect(this.x,this.y,105, 200);
        if (Boundary.withinBoundary(x, y)) {
            this.setPlayerPosition(x, y);
        }

        this.sprite.render(c);
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
