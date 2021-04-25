package app.dungeoncrawler.views.InitialGame;


import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;

import javafx.stage.Stage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import org.testfx.util.WaitForAsyncUtils;

import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.UP;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LastModuleTest extends ApplicationTest {

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
    public void testStat1() {
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#shield");
        clickOn("#health");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(Game.getItemsBought() > 0);

    }
    @Test
    public void testStat2() {
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#shield");
        clickOn("#health");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#potiontab");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#health");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(Game.getItemsUsed() > 0);

    }

    @Test
    public void testStat3() {
        // to go to the next room that has a monster

        for (int i = 0; i < 10; i++) {
            press(UP).release(UP);
        }
        Monster m = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        System.out.println(m);
        m.setHealth(0);
        sleep(5);
        assertFalse(Game.gameSingleInstance().getActiveRoom().isHasMonster());
        assertTrue(Game.getMonstersDied() > 0);
    }
}
