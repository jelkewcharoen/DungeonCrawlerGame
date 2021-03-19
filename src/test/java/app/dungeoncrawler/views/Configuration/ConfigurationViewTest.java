package app.dungeoncrawler.views.Configuration;

import app.dungeoncrawler.models.Weapon;
import app.dungeoncrawler.utils.DefaultWeapons;
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
import app.dungeoncrawler.models.Game;


import static org.junit.Assert.assertEquals;
import static org.testfx.matcher.base.NodeMatchers.hasChild;

public class ConfigurationViewTest extends ApplicationTest {
    private static String error;
    private static ConfigurationViewController controller;
    private Stage stage;
    private AppScenes scene;
    
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
        System.out.println("ivans");
        this.stage = stage;
        this.scene = new AppScenes(stage);
        AppScenes.buildScreen(SceneNames.CONFIGURATION, stage);
        ViewBase thisScene = this.scene.getScreen(SceneNames.CONFIGURATION);
        stage.setScene(thisScene.getScene());
        stage.setWidth(Game.WINDOW_WIDTH);
        stage.setHeight(Game.WINDOW_HEIGHT);
        controller = ((ConfigurationView) thisScene).getController();
        stage.show();
        stage.toFront();
    }

    //checks to see if there is a error message on the screen
    @Test
    public void testNameInput() {
        // then:
        FxAssert.verifyThat("#configurationView", hasChild("#error"));

    }
    //checks to see if the power is updated based on what weapon is selected
    @Test
    public void testWeapon1Power() {
        // when:
        clickOn("#weapon1");
        WaitForAsyncUtils.waitForFxEvents();
        // then:
        int p = controller.getPower();
        assertEquals(Weapon.getWeaponsWeaponMap().get(DefaultWeapons.WEAPON1).getPower(), p);

    }

    @Test
    public void testWeapon3Power() {
        // when:
        clickOn("#weapon3");
        WaitForAsyncUtils.waitForFxEvents();
        // then:
        int p = controller.getPower();
        assertEquals(Weapon.getWeaponsWeaponMap().get(DefaultWeapons.WEAPON3).getPower(), p);
    }

    @Test
    public void testWeapon2Power() {
        // when:
        clickOn("#weapon2");
        WaitForAsyncUtils.waitForFxEvents();
        // then:
        int p = controller.getPower();
        assertEquals(Weapon.getWeaponsWeaponMap().get(DefaultWeapons.WEAPON2).getPower(), p);

    }

    @Test
    public void testNavigationToInitialGame() {
        clickOn("#nameEnter").write("name");
        clickOn("#weapon1");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#initialGamePane", NodeMatchers.isNotNull());
    }

    @Test
    public void testDifficultyEasy() {
        clickOn("#nameEnter").write("easy");
        clickOn("#difficultyLevel");
        clickOn("EASY");
        clickOn("#weapon1");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(100, Game.getPlayer().getGold());
    }
}
