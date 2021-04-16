package app.dungeoncrawler.views.Welcome;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeViewController implements Initializable {

    @FXML
    private Button buttonNavigate;

    /**
     * Initializes the WelcomeViewController screen
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonNavigate.setOnMouseClicked((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.CONFIGURATION);
        });
    }
}
