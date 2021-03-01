package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;

public class Game {
    private static Dungeon dungeon;
    private static Player player;

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
        Game.setDungeon(new Dungeon(difficulties));
    }
}
