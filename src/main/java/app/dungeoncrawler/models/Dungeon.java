package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Difficulties;

import java.util.HashMap;
import java.util.Map;

public class Dungeon {
    private static int currentID = 0; //current id of the room
    private static HashMap<Integer, Room> rooms = new HashMap<>();
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

        this.activeRoom = new Room(
                null,
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
     * sets active room
     * @param r room which we want to activate
     */
    public void setActiveRoom(Room r) {
        this.activeRoom = r;
    }

    /**
     * gets active room
     * @return returns active room
     */
    public Room getActiveRoom() {
        return activeRoom;
    }

    /**
     * gets initial room
     * @return returns initial room
     */
    public Room getInitialRoom() {
        return rooms.get(0);
    }

    /**
     * sets active player
     * @param activePlayer who we want to make active
     */
    public void setActivePlayer(Player activePlayer) {
        Room initialRoom = this.getInitialRoom();
        activePlayer.setPosition(
                initialRoom.getRoomMap().getRoomLayer().getDimension().averageX(),
                initialRoom.getRoomMap().getRoomLayer().getDimension().averageY()
        );

        this.activePlayer = activePlayer;
    }
}
