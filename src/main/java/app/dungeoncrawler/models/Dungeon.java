package app.dungeoncrawler.models;

import app.dungeoncrawler.utils.Difficulties;

import java.util.HashMap;
import java.util.Map;

public class Dungeon {
 
    private Difficulties difficulty;
    public static final Map<Difficulties, Integer> difficulties = new HashMap<>(){
        {
            put(Difficulties.EASY, 1);
            put(Difficulties.MEDIUM, 2);
            put(Difficulties.HARD, 3);
        }
    };

    public Dungeon (String d) {
        Map<String, Difficulties> mapStringToEnum = new HashMap<>(){
            {
                put(Difficulties.EASY.toString(), Difficulties.EASY);
                put(Difficulties.MEDIUM.toString(), Difficulties.MEDIUM);
                put(Difficulties.HARD.toString(), Difficulties.HARD);
            }
        };
        
        this.difficulty = mapStringToEnum.get(d) != null 
                ? mapStringToEnum.get(d) 
                : Difficulties.EASY;
    }   
    
    public Dungeon (Difficulties d) {
        this.difficulty = d;
    }
    
    public Difficulties getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulties difficulty) {
        this.difficulty = difficulty;
    }
}
