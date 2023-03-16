package com.flappy.game.util;

public class Difficulty {
    private String difficulty;

    public Difficulty(){
        difficulty = "Medium";
    }
    public String getDifficulty(){
        return difficulty;
    }
    public int getDifficultyNumber(){
        switch (difficulty){
            case "Easy":
                return 1;
            case "Medium":
                return 2;
            case "Hard":
                return 3;
        }
        return 0;
    }
    public void setDifficulty(int difficultyNumber){
        switch (difficultyNumber){
            case 1:
                difficulty = "Easy";
                break;
            case 2:
                difficulty = "Medium";
                break;
            case 3:
                difficulty = "Hard";
                break;
        }
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
