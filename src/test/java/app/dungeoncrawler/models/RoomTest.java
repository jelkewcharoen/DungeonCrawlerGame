package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.GameMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class RoomTest {
    private Dungeon dungeon;
    
    @Before
    public void setup() throws Exception {
        Game.createDungeon("EASY");
        Game.createPlayer("X", DefaultWeapons.WEAPON1);
        dungeon = Game.getDungeon();
    }

    @Test
    public void getRoomMap() {
        Room room = new Room(null, 4, 0);
        GameMap gameMap = room.getRoomMap();
        assertEquals(gameMap, Game.getCurrentGameMap());
    }

    @Test
    public void getStartingDoor() {
        Room room = new Room(null, 4, 0);
        assertEquals(room.getStartingDoor(), null);
    }
}