package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.DoorDimension;
import app.dungeoncrawler.utils.GameMap;
import app.dungeoncrawler.utils.NodeLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Room {
    private static final int ROOM_HEIGHT = 480;
    private static final int ROOM_WIDTH = 640;
    
    private Room parent;
    private boolean hasMonster; //has a monster
    private boolean isExit; //is an exit room
    private int doors; //number of doors
    private int depth;
    
    private HashMap<Integer, Room> roomsTree = new HashMap<>();
    private HashMap<Integer, NodeLayer> doorsNodes = new HashMap<>();
    private HashMap<Integer, Boolean> activeDoors = new HashMap<>();
    private GameMap roomMap;
    
    private int doorIdWherePlayerEnterRoom;
    private int doorIdWherePlayerLeftTheRoom;

    /**
     * instructor for the class Room
     * @param parent the id of the previous room
     * @param hasMonster if the room has a monster
     * @param isExit if the room is the exit room
     * @param doors the number of doors the room has
     * @param depth how far away from the starting room this room is
     */
    public Room(Room parent, boolean hasMonster, boolean isExit, int doors, int depth) {
        if (doors > 4 || doors < 1) {
            throw new IllegalArgumentException("doors have to be between 1-4 inclusive");
        }
        
        this.parent = parent;
        this.hasMonster = hasMonster;
        this.isExit = isExit;
        this.doors = doors;
        this.depth = depth;
        this.setDoorWherePlayerEnterRoom();
        this.createRoomMap(this.doors);

        if (this.parent != null) {
            this.drawRoom(true);
        }
    }

    /**
     * gets room map
     * @return room map
     */
    public GameMap getRoomMap() {
        return roomMap;
    }

    /**
     * gets door id where player left the room
     * @return gets door id where player left the room
     */
    public int getDoorIdWherePlayerLeftTheRoom() {
        return doorIdWherePlayerLeftTheRoom;
    }

    /**
     * gets starting door
     * @return starting door
     */
    public NodeLayer getStartingDoor() {
        return this.doorsNodes.get(this.doorIdWherePlayerEnterRoom);
    }

    /**
     * gets inactive doors
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
     * gets starting door index
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
            System.out.println(parentDoor);
            if (parentDoor > 1) {
                this.doorIdWherePlayerEnterRoom = parentDoor - 2;
                return;
            }
            
            this.doorIdWherePlayerEnterRoom = parentDoor + 2;
        } else {
            this.doorIdWherePlayerEnterRoom = 0;
        }
    }

    /**
     * creates room map
     * @param numberOfDoors number of doors needed to create the room map
     */
    public void createRoomMap(int numberOfDoors) {
        ArrayList<NodeLayer> doors = new ArrayList<>(Game.getCurrentGameMap().getDoorsLayer());
        ArrayList<NodeLayer> activeDoorsNode = new ArrayList<>();

        NodeLayer initDoor = doors.remove(this.doorIdWherePlayerEnterRoom);
        activeDoors.put(initDoor.getId(), true);
        doorsNodes.put(initDoor.getId(), initDoor);
        activeDoorsNode.add(initDoor);
        
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
        
        this.roomMap = new GameMap(Game.getCurrentGameMap().getRoomLayer(), activeDoorsNode);
    }

    /**
     * draws room
     * @param enteringRoom entering room where player enters to enter room
     */
    public void drawRoom(boolean enteringRoom) {
        this.getRoomMap().getRoomLayer().draw();
        Game.getDungeon().setActiveRoom(this);

        ArrayList<NodeLayer> inactiveDoors = this.getInactiveDoors();
        for (int i = 0; i < inactiveDoors.size(); i++) {
            NodeLayer layer = inactiveDoors.get(i);

            layer.setPosition(layer.getDimension().averageX(), layer.getDimension().averageY());
            layer.draw();
        }

        int playerLocationDoorId = enteringRoom ?
                this.doorIdWherePlayerEnterRoom : this.doorIdWherePlayerLeftTheRoom;
        NodeLayer initialDoor = this.doorsNodes.get(playerLocationDoorId);
        DoorDimension initialDoorDimension = (DoorDimension) initialDoor.getDimension();
        
        Game.getPlayer().movePlayer(initialDoorDimension.getPositionXForPlayer(),
                initialDoorDimension.getPositionYForPlayer());
    }

    /**
     * tracks player movement
     * @param x where x represents x position of player
     * @param y where y represents y position of player
     */
    public void trackPlayerMovement(int x, int y) {
        for (int i = 0; i < this.doorsNodes.size(); i++) {
            NodeLayer doorNode = this.doorsNodes.get(i);
            boolean isPlayerInDoorWhereHeEntered = doorNode.getId() ==
                    this.doorIdWherePlayerEnterRoom;
            boolean isPlayerInsideDoorDimension = this.activeDoors.get(doorNode.getId())
                    && doorNode.getDimension().isInsideCoordinates(x, y);

            if (isPlayerInsideDoorDimension && isPlayerInDoorWhereHeEntered
                    && this.parent != null) {
                Game.getDungeon().setActiveRoom(this.parent);
                this.removeDoorsOfCanvas();
                this.parent.drawRoom(false);
                
            } else if (isPlayerInsideDoorDimension && !isPlayerInDoorWhereHeEntered) {
                if (roomsTree.get(doorNode.getId()) != null) {
                    Room alreadyCreatedRoom = roomsTree.get(doorNode.getId());
                    this.removeDoorsOfCanvas();
                    alreadyCreatedRoom.drawRoom(true);
                    return;
                }
                
                this.doorIdWherePlayerLeftTheRoom = doorNode.getId();
                this.createRandomRoom(doorNode.getId());
            }
        }
    }

    /**
     * removes doors of canvas
     */
    public void removeDoorsOfCanvas() {
        for (int i = 0; i < this.doorsNodes.size(); i++) {
            this.doorsNodes.get(i).clear();
        }
    }

    /**
     * creates random room
     * @param doorId door id which will be assigned to new room
     * @return random room created
     */
    public Room createRandomRoom(int doorId) {
        ArrayList<NodeLayer> doors = Game.getCurrentGameMap().getDoorsLayer();

        int randomNumberOfDoors = (int) (Math.random() * (3 - 1 + 1) + 1);
        
        this.removeDoorsOfCanvas();
        System.out.println("REMOVE FROM CANVAS");
        Room randomRoom = new Room(
                this,
                false,
                false,
                randomNumberOfDoors,
                this.depth + 1
        );
        
        this.roomsTree.put(doorId, randomRoom);
        return randomRoom;
    }
}
