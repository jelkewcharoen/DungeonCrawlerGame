package app.dungeoncrawler.views.InitialGame;


import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.Configuration.ConfigurationView;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InitialGameTest extends ApplicationTest {
    ViewBase thisScene;
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
        AppScenes scenes = new AppScenes(stage);
        Map<SceneNames, ViewBase> scenesMap = scenes.getUiViews();
        thisScene = scenesMap.get(SceneNames.INITIAL_GAME);

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
}
