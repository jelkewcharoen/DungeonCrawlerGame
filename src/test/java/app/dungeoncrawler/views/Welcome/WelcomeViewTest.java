package app.dungeoncrawler.views.Welcome;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.DefaultWeapons;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

public class WelcomeViewTest extends ApplicationTest {
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
        AppScenes scenes = new AppScenes(stage, SceneNames.WELCOME);
        ViewBase thisScene = scenes.getScreen(SceneNames.WELCOME);

        stage.setWidth(Game.WINDOW_WIDTH);
        stage.setHeight(Game.WINDOW_HEIGHT);
        stage.setScene(thisScene.getScene());
        stage.show();
    }
    
    @Test
    public void buildSceneTestFxmlRendered() {
        FxAssert.verifyThat("#buttonNavigate", LabeledMatchers.hasText("START"));
    }    
    
    @Test
    public void buildSceneTestNavigationToConfiguration() {
        FxAssert.verifyThat("#buttonNavigate", LabeledMatchers.hasText("START"));
        clickOn("#buttonNavigate");
        WaitForAsyncUtils.waitForFxEvents();
        sleep(2000);
        FxAssert.verifyThat("#configurationView", NodeMatchers.isNotNull());
    }


}