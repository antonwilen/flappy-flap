package com.flappy.game.player;

public class Player implements Comparable<Player> {
    private String name;
    private int score;

    public Player() {
        this.name = "player";
        this.score = 0;
    }

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public  Player(int score, String name) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) { this.score = score; }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(score, o.score);
    }

    @Override
    public String toString() {
        return score + ": " + name;
    }
}
