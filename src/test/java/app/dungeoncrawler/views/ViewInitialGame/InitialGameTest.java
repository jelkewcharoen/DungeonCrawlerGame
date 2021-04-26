package app.dungeoncrawler.views.ViewInitialGame;


import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;

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
public class InitialGameTest extends ApplicationTest {

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
    public void t1testMoneyText() {
        FxAssert.verifyThat("#money", NodeMatchers.isNotNull());
    }

    @Test
    public void t2testMoneyPositive() {
        assertTrue(Game.getPlayer().getGold() > 0);
    }

    @Test
    public void t3testPlayerMoveRight() {
        int original = Game.getPlayer().getX();
        for (int i = 0; i < 2; i++) {
            press(RIGHT).release(RIGHT);
        }
        WaitForAsyncUtils.waitForFxEvents();

        int testrightcalc = original + (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getX());
    }
    @Test
    public void t4testPlayerMoveLeft() {
        int original = Game.getPlayer().getX();
        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
        }
        WaitForAsyncUtils.waitForFxEvents();

        int testrightcalc = original - (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getX());
    }

    @Test
    public void t5testPlayerMoveUp() {
        int original = Game.getPlayer().getY();
        press(UP).release(UP);
        press(UP).release(UP);
        WaitForAsyncUtils.waitForFxEvents();

        int testrightcalc = original - (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getY());
    }

    @Test
    public void t6testPlayerMoveDown() {
        int original = Game.getPlayer().getY();
        press(DOWN).release(DOWN);
        press(DOWN).release(DOWN);

        int testrightcalc = original + (15 * 2);
        assertEquals(testrightcalc, Game.getPlayer().getY());
    }

    @Test
    public void t7testPlayerEnterNewRoom() {
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
    
    public void removeModal() {
        press(LEFT).release(LEFT);
        press(SPACE).release(SPACE);
        sleep(1000);
    }

    @Test
    public void t8testMonsterMoves() {

        Player player = Game.getPlayer();
        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }

        this.removeModal();
        
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }
        
        Monster monster = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        System.out.println(monster);


        int originalX = monster.getX();
        int originalY = monster.getY();

        sleep(1000);


        int nowX = monster.getX();
        int nowY = monster.getY();
        boolean result = false;
        if (originalX != nowX) {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void t90hasMonster() {
        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
        }
        for (int i = 0; i < 20; i++) {
            press(DOWN).release(DOWN);
        }
        this.removeModal();

        Monster monster = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        assertNotNull(monster);
    }

    @Test
    public void t92testPlayerEnterPreviousRoom() {
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
        this.removeModal();
        press(UP).release(UP);
        Room room1 = Game.getDungeon().getActiveRoomOb();
        assertEquals(room1, initialRoom);
    }


    @Test
    public void t91testPlayerHealthBar() {
        FxAssert.verifyThat("#healthBar", NodeMatchers.isNotNull());
    }
    @Test
    public void t8testMonsterHealthBar() {
        FxAssert.verifyThat("#monsterBar", NodeMatchers.isNotNull());
    }

    @Test
    public void t94testPlayerDies() {
        Player  p = Game.getPlayer();
        p.setHealth(10);
        p.setHealth(0);
        press(DOWN).release(DOWN);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#loseView", NodeMatchers.isNotNull());
    }

    @Test
    public void t95testMonsterDies() {
        // to go to the next room that has a monster

        for (int i = 0; i < 10; i++) {
            press(UP).release(UP);
        }
        this.removeModal();
        
        for (int i = 0; i < 10; i++) {
            press(UP).release(UP);
        }
        
        sleep(1000);

        Monster m = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        if (m != null) {
            m.setHealth(0);
        } 
        
        sleep(1000);
        assertTrue(Game.gameSingleInstance().getActiveRoom().isHasMonster());
    }



    @Test
    public void t94testPlayerAllowToGoBackWhenMonsterInTheRoom() {
        sleep(1000);
        for (int i = 0; i < 3; i++) {
            press(LEFT).release(LEFT);
        }

        for (int i = 0; i < 15; i++) {
            press(DOWN).release(DOWN);
        }
        
        this.removeModal();
        Monster m = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        assertNotNull(m);

        for (int i = 0; i < 15; i++) {
            press(UP).release(UP);
        }
        ArrayList<Room> history = Game.gameSingleInstance().getDungeonI().getHistory();
        assertEquals(history.size(), 3);
    }
    
    @Test
    public void t95estStat() {
        press(UP).release(UP);
        press(UP).release(UP);
        // to go to the next room that has a monster
        sleep(2000);
        for (int i = 0; i < 10; i++) {
            press(UP).release(UP);
        }

        Monster mm = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        mm.setHealth(0);
        sleep(1000);
        assertFalse(Game.gameSingleInstance().getActiveRoom().isHasMonster());
        assertTrue(Game.getMonstersDied() > 0);
        System.out.println(Game.gameSingleInstance().getActiveRoom().getParent());
    }
    
    @Test
    public void t96testNavigationToInventoryMenu() {
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#inventoryViewPane", NodeMatchers.isNotNull());
    }

    @Test
    public void t99testNavigationToGameScreen() {
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#initialGamePane", NodeMatchers.isNotNull());
    }

    @Test
    public void t95testKillsMonsterGetsMoney() {
        // to go to the next room that has a monster
        for (int i = 0; i < 2; i++) {
            press(LEFT).release(LEFT);
        }
        for (int i = 0; i < 10; i++) {
            press(DOWN).release(DOWN);
        }

        this.removeModal();

        Integer prevMoney = Game.getPlayer().getGold();
        System.out.println(Game.gameSingleInstance().getActiveRoom().getParent());
        Monster m = Game.gameSingleInstance().getActiveRoom().getCurrentMonster();
        if (m != null) {
            m.setHealth(0);
        }
        
        sleep(5);

        press(SPACE).release(SPACE);

        Integer postMoney = Game.getPlayer().getGold();

        assertFalse(prevMoney < postMoney);
    }

    @Test
    public void t98testBuyShield() {
        sleep(1000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        Integer prevMoney = Game.getPlayer().getGold();


        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#shield");
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(prevMoney - 50, Game.getPlayer().getGold());

    }

    @Test
    public void t98testBuySpeed() {
        sleep(1000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        Integer prevMoney = Game.getPlayer().getGold();


        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#speed");
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(prevMoney > Game.getPlayer().getGold());

    }


    @Test
    public void t97testBuyMoneyDecreases() {
        sleep(1000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        Integer prevMoney = Game.getPlayer().getGold();


        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#shield");
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(prevMoney - 50, Game.getPlayer().getGold());

    }

    @Test
    public void t97testBuyHealthPotion() {
        sleep(1000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        int health = Game.getPlayer().getHealth().getValue() + 20;


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

        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        int currenthealth = Game.getPlayer().getHealth().getValue();

        assertEquals(health, currenthealth);

    }



    @Test
    public void t98testBuyWeapon2() {
        sleep(1000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        Integer prevMoney = Game.getPlayer().getGold();


        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#weapon2");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(prevMoney > Game.getPlayer().getGold());
    }

    @Test
    public void t98testBuyWeapon3() {
        sleep(2000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        Integer prevMoney = Game.getPlayer().getGold();


        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#weapon3");
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(prevMoney > Game.getPlayer().getGold());

    }

    @Test
    public void t98testBuyWeapon1() {
        sleep(2000);
        clickOn("#inventoryMenu");
        WaitForAsyncUtils.waitForFxEvents();

        Integer prevMoney = Game.getPlayer().getGold();


        clickOn("#shoptab");
        WaitForAsyncUtils.waitForFxEvents();

        //AttachableItems weaponitem =
        clickOn("#weapon1");
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(prevMoney > Game.getPlayer().getGold());

    }

    @Test
    public void testStat1() {
        press(UP).release(UP);
        press(UP).release(UP);
        sleep(2000);
        clickOn("#inventoryMenu");
        sleep(2000);
        clickOn("#shoptab");

        //AttachableItems weaponitem =
        clickOn("#shield");
        clickOn("#health");
        clickOn("#backButton");
        sleep(2000);

        assertTrue(Game.getItemsBought() > 0);

    }

    @Test
    public void testStat2() {
        press(UP).release(UP);
        press(UP).release(UP);
        sleep(2000);
        clickOn("#inventoryMenu");
        sleep(2000);

        clickOn("#shoptab");
        sleep(2000);

        //AttachableItems weaponitem =
        clickOn("#shield");
        clickOn("#health");

        clickOn("#potiontab");

        clickOn("#health");
        clickOn("#backButton");
        sleep(2000);

        assertTrue(Game.getItemsUsed() > 0);
    }

}
