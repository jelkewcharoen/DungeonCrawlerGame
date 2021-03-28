package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Dungeon;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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
    @FXML private final Pane initialGamePane = new Pane();

    private int multiplier;
    private int multiplier1 = 200 / 10; //10 is monster's health
    private Player player;
    private Dungeon dungeon;
    Monster mon;
    private Room activeRoom;
    
    /**
     * initialize the controller of the scene
     */
    public InitialGameController() {
        try {

            this.player = Game.Game().getPlayerI();
            this.dungeon = Game.Game().getDungeonI();

            System.out.println("InitialGameController");

            this.loadRoom();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println("initialize");
            this.loadCanvas();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * trigger the scene to start
     */
    public void mounting() {
        System.out.println("mounting");

        this.money.setText("$" + Game.getPlayer().getGold());
     
        IntegerProperty playerHealth = player.getHealth();
        multiplier = 200 / playerHealth.getValue();
        //DoubleBinding p = healthBar.widthProperty().multiply(1);
        healthBar.widthProperty().bind(playerHealth.multiply(multiplier));
        healthBar.setHeight(20);
        monsterBar.setHeight(20);

        mon = Game.Game().getCurrentMonster();
        IntegerProperty monsterHealth = mon.getHealth();
        System.out.println(monsterHealth.getValue());
        monsterBar.widthProperty().bind(monsterHealth.multiply(multiplier1));

        this.initialGamePane.setOnKeyPressed(this::handleOnKeyPressed);
        
        SimpleObjectProperty<Room> roomSimpleObjectProperty = Game.Game().getDungeonI().activeRoomObProperty();
        roomSimpleObjectProperty.addListener(this::onRoomUpdate);

        SimpleObjectProperty<ArrayList<Integer>> cordinates = player.cordinatesProperty();
        cordinates.addListener(this::onPlayerMove);
    }
    
    public void onRoomUpdate(ObservableValue<? extends Room> observable, Room oldValue, Room newValue) {
        System.out.println("onRoomUpdate");
        System.out.println(newValue.getDepth());
        if (oldValue != null) {
            oldValue.clearRoom(doorsLayer.getGraphicsContext2D());
        }
        
        newValue.drawRoom(roomLayer.getGraphicsContext2D(), doorsLayer.getGraphicsContext2D());
        player.move(
            newValue.getInitialPositionXForPlayer(), 
            newValue.getInitialPositionYForPlayer()
        );
    }    
    
    public void onPlayerMove(ObservableValue<? extends ArrayList<Integer>> observable, ArrayList<Integer> oldValue, ArrayList<Integer> newValue) {
        player.draw(playerLayer.getGraphicsContext2D());
    }
    
    public void unmount() {
        // TODO: remove lsitenr
    }

    /**
     * moves character each time we press key
     * @param e key event
     */
    @FXML public void handleOnKeyPressed(KeyEvent e) {
        int x = this.player.getX();
        int y = this.player.getY();
        if (e.getCode().equals(KeyCode.DOWN)) {
            y += Player.PLAYER_SPEED;
            
        } else if (e.getCode().equals(KeyCode.UP)) {
            y -= Player.PLAYER_SPEED;

        } else if (e.getCode().equals(KeyCode.LEFT)) {
            x -= Player.PLAYER_SPEED;

        } else if (e.getCode().equals(KeyCode.RIGHT)) {
            x += Player.PLAYER_SPEED;
        } else if (e.getCode().equals(KeyCode.SPACE) && mon.collides(player.getX(), player.getY())) {
            mon.setHealth(mon.getHealth().getValue() - 1);
            if (mon.getHealth().getValue() == 0) {
                mon.clear(monsterLayer.getGraphicsContext2D());
            }
        }
        
        if (this.dungeon.isPositionValid(x, y)) {
            this.player.move(x, y);
        }
        
//        player.movePlayer(x, y, playerLayer.getGraphicsContext2D());
        this.activeRoom.trackPlayerMovement(player.getX(), player.getY());

        System.out.println(this.activeRoom.isPlayerExitedRoom());
        if (this.activeRoom.isPlayerExitedRoom()) {
            Scene thisScene = (Scene) e.getSource();
            Stage thisStage = (Stage) thisScene.getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WIN);
        }
    }

    /**
     * loads room
     */
    public void loadRoom() {
        System.out.println("loadRoom");

        try {
            dungeon.createRoom();
            this.activeRoom = this.dungeon.getActiveRoomOb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadCanvas() {
        System.out.println("loadCanvas");
        for (int i = 0; i < this.canvasList.size(); i++) {
            this.canvasList.get(i).setHeight(Game.WINDOW_HEIGHT);
            this.canvasList.get(i).setWidth(Game.WINDOW_WIDTH);
        }
        
        this.drawGame();
    }
    
    public void drawGame() {
        System.out.println("draw game");
        dungeon.setActivePlayer(player);
        Room initialRoom = dungeon.getActiveRoomOb();
        NodeLayer roomNodeLayer = initialRoom.getRoomMap().getRoomLayer();
        ArrayList<NodeLayer> inactiveDoors = initialRoom.getInactiveDoors();

        roomNodeLayer.draw(roomLayer.getGraphicsContext2D());
        player.draw(playerLayer.getGraphicsContext2D());

        for (int i = 0; i < inactiveDoors.size(); i++) {
            NodeLayer doorNodeLayer = inactiveDoors.get(i);
            doorNodeLayer.setPosition(doorNodeLayer.getDimension().averageX(), doorNodeLayer.getDimension().averageY());
            doorNodeLayer.draw(doorsLayer.getGraphicsContext2D());
        }
    }
}
