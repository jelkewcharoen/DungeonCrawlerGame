package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;

import app.dungeoncrawler.utils.GameMap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Dungeon dungeon;
    
    @Before
    public void setup() throws Exception {
        Game.gameSingleInstance().createDungeon("EASY");
        Game.gameSingleInstance().createPlayer("X", DefaultWeapons.WEAPON1);
        dungeon = Game.getDungeon();
    }

    @Test
    public void getRoomMap() {
        Room room = new Room(null, 4, 0);
        GameMap gameMap = room.getRoomMap();
        assertEquals(gameMap, Game.gameSingleInstance().getCurrentGameMap());
    }

    @Test
    public void getStartingDoor() {
        Room room = new Room(null, 4, 0);
        assertEquals(room.getStartingDoor(), null);
    }
}