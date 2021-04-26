package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpeedPotionTest {

    @Test
    public void addToPlayer() {
        Player player = new Player("some", DefaultWeapons.WEAPON1, Difficulties.EASY);
        int sp = player.getPlayerSpeed();
        SpeedPotion sh = new SpeedPotion("somerand");
        sh.addToPlayer(player);
        assertTrue( sp < player.getPlayerSpeed());
    }
}