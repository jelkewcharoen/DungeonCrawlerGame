package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Dimension;
import app.dungeoncrawler.utils.DoorDimension;
import app.dungeoncrawler.utils.GameMap;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Room {
    private static final int ROOM_HEIGHT = 480;
    private static final int ROOM_WIDTH = 640;
    
    private Room parent;
    private boolean hasMonster; //has a monster
    private boolean isExit; //is an exit room
    private int doors; //number of doors
    private int depth;
    private boolean playerExitsExitRoom = false;
    
    private HashMap<Integer, Room> roomsTree = new HashMap<>();
    private HashMap<Integer, NodeLayer> doorsNodes = new HashMap<>();
    private HashMap<Integer, Boolean> activeDoors = new HashMap<>();
    private GameMap roomMap;
    private int randMonster = (int)(Math.random() * 3);
    private Monster monster = Game.getCurrentMonster();
    
    private int doorIdWherePlayerEnterRoom = -1;
    private int doorIdWherePlayerLeftTheRoom = -1;

    int xmon;
    int ymon;

    /**
     * construcs room
     * @param parent needed to create the room
     * @param doors number of doors will be created in the room
     * @param depth depth of the room
     */
    public Room(Room parent, int doors, int depth) {
        this(parent, false, false, doors, depth);
    }

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

        System.out.println(this.activeDoors);
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
     * gets door id where player entered the room
     * @return gets door id where player entered the room
     */
    public int getDoorIdWherePlayerEnterTheRoom() {
        return doorIdWherePlayerEnterRoom;
    }
    /**
     * gets door nodes of the active room
     * @return gets door nodes of the active room
     */
    public HashMap<Integer, NodeLayer> getDoorsNodes() {
        return doorsNodes;
    }
    /**
     * gets the list of active door nodes of the active room
     * @return gets active door nodes of the active room
     */
    public HashMap<Integer, Boolean> getActiveDoorsNodes() {
        return activeDoors;
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
     * returns room dimensions
     * @return room dimensions
     */
    public Dimension getRoomDimensions() {
        return this.roomMap.getRoomLayer().getDimension();
    }

    public Monster getMonster() { return this.monster; }

    /**
     * gets starting door index
     * @return starting door index
     */
    public int getStartingDoorIndex() {
        return doorIdWherePlayerEnterRoom;
    }

    /**
     * returns whether if the room is exit room
     * @return true if the room is exit room. false otherwise.
     */
    public boolean getIsExit() {
        return isExit;
    }

    /**
     * returns whether player exits exit room
     * @return true if player exits exit room. false otherwise.
     */
    public boolean getPlayerExitsExitRoom() {
        return playerExitsExitRoom;
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
     * @param numberOfDoors number of doors needed to create the room map
     */
    public void createRoomMap(int numberOfDoors) {
        ArrayList<NodeLayer> doors = new ArrayList<>(Game.getCurrentGameMap().getDoorsLayer());
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

        int playerLocationDoorId = enteringRoom
                ? this.doorIdWherePlayerEnterRoom : this.doorIdWherePlayerLeftTheRoom;
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
            boolean isPlayerInDoorWhereHeEntered = doorNode.getId()
                    == this.doorIdWherePlayerEnterRoom;
            boolean isPlayerInsideDoorDimension = this.activeDoors.get(doorNode.getId())
                    && doorNode.getDimension().isInsideCoordinates(x, y);
            
            if (isPlayerInsideDoorDimension && isPlayerInDoorWhereHeEntered
                    && this.parent != null) {
                Game.getDungeon().setActiveRoom(this.parent);
                this.removeDoorsOfCanvas();
                this.parent.drawRoom(false);
                
            }  else if (isPlayerInsideDoorDimension && !isPlayerInDoorWhereHeEntered) {
                this.doorIdWherePlayerLeftTheRoom = doorNode.getId();
                this.removeDoorsOfCanvas();

                if (roomsTree.get(doorNode.getId()) != null) {
                    Room alreadyCreatedRoom = roomsTree.get(doorNode.getId());
                    alreadyCreatedRoom.drawRoom(true);
                    return;
                }

                if (isExit) {
                    playerExitsExitRoom = true;
                } else {
                    this.doorIdWherePlayerLeftTheRoom = doorNode.getId();
                    this.createRandomRoom(doorNode.getId());
                }

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
     * gets random number of doors
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
     * creates random room
     * @param doorId door id that will be needed to create a random room
     */
    public void createRandomRoom(int doorId) {
        ArrayList<NodeLayer> doors = Game.getCurrentGameMap().getDoorsLayer();

        int randomNumberOfDoors = (int) (Math.random() * (3 - 1 + 1) + 1);

        boolean nextRoomExit = false;
        if (this.depth > 5) {
            Random nextExit = new Random();
            nextRoomExit = nextExit.nextBoolean();
        }
 
        this.removeDoorsOfCanvas();
        System.out.println("REMOVE FROM CANVAS");
        Room randomRoom = new Room(
                this,
                false,
                nextRoomExit,
                randomNumberOfDoors,
                this.depth + 1
        );

        Random rand = new Random();

         xmon = rand.nextInt(225) + 160;
         ymon = rand.nextInt(240) + 60;

        monster = Game.getNewMonster();
        monster.setPosition(xmon, ymon);
        monster.draw();

        AnimationTimer animate = new AnimateMonster();
        animate.start();

        System.out.println(isExit);
        this.roomsTree.put(doorId, randomRoom);
    }

    @Override
    public boolean equals(Object obj) {
        Room room = (Room) obj;
        
        return this.doorIdWherePlayerEnterRoom == room.doorIdWherePlayerEnterRoom
                && this.doorIdWherePlayerLeftTheRoom == room.doorIdWherePlayerLeftTheRoom
                && this.roomMap.equals(room.roomMap);
    }

    /**
     * returns parent
     * @return parent
     */
    public Room getParent() {
        return parent;
    }

    /**
     * Class that allows the Monster to move automatically.
     */
    private class AnimateMonster extends AnimationTimer {
        int framerate = 0;
        int count = 0;
        boolean now = true;
        Player player = Game.getPlayer();
        @Override
        public void handle(long l) {
            if (framerate % 10 == 0) {
                if (monster.collides(player.getX(), player.getY())) {
                    stop();
                    // fight





                }

                if (now) {
                    if (xmon > 350) {
                        now = false;
                        count++;
                        System.out.println("COUNT-----: " + count);
                    }
                    xmon += 15;
                    monster.setPosition(xmon, ymon);
                    monster.draw();
                } else {
                    if (xmon <250) {
                        now = true;
                    }
                    xmon -= 15;
                    monster.setPosition(xmon, ymon);
                    monster.draw();
                }
            }
            framerate++;



        }

    }

}
