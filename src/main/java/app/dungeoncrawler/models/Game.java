package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.*;
import app.dungeoncrawler.views.AppScenes;

import java.util.ArrayList;

public class Game {
    public static final int WINDOW_HEIGHT = 480;
    public static final int WINDOW_WIDTH = 640;
    private static Game currentGame;
    private Dungeon dungeon;
    private Player player;
    private GameMap currentGameMap;

    private Shop shop;
    // counter for the number of monsters that died
    private static int monstersDied = 0;
    // counter for the number of rooms visited
    private static int itemsUsed= 0;
    // counter for how many items the user bought
    private static int itemsBought = 0;

    /**
     * creates and get the current instance of the game
     * @return the game
     */
    public static Game gameSingleInstance() {
        if (Game.currentGame != null) {
            return Game.currentGame;
        }

        Game.currentGame = new Game();
        return Game.currentGame;
    }
    
    public Shop getShop() {
        return shop;
    }

    /**
     * creates a new single instances of game.
     * @param startNewGame to start a new game.
     * @return the game
     */
    public static Game newGame(boolean startNewGame) {
        if (startNewGame) {
            Game.currentGame = new Game();
            AppScenes.reset();
            return Game.currentGame;
        }

        return Game.gameSingleInstance();
    }
    
    /**
     * gets current game map
     * @return returns current game map
     */
    public GameMap getCurrentGameMap() {
        return currentGameMap;
    }

    /**
     * getter for current game map room layer
     * @return game map room layer
     */
    public NodeLayer getCurrentGameMapRoomLayer() {
        return currentGameMap.getRoomLayer();
    }

    /**
     * getter for the game map door layer
     * @return list of doors
     */
    public ArrayList<NodeLayer> getCurrentGameMapDoorsLayer() {
        return currentGameMap.getDoorsLayer();
    }

    /**
     * returns a the player of the game.
     * @return @Player.
     */
    public Player getPlayerI() {
        return player;
    }

    /**
     * getter for the player
     * @return player
     */
    public static Player getPlayer() {
        return gameSingleInstance().player;
    } 

    /**
     * set the player of the dungeon.
     * @param player the player being set.
     */
    private void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * CreatePlayer creates a player in the dungeon.
     * @param name name of the player.
     * @param weapons the weapon to create the player with.
     */
    public void createPlayer(String name, DefaultWeapons weapons) {
        this.setPlayer(new Player(name, weapons, Game.getDungeon().getDifficulty()));
    }

    /**
     * creates player
     * @param player player frame will be needed to create a player
     */
    public void createPlayer(Player player) {
        this.setPlayer(player);
    }
    
    /**
     * get the Dungeon of the game.
     * @return the Dungeon of the game.
     */
    public Dungeon getDungeonI() {
        return this.dungeon;
    }

    /**
     * getter for the active room
     * @return active room object
     */
    public Room getActiveRoom() {
        return this.dungeon.getActiveRoomOb();
    }

    /**
     * getter for the dungeon
     * @return dungeon
     */
    public static Dungeon getDungeon() {
        return gameSingleInstance().getDungeonI();
    }

    /**
     * setter for the active room
     * @param r new room object
     */
    public void setActiveRoom(Room r) {
        this.dungeon.setActiveRoom(r);
    }
    
    /**
     * setter for dungeon for the Game.
     * @param dungeon Dungeon of the game.
     */
    private void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * the create a dungeon in the game.
     * @param difficulties the level of difficulties (EASY, MEDIUM, HARD).
     */
    public void createDungeon(String difficulties) {
        GameMap.generateAllGameMaps(Game.WINDOW_HEIGHT, Game.WINDOW_WIDTH);
        this.currentGameMap = GameMap.getAvailableMaps().get(MapName.MAP_1);
        this.setDungeon(new Dungeon(difficulties));
        this.shop = new Shop();
    }

    public static int getItemsUsed() {
        return itemsUsed;
    }

    public static int getMonstersDied() {
        return monstersDied;
    }

    public static int getItemsBought() {
        return itemsBought;
    }

    public static void incMonstersDied(int number) {
        monstersDied += number;
    }
    public static void incItemsUsed() {
        itemsUsed++;
    }

    public static void incItemsBought() {
        itemsBought++;
    }
}
