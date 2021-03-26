package app.dungeoncrawler.views.Configuration;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Player;
import app.dungeoncrawler.models.Weapon;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.utils.SceneNames;
import javafx.beans.property.SimpleStringProperty;
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

    private String selectedDifficulty = null;
    private DefaultWeapons selectedWeapon = null;
    private SimpleStringProperty powerObservable = new SimpleStringProperty("The power is: 0");
    private SimpleStringProperty errorText = new SimpleStringProperty("Please enter a valid name");
    private String nameText = "";
    private int power;

    @FXML
    private Button startGame;
    @FXML
    private Text error;
    @FXML
    private Label powerDisplay;
    @FXML
    private TextField nameEnter;
    @FXML
    private Button weapon1;
    @FXML
    private Button weapon2;
    @FXML
    private Button weapon3;
    @FXML
    private ComboBox<String> difficultyLevel;    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.onDifficultyChange();
        this.selectWeaponEventHandler();
        this.bindLabels();
        this.createSubmitButtonEventHandler();
        this.onNameInputChange();
    }

    /**
     * add the labels for the weapon power and error message
     */
    public void bindLabels() {
        this.powerDisplay.textProperty().bind(this.powerObservable);
        this.error.textProperty().bind(this.errorText);
    }

    /**
     * check if the name field is not empty
     * @return whether the name text field is filled
     */
    private boolean isFormValid() {
        if (this.nameText == "" || !Player.isPlayerNameValid(this.nameText)
        ) {
            this.errorText.set("Please enter a valid name");
            return false;
        }

        this.errorText.set("");
        return true;
    };

    /**
     * create the event handle for the submit button
     */
    private void createSubmitButtonEventHandler() {

        this.startGame.setOnAction((event) -> {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            
            if (!this.isFormValid()) {
                return;
            }
            
            Game.Game().createDungeon(this.selectedDifficulty);
            Game.Game().createPlayer(this.nameText, this.selectedWeapon);
            
            AppScenes.navigateTo(thisStage, SceneNames.INITIAL_GAME);
        }); 
    }

    /**
     * map each weapon to an id and return the weapon of the chosen id
     * @param weaponId the id of the chosen weapon
     * @return the chosen weapon
     */
    private DefaultWeapons mapWeaponIdToEnum(String weaponId) {
        Map<String, DefaultWeapons> weaponMap = new HashMap() {{
                put(weapon1.getId(), DefaultWeapons.WEAPON1);
                put(weapon2.getId(), DefaultWeapons.WEAPON2);
                put(weapon3.getId(), DefaultWeapons.WEAPON3);
                }};
        return weaponMap.get(weaponId) != null ? weaponMap.get(weaponId) : DefaultWeapons.WEAPON1;
    }

    /**
     * set the event handler of the weapon selection field
     */
    private void selectWeaponEventHandler() {
        Button[] weapons = new Button[]{weapon1, weapon2, weapon3};
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button weapon = (Button) event.getSource();
                for (int i = 0; i < weapons.length; i++) {
                    if (weapons[i].getId() == weapon.getId()) {
                        
                        // updating styles
                        weapons[i].getStyleClass().clear();
                        weapons[i].getStyleClass().add("pickWeaponTile");  
                        weapons[i].getStyleClass().add("selected");
                        
                        // assigns selected weapon
                        selectedWeapon = mapWeaponIdToEnum(weapon.getId());
                        
                        // retrieving the power from the weapon
                        power = Weapon.getWeaponsWeaponMap().get(selectedWeapon).getPower();
                        powerObservable.set("The power is: " + power);
                        
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

    /**
     * set the difficulty level based on the player's choice
     */
    private void onDifficultyChange() {
        this.difficultyLevel.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (oldValue != newValue && this.selectedDifficulty != newValue) {
                    this.selectedDifficulty = newValue;
                    this.isFormValid();
                }
            });
    }

    /**
     * set the name to the name that player writes
     */
    public void onNameInputChange() {
        this.nameEnter.textProperty().addListener((observable, oldValue, newValue) -> {
            this.nameText = newValue;
            this.isFormValid();
        });
    }

    /**
     * getter fot the weapon's power
     * @return the weapon's power
     */
    public int getPower() {
        return power;
    }
}
