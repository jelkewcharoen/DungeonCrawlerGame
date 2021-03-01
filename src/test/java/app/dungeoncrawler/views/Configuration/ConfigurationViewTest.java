package app.dungeoncrawler.views.Configuration;

import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.util.NodeQueryUtils.hasText;

public class ConfigurationViewTest extends ApplicationTest {
    private static String error;
    private static ConfigurationViewController controller;
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
        Map<SceneNames, ViewBase> x = scenes.getUiViews();
        ViewBase thisScene = x.get(SceneNames.CONFIGURATION);
        stage.setWidth(720);
        stage.setHeight(512);
        stage.setScene(thisScene.getScene());

        controller = ((ConfigurationView)thisScene).getController();
        stage.show();
        stage.toFront();
    }

    //checks to see if there is a error message on the screen
    @Test
    public void testNameInput() {
        // when:
        clickOn("#startGame");

        // then:
        FxAssert.verifyThat("#configurationView", hasChild("#error"));

    }
    //checks to see if the power is updated based on what weapon is selected
    @Test
    public void testWeapon1Power() {
        // when:
        clickOn("#weapon1");

        // then:
        assertEquals(100, controller.getPower());

    }

    @Test
    public void testNavigationToInitialGame() {
        clickOn("#nameEnter").write("name");
        clickOn("#buttonNavigate");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#InitialGame", NodeMatchers.isNotNull());
    }

}