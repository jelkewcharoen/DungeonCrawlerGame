package app.dungeoncrawler.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    public NodeLayer roomLayer;
    public ArrayList<NodeLayer> doorsLayers = new ArrayList<>();

    public static Map<MapName, GameMap> availableMaps = new HashMap(){};
    
    private GameMap() {};
    public GameMap(NodeLayer roomLayer, ArrayList<NodeLayer> doorsLayers) {
        this.roomLayer = roomLayer;
        this.doorsLayers = doorsLayers;
    }
    
    public static void generateAllGameMaps(int screenHeight, int screenWidth) {
        GameMap.generateMap1(screenHeight, screenWidth);
    }
    
    private static void generateMap1(int screenHeight, int screenWidth) {
        NodeLayer roomLayer;
        ArrayList<NodeLayer> doorsLayer = new ArrayList<>();

        Dimension roomDimensions = new Dimension(135, 405,35,325);
        roomLayer = new NodeLayer("/app/assets/4door.png", screenWidth, screenHeight, roomDimensions);
        
        Dimension door1 = new Dimension(135, -5,210,140); // put in term of porcetange
        Dimension door2 = new Dimension(250, 295,35,-45);// put in term of porcetange
        Dimension door3 = new Dimension(405, 565,85,160);// put in term of porcetange
        Dimension door4 = new Dimension(200, 240,370,325);// put in term of porcetange
        doorsLayer.add(new NodeLayer("/app/assets/construction.png", 100, 100, door1));
        doorsLayer.add(new NodeLayer("/app/assets/construction.png", 100, 100, door2));
        doorsLayer.add(new NodeLayer("/app/assets/construction.png", 100, 100, door3));
        doorsLayer.add(new NodeLayer("/app/assets/construction.png", 100, 100, door4));
        
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

            coordinatesAreInside = availableSpace.get(i).getDimension().isInsideCoordinates(x,y);
        }
        
        return coordinatesAreInside;
    }
}
