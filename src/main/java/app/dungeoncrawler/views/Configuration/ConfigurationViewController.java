package app.dungeoncrawler.views.Configuration;

import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.SceneNames;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfigurationViewController {

    public int power;
    @FXML
    public Button buttonNavigate;
    public TextField nameEnter;
    public Text error;
    public Button weapon1;
    public Button weapon2;
    public Button weapon3;
    public Label powerDisplay;

    public void initialize() {
        
        buttonNavigate.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            if (checkName()) {

                AppScenes.navigateTo(thisStage, SceneNames.INITIAL_GAME);
            } else {
                error.setText("Please type a valid name!");
            }
        });

        weapon1.setOnAction((event) -> {
            power = 100;
            powerDisplay.setText("The power is: " + power);

        });

        weapon2.setOnAction((event) -> {
            power = 200;
            powerDisplay.setText("The power is: " + power);

        });

        weapon3.setOnAction((event) -> {
            power = 300;
            powerDisplay.setText("The power is: " + power);

        });
    }

    private boolean checkName() {
        boolean result = true;
        String n = nameEnter.getText();
        if (n == null) {
            result = false;

        } else if (n.trim().isEmpty()) {
            result = false;

        } else if (n.equals("")) {
            result = false;

        }
        return result;
    }
    public String errormessage() {
        return error.getText();
    }
    public Text getError() {
        return error;
    }
    public int getPower() {
        return power;
    }
}
