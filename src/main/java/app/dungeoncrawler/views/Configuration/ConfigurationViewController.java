package app.dungeoncrawler.views.Configuration;

import app.dungeoncrawler.models.Dungeon;
import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Weapon;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ConfigurationViewController implements Initializable {

    private String selectedDifficulty;
    private DefaultWeapons selectedWeapon;
    private SimpleBooleanProperty isButtonDisabled = new SimpleBooleanProperty(true);
    private String nameText = "";
    public int power;

    @FXML
    public Button startGame;   
    public Text error;
    public Label powerDisplay;
    public TextField nameEnter;    
    public Button weapon1;        
    public Button weapon2;    
    public Button weapon3;
    public ComboBox<String> difficultyLevel;    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.onDifficultyChange();
        this.selectWeaponEventHandler();
        this.onnameEnterChange();
        this.createSubmitButtonEventHandler();
    }
    
    private void isFormValid() {
        if (this.selectedWeapon == null 
                || selectedDifficulty == null 
                || this.nameText == "" 
                || !Player.isPlayerNameValid(this.nameText)
        ) {
            this.isButtonDisabled.set(true);
            return;
        }

        this.isButtonDisabled.set(false);
    };
    
    private void createSubmitButtonEventHandler() {
        this.startGame.disableProperty().bind(this.isButtonDisabled);
        this.startGame.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();

            if (!Player.isPlayerNameValid(nameText)) {
                error.setText("Please type a valid name!");
                return;
            }
            
            Game.createDungeon(this.selectedDifficulty);
            Game.createPlayer(this.nameText, this.selectedWeapon);
            
            AppScenes.navigateTo(thisStage, SceneNames.INITIAL_GAME);
        }); 
    }
    
    private DefaultWeapons mapWeaponIdToEnum(String weaponId) {
        Map<String, DefaultWeapons> weaponMap = new HashMap <> (){{
            put(weapon1.getId(), DefaultWeapons.WEAPON1);
            put(weapon2.getId(), DefaultWeapons.WEAPON2);
            put(weapon3.getId(), DefaultWeapons.WEAPON3);
        }};
        return weaponMap.get(weaponId) != null ? weaponMap.get(weaponId) : DefaultWeapons.WEAPON1;
    }
    
    private void selectWeaponEventHandler() {
        Button[] weapons = new Button[]{ weapon1,weapon2, weapon3 };
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button weapon = (Button) event.getSource();
                for (int i = 0; i < weapons.length; i++) {
                    if (weapons[i].getId() == weapon.getId()) {
                        weapons[i].getStyleClass().clear();
                        weapons[i].getStyleClass().add("pickWeaponTile");  
                        weapons[i].getStyleClass().add("selected");
                        
                        selectedWeapon = mapWeaponIdToEnum(weapon.getId());
                    } else {
                        weapons[i].getStyleClass().clear();
                        weapons[i].getStyleClass().add("pickWeaponTile");
                    }
                }
                isFormValid();
            }
        };
        
        weapon1.setOnAction(handler);
        weapon2.setOnAction(handler);
        weapon3.setOnAction(handler);
    }
    
    private void onDifficultyChange() {
        this.difficultyLevel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue && this.selectedDifficulty != newValue) {
                this.selectedDifficulty = newValue;
                this.isFormValid();
            }
        });
    }
    
    public void onnameEnterChange() {
        this.nameEnter.textProperty().addListener((observable, oldValue, newValue) -> {
            this.nameText = newValue;
            this.isFormValid();
        });

        weapon1.setOnAction((event) -> {
            power = Weapon.defaultWeapons.get(this.selectedWeapon).getPower();
            powerDisplay.setText("The power is: " + power);

        });

        weapon2.setOnAction((event) -> {
            power = Weapon.defaultWeapons.get(this.selectedWeapon).getPower();
            powerDisplay.setText("The power is: " + power);

        });

        weapon3.setOnAction((event) -> {
            power = Weapon.defaultWeapons.get(this.selectedWeapon).getPower();
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
