package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Game;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class InitialGameController {
    
    @FXML
    public Text money;

    /**
     * initialize the controller of the scene
     */
    public void initialize() {
        System.out.println(money);    
    }

    /**
     * trigger the scene to start
     */
    public void mounting() {
        this.money.setText("" + Game.getPlayer().getGold());
    }
}
