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

import org.testfx.util.WaitForAsyncUtils;

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
        AppScenes.clearUiViews();
        Game.Game().createDungeon("EASY");
        Game.Game().createPlayer("Test player", DefaultWeapons.WEAPON1);
        
        AppScenes scenes = new AppScenes(stage, SceneNames.INITIAL_GAME);
        thisScene = scenes.getScreen(SceneNames.INITIAL_GAME);
        
        stage.setWidth(Game.WINDOW_WIDTH);
        stage.setHeight(Game.WINDOW_HEIGHT);
        stage.setScene(thisScene.getScene());
        stage.show();
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
        for (int i = 0; i < 2; i++) {
            press(RIGHT).release(RIGHT);
        }
        WaitForAsyncUtils.waitForFxEvents();

        int testrightcalc = original + (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getX());
    }
    @Test
    public void testPlayerMoveLeft() {
        int original = Game.getPlayer().getX();
        for (int i = 0; i < 2; i++) {
            press(RIGHT).release(RIGHT);
        }
        press(LEFT).release(LEFT);
        WaitForAsyncUtils.waitForFxEvents();

        int testrightcalc = original + (15 * 1);
        assertEquals(testrightcalc, Game.getPlayer().getX());
    }

    @Test
    public void testPlayerMoveUp() {
        int original = Game.getPlayer().getY();
        press(UP).release(UP);
        press(UP).release(UP);
        WaitForAsyncUtils.waitForFxEvents();

        int testrightcalc = original - (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getY());
    }

    @Test
    public void testPlayerMoveDown() {
        int original = Game.getPlayer().getY();
        press(DOWN).release(DOWN);
        press(DOWN).release(DOWN);

        int testrightcalc = original + (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getY());
    }
    @Test
    public void testPlayerEnterNewRoomPositionX() {

        Player player = Game.getPlayer();

        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
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

        Player player = Game.getPlayer();

        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
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
        Room initialRoom = Game.getDungeon().getActiveRoomOb();

        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }
        press(UP).release(UP);
        Room room1 = Game.getDungeon().getActiveRoom();
        assertEquals(room1, initialRoom);
    }
}
