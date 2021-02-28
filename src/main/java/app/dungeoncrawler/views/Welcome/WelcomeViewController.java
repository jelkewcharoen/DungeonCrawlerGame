package app.dungeoncrawler.views.Welcome;

import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.SceneNames;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WelcomeViewController {
    
    @FXML
    public Button buttonNavigate;
    
    public void initialize() {
        System.out.println("ivan contro");
        System.out.println(buttonNavigate);
        buttonNavigate.setOnMouseClicked((event) -> {
            try {
                System.out.println("ivan 12");
    
                Node node = (Node) event.getSource();
                Stage thisStage = (Stage) node.getScene().getWindow();
                
                AppScenes.navigateTo(thisStage, SceneNames.CONFIGURATION);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
