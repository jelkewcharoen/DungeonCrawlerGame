package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class InitialGame extends ViewBase {
    private Scene scene;
    private InitialGameController controller;
    
    public InitialGame(Stage stage) {
        super(stage, SceneNames.INITIAL_GAME);
        this.buildScene();
    }

    public void buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("InitialGame.fxml"));
            AnchorPane root = loader.load();
            System.out.println(root.getId());
            InitialGameController controller = loader.getController();
            this.controller = controller;
            
            this.scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mountingScene() {
        this.controller.mounting();
    }

    public Scene getScene() {
        return this.scene;
    }
    public String getMoney() {
        return money.getText();
    }
}
