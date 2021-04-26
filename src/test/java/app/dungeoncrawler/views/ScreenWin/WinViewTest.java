package app.dungeoncrawler.views.ScreenWin;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
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

public class WinViewTest extends ApplicationTest {
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
        this.stage = stage;
        this.scene = new AppScenes(stage);
        AppScenes.buildScreen(SceneNames.WIN, stage);
        ViewBase thisScene = this.scene.getScreen(SceneNames.WIN);
        stage.setScene(thisScene.getScene());
        stage.setWidth(Game.WINDOW_WIDTH);
        stage.setHeight(Game.WINDOW_HEIGHT);
        stage.show();
        stage.toFront();
    }

    @Test
    public void buildSceneTestNavigationToWelcome() {
        clickOn("#yes");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#buttonNavigate", NodeMatchers.isNotNull());
    }

    @Test
    public void displayStatistics() {
        FxAssert.verifyThat("#labelstat", NodeMatchers.isNotNull());
    }

}
