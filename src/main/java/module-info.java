module app.dungeoncrawler {
    requires javafx.fxml;
    requires javafx.controls;
    exports app.dungeoncrawler;
    exports app.dungeoncrawler.views.Welcome;
    exports app.dungeoncrawler.views.Configuration;
    exports app.dungeoncrawler.views.InitialGame;
    exports app.dungeoncrawler.utils;
    opens app.dungeoncrawler.views.Welcome;
    opens app.dungeoncrawler.views.Configuration;
    opens app.dungeoncrawler.views.InitialGame;
}