package com.flappy.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Highscore {
    private int currentScore;
    private Path path;
    private String[] allScores;
    private String results;

    public Highscore(int currentScore, int difficulty) {
        try {
            String infile;
            this.currentScore = currentScore;

            switch (difficulty) {
                case 1:
                    path = Path.of("highscore/1highscore.txt");
                    break;
                case 2:
                    path = Path.of("highscore/2highscore.txt");
                    break;
                case 3:
                    path = Path.of("highscore/3highscore.txt");
                    break;
            }

            infile = Files.readString(path);
            allScores = infile.split(",");

            saveHighscore();

        } catch (IOException ex) {
            System.err.println("Something went wrong with receiving the highscore list");
        }
    }

    public String receiveHighscore() {
        return results;
    }

    public void saveHighscore() throws IOException {
        try {
            boolean inserted = false;
            StringBuilder highscores = new StringBuilder();
            StringBuilder result = new StringBuilder();
            List<String> resultList = new ArrayList<>();

            for (int i = 0; i < 10; i += 2) {
                if (currentScore > Integer.parseInt(allScores[i]) && !inserted) {
                    result.append(currentScore + ": " + "testnamn");
                    resultList.add(Integer.toString(currentScore));
                    resultList.add("testnamn");
                    inserted = true;
                    i -= 2;
                } else {
                    result.append(allScores[i] + ": " + allScores[i + 1]);
                    resultList.add(allScores[i]);
                    resultList.add(allScores[i + 1]);
                }
                result.append("\n");
            }

            for (int j = 0; j < 10; j++) {
                highscores.append(resultList.get(j));
                highscores.append(",");
            }

            results = result.toString();

            Files.writeString(path, highscores);
        } catch (IOException ex) {
            System.err.println("Something went wrong with saving the highscores");
        }
    }
}
