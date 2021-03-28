package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Dungeon;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
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
    private int multiplier1;
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
        healthBar.setWidth(200);
        multiplier = 200 / Game.getPlayer().getHealth();
        multiplier1 = 200 / Game.getCurrentMonster().getHealth();
        monsterBar.setWidth(200);
        monsterBar.setHeight(20);

        /*DoubleProperty playerHealthPercentage = new SimpleDoubleProperty(1.0);
        DoubleProperty monsterHealthPercentage = new SimpleDoubleProperty(1.0);
        DoubleBinding p = healthBar.widthProperty().multiply(playerHealthPercentage);
        DoubleBinding m = monsterBar.widthProperty().multiply(monsterHealthPercentage);
        healthBar.widthProperty().bind(p);
        monsterBar.widthProperty().bind(m);
        healthBar.setHeight(20);
        monsterBar.setHeight(20);*/
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
        } else if (e.getCode().equals(KeyCode.SPACE)) {
            Monster mon = Game.getCurrentMonster();
            mon.setHealth(mon.getHealth()-1);
            monsterBar.setWidth(mon.getHealth() * multiplier1);
        }

        Room room = Game.getDungeon().getActiveRoom();

        player.movePlayer(x, y, playerLayer.getGraphicsContext2D());
        room.trackPlayerMovement(player.getX(), player.getY());
        if (room.getIsExit() && room.getPlayerExitsExitRoom()) {
            Scene thisScene = (Scene) e.getSource();
            Stage thisStage = (Stage) thisScene.getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WIN);

        }

        player.setHealth(player.getHealth() - 1);
<<<<<<< HEAD
        healthBar.setWidth(player.getHealth() * multipler);

        System.out.println("player - x: " + player.getX());
        System.out.println("player - y: " + player.getY());
        Monster monster = Game.getCurrentMonster();

        System.out.println("Does it collide: " + monster.collides(player.getX(), player.getY()));



=======
        healthBar.setWidth(player.getHealth() * multiplier);
>>>>>>> 714dac2658b3ec29e8fb7a0d4aa1ea8fd7c4fafb
    }

    /**
     * loads room
     * @param pane pane which can be a room set
     */
    public void loadRoom(Pane pane) {

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
                doorNodeLayer.setPosition(doorNodeLayer.getDimension().averageX(),
                        doorNodeLayer.getDimension().averageY());
                doorNodeLayer.draw();
            }
            player.draw();

        });
    }
}
