module app.dungeoncrawler {
    requires javafx.fxml;
    requires javafx.controls;
    exports app.dungeoncrawler;
    exports app.dungeoncrawler.views.Welcome;
    exports app.dungeoncrawler.views.Configuration;
    exports app.dungeoncrawler.views.InitialGame;
    exports app.dungeoncrawler.Components.WeaponInv;
    exports app.dungeoncrawler.Components.ShopItem;
    exports app.dungeoncrawler.utils;
    exports app.dungeoncrawler.Components.PotionInv;
    opens app.dungeoncrawler.views.Welcome;
    opens app.dungeoncrawler.Components.WeaponInv;
    opens app.dungeoncrawler.views.Configuration;
    opens app.dungeoncrawler.views.InitialGame;
    opens app.dungeoncrawler.views.Win;
    opens app.dungeoncrawler.views.Lose;
    opens app.dungeoncrawler.views.Inventory;
    opens app.dungeoncrawler.Components.ShopItem;
    opens app.dungeoncrawler.Components.PotionInv;
}