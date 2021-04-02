package app.dungeoncrawler.utils;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private NodeLayer roomLayer;
    private ArrayList<NodeLayer> doorsLayers = new ArrayList<>();
    private static Map<MapName, GameMap> availableMaps = new HashMap();

    public GameMap(NodeLayer roomLayer, ArrayList<NodeLayer> doorsLayers) {
        this.roomLayer = roomLayer;
        this.doorsLayers = doorsLayers;
    }

    /**
     * generates all game map
     * @param screenHeight screen height used to generate game map
     * @param screenWidth screen width used to generage game map
     */
    public static void generateAllGameMaps(int screenHeight, int screenWidth) {
        GameMap.generateMap1(screenHeight, screenWidth);
    }
    
    public void drawRoom(GraphicsContext c) {
        this.roomLayer.draw(c);
    }

    /**
     * generates a map with 4 doors
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the height of the width
     */
    private static void generateMap1(int screenHeight, int screenWidth) {
        NodeLayer roomLayer;
        ArrayList<NodeLayer> doorsLayer = new ArrayList<>();

        Dimension roomDimensions = new Dimension(135, 405, 35, 325);
        roomLayer = new NodeLayer(0, "/app/assets/4door.png",
                screenWidth, screenHeight, roomDimensions);
        
        Dimension doorDimension1 = new DoorDimension(135, -5, 210, 140,
                135, 0); // put in term of percentage
        Dimension doorDimension2 = new DoorDimension(250, 295, 35, -45,
                0, 35); // put in term of percentage
        Dimension doorDimension3 = new DoorDimension(405, 565, 85, 160,
                405, 0); // put in term of percentage
        Dimension doorDimension4 = new DoorDimension(200, 240, 370, 325,
                0, 325); // put in term of percentage
        
        int doorHeight = 200;
        int doorWidth = 392;
        NodeLayer door1 = new NodeLayer(0, "/app/assets/door1.png",
                doorWidth + 100, 430, doorDimension1);
        NodeLayer door2 = new NodeLayer(1, "/app/assets/door2.png",
                doorWidth, doorHeight, doorDimension2);
        NodeLayer door3 = new NodeLayer(2, "/app/assets/door3.png",
                doorWidth + 100, 255, doorDimension3);
        NodeLayer door4 = new NodeLayer(3, "/app/assets/door4.png",
                doorWidth + 3, doorHeight, doorDimension4);
        
        door1.setPosition(0, 3);
        door2.setPosition(123, 0);
        door3.setPosition(460, 27);
        door4.setPosition(123, 400);
        
        doorsLayer.add(door1);
        doorsLayer.add(door2);
        doorsLayer.add(door3);
        doorsLayer.add(door4);
        
        GameMap.availableMaps.put(MapName.MAP_1, new GameMap(roomLayer, doorsLayer));
    }

    /**
     * gets the available maps
     *
     * @return available maps
     */
    public static Map<MapName, GameMap> getAvailableMaps() {
        return availableMaps;
    }

    /**
     * gets the door layers
     *
     * @return door layers
     */
    public ArrayList<NodeLayer> getDoorsLayer() {
        return doorsLayers;
    }

    /**
     * gets the room layer
     *
     * @return room layer
     */
    public NodeLayer getRoomLayer() {
        return roomLayer;
    }

    /**
     * returns a boolean based on if the coordinates passed in are in the map
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return boolean on if the coordinate is in the map
     */
    public boolean isCoordinateInsideTheMap(int x, int y) {
        boolean coordinatesAreInside = false;
        ArrayList<NodeLayer> availableSpace = this.doorsLayers;
        availableSpace.add(roomLayer);

        for (int i = 0; i < availableSpace.size(); i++) {
            if (coordinatesAreInside) {
                break;
            }

            coordinatesAreInside = availableSpace.get(i).getDimension().isInsideCoordinates(x, y);
        }
        
        return coordinatesAreInside;
    }
    
    @Override
    public boolean equals(Object obj) {
        GameMap gameMap = (GameMap) obj;
        return this.roomLayer.equals(gameMap.getRoomLayer());
    }
}
