package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Player {
    private final int defaultHealth = 10;
    private final int defaultGold = 10;
    private int health;
    private Weapon weapon;
    private int gold;
    private String name;
    
    public static Map<Difficulties, Integer> playerLevelsMultiplier = new HashMap<>(){{
       put(Difficulties.EASY, 10);
       put(Difficulties.MEDIUM, 20);
       put(Difficulties.HARD, 30);
    }};
    
    public Player(String name, DefaultWeapons weapons, Difficulties difficulties) {
        int multiplier = playerLevelsMultiplier.get(difficulties);
        
        this.name = name;
        this.weapon = Weapon.defaultWeapons.get(weapons);
        this.gold = defaultGold * multiplier;
        this.health = defaultHealth * multiplier;
    }
    
    public static boolean isPlayerNameValid(String name) {
        Pattern matchAllWhiteSpaces = Pattern.compile("^[\\s]+$");
        return name != null && !matchAllWhiteSpaces.matcher(name).find();
    }

    public int getGold() {
        return gold;
    }
}
