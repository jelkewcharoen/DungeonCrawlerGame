package app.dungeoncrawler.views.InitialGame;


import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.DoorDimension;
import app.dungeoncrawler.utils.NodeLayer;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.util.Map;

import static javafx.scene.input.KeyCode.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InitialGameTest extends ApplicationTest {
    private ViewBase thisScene;
    @BeforeClass
    public static void config() throws Exception {
        System.getProperties().put("testfx.robot", "glass");
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game.createDungeon("EASY");
        Game.createPlayer("Test player", DefaultWeapons.WEAPON1);
        AppScenes scenes = new AppScenes(stage);
        thisScene = scenes.getScreen(SceneNames.INITIAL_GAME);

        stage.setWidth(720);
        stage.setHeight(512);
        stage.setScene(thisScene.getScene());
        stage.show();
        stage.toFront();
    }

    @Test
    public void testMoneyText() {
        FxAssert.verifyThat("#money", NodeMatchers.isNotNull());
    }

    @Test
    public void testMoneyPositive() {
        assertTrue(Game.getPlayer().getGold() > 0);
    }

    @Test
    public void testPlayerMoveRight() {
        int original = Game.getPlayer().getX();
        for (int i = 0; i < 10; i++) {
            press(RIGHT).release(RIGHT);
        }

        int testrightcalc = original + (15 * 10);
        assertEquals(testrightcalc, Game.getPlayer().getX());
    }
    @Test
    public void testPlayerMoveLeft() {
        int original = Game.getPlayer().getX();
        for (int i = 0; i < 6; i++) {
            press(RIGHT).release(RIGHT);
        }
        press(LEFT).release(LEFT);

        int testrightcalc = original + (15 * 5);
        assertEquals(testrightcalc, Game.getPlayer().getX());
    }

    @Test
    public void testPlayerMoveUp() {
        int original = Game.getPlayer().getY();
        for (int i = 0; i < 10; i++) {
            press(RIGHT).release(RIGHT);
        }
        press(UP).release(UP);
        press(UP).release(UP);

        int testrightcalc = original - (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getY());
    }

    @Test
    public void testPlayerMoveDown() {
        int original = Game.getPlayer().getY();
        for (int i = 0; i < 10; i++) {
            press(RIGHT).release(RIGHT);
        }
        press(DOWN).release(DOWN);
        press(DOWN).release(DOWN);

        int testrightcalc = original + (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getY());
    }
    @Test
    public void testPlayerEnterNewRoomPositionX() {
        Room initialRoom = Game.getDungeon().getInitialRoom();
        Player player = Game.getPlayer();
        player.setPosition(
                initialRoom.getStartingDoor().getDimension().averageX(),
                initialRoom.getStartingDoor().getDimension().averageY()
        );
        for (int i = 0; i < 10; i++) {
            press(RIGHT).release(RIGHT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }
        Room currentRoom = Game.getDungeon().getActiveRoom();
        int playerLocationDoorId = currentRoom.getDoorIdWherePlayerEnterTheRoom();
        NodeLayer initialDoor = currentRoom.getDoorsNodes().get(playerLocationDoorId);
        DoorDimension initialDoorDimension = (DoorDimension) initialDoor.getDimension();
        assertEquals(player.getX(), initialDoorDimension.getPositionXForPlayer());
    }
    @Test
    public void testPlayerEnterNewRoomPositionY() {
        Room initialRoom = Game.getDungeon().getInitialRoom();
        Player player = Game.getPlayer();
        player.setPosition(
                initialRoom.getStartingDoor().getDimension().averageX(),
                initialRoom.getStartingDoor().getDimension().averageY()
        );
        for (int i = 0; i < 10; i++) {
            press(RIGHT).release(RIGHT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }
        Room currentRoom = Game.getDungeon().getActiveRoom();
        int playerLocationDoorId = currentRoom.getDoorIdWherePlayerEnterTheRoom();
        NodeLayer initialDoor = currentRoom.getDoorsNodes().get(playerLocationDoorId);
        DoorDimension initialDoorDimension = (DoorDimension) initialDoor.getDimension();
        assertEquals(player.getY(), initialDoorDimension.getPositionYForPlayer());
    }
    @Test
    public void testPlayerEnterPreviousRoom() {
        Room initialRoom = Game.getDungeon().getInitialRoom();
        Player player = Game.getPlayer();
        player.setPosition(
                initialRoom.getStartingDoor().getDimension().averageX(),
                initialRoom.getStartingDoor().getDimension().averageY()
        );
        for (int i = 0; i < 10; i++) {
            press(RIGHT).release(RIGHT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }
        press(UP).release(UP);
        Room room1 = Game.getDungeon().getActiveRoom();
        assertEquals(room1, initialRoom);
    }
}
