package app.dungeoncrawler.models;

public class Boundary {
    //check reference image
    int[] xPos = new int[5];
    int[] yPos = new int[5];
    int numOfDoor;

    public Boundary(int numOfDoor) {
        this.numOfDoor = numOfDoor;
    }

    /**
     * check whether x and y are in boundary when the room has only 1 exit
     *
     * @param x x position
     * @param y y position
     * @return true if the position is within the boundary
     */
    public static boolean withinBoundary(int x, int y) {

        boolean result = true;
        if (x < 150) {
            if (y >= 175 && y <= 205) { // left exit
                result = true;
            } else {
                result = false;
            }
        }
        if (x >= 150 && x <= 225) {
            if (y < 55 || y > 325) { //before bottom exit
                result = false;
            }
        } else if (x >= 270 && x <= 285) { //top exit - CLOSED -
            if (y < 55 ||y > 325) {
                result = false;
            }
        } else if (x > 405) { // right exit
            result = false;
        } else {
            if (y < 55 || y > 325) {
                result = false;
            }
        }


        return result;
    }




    /**
     * check whether x and y are in boundary when the room has 2 exits
     *
     * @param x x position
     * @param y y position
     * @return true if the position is within the boundary
     */
    public static boolean withinBoundary2Exit(int x, int y) {

        boolean result = true;
        if (x < 150) {
            if (y >= 175 && y <= 205) { // left exit
                result = true;
            } else {
                result = false;
            }
        }
        if (x >= 150 && x <= 225) {
            if (y < 55 || y > 325) { //before bottom exit
                result = false;
            }
        } else if (x >= 270 && x <= 285) { //top exit
            if (y > 325) {
                result = false;
            }
        } else if (x > 405) { // right exit
            result = false;
        } else {
            if (y < 55 || y > 325) {
                result = false;
            }
        }


        return result;
    }


    /**
     * check whether x and y are in boundary when the room has 4 exits
     *
     * @param x x position
     * @param y y position
     * @return true if the position is within the boundary
     */
    public static boolean withinBoundary4Exit(int x, int y) {

        boolean result = true;
        if (x < 150) {
            if (y >= 175 && y <= 205) { // left exit
                result = true;
            } else {
                result = false;
            }
        }
        if (x >= 150 && x < 210) {
            if (y < 55 || y > 325) { //before bottom exit
                result = false;
            }
        } else if (x >= 210 && x <= 225) { // bottom exit
            if (y < 55) {
                result = false;
            }
        } else if (x >= 270 && x <= 285) { //top exit
            if (y > 325) {
                result = false;
            }
        } else if (x > 405) {
            if (y >= 130 && y <= 160) {
                result = true;
            } else {
                result = false;
            }
        } else {
            if (y < 55 || y > 325) {
                result = false;
            }
        }


        return result;
    }


    /**
     * check whether x and y are in boundary when the room has 3 exits
     * @param x - x coordinate of the player
     * @param y - y coordinate of the player
     * @return true if the player is within the boundary
     */
    public static boolean withinBoundary3Exits(int x, int y) {
        boolean result = true;
        if (x < 150) {
            if (y >= 175 && y <= 205) { // left exit
                result = true;
            } else {
                result = false;
            }
        }
        if (x >= 150 && x < 210) {
            if (y < 55 || y > 325) { //before bottom exit
                result = false;
            }
        } else if (x >= 210 && x <= 225) { // bottom exit
            if (y < 55) {
                result = false;
            }
        } else if (x >= 270 && x <= 285) { //top exit
            if (y > 325) {
                result = false;
            }
        } else if (x > 405) {
            result = false;
        } else {
            if (y < 55 || y > 325) {
                result = false;
            }
        }


        return result;
    }
}
