package app.dungeoncrawler.utils;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    public NodeLayer roomLayer;
    public ArrayList<NodeLayer> doorsLayers = new ArrayList<>();

    public static Map<MapName, GameMap> availableMaps = new HashMap() {

    };
    
    private GameMap() {};
    public GameMap(NodeLayer roomLayer, ArrayList<NodeLayer> doorsLayers) {
        this.roomLayer = roomLayer;
        this.doorsLayers = doorsLayers;
    }
    
    public static void generateAllGameMaps(int screenHeight, int screenWidth) {
        GameMap.generateMap1(screenHeight, screenWidth);
    }
    
    public void setRoomGraphics(GraphicsContext c) {
        this.roomLayer.setGraphicsContext(c);
    }
    
    public void setDoorsGraphics(GraphicsContext c) {
        for (int i = 0; i < this.doorsLayers.size(); i++) {
            NodeLayer door = this.doorsLayers.get(i);
            door.setGraphicsContext(c);
        }
    }
    
    private static void generateMap1(int screenHeight, int screenWidth) {
        NodeLayer roomLayer;
        ArrayList<NodeLayer> doorsLayer = new ArrayList<>();

        Dimension roomDimensions = new Dimension(135, 405, 35, 325);
        roomLayer = new NodeLayer(
                0, "/app/assets/4door.png", screenWidth, screenHeight, roomDimensions);
        
        Dimension doorDimesion1 = new DoorDimension(
                135, -5, 210, 140, 135, 0); // put in term of porcetange
        Dimension doorDimesion2 = new DoorDimension(
                250, 295, 35,-45, 0, 35);// put in term of porcetange
        Dimension doorDimesion3 = new DoorDimension(
                405, 565, 85, 160, 405, 0);// put in term of porcetange
        Dimension doorDimesion4 = new DoorDimension(
                200, 240, 370, 325, 0, 325);// put in term of porcetange
        
        NodeLayer door1 = new NodeLayer(
                0, "/app/assets/construction.png", 80, 100, doorDimesion1);
        NodeLayer door2 = new NodeLayer(
                1, "/app/assets/construction.png", 80, 100, doorDimesion2);
        NodeLayer door3 = new NodeLayer(
                2, "/app/assets/construction.png", 80, 100, doorDimesion3);
        NodeLayer door4 = new NodeLayer(
                3, "/app/assets/construction.png", 80, 100, doorDimesion4);
        
        door1.setPosition(doorDimesion1.averageX(), doorDimesion1.averageY());
        door2.setPosition(doorDimesion2.averageX(), doorDimesion2.averageY());
        door3.setPosition(doorDimesion3.averageX(), doorDimesion3.averageY());
        door4.setPosition(doorDimesion4.averageX(), doorDimesion4.averageY());
        
        doorsLayer.add(door1);
        doorsLayer.add(door2);
        doorsLayer.add(door3);
        doorsLayer.add(door4);
        
        GameMap.availableMaps.put(MapName.MAP_1, new GameMap(roomLayer, doorsLayer));
    }

    public static Map<MapName, GameMap> getAvailableMaps() {
        return availableMaps;
    }

    public ArrayList<NodeLayer> getDoorsLayer() {
        return doorsLayers;
    }

    public NodeLayer getRoomLayer() {
        return roomLayer;
    }
    
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
}
