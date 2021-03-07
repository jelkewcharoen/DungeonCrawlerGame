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
     * check whether the x and y position is within the boundary
     * @param x x position
     * @param y y position
     * @return true if the position is within the boundary
     */
    public boolean withinBoundary(int x, int y) {
        if(numOfDoor >= 3) {
            if (y > yPos[2] && y < yPos[4] && x < xPos[0]) {
                return true;
            }
        }
        if(numOfDoor >= 2) {
            if (y > yPos[1] && y < yPos[3] && x > xPos[5]) {
                return true;
            }
        }
        if(numOfDoor >= 1) {
            if (y > yPos[5] && x > xPos[1] && x < xPos[3]) {
                return true;
            }
        }
        if(numOfDoor >= 0) {
            if (y > yPos[0] && y < yPos[5] && x > xPos[0] && x < xPos[5]) {
                return true;
            } else if (y < yPos[0] && x > xPos[2] && x < xPos[4]) {
                return true;
            }
        }
        return false;
    }
}
