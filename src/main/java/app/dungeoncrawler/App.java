package app.dungeoncrawler;

import app.dungeoncrawler.views.AppScenes;
import app.dungeoncrawler.views.Configuration.ConfigurationView;
import app.dungeoncrawler.views.SceneNames;
import app.dungeoncrawler.views.Welcome.WelcomeView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		AppScenes scenes = new AppScenes(stage);
		stage.setWidth(720);
		stage.setHeight(512);
		stage.setScene(scenes.getMainPage());
		stage.setTitle("Team 39: 404 Not Found");
		stage.setResizable(false);
		stage.show();
	}
	
	@Override
	public void stop() {
		System.out.println("Stopping");
	}
	
	@Override
	public void init() {
		System.out.println("init");
	}
	
	public static void main(String... args) {
		Application.launch(args);
	}
}
