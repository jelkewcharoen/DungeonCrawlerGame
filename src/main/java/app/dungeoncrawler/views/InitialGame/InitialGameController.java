package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.*;
import app.dungeoncrawler.utils.NodeLayer;
import app.dungeoncrawler.utils.ObserverObject;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class InitialGameController implements Initializable {
    @FXML private Text money;
    @FXML private Rectangle healthBar;
    @FXML private Rectangle monsterBar;
    @FXML private Rectangle monsterBar2;
    @FXML private Rectangle monsterBar3;
    @FXML private Button inventoryMenu;

    @FXML private Canvas roomLayer;
    @FXML private Canvas playerLayer;
    @FXML private Canvas doorsLayer;
    @FXML private Canvas monsterLayer;
    
    @FXML private List<Canvas> canvasList;
    @FXML private final Pane initialGamePane = new Pane();
    @FXML private Label weaponImageInit;

    
    private Player player;
    private Dungeon dungeon;

    private Rectangle[] monsterHealthBars = new Rectangle[3];

    private ArrayList<Monster> monsterList = new ArrayList<>();
    private ArrayList<Rectangle> monsterBarList = new ArrayList<>();
    private Timer timer;
    private Stage stage;

    private boolean isBoss = false;

    /**
     * initialize the controller of the scene
     */
    public InitialGameController() {
        try {

            this.player = Game.gameSingleInstance().getPlayerI();
            this.dungeon = Game.gameSingleInstance().getDungeonI();
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
        System.out.println(player.getHealth().getValue());
        healthBar.widthProperty().bind(player.getHealth());
        healthBar.setHeight(20);
        monsterHealthBars[0] = monsterBar;
        monsterHealthBars[1] = monsterBar2;
        monsterHealthBars[2] = monsterBar3;
        for (Rectangle r: monsterHealthBars) {
            r.setHeight(20);
        }
        
        SimpleObjectProperty<ObserverObject<Room>> roomSimpleObjectProperty =
                Game.gameSingleInstance().getDungeonI().activeRoomObProperty();
        roomSimpleObjectProperty.addListener(this::onRoomUpdate);

        SimpleObjectProperty<ArrayList<Integer>> cordinates = player.cordinatesProperty();
        cordinates.addListener(this::onPlayerMove);
        timer = new Timer();
        timer.scheduleAtFixedRate(this.monsterSelfMovement(), 1000, 1000);

        this.player.getHealth().addListener(this::onPlayerHealthUpdate);
        this.inventoryMenu.setOnMouseClicked(this::onInventoryClick);
        this.inventoryMenu.setOnKeyPressed(this::handleOnKeyPressed);
        this.weaponImageInit.getStyleClass().add(this.player.getWeapon().getImage());
    }

    private void onInventoryClick(MouseEvent event) {
        AppScenes.navigateTo(this.stage, SceneNames.SHOP);
        event.consume();
    }

    /**
     * handler for the player's health update
     *
     * @param observable health object
     * @param obOldValue old health
     * @param obNewValue new health
     */
    public void onPlayerHealthUpdate(ObservableValue<? extends Number> observable,
                                     Number obOldValue, Number obNewValue) {
        if (this.player.getHealth().get() <= 0) {
            System.out.println("Player lost");
        }
    }

    /**
     * handler for the monster's health update
     *
     * @param observable health object
     * @param obOldValue old health
     * @param obNewValue new health
     */
    public void onMonsterHealthUpdate(ObservableValue<? extends Number> observable,
                                      Number obOldValue, Number obNewValue) {
        boolean allDead = true;
        for (Monster monster: monsterList) {
            if (monster.getHealth().get() <= 0 ) {
                monster.clearCurrent(monsterLayer.getGraphicsContext2D());
            } else {
                allDead = false;
            }
        }
        if (allDead) {
            Game.incMonstersDied(monsterList.size());
            //System.out.println("The number of monsters that died: " + Game.getMonstersDied());

            Game.gameSingleInstance().getActiveRoom().setHasMonster(false);
            for (Rectangle r: monsterHealthBars) {
                r.widthProperty().unbind();
                r.widthProperty().set(0);
            }
        }
    }

    /**
     * handler for the room update
     *
     * @param observable room object
     * @param obOldValue old room
     * @param obNewValue new room
     */
    public void onRoomUpdate(ObservableValue<? extends ObserverObject<Room>> observable,
                             ObserverObject<Room> obOldValue, ObserverObject<Room> obNewValue) {
        Room newValue = obNewValue.getField();
        Room oldValue = obOldValue.getField();

        if (newValue.getParent() != null && newValue.isChallengeRoom()) {
            Alert al = new Alert(AlertType.CONFIRMATION);
            al.setContentText("Would you like to do a Challenge room");
            al.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    al.hide();
                } else {
                    newValue.setChallengeRoom(false);
                    al.hide();
                }
            });
        }

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

        if (!newValue.isHasMonster()) {
            for (Rectangle r: monsterHealthBars) {
                r.widthProperty().unbind();
                r.setWidth(0);
            }
            for (Monster monster: monsterList) {
                monster.getHealth().removeListener(this::onMonsterHealthUpdate);
                monster.clearCurrent(monsterLayer.getGraphicsContext2D());
                Game.gameSingleInstance().getActiveRoom().clearCurrentMonster();
                return;
            }
        }
        //logic for the challenge room
        if (newValue.isChallengeRoom()) {
            System.out.println("this room is a challenge room");
            monsterList = Game.gameSingleInstance().getActiveRoom().getChallengeMonster();
            monsterList.get(0).setPosition(320,180);
            monsterList.get(1).setPosition(250,250);
            monsterList.get(2).setPosition(190 ,120);
            for (int i = 0; i < 3; i++) {
                Monster m = monsterList.get(i);
                IntegerProperty monsterHealth = m.getHealth();
                double multiplier1 = 200.0 / monsterHealth.getValue();
                monsterHealthBars[i].widthProperty().bind(monsterHealth.multiply(multiplier1));
                m.getHealth().addListener(this::onMonsterHealthUpdate);
                m.draw(monsterLayer.getGraphicsContext2D());
            }
            return;

        }
        monsterList.clear();

        Monster monster = null;

        if (!newValue.isChallengeRoom()) {

            if (!this.dungeon.getActiveRoomOb().isPlayerExitsExitRoom()) {
                monster = Game.gameSingleInstance().getActiveRoom().getNewMonster();
            } else {
                monster = Game.gameSingleInstance().getActiveRoom().getBossMonster();
                isBoss = true;
                System.out.println("Warning: boss is here");
            }
        }

        monsterList.add(monster);
        monster.setPosition(225, 240);
        IntegerProperty monsterHealth = monster.getHealth();
        double multiplier1 = 200.0 / monster.getHealth().getValue();
        monsterBar.widthProperty().bind(monsterHealth.multiply(multiplier1));
        monster.getHealth().addListener(this::onMonsterHealthUpdate);
        monster.draw(monsterLayer.getGraphicsContext2D());

    }

    /**
     * method for the monster to move by itself
     *
     * @return Timer
     */
    public TimerTask monsterSelfMovement() {
        return new TimerTask() {
            @Override
            public void run() {
                for (Monster monster: monsterList) {
                     if (!Game.gameSingleInstance().getActiveRoom().isHasMonster()) {
                        return;
                    }
                    if (player == null) {
                        return;
                    }

                    int x = monster.getX();
                    int y = monster.getY();

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
                        Game.gameSingleInstance().getActiveRoom().clearCurrentMonster();
                        continue;
                    }

                    if (player.getHealth().get() <= 0) {
                        player.clear(playerLayer.getGraphicsContext2D());
                        return;
                    }
                    monster.move(x, y);
                    player.reduceHealth(monster);
                    monster.draw(monsterLayer.getGraphicsContext2D());
                }
            }
        };
    }

    /**
     * handler for the player's movement
     *
     * @param observable position of the player
     * @param oldValue   old position
     * @param newValue   new position
     */
    public void onPlayerMove(ObservableValue<? extends ArrayList<Integer>> observable,
                             ArrayList<Integer> oldValue, ArrayList<Integer> newValue) {
        player.draw(playerLayer.getGraphicsContext2D());
    }

    /**
     * unmount the scene
     */
    public void unmount() {
        if (timer != null) {
            timer.purge();
        }

    }

    /**
     * moves character each time we press key
     *
     * @param e key event
     */
    public void handleOnKeyPressed(KeyEvent e) {
        if (player.getHealth().getValue() <= 0) {
            AppScenes.navigateTo(this.stage, SceneNames.LOSE);
            return;
        }

        int x = this.player.getX();
        int y = this.player.getY();
        if (e.getCode().equals(KeyCode.DOWN)) {
            y += this.player.getPlayerSpeed();

        } else if (e.getCode().equals(KeyCode.UP)) {
            y -= this.player.getPlayerSpeed();

        } else if (e.getCode().equals(KeyCode.LEFT)) {
            x -= this.player.getPlayerSpeed();

        } else if (e.getCode().equals(KeyCode.RIGHT)) {
            x += this.player.getPlayerSpeed();
        } else if (e.getCode().equals(KeyCode.SPACE)) {
            //player attack monster
            for (Monster monster: monsterList) {
                if (Game.gameSingleInstance().getActiveRoom().isHasMonster()) {
                    monster.reduceHealth(player);
                }
                if (monster != null && !Game.gameSingleInstance().getActiveRoom().getIsMoneyUpdated() &&
                        monster.getHealth().get() <= 0) {

                    Game.getPlayer().addGold(Game.gameSingleInstance().getActiveRoom().getGoldFoundInTheRoom());

                    this.money.setText("$" + Game.getPlayer().getGold());

                    Game.gameSingleInstance().getActiveRoom().setIsMoneyUpdated(true);

                    if (isBoss) {

                        AppScenes.navigateTo(this.stage, SceneNames.WIN);
                    }
                }
            }
        }

        if (this.dungeon.isPositionValid(x, y)) {
            this.player.move(x, y);
            this.dungeon.getActiveRoomOb().trackPlayerMovement(player.getX(), player.getY());
        }

        e.consume();
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

    /**
     * load canvas
     */
    public void loadCanvas() {
        for (Canvas canvas : this.canvasList) {
            canvas.setHeight(Game.WINDOW_HEIGHT);
            canvas.setWidth(Game.WINDOW_WIDTH);
        }

        this.drawGame();
    }

    /**
     * draw game layer on screen
     */
    public void drawGame() {
        dungeon.setActivePlayer(player);
        Room initialRoom = dungeon.getActiveRoomOb();
        NodeLayer roomNodeLayer = initialRoom.getRoomMap().getRoomLayer();
        ArrayList<NodeLayer> inactiveDoors = initialRoom.getInactiveDoors();

        roomNodeLayer.draw(roomLayer.getGraphicsContext2D());
        player.draw(playerLayer.getGraphicsContext2D());

        for (NodeLayer doorNodeLayer : inactiveDoors) {
            doorNodeLayer.setPosition(doorNodeLayer.getPositionAtX(),
                    doorNodeLayer.getPositionAtY());
            doorNodeLayer.draw(doorsLayer.getGraphicsContext2D());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
