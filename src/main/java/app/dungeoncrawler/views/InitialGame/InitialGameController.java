package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Dungeon;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.utils.*;
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
    private int multiplier1; //10 is monster's health
    private Player player;
    private Dungeon dungeon;

    private Monster monster;
    private Timer timer = new Timer();


    /**
     * initialize the controller of the scene
     */
    public InitialGameController() {
        try {

            this.player = Game.Game().getPlayerI();
            this.dungeon = Game.Game().getDungeonI();
            this.loadRoom();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadCanvas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * trigger the scene to start
     */
    public void mounting() {
        this.money.setText("$" + Game.getPlayer().getGold());
        IntegerProperty playerHealth = player.getHealth();
        multiplier = 200 / playerHealth.getValue();
        healthBar.widthProperty().bind(playerHealth.multiply(multiplier));
        healthBar.setHeight(20);
        monsterBar.setHeight(20);
        
        this.initialGamePane.setOnKeyPressed(this::handleOnKeyPressed);
        
        SimpleObjectProperty<ObserverObject<Room>> roomSimpleObjectProperty = Game.Game().getDungeonI().activeRoomObProperty();
        roomSimpleObjectProperty.addListener(this::onRoomUpdate);

        SimpleObjectProperty<ArrayList<Integer>> cordinates = player.cordinatesProperty();
        cordinates.addListener(this::onPlayerMove);
        timer.scheduleAtFixedRate(this.monsterSelfMovement(), 1000, 1000);
        System.out.println("set timer");
        //this.player.getHealth().addListener(this::onPlayerHealthUpdate);
    }
    
    public void onPlayerHealthUpdate(ObservableValue<? extends Number> observable, Number obOldValue, Number obNewValue) {
        if (this.player.getHealth().get() <= 0) {
            System.out.println("Player lost");
        }
    }
    
    public void onMonsterHealthUpdate(ObservableValue<? extends Number> observable, Number obOldValue, Number obNewValue) {
        if (this.monster.getHealth().get() <= 0) {
            this.monster.clearCurrent(monsterLayer.getGraphicsContext2D());
            Game.Game().getActiveRoom().setHasMonster(false);
           /* System.out.println(Game.Game().getActiveRoom().isHasMonster());
            System.out.println(Game.Game().getActiveRoom());*/
        }
    }
    
    public void onRoomUpdate(ObservableValue<? extends ObserverObject<Room>> observable, ObserverObject<Room> obOldValue, ObserverObject<Room> obNewValue) {
        Room newValue = obNewValue.getField();
        Room oldValue = obOldValue.getField();
        
        if (oldValue != null) {
            oldValue.clearRoom(doorsLayer.getGraphicsContext2D());
        }
        
        int x = 0;
        int y = 0;
        
        int newRoomLeftFlag = newValue.getDoorIdWherePlayerLeftTheRoom();
        int newRoomEnterFlag = newValue.getDoorIdWherePlayerEnterTheRoom();
        
        if (newRoomLeftFlag > -1) { // player has a left room flag
            x = newValue.getDoorDimension(newRoomLeftFlag).getPositionXForPlayer();
            y = newValue.getDoorDimension(newRoomLeftFlag).getPositionYForPlayer();
        } else {
            x = newValue.getDoorDimension(newRoomEnterFlag).getPositionXForPlayer();
            y = newValue.getDoorDimension(newRoomEnterFlag).getPositionYForPlayer();
        }
        
        newValue.drawRoom(roomLayer.getGraphicsContext2D(), doorsLayer.getGraphicsContext2D());
        player.move(x, y);
        
        if (monster != null) {
            monster.getHealth().removeListener(this::onMonsterHealthUpdate);
            monster.clearCurrent(monsterLayer.getGraphicsContext2D());
        }

        if (!newValue.isHasMonster()) {
            //backupMonster = monster;
            monster.getHealth().removeListener(this::onMonsterHealthUpdate);
            monster.clearCurrent(monsterLayer.getGraphicsContext2D());
            //monster = null;
            Game.Game().clearCurrentMonster();
            return;
        }
        monster = Game.Game().getNewMonster();
        monster.setPosition(225, 240);
        IntegerProperty monsterHealth = monster.getHealth();
        multiplier1 = 200 / monster.getHealth().getValue();
        monsterBar.widthProperty().bind(monsterHealth.multiply(multiplier1));
        monster.getHealth().addListener(this::onMonsterHealthUpdate);
        System.out.println();
        monster.draw(monsterLayer.getGraphicsContext2D());
    }

    public TimerTask monsterSelfMovement() {
        return new TimerTask() {
            @Override
            public void run() {
                if (monster == null) {
                    return;
                }
                
                if (player == null) {
                    return;
                }
                
                int x = monster.getX(), y = monster.getY();

                if (player.getX() > x + Monster.MONSTER_SPEED) {
                    x += Monster.MONSTER_SPEED;
                } else if (player.getX() < x + Monster.MONSTER_SPEED) {
                    x -= Monster.MONSTER_SPEED;
                }

                if (player.getY() > y + Monster.MONSTER_SPEED) {
                    y += Monster.MONSTER_SPEED;
                } else if (player.getY() < y + Monster.MONSTER_SPEED) {
                    y -= Monster.MONSTER_SPEED;
                }
                
                if (monster.getHealth().get() <= 0) {
                    Game.Game().clearCurrentMonster();
                    return;
                }
                if (!Game.Game().getActiveRoom().isHasMonster()) {
                    return;
                }
                if (player.getHealth().get() <= 0) {
                    player.clear(playerLayer.getGraphicsContext2D());
                    return;
                }

                monster.move(x, y);
                player.reduceHealth(monster);
                System.out.print("/");
                monster.draw(monsterLayer.getGraphicsContext2D());
            }
        };
    }
    
    public void onPlayerMove(ObservableValue<? extends ArrayList<Integer>> observable, ArrayList<Integer> oldValue, ArrayList<Integer> newValue) {
        player.draw(playerLayer.getGraphicsContext2D());
    }
    
    public void unmount() {
        if (timer != null) {
            timer.cancel();
            System.out.println("stop timer");
        }
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
        } else if (e.getCode().equals(KeyCode.SPACE)) {
            monster.reduceHealth(player);
        }
        
        if (this.dungeon.isPositionValid(x, y)) {
            this.player.move(x, y);
            this.dungeon.getActiveRoomOb().trackPlayerMovement(player.getX(), player.getY());
        }
        
        if (this.dungeon.getActiveRoomOb().isPlayerExitedRoom()) {
            Scene thisScene = (Scene) e.getSource();
            Stage thisStage = (Stage) thisScene.getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WIN);
        }

        if (player.getHealth().get() <= 0) {
            Scene thisScene = (Scene) e.getSource();
            Stage thisStage = (Stage) thisScene.getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.LOSE);
        }
    }

    /**
     * loads room
     */
    public void loadRoom() {
        try {
            dungeon.createRoom();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadCanvas() {
        for (int i = 0; i < this.canvasList.size(); i++) {
            this.canvasList.get(i).setHeight(Game.WINDOW_HEIGHT);
            this.canvasList.get(i).setWidth(Game.WINDOW_WIDTH);
        }
        
        this.drawGame();
    }
    
    public void drawGame() {
        dungeon.setActivePlayer(player);
        Room initialRoom = dungeon.getActiveRoomOb();
        NodeLayer roomNodeLayer = initialRoom.getRoomMap().getRoomLayer();
        ArrayList<NodeLayer> inactiveDoors = initialRoom.getInactiveDoors();

        roomNodeLayer.draw(roomLayer.getGraphicsContext2D());
        player.draw(playerLayer.getGraphicsContext2D());

        for (int i = 0; i < inactiveDoors.size(); i++) {
            NodeLayer doorNodeLayer = inactiveDoors.get(i);
            doorNodeLayer.setPosition(doorNodeLayer.getPositionAtX(),
                    doorNodeLayer.getPositionAtY());
            doorNodeLayer.draw(doorsLayer.getGraphicsContext2D());
        }
    }
}
