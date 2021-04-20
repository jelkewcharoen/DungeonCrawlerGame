package app.dungeoncrawler.views.Win;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.InitialGame.InitialGameController;
import app.dungeoncrawler.views.Inventory.InventoryViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class WinViewController implements Initializable {
    @FXML
    private Button exit;

    @FXML
    private Button yes;

    @FXML
    private Label labelstat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        labelstat.setText("Number of monsters killed: " + Game.getMonstersDied()
        + "\n Number of items bought: " + Game.getItemsBought()
        + "\n Number of potions used: " + Game.getItemsUsed());

        yes.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WELCOME);
        });

        exit.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();

            thisStage.close();
        });
    }
}
