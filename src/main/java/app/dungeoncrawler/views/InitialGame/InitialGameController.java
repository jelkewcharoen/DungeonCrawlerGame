package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.models.Game;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class InitialGameController {
    
    @FXML
    public Text money;
    
    public void initialize() {
        System.out.println(money);    
    }
    
    public void mounting() {
        this.money.setText("$" + Game.getPlayer().getGold());
    }
}
