package app.dungeoncrawler.views.Win;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WinViewController implements Initializable {
    @FXML
    private Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WELCOME);
        });
    }
}
