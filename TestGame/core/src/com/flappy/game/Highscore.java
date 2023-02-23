package com.flappy.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Highscore {
    public static String getHighscore(int currentScore, int difficulty) {
        StringBuilder sbResult = new StringBuilder();
        StringBuilder sbOutputFile = new StringBuilder();
        List<String> output = new ArrayList<>();

        try {
            Path path = null;
            boolean inserted = false;
            switch (difficulty) {
                case 1:
                    path = Path.of("assets/highscore/1highscore.txt");
                    break;
                case 2:
                    path = Path.of("assets/highscore/2highscore.txt");
                    break;
                case 3:
                    path = Path.of("assets/highscore/3highscore.txt");
                    break;
            }
            String infile = Files.readString(path);

            String[] allScores = infile.split(",");

            for(int i = 0; i < allScores.length; i += 2) {
                if(currentScore > Integer.parseInt(allScores[i]) && !inserted) {
                    sbResult.append(currentScore).append(": ").append("TESTNAME");
                    output.add(Integer.toString(currentScore));
                    output.add("TESTNAME");
                    inserted = true;
                    i -= 2;
                }
                else {
                    sbResult.append(allScores[i]).append(": ").append(allScores[i + 1]);
                    output.add(allScores[i]);
                    output.add(allScores[i+1]);
                }
                sbResult.append("\n");
            }

            for(int i = 0; i < 10; i++) {
                sbOutputFile.append(output.get(i)).append(",");
            }

            Files.writeString(path, sbOutputFile);

        } catch (IOException ex) {
            System.err.println("Something went wrong");
            ex.printStackTrace();
        }

        return sbResult.toString();
    }
}