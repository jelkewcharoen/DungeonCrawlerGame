package app.dungeoncrawler.views.Welcome;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.utils.SceneNames;
import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.ViewBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WelcomeView extends ViewBase {
    private Scene scene;

    /**
     * Constructor to create the Welcome View
     * @param stage - used to set up the welcome view
     */
    public WelcomeView(Stage stage) {
        super(stage, SceneNames.WELCOME);
        this.buildScene();
    }

    /**
     * Builds the Welcome View Scene
     */
    public void buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("WelcomeView.fxml"));
            HBox box = loader.load();
            WelcomeViewController controller = loader.getController();

            this.scene = new Scene(box, this.stage.getWidth(), this.stage.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mountingScene() {
        Game.newGame(true);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
