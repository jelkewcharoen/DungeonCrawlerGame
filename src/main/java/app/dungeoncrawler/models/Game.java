package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.*;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Game {
    public static final int WINDOW_HEIGHT = 480;
    public static final int WINDOW_WIDTH = 640;
    public static Game currentGame; 
    private Dungeon dungeon;
    private Player player;
    private GameMap currentGameMap;
    private Monster currentMonster;

    public static Game Game() {
        if (Game.currentGame != null) {
            return Game.currentGame;
        }

        Game.currentGame = new Game();
        return Game.currentGame;
    }
    /**
     * gets current game map
     * @return returns current game map
     */
    public GameMap getCurrentGameMap() {
        return currentGameMap;
    }

    public NodeLayer getCurrentGameMapRoomLayer() {
        return currentGameMap.getRoomLayer();
    }    
    
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

    public static Player getPlayer() {
        return Game().player;
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
    
    public Monster createMonster() {
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
    
    public Room getActiveRoom() {
        return this.dungeon.getActiveRoomOb();
    }
    
    public static Dungeon getDungeon() {
        return Game().getDungeonI();
    }
    
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
    }

    public Monster getCurrentMonster() {
        return this.currentMonster;
    }

    public Monster getNewMonster() {
        int rand = (int)(Math.random() * 3); // generates 0, 1 or 2
        System.out.println("new image:"+rand);
        if (rand == 0) {
            this.currentMonster.setImage("/app/assets/monster1.png");
        } else if (rand == 1) {
            this.currentMonster.setImage("/app/assets/monster2.png");
        } else {
            this.currentMonster.setImage("/app/assets/monster3.png");
        }
        //mon = new Monster("/app/assets/monster1.png");
        this.currentMonster.setHealth(10);
        return this.currentMonster;
    }
}
