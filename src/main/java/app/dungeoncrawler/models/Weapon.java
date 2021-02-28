package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;

import java.util.HashMap;
import java.util.Map;

public class Weapon {
    public static Map<DefaultWeapons, Weapon> defaultWeapons = new HashMap<>(){{
        put(DefaultWeapons.WEAPON1, new Weapon(10));
        put(DefaultWeapons.WEAPON2, new Weapon(20));
        put(DefaultWeapons.WEAPON3, new Weapon(30));
    }};
    
    public static Map<Difficulties, Integer> multiplier = new HashMap<>(){{
        put(Difficulties.EASY, 1);
        put(Difficulties.MEDIUM, 2);
        put(Difficulties.HARD, 3);
    }};
    
    private int power;
    
    public Weapon(int power) {
        this.power = power;
    };

    public int getPower() {
        return power;
    }
}
