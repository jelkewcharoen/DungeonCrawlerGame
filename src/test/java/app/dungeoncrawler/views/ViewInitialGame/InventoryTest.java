package app.dungeoncrawler.views.ViewInitialGame;


import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import org.testfx.util.WaitForAsyncUtils;


import java.util.ArrayList;

import static javafx.scene.input.KeyCode.*;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryTest extends ApplicationTest {

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
        Game.newGame(true);
        Game.gameSingleInstance().createDungeon("EASY");
        Game.gameSingleInstance().createPlayer("Test player", DefaultWeapons.WEAPON1);

        AppScenes scenes = new AppScenes(stage, SceneNames.INITIAL_GAME);
        thisScene = scenes.getScreen(SceneNames.INITIAL_GAME);

        stage.setWidth(Game.WINDOW_WIDTH);
        stage.setHeight(Game.WINDOW_HEIGHT);
        stage.setScene(thisScene.getScene());
        stage.show();
    }
    @Test
    public void testPlayerEnterChallengeRoom() {
        Room initialRoom = Game.getDungeon().getActiveRoomOb();
        Player player = Game.getPlayer();
        player.setPosition(initialRoom.getRoomMap().getRoomLayer().getDimension().averageX(),
                initialRoom.getRoomMap().getRoomLayer().getDimension().averageY());

        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }

        Room room1 = Game.getDungeon().getActiveRoomOb();
        assertNotEquals(room1, initialRoom);

    }
}