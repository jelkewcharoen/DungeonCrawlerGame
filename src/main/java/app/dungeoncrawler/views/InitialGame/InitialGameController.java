package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialGameController implements Initializable {
    
    @FXML
    private Text money;

    /**
     * initialize the controller of the scene
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(money.getText());
    }
    /**
     * trigger the scene to start
     */
    public void mounting() {
        this.money.setText("" + Game.getPlayer().getGold());
    }


}
