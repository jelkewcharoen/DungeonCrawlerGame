package app.dungeoncrawler.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class DimensionTest extends TestCase {

    @Test
    public void testAverageX() {
        Dimension dimension = new Dimension(2, 4, 3, 9);
        int avrX = dimension.averageX();
        assertEquals((dimension.getLowX() + dimension.getHighX()) / 2, avrX);
    }

    @Test
    public void testAverageY() {
        Dimension dimension = new Dimension(2, 4, 3, 9);
        int avrY = dimension.averageY();
        assertEquals((dimension.getLowY() + dimension.getHighY()) / 2, avrY);
    }

    @Test
    public void testAverage() {
        Dimension dimension = new Dimension(2, 4, 3, 9);
        assertEquals(5, Dimension.average(dimension.getHighY(), dimension.getLowX()));
    }
}