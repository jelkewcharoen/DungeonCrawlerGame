package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DefaultWeapons;
import app.dungeoncrawler.utils.Difficulties;
import org.junit.Test;

import static org.junit.Assert.*;

public class HealthPotionTest {

    @Test
    public void addToPlayer() {
        Player player = new Player("some", DefaultWeapons.WEAPON1, Difficulties.EASY);
        System.out.println(player.getHealth());
        int health = player.getHealth().get();
        HealthPotion sh = new HealthPotion("somerand");
        sh.addToPlayer(player);
        assertTrue(health < player.getHealth().get());
    }
}