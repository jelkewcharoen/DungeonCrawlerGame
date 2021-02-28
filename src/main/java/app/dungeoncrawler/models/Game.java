package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;

public class Game {
    public static Dungeon dungeon;
    public static Player player;

    public static Player getPlayer() {
        return player;
    }

    public static void createPlayer(String name, DefaultWeapons weapons) {
        Game.player = new Player(name, weapons, Game.dungeon.getDifficulty());
    }
    
    public static Dungeon getDungeon() {
        return dungeon;
    }

    public static void createDungeon(String d) {
        Game.dungeon = new Dungeon(d);
    }
    
}
