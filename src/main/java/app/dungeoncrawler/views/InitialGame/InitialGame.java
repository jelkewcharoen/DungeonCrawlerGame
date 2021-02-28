package app.dungeoncrawler.views.InitialGame;

import app.dungeoncrawler.views.SceneNames;
import app.dungeoncrawler.views.ViewBase;
import app.dungeoncrawler.views.Welcome.WelcomeViewController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class InitialGame extends ViewBase {
    private Scene scene;
    int StartingMoneyInt = 50;  //this will change according to the difficulty
    String StartingMoney = ""+StartingMoneyInt;
    Text money;
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

            money = new Text(StartingMoney);
            money.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
            money.setFill(Color.BLACK);
            money.setY(55);
            money.setX(80);

            root.getChildren().add(money);
            this.scene = new Scene(root, this.stage.getWidth(), this.stage.getHeight());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return this.scene;
    }
    public String getMoney() {
        return money.getText();
    }
}
