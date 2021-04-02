package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Dimension;
import app.dungeoncrawler.utils.DoorDimension;
import app.dungeoncrawler.utils.GameMap;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Room {
    private static final int ROOM_HEIGHT = 480;
    private static final int ROOM_WIDTH = 640;

    private final Room parent;
    private boolean hasMonster = false; //has a monster
    private final int doors; //number of doors
    private final int depth;
    private final HashMap<Integer, Room> roomsTree = new HashMap<>();
    private final HashMap<Integer, NodeLayer> doorsNodes = new HashMap<>();
    private final HashMap<Integer, Boolean> activeDoors = new HashMap<>();
    private GameMap roomMap;
    private boolean playerExitedRoom = false;
    private int doorIdWherePlayerEnterRoom = -1;
    private int doorIdWherePlayerLeftTheRoom = -1;
    /**
     * construcs room
     *
     * @param parent needed to create the room
     * @param doors  number of doors will be created in the room
     * @param depth  depth of the room
     */
    public Room(Room parent, int doors, int depth) {
        this(parent, false, doors, depth);
    }
    /**
     * instructor for the class Room
     *
     * @param parent     the id of the previous room
     * @param hasMonster if the room has a monster
     * @param doors      the number of doors the room has
     * @param depth      how far away from the starting room this room is
     */
    public Room(Room parent, boolean hasMonster, int doors, int depth) {
        if (doors > 4 || doors < 1) {
            throw new IllegalArgumentException("doors have to be between 1-4 inclusive");
        }
        if (parent != null) {
            this.hasMonster = true;
        }
        this.parent = parent;

        this.doors = doors;
        this.depth = depth;
        this.setDoorWherePlayerEnterRoom();
        this.createRoomMap(this.doors);
    }

    /**
     * getter for hasMonster
     *
     * @return hasMonster
     */
    public boolean isHasMonster() {
        return hasMonster;
    }

    /**
     * setter for hasMonster
     *
     * @param hasMonster hasMonster to set
     */
    public void setHasMonster(boolean hasMonster) {
        this.hasMonster = hasMonster;
    }

    /**
     * getter for playerExitedRoom
     *
     * @return playerExitedRoom
     */
    public boolean isPlayerExitedRoom() {
        return playerExitedRoom;
    }

    /**
     * setter for playerExitedRoom
     *
     * @param playerExitedRoom new value for playerExitedRoom
     */
    public void setPlayerExitedRoom(boolean playerExitedRoom) {
        this.playerExitedRoom = playerExitedRoom;
    }

    /**
     * getter for room depth
     *
     * @return the room's depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * gets room map
     *
     * @return room map
     */
    public GameMap getRoomMap() {
        return roomMap;
    }

    /**
     * gets door id where player left the room
     *
     * @return gets door id where player left the room
     */
    public int getDoorIdWherePlayerLeftTheRoom() {
        return doorIdWherePlayerLeftTheRoom;
    }

    /**
     * gets door id where player entered the room
     *
     * @return gets door id where player entered the room
     */
    public int getDoorIdWherePlayerEnterTheRoom() {
        return doorIdWherePlayerEnterRoom;
    }

    /**
     * gets door nodes of the active room
     *
     * @return gets door nodes of the active room
     */
    public HashMap<Integer, NodeLayer> getDoorsNodes() {
        return doorsNodes;
    }

    /**
     * gets the list of active door nodes of the active room
     *
     * @return gets active door nodes of the active room
     */
    public HashMap<Integer, Boolean> getActiveDoorsNodes() {
        return activeDoors;
    }

    /**
     * gets starting door
     *
     * @return starting door
     */
    public NodeLayer getStartingDoor() {
        return this.doorsNodes.get(this.doorIdWherePlayerEnterRoom);
    }

    /**
     * gets inactive doors
     *
     * @return inactive doors
     */
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
     * check if the input coordinates are within the room boundary
     *
     * @param x x value
     * @param y y value
     * @return whether coordinates inside the room
     */
    public boolean isCordinateInRoom(int x, int y) {
        return this.roomMap.isCoordinateInsideTheMap(x, y);
    }

    /**
     * returns room dimensions
     *
     * @return room dimensions
     */
    public Dimension getRoomDimensions() {
        return this.roomMap.getRoomLayer().getDimension();
    }

    /**
     * gets starting door index
     *
     * @return starting door index
     */
    public int getStartingDoorIndex() {
        return doorIdWherePlayerEnterRoom;
    }

    /**
     * sets door where player enters room
     */
    public void setDoorWherePlayerEnterRoom() {
        if (this.parent != null) {
            int parentDoor = this.parent.getDoorIdWherePlayerLeftTheRoom();

            if (parentDoor > 1) {
                this.doorIdWherePlayerEnterRoom = parentDoor - 2;
                return;
            }

            this.doorIdWherePlayerEnterRoom = parentDoor + 2;
        }
    }

    /**
     * creates room map
     *
     * @param numberOfDoors number of doors needed to create the room map
     */
    public void createRoomMap(int numberOfDoors) {
        ArrayList<NodeLayer> doors = new ArrayList<>(
                Game.gameSingleInstance().getCurrentGameMap().getDoorsLayer());
        ArrayList<NodeLayer> activeDoorsNode = new ArrayList<>();

        if (this.parent != null) {
            NodeLayer initDoor = doors.remove(this.doorIdWherePlayerEnterRoom);
            activeDoors.put(initDoor.getId(), true);
            doorsNodes.put(initDoor.getId(), initDoor);
            activeDoorsNode.add(initDoor);
        }

        Collections.shuffle(doors);
        for (int i = 0; i < doors.size(); i++) {
            NodeLayer door = doors.get(i);
            doorsNodes.put(door.getId(), door);
            if (numberOfDoors > i) {
                activeDoors.put(door.getId(), true);
                activeDoorsNode.add(door);
                continue;
            }
            activeDoors.put(door.getId(), false);
        }

        this.roomMap = new GameMap(
            Game.gameSingleInstance().
                getCurrentGameMapRoomLayer(), 
            activeDoorsNode
        );
    }

    /**
     * draws room
     * @param door graphic context
     */
    public void clearRoom(GraphicsContext door) {
        this.removeDoorsOfCanvas(door);
    }

    /**
     * draw the room
     * @param room graphic context for room
     * @param door graphic context for door
     */
    public void drawRoom(GraphicsContext room, GraphicsContext door) {
        this.roomMap.drawRoom(room);

        ArrayList<NodeLayer> inactiveDoors = this.getInactiveDoors();
        for (NodeLayer layer : inactiveDoors) {
            layer.setPosition(layer.getPositionAtX(),
                    layer.getPositionAtY());
            layer.draw(door);
        }
    }

    /**
     * get dimensions for the door
     *
     * @param playerLocationDoorId the id of the door
     * @return dimensions of the door
     */
    public DoorDimension getDoorDimension(int playerLocationDoorId) {
        NodeLayer initialDoor = this.doorsNodes.get(playerLocationDoorId);
        return (DoorDimension) initialDoor.getDimension();
    }

    /**
     * tracks player movement
     *
     * @param x where x represents x position of player
     * @param y where y represents y position of player
     */
    public void trackPlayerMovement(int x, int y) {
        for (int i = 0; i < this.doorsNodes.size(); i++) {
            NodeLayer doorNode = this.doorsNodes.get(i);

            boolean isPlayerInDoorWhereHeEntered = doorNode.getId()
                    == this.doorIdWherePlayerEnterRoom;

            boolean isPlayerInsideDoorDimension = this.activeDoors.get(doorNode.getId())
                    && doorNode.getDimension().isInsideCoordinates(x, y);

            if (isPlayerInsideDoorDimension
                    && isPlayerInDoorWhereHeEntered
                    && this.parent != null
            ) {
                // go back to previous room
                Game.gameSingleInstance().setActiveRoom(this.parent);

            } else if (isPlayerInsideDoorDimension && !isPlayerInDoorWhereHeEntered
                    && Game.gameSingleInstance().getCurrentMonster() == null) {
                // player going to a new door
                this.doorIdWherePlayerLeftTheRoom = doorNode.getId();

                if (roomsTree.get(doorNode.getId()) != null) {
                    // if he already visited that door
                    Room alreadyCreatedRoom = roomsTree.get(doorNode.getId());
                    Game.gameSingleInstance().setActiveRoom(alreadyCreatedRoom);
                    return;
                }

                if (this.isPlayerExitsExitRoom()) {
                    this.setPlayerExitedRoom(true);
                    return;
                }

                Room randomRoom = this.createRandomRoom(doorNode.getId());
                Game.gameSingleInstance().setActiveRoom(randomRoom);
            }
        }
    }

    /**
     * removes doors of canvas
     *
     * @param doors graphic context to remove
     */
    public void removeDoorsOfCanvas(GraphicsContext doors) {
        for (int i = 0; i < this.doorsNodes.size(); i++) {
            this.doorsNodes.get(i).clear(doors);
        }
    }

    /**
     * gets random number of doors
     *
     * @return random number of doors
     */
    public int getRandomNumberOfDoors() {
        ArrayList<Integer> doorsPossibilities = new ArrayList<>();
        // 3 - 3/10 will have 3 doors
        // 2 - 5/10 will have 2 doors
        // 1 - 2/10 will have 1 doors
        for (int i = 1; i < 11; i++) {
            if (i <= 3) {
                doorsPossibilities.add(3);
            } else if (i >= 8) {
                doorsPossibilities.add(1);
            } else {
                doorsPossibilities.add(1);
            }
        }

        Collections.shuffle(doorsPossibilities);
        return doorsPossibilities.get(0);
    }

    /**
     * getter for playerExitedRoom
     *
     * @return playerExitedRoom
     */
    public boolean isPlayerExitsExitRoom() {
        boolean nextRoomExit = false;
        if (this.depth > 5) {
            Random nextExit = new Random();
            nextRoomExit = nextExit.nextBoolean();
        }

        return nextRoomExit;
    }

    /**
     * creates random room
     *
     * @param doorId door id that will be needed to create a random room
     * @return Room that is created
     */
    public Room createRandomRoom(int doorId) {
        ArrayList<NodeLayer> doors = Game.gameSingleInstance().getCurrentGameMapDoorsLayer();

        int randomNumberOfDoors = (int) (Math.random() * (3 - 1 + 1) + 1);

        Room randomRoom = new Room(
                this,
                false,
                randomNumberOfDoors,
                this.depth + 1
        );

        this.roomsTree.put(doorId, randomRoom);
        return randomRoom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Room room = (Room) obj;
        return this.doorIdWherePlayerEnterRoom == room.doorIdWherePlayerEnterRoom
                && this.doorIdWherePlayerLeftTheRoom == room.doorIdWherePlayerLeftTheRoom
                && this.roomMap.equals(room.roomMap);
    }

    /**
     * returns parent
     *
     * @return parent
     */
    public Room getParent() {
        return parent;
    }

}
