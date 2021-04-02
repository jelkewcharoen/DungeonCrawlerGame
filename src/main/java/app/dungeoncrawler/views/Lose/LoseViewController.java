package app.dungeoncrawler.views.Lose;


import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class LoseViewController implements Initializable {

    @FXML
    private Button exit;

    @FXML
    private Button yes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yes.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            AppScenes.navigateTo(thisStage, SceneNames.WELCOME);
        });

        exit.setOnAction((event) -> {
            exit();
        });
    }
}
