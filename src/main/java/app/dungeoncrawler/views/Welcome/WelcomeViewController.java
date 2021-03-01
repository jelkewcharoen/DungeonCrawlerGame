package app.dungeoncrawler.views.Welcome;

import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeViewController {
    
    @FXML
    public Button buttonNavigate;

    /**
     * Initializes the WelcomeViewController screen
     */
    public void initialize() {
        buttonNavigate.setOnMouseClicked((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.CONFIGURATION);
        });
    }
}
