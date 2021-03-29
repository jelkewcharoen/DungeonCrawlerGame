package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Dungeon;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.URL;
import java.util.*;

public class InitialGameController implements Initializable {
    @FXML private Text money;
    @FXML private Rectangle healthBar;
    @FXML private Rectangle monsterBar;
    @FXML private Canvas roomLayer;
    @FXML private Canvas playerLayer;
    @FXML private Canvas doorsLayer;
    @FXML private Canvas monsterLayer;
    @FXML private List<Canvas> canvasList;
    @FXML private Pane initialGamePane = new Pane();
    private int multiplier;
    private int multiplier1 = 200 / 10; //10 is monster's health
    Player player = Game.getPlayer();
    Monster mon;
    /**
     * initialize the controller of the scene
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadRoom(this.initialGamePane);
        this.initialGamePane.setOnKeyPressed(this::handleOnKeyPressed);
    }
    /**
     * trigger the scene to start
     */
    public void mounting() {
        this.money.setText("$" + Game.getPlayer().getGold());
        healthBar.setHeight(20);
        monsterBar.setHeight(20);
        IntegerProperty playerHealth = player.getHealth();
        multiplier = 200 / playerHealth.getValue();
        //DoubleBinding p = healthBar.widthProperty().multiply(1);
        healthBar.widthProperty().bind(playerHealth.multiply(multiplier));
        healthBar.setHeight(20);
        monsterBar.setHeight(20);

        mon = Game.getCurrentMonster();
        IntegerProperty monsterHealth = mon.getHealth();
        System.out.println(monsterHealth.getValue());
        monsterBar.widthProperty().bind(monsterHealth.multiply(multiplier1));
    }

    /**
     * moves character each time we press key
     * @param e key event
     */
    @FXML public void handleOnKeyPressed(KeyEvent e) {
        Player player = Game.getPlayer();

        int x = 0;
        int y = 0;
        if (e.getCode().equals(KeyCode.DOWN)) {
            x = player.getX();
            y = player.getY() + Player.PLAYER_SPEED;
            
        } else if (e.getCode().equals(KeyCode.UP)) {
            x = player.getX();
            y = player.getY() - Player.PLAYER_SPEED;

        } else if (e.getCode().equals(KeyCode.LEFT)) {
            x = player.getX() - Player.PLAYER_SPEED;
            y = player.getY();

        } else if (e.getCode().equals(KeyCode.RIGHT)) {
            x = player.getX() + Player.PLAYER_SPEED;
            y = player.getY();
        } else if (e.getCode().equals(KeyCode.SPACE) && mon.collides(player.getX(), player.getY())) {
            mon.setHealth(mon.getHealth().getValue() - 1);
            if (mon.getHealth().getValue() == 0) {
                mon.clear();
            }
            //monsterBar.setWidth(mon.getHealth() * multiplier1);
        }

        Room room = Game.getDungeon().getActiveRoom();

        player.movePlayer(x, y, playerLayer.getGraphicsContext2D());
        room.trackPlayerMovement(player.getX(), player.getY());
        if (room.getIsExit() && room.getPlayerExitsExitRoom()) {
            Scene thisScene = (Scene) e.getSource();
            Stage thisStage = (Stage) thisScene.getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WIN);

        }
        //healthBar.setWidth(player.getHealth() * multiplier);

        player.setHealth(player.getHealth() - 1);
        healthBar.setWidth(player.getHealth() * multiplier);
    } 
    

    /**
     * loads room
     * @param pane pane which can be a room set
     */
    public void loadRoom(Pane pane) {

//        Game.createDungeon("EASY");
//        Game.createPlayer("invas", DefaultWeapons.WEAPON1);
        Player player = Game.getPlayer();
        Dungeon dungeon = Game.getDungeon();
        dungeon.setActivePlayer(player);
        Monster monster = Game.createMonster();

        GraphicsContext roomLayerGc = roomLayer.getGraphicsContext2D();
        GraphicsContext playerLayerGc = playerLayer.getGraphicsContext2D();
        GraphicsContext doorsLayerGc = doorsLayer.getGraphicsContext2D();
        GraphicsContext monsterLayerGc = monsterLayer.getGraphicsContext2D();
        Game.getCurrentGameMap().setRoomGraphics(roomLayerGc);
        Game.getCurrentGameMap().setDoorsGraphics(doorsLayerGc);
        player.setGraphicsContext(playerLayerGc);
        monster.setGraphicsContext(monsterLayerGc);

        Room initialRoom = dungeon.getInitialRoom();
        NodeLayer roomNodeLayer = initialRoom.getRoomMap().getRoomLayer();
        ArrayList<NodeLayer> inactiveDoors = initialRoom.getInactiveDoors();

        
        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            for (int i = 0; i < this.canvasList.size(); i++) {
                this.canvasList.get(i).setHeight(newValue.getHeight());
                this.canvasList.get(i).setWidth(newValue.getWidth());
            }
            
            roomNodeLayer.draw();

            for (int i = 0; i < inactiveDoors.size(); i++) {
                NodeLayer doorNodeLayer = inactiveDoors.get(i);
                doorNodeLayer.setPosition(doorNodeLayer.getPositionAtX(),
                            doorNodeLayer.getPositionAtY());
                doorNodeLayer.draw();
            }
            player.draw();

        });
    }

}
