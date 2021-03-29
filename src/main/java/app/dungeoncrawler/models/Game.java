package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.GameMap;
import app.dungeoncrawler.utils.MapName;

public class Game {
    public static final int WINDOW_HEIGHT = 480;
    public static final int WINDOW_WIDTH = 640;
    private static Dungeon dungeon;
    private static Player player;
    private static GameMap currentGameMap;
    private static Monster currentMonster;

    /**
     * gets current game map
     * @return returns current game map
     */
    public static GameMap getCurrentGameMap() {
        return currentGameMap;
    }

    /**
     * returns a the player of the game.
     * @return @Player.
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * set the player of the dungeon.
     * @param player the player being set.
     */
    private static void setPlayer(Player player) {
        Game.player = player;
    }

    /**
     * CreatePlayer creates a player in the dungeon.
     * @param name name of the player.
     * @param weapons the weapon to create the player with.
     */
    public static void createPlayer(String name, DefaultWeapons weapons) {
        Game.setPlayer(new Player(name, weapons, Game.getDungeon().getDifficulty()));
    }
    public static Monster createMonster() {
        System.out.println("create monster");
        int rand = (int)(Math.random() * 3); // generates 0, 1 or 2
        if (rand == 0) {
            currentMonster = new Monster("/app/assets/monster1.png");
        } else if (rand == 1) {
            currentMonster = new Monster("/app/assets/monster2.png");
        } else {
            currentMonster = new Monster("/app/assets/monster3.png");
        }
        return currentMonster;
    }

    /**
     * creates player
     * @param player player frame will be needed to create a player
     */
    public static void createPlayer(Player player) {
        Game.setPlayer(player);
    }
    
    /**
     * get the Dungeon of the game.
     * @return the Dungeon of the game.
     */
    public static Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * setter for dungeon for the Game.
     * @param dungeon Dungeon of the game.
     */
    private static void setDungeon(Dungeon dungeon) {
        Game.dungeon = dungeon;
    }

    /**
     * the create a dungeon in the game.
     * @param difficulties the level of difficulties (EASY, MEDIUM, HARD).
     */
    public static void createDungeon(String difficulties) {
        GameMap.generateAllGameMaps(Game.WINDOW_HEIGHT, Game.WINDOW_WIDTH);
        Game.currentGameMap = GameMap.getAvailableMaps().get(MapName.MAP_1);
        Game.setDungeon(new Dungeon(difficulties));
    }

    public static Monster getCurrentMonster() {
        return currentMonster;
    }

    public static Monster getNewMonster() {
        int rand = (int)(Math.random() * 3); // generates 0, 1 or 2
        System.out.println("new image:"+rand);
        if (rand == 0) {
            currentMonster.setImage("/app/assets/monster1.png");
        } else if (rand == 1) {
            currentMonster.setImage("/app/assets/monster2.png");
        } else {
            currentMonster.setImage("/app/assets/monster3.png");
        }
        //mon = new Monster("/app/assets/monster1.png");
        currentMonster.setHealth(10);
        return currentMonster;
    }
}
