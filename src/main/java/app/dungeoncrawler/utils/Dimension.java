package app.dungeoncrawler.utils;

public class Dimension {
    private int lowX;
    private int highX;
    private int lowY;
    private int highY;

    /**
     * intializes dimensions for rooms
     *
     * @param x1 x coordinate
     * @param x2 x coordinate
     * @param y1 y coordinate
     * @param y2 y coordinate
     */
    public Dimension(int x1, int x2, int y1, int y2) {
        this.lowX = Math.min(x1, x2);     
        this.highX = Math.max(x1, x2);         
        this.lowY = Math.min(y1, y2);     
        this.highY = Math.max(y1, y2);     
    }

    /**
     * returns true if coordinates passed in are inside the dimensions
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return boolean on if coordinates are inside the dimensions
     */
    public boolean isInsideCoordinates(int x, int y) {
        return x <= this.highX 
                && x >= this.lowX 
                && y >= this.lowY 
                && y <= this.highY;
    }

    /**
     * averages the high and low x coordinate values
     *
     * @return the average between the high and low coordinate values
     */
    public int averageX() {
        return Dimension.average(this.highX, this.lowX);
    }

    /**
     * averages the high and low y coordinate values
     *
     * @return the average between the high and low coordinate values
     */
    public int averageY() {
        return Dimension.average(this.highY, this.lowY);
    }

    /**
     * calculates the average of two numbers
     *
     * @param highNumber the greater integer
     * @param lowNumber the smaller integer
     * @return the average of both integers
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
