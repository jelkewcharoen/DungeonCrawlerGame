package app.dungeoncrawler.utils;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.ViewBase;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class GameMapTest extends ApplicationTest {

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
        Game.createDungeon("EASY");
        Game.createPlayer("Test player", DefaultWeapons.WEAPON1);
        AppScenes scenes = new AppScenes(stage);
        thisScene = scenes.getScreen(SceneNames.INITIAL_GAME);

        stage.setWidth(720);
        stage.setHeight(512);
        stage.setScene(thisScene.getScene());
        stage.show();
        stage.toFront();
    }

    @Test
    public void testGenerateMap() {

        GameMap.generateAllGameMaps(Game.WINDOW_HEIGHT, Game.WINDOW_WIDTH);

        GameMap map = GameMap.getAvailableMaps().get(MapName.MAP_1);

        assertTrue(map.getDoorsLayer().size() == 4);
    }
}
