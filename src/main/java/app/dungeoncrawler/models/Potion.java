package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Fighter;
import app.dungeoncrawler.utils.AttachableItems;

import java.util.HashMap;
import java.util.Map;

public abstract class Potion extends AttachableItems {
    public static final Map<String, Potion> ALL_POTION = new HashMap<>() {{
        put("Health Potion", new HealthPotion("health"));
        put("Speed Potion", new SpeedPotion("speed"));
        put("Shield Potion", new ShieldPotion("shield"));
    }};
}
