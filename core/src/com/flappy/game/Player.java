package com.flappy.game;

public class Player implements Comparable<Player> {
    private String name;
    private int score;

    public Player(int score, String name) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(score, o.score);
    }

    @Override
    public String toString() {
        return score + ": " + name;
    }
}
