package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.*;
import javafx.beans.property.SimpleObjectProperty;

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

    public static final int DEFAULTHEALTH = 10;
    private final int defaultGold = 10;
    private Weapon weapon;
    private Wallet wallet;
    private final String name;
    public static final int PLAYER_SPEED = 15;
    private Inventory playerInventory = new Inventory();
    private int speed = 0;

    /**
     * Creates a new player.
     * @param name name of the player.
     * @param weapons the selected Weapon.
     * @param difficulties the selected difficulty.
     */
    public Player(String name, DefaultWeapons weapons, Difficulties difficulties) {

        super("/app/assets/player.png", 200, 85, 100);

        int multiplier = Player.playerLevelsMultiplier.get(difficulties);
        this.name = name;
        
        Weapon selectedWeapon = Weapon.getWeaponsWeaponMap().get(weapons);
        InventoryItem inventoryItem = new InventoryItem(selectedWeapon, 0, 1);
        playerInventory.addItem(inventoryItem, selectedWeapon.getImage());
        this.weapon = selectedWeapon;
        wallet = new Wallet(defaultGold * multiplier);
        this.setPower(this.weapon.getPower());
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public void setHealth(int health) {
        if (health > 250) {
            return;
        }

        super.setHealth(health);
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
    
    @Override
    public int getPlayerSpeed() {
        return speed == 0 ? PLAYER_SPEED : speed;
    }
    
    @Override
    public void setSpeed(int sp) {
        if (this.speed > 25) {
            return;
        } 
        
        if (this.speed == 0) {
            this.speed = PLAYER_SPEED + sp;
            return;
        }

        this.speed += sp;
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
        return wallet.getGold();
    }

    public void addGold(int income) {
        wallet.setGold(wallet.getGold() + income);
    }
    public void reduceGold(int money) {
        wallet.setGold(wallet.getGold() - money);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.setPower(weapon.getPower());
    }
}
