package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Difficulties;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Dungeon {
    public static int currentID = 0; //current id of the room
    public static HashMap<Integer, Room> rooms = new HashMap<>();
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
        rooms.put(0, new Room(0,false, false, 4, 0)); //starting room

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
    /**
     * method for creating a new room
     * @param doorIndex the index of the door
     * @return the new room
     */
    public Room createNewRoom(int doorIndex) {
        Random random = new Random();
        int newID = rooms.size();
        int currentDepth = rooms.get(currentID).getDepth();
        boolean isExit = false;
        if (currentDepth >= 6) {
            isExit = random.nextBoolean();
        }
        Room newRoom = new Room(currentID, random.nextBoolean(), isExit,random.nextInt(4 ), currentDepth + 1);
        newRoom.setImage(""); //add image here
        newRoom.setDoorID(doorIndex, newID);
        rooms.put(newID, newRoom);
        currentID = newID;
        return rooms.get(currentID);
    }
}
