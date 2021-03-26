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
        Game.Game().createDungeon("EASY");
        Game.Game().createPlayer("X", DefaultWeapons.WEAPON1);
        dungeon = Game.getDungeon();
    }

    @Test
    public void getRoomMap() {
        Room room = new Room(null, 4, 0);
        GameMap gameMap = room.getRoomMap();
        assertEquals(gameMap, Game.Game().getCurrentGameMap());
    }

    @Test
    public void getStartingDoor() {
        Room room = new Room(null, 4, 0);
        assertEquals(room.getStartingDoor(), null);
    }
}