package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Fighter;
import app.dungeoncrawler.utils.AttachableItems;

import java.util.HashMap;
import java.util.Map;

public class Weapon extends AttachableItems {
    public static final Map<DefaultWeapons, Weapon> WEAPONS_WEAPON_MAP = new HashMap<>() {{
            put(DefaultWeapons.WEAPON1, new Weapon(10, "weapon1"));
            put(DefaultWeapons.WEAPON2, new Weapon(20, "weapon2"));
            put(DefaultWeapons.WEAPON3, new Weapon(30, "weapon3"));
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
    
    public Weapon(int power, String image) {
        this.power = power;
        this.setImage(image);
        this.setName(image);
        this.setType("weapon");
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

    @Override
    public void addToPlayer(Fighter fighter) {
        
    }

    @Override
    public void removeFromPlayer(Fighter fighter) {
        
    }
}
