package app.dungeoncrawler.models;

import org.junit.Test;

import static org.junit.Assert.*;
import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;

public class ShieldPotionTest {

    @Test
    public void addToPlayer() {
        Player player = new Player("some", DefaultWeapons.WEAPON1, Difficulties.EASY);
        int health = player.getHealth().get();
        ShieldPotion sh = new ShieldPotion("somerand");
        sh.addToPlayer(player);
        assertTrue(health < player.getHealth().get());
    }
}