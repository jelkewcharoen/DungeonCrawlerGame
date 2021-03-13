package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Dungeon;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Room;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InitialGameController implements Initializable {
    @FXML
    private Text money;
    
    @FXML private Canvas roomLayer;
    @FXML private Canvas playerLayer;
    @FXML private Pane initialGamePane;
    
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
        this.money.setText("" + Game.getPlayer().getGold());
    }
    
    @FXML public void handleOnKeyPressed(KeyEvent e) {
        Player player = Game.getPlayer();

        if (e.getCode().equals(KeyCode.DOWN)) {
            player.movePlayer(player.getX(), player.getY() + Player.PLAYER_SPEED, playerLayer.getGraphicsContext2D());
        } else if (e.getCode().equals(KeyCode.UP)) {
            player.movePlayer(player.getX(), player.getY() - Player.PLAYER_SPEED, playerLayer.getGraphicsContext2D());
        } else if (e.getCode().equals(KeyCode.LEFT)) {
            player.movePlayer(player.getX() - Player.PLAYER_SPEED, player.getY(), playerLayer.getGraphicsContext2D());
        } else if (e.getCode().equals(KeyCode.RIGHT)) {
            player.movePlayer(player.getX() + Player.PLAYER_SPEED, player.getY(), playerLayer.getGraphicsContext2D());
        }
    } 

    public void loadRoom(Pane pane) {
        // to be removed
        Game.createDungeon("");
        Game.createPlayer("ivan", DefaultWeapons.WEAPON1);
        //to be removed ^

        Player player = Game.getPlayer();
        Dungeon dungeon = Game.getDungeon();
        GraphicsContext roomLayerGc = roomLayer.getGraphicsContext2D();
        GraphicsContext playerLayerGc = playerLayer.getGraphicsContext2D();
        
        Room initialRoom = dungeon.getInitialRoom();
        NodeLayer roomNodeLayer = initialRoom.getRoomMap().getRoomLayer();
        ArrayList<NodeLayer> inactiveDoors = initialRoom.getInactiveDoors();
        
        player.setPlayerPosition(
                initialRoom.getStartingDoor().getDimension().averageX(), 
                initialRoom.getStartingDoor().getDimension().averageY()
        );

        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            roomLayer.setWidth(newValue.getWidth());
            roomLayer.setHeight(newValue.getHeight());
            playerLayer.setHeight(newValue.getHeight());
            playerLayer.setWidth(newValue.getWidth());
            roomNodeLayer.draw(roomLayerGc);
            
            for (int i = 0; i < inactiveDoors.size(); i++) {
                System.out.println("SOMETHING");
                NodeLayer doorNodeLayer = inactiveDoors.get(i);
                doorNodeLayer.setPosition(doorNodeLayer.getDimension().averageX(), doorNodeLayer.getDimension().averageY());
                doorNodeLayer.draw(roomLayerGc);
            }

            player.draw(playerLayerGc);
        });
    }
}
