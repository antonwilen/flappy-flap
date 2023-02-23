package com.flappy.game;

public class Difficulty {
    private String difficulty;

    public Difficulty(){
        difficulty = "Medium";
    }
    public String getDifficulty(){
        return difficulty;
    }
    public void next(){
        switch (difficulty){
            case "Easy":
                difficulty = "Medium";
                break;
            case "Medium":
                difficulty = "Hard";
                break;
            case "Hard":
                difficulty = "Easy";
                break;
        }
    }
}
