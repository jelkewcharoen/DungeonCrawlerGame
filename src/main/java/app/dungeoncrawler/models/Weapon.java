package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;

import java.util.HashMap;
import java.util.Map;

public class Weapon {
    private static final Map<DefaultWeapons, Weapon> WEAPONS_WEAPON_MAP = new HashMap<>() {{
            put(DefaultWeapons.WEAPON1, new Weapon(10));
            put(DefaultWeapons.WEAPON2, new Weapon(20));
            put(DefaultWeapons.WEAPON3, new Weapon(30));
        }
    };
    
    private final int power;

    /**
     * create a weapon.
     * @param power the power of the weapon.
     */
    public Weapon(int power) {
        this.power = power;
    }

    /**
     * returns a map of default weapons to their actual weapon object.
     * @return the map of the default weapons.
     */
    public static Map<DefaultWeapons, Weapon> getWeaponsWeaponMap() {
        return WEAPONS_WEAPON_MAP;
    }

    /**
     * getter method.
     * @return the power of the weapon.
     */
    public int getPower() {
        return power;
    }
}
