package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.NodeLayer;
import javafx.collections.MapChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Dungeon {
    public static int currentID = 0; //current id of the room
    public static HashMap<Integer, Room> rooms = new HashMap<>();
    private Room activeRoom;
    private Player activePlayer;
    
    public static final Map<Difficulties, Integer> DIFFICULTIES = new HashMap<>() {
        {
            put(Difficulties.EASY, 1);
            put(Difficulties.MEDIUM, 2);
            put(Difficulties.HARD, 3);
        }
    };
    private Difficulties difficulty;

    /**
     * Creates a Dungeon with a difficulty.
     *
     * @param stringDifficulty a string containing (EASY, MEDIUM, HARD).
     */
    public Dungeon(String stringDifficulty) {
        Map<String, Difficulties> mapStringToEnum = new HashMap<>() {{
                put(Difficulties.EASY.toString(), Difficulties.EASY);
                put(Difficulties.MEDIUM.toString(), Difficulties.MEDIUM);
                put(Difficulties.HARD.toString(), Difficulties.HARD);
            }
        };

        this.difficulty = mapStringToEnum.get(stringDifficulty) != null
                ? mapStringToEnum.get(stringDifficulty)
                : Difficulties.EASY;

        this.activeRoom = new Room(
                0,
                false,
                false,
                4,
                0);
        rooms.put(0, this.activeRoom);

    }

    /**
     * getter for difficulties.
     * @return a Difficulties Enum.
     */
    public Difficulties getDifficulty() {
        return difficulty;
    }

    /**
     * method for entering the room of id = id
     * @param id id of the room
     * @return Room object
     */
    public Room enterRoom(int id) {
        return rooms.get(id);
    }
    
    public Room getActiveRoom() {
        return activeRoom;
    }
    
    public Room getInitialRoom() {
        return rooms.get(0);
    }

    public void setActivePlayer(Player activePlayer) {
        Room initialRoom = this.getInitialRoom();
        activePlayer.setPosition(
                initialRoom.getStartingDoor().getDimension().averageX(),
                initialRoom.getStartingDoor().getDimension().averageY()
        );

        this.activePlayer = activePlayer;
//        this.activePlayer.getPlayerLocationProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(newValue);
//        });
    }
}
