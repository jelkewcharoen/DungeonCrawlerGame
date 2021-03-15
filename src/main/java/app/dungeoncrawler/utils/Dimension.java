package app.dungeoncrawler.utils;

public class Dimension {
    private int lowX;
    private int highX;
    private int lowY;
    private int highY;

    /**
     * Constructs dimension
     * @param x1 first x candidates for low or high x value
     * @param x2 second x candidates for low or high x value
     * @param y1 first y candidates for low or high y value
     * @param y2 second y candidates for low or high y value
     */
    public Dimension(int x1, int x2, int y1, int y2) {
        this.lowX = Math.min(x1, x2);     
        this.highX = Math.max(x1, x2);         
        this.lowY = Math.min(y1, y2);     
        this.highY = Math.max(y1, y2);     
    }

    /**
     * checks if player is inside coordinates
     * @param x x coordinates of player position
     * @param y y coordinates of player position
     * @return true if player is inside coordinates, false otherwise
     */
    public boolean isInsideCoordinates(int x, int y) {
        return x <= this.highX 
                && x >= this.lowX 
                && y >= this.lowY 
                && y <= this.highY;
    }

    /**
     * returns average x value
     * @return average x value
     */
    public int averageX() {
        return Dimension.average(this.highX, this.lowX);
    }

    /**
     * returns average y value
     * @return average y value
     */
    public int averageY() {
        return Dimension.average(this.highY, this.lowY);
    }

    /**
     * returns average value of two given values
     * @param highNumber high value to divide by 2 after addition
     * @param lowNumber low value to divide by 2 after addition
     * @return average value of high number and low number
     */
    public static int average(int highNumber, int lowNumber) {
        return  (highNumber + lowNumber) / 2;
    }

    /**
     * returns low x coordinate value
     *
     * @return low x coordinate
     */
    public int getLowX() {
        return this.lowX;
    }

    /**
     * returns high x coordinate value
     *
     * @return high x coordinate
     */
    public int getHighX() {
        return this.highX;
    }

    /**
     * returns low y coordinate value
     *
     * @return low y coordinate
     */
    public int getLowY() {
        return this.lowY;
    }

    /**
     * returns high y coordinate value
     *
     * @return high y coordinate
     */
    public int getHighY() {
        return this.highY;
    }

}
