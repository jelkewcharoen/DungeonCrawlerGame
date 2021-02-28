package app.dungeoncrawler.views.Configuration;

import app.dungeoncrawler.views.AppScenes;
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
        ConfigurationView scenes = new ConfigurationView(stage);
        controller = scenes.getController();
        stage.setWidth(720);
        stage.setHeight(512);
        stage.setScene(scenes.getScene());
        stage.show();
        stage.toFront();
    }

    //checks to see if there is a error message on the screen
    @Test
    public void testNameInput() {
        // when:
        clickOn("#buttonNavigate");

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

}