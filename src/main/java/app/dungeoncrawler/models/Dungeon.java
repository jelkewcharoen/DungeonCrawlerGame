package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Difficulties;
import app.dungeoncrawler.utils.ObserverObject;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dungeon {
    private static int currentID = 0; //current id of the room
    private static HashMap<Integer, Room> rooms = new HashMap<>();
    private Room activeRoom;
    private SimpleObjectProperty<ObserverObject<Room>> activeRoomOb = new SimpleObjectProperty<>();

    private ArrayList<Room> history = new ArrayList<>();
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
     * constructs dungeon with easy version
     */
    public Dungeon() {
        this("EASY");
    }

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
    }
    
    public void createRoom() {
        this.activeRoom = new Room(
            null,
            false,
            4,
            0
        );

        this.activeRoomOb.set(new ObserverObject<>(this.activeRoom));
        history.add(this.activeRoom);
    }

    /**
     * getter for difficulties.
     * @return a Difficulties Enum.
     */
    public Difficulties getDifficulty() {
        return difficulty;
    }

    public Room getActiveRoomOb() {
        return activeRoomOb.get().getField();
    }


    public SimpleObjectProperty<ObserverObject<Room>> activeRoomObProperty() {
        return activeRoomOb;
    }
    
    public ArrayList<Room> getHistory() {
        return history;
    }
    
    /**
     * sets active room
     * @param r room which we want to activate
     */
    public void setActiveRoom(Room r) {
        this.activeRoom = r;
        ObserverObject<Room> oldRoom = new ObserverObject<Room>(r);
        oldRoom.setField(r);
        this.activeRoomOb.set(oldRoom);
        history.add(r);
    }

    /**
     * gets active room
     * @return returns active room
     */
    public Room getActiveRoom() {
        return activeRoom;
    }
    
    public boolean isPositionValid(int x, int y) {
        return this.getActiveRoomOb().isCordinateInRoom(x, y);
    }
    
    /**
     * sets active player
     * @param activePlayer who we want to make active
     */
    public void setActivePlayer(Player activePlayer) {
        activePlayer.setPosition(
                this.getActiveRoomOb().getRoomMap().getRoomLayer().getDimension().averageX(),
                this.getActiveRoomOb().getRoomMap().getRoomLayer().getDimension().averageY()
        );

        this.activePlayer = activePlayer;
    }
}
