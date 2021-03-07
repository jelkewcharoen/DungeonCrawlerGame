package app.dungeoncrawler.models;

import java.util.HashMap;
import java.util.Random;

public class Room {
    private int parentID;
    private boolean hasMonster; //has a monster
    private boolean isExit; //is an exit room
    private int doors; //number of doors
    private int depth;
    private HashMap<Integer, Integer> doorID = new HashMap<>();
    private String image;

    /**
     * instructor for the class Room
     * @param parentID the id of the previous room
     * @param hasMonster if the room has a monster
     * @param isExit if the room is the exit room
     * @param doors the number of doors the room has
     * @param depth how far away from the starting room this room is
     */
    public Room(int parentID, boolean hasMonster, boolean isExit, int doors, int depth) {
        this.parentID = parentID;
        this.hasMonster = hasMonster;
        this.isExit = isExit;
        this.doors = doors;
        this.depth = depth;
    }

    /**
     * getter for the monster status
     * @return whether there is a monster in the room
     */
    public boolean getMonster() {
        return hasMonster;
    }

    /**
     * remove the monster in the room
     */
    public void killMonster() {
        hasMonster = false;
    }

    /**
     * getter for isExit
     * @return whether the room is an exit room
     */
    public boolean getIsExit() {
        return isExit;
    }

    /**
     * getter for the number of doors
     * @return number of doors
     */
    public int getDoors() {
        return doors;
    }

    /**
     * set the id of the specified door to the specified id
     * @param index index of the door
     * @param id  door/room id
     */
    public void setDoorID(int index, int id) {
        doorID.put(index, id);
    }
    /**
     * getter for the id's of the specified door
     * @param index the index of the door
     * @return the id of the door
     */
    public int getDoorID(int index) {
        return doorID.get(index);
    }

    /**
     * getter for the depth of the room
     * @return depth of the room
     */
    public int getDepth() {
        return depth;
    }

    /**
     * getter for the image
     * @return the current image for the room
     */
    public String getImage() {
        return image;
    }

    /**
     * setter for the room's image
     * @param image the image for the room
     */
    public void setImage(String image) {
        this.image = image;
    }
}
