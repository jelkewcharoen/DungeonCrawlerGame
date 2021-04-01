package app.dungeoncrawler.utils;

import app.dungeoncrawler.models.Game;
import app.dungeoncrawler.models.Monster;
import app.dungeoncrawler.models.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class FighterTest extends TestCase {

    @Test
    public void testPAttacksM() {

        Game.Game().createDungeon("EASY");

        Game.Game().createPlayer("test player", DefaultWeapons.WEAPON1);

        Player  p = Game.getPlayer();

        Monster m = Game.Game().getNewMonster();

        p.setPosition(225, 240);

        m.setPosition(225, 240);

        Integer attackN = m.getHealth().getValue();

        m.reduceHealth(p);

        Integer attackY = m.getHealth().getValue();

        assertTrue(attackN > attackY);
    }

    @Test
    public void testMAttacksP() {

        Game.Game().createDungeon("EASY");

        Game.Game().createPlayer("test player", DefaultWeapons.WEAPON1);

        Player  p = Game.getPlayer();

        Monster m = Game.Game().getNewMonster();

        p.setPosition(225, 240);

        m.setPosition(225, 240);

        Integer attackN = p.getHealth().getValue();

        p.reduceHealth(m);

        Integer attackY = p.getHealth().getValue();

        assertTrue(attackN > attackY);
    }
}