package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.GameMap;
import app.dungeoncrawler.utils.NodeLayer;
import app.dungeoncrawler.utils.Sprite;
import app.dungeoncrawler.utils.SpriteElement;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Room {
    private final int ROOM_HEIGHT = 480;
    private final int ROOM_WIDTH = 640;
    
    private int parentID;
    private boolean hasMonster; //has a monster
    private boolean isExit; //is an exit room
    private int doors; //number of doors
    private int depth;
    
    private HashMap<Integer, Room> roomsTree = new HashMap<>();
    private HashMap<Integer, NodeLayer> doorsNodes = new HashMap<>();
    private HashMap<Integer, Boolean> activeDoors = new HashMap<>();
    private GameMap roomMap;
    
    private NodeLayer doorWherePlayerEnterRoom;
    private NodeLayer doorWherePlayerLeftTheRoom;

    /**
     * instructor for the class Room
     * @param parentID the id of the previous room
     * @param hasMonster if the room has a monster
     * @param isExit if the room is the exit room
     * @param doors the number of doors the room has
     * @param depth how far away from the starting room this room is
     */
    public Room(int parentID, boolean hasMonster, boolean isExit, int doors, int depth) {
        if (doors > 4 || doors < 1) {
            throw new IllegalArgumentException("doors have to be between 1-4 inclusive");
        }
        
        this.parentID = parentID;
        this.hasMonster = hasMonster;
        this.isExit = isExit;
        this.doors = doors;
        this.depth = depth;
        this.setDoors(2);
        this.setDoorWherePlayerEnterRoom();
    }
    
    public void setDoorWherePlayerEnterRoom() {
        for (int key : this.activeDoors.keySet()) {
            if (this.activeDoors.get(key)) {
                this.doorWherePlayerEnterRoom = doorsNodes.get(key);
                break;
            }
        }
    }
    
    public NodeLayer getStartingDoor() {
        return doorWherePlayerEnterRoom;
    }
    
    public void setDoors(int numberOfDoors) {
        ArrayList<NodeLayer> doors = Game.getCurrentGameMap().getDoorsLayer();
        ArrayList<NodeLayer> activeDoorsNode = new ArrayList<>();
        
        for (int i = 0; i < doors.size(); i++) {
            doorsNodes.put(i, doors.get(i));

            if (numberOfDoors > (i + 1)) {
                activeDoors.put(i, false);
                continue;
            }
            
            activeDoors.put(i, true);
            activeDoorsNode.add(doors.get(i));
        }
        
        roomMap = new GameMap(Game.getCurrentGameMap().getRoomLayer(), activeDoorsNode);
    }

    public GameMap getRoomMap() {
        return roomMap;
    }

    public ArrayList<NodeLayer> getInactiveDoors() {
        ArrayList<NodeLayer> doorsInactive = new ArrayList<>();
        for (int key : this.doorsNodes.keySet()) {
            if (!this.activeDoors.get(key)) {
                doorsInactive.add(this.doorsNodes.get(key));
            }
        }
        
        return doorsInactive;
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

//    /**
//     * set the id of the specified door to the specified id
//     * @param index index of the door
//     * @param id  door/room id
//     */
//    public void setDoorID(int index, int id) {
//        doorID.put(index, id);
//    }
//    /**
//     * getter for the id's of the specified door
//     * @param index the index of the door
//     * @return the id of the door
//     */
//    public int getDoorID(int index) {
//        return doorID.get(index);
//    }

    /**
     * getter for the depth of the room
     * @return depth of the room
     */
    public int getDepth() {
        return depth;
    }
    
}
