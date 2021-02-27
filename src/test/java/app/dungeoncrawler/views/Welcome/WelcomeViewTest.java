package app.dungeoncrawler.views.Welcome;

import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.Configuration.ConfigurationView;
import app.dungeoncrawler.views.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.stage.Stage;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

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
        AppScenes scenes = new AppScenes(stage);
        stage.setWidth(720);
        stage.setHeight(512);
        stage.setScene(scenes.getMainPage());
        stage.show();
        stage.toFront();
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
        FxAssert.verifyThat("#configurationView", NodeMatchers.isNotNull());
    }

    @Test
    public void getScene() {
    }
}