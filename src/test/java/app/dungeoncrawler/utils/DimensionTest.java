package app.dungeoncrawler.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class DimensionTest extends TestCase {

    @Test
    public void testAverageX() {
        Dimension dimension = new Dimension(2, 4, 3, 9);
        int avrX = dimension.averageX();
        assertEquals((dimension.lowX + dimension.highX) / 2, avrX);
    }

    @Test
    public void testAverageY() {
        Dimension dimension = new Dimension(2, 4, 3, 9);
        int avrY = dimension.averageY();
        assertEquals((dimension.lowY + dimension.highY) / 2, avrY);
    }

    @Test
    public void testAverage() {
        Dimension dimension = new Dimension(2, 4, 3, 9);
        assertEquals(5, Dimension.average(dimension.highY, dimension.lowX));
    }

    @Test
    public void testIsInsideCoordinates() {

        Dimension dimension = new Dimension(2, 4, 3, 9);

        assertEquals(true, dimension.isInsideCoordinates(3, 6));
        assertEquals(false, dimension.isInsideCoordinates(5, 6));
    }

}