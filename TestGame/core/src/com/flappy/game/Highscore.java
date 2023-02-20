package com.flappy.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Highscore {
    private static Map<Integer, String> highscore = new HashMap<>();
    public static String getHighscore() {
        StringBuilder sb = new StringBuilder();

        try {
            List highscoreFile = Files.readAllLines(Path.of("high.score"));

            for(int i = 0; i < highscoreFile.size(); i++) {
                String line = highscoreFile.get(i).toString();
                String[] result = line.split(",");

                highscore.put(Integer.getInteger(result[0]), result[1]);

                for(Integer key : highscore.keySet()) {
                    sb.append(key + " : " + highscore.get(key));
                    sb.append("\n");
                }
            }

        } catch (IOException ex) {
            System.err.println("Something went wrong");
        }

        return sb.toString();
    }
}
