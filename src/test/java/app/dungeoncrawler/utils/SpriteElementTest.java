package app.dungeoncrawler.utils;

import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.models.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpriteElementTest {

    @Test
    public void testCollides() {
        SpriteElement sp1 = new Player("Some", DefaultWeapons.WEAPON1, Difficulties.EASY);
        sp1.setPosition(0, 0);
        SpriteElement sp2 = new Monster("/some", 30);

        sp2.setPosition(200, 200);
        assertFalse(sp1.collides(sp2));

        sp1.setPosition(0, 0);
        sp2.setPosition(10, 10);
        assertTrue(sp1.collides(sp2));
    }
}