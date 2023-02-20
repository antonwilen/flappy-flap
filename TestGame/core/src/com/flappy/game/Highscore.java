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
//            List highscoreFile = Files.readAllLines(Path.of("high.score"));
            Path path = Path.of("assets/highscore.txt");
            String infile = Files.readString(path);

            String[] highscore = infile.split(",");

            for(int i = 0; i < highscore.length; i++) {
                sb.append(highscore[i] + ": " + highscore[i+1] + "\n");
                i++;
            }

//            for(int i = 0; i < highscoreFile.size(); i++) {
//                String line = highscoreFile.get(i).toString();
//                String[] result = line.split(",");
//
//                highscore.put(Integer.getInteger(result[0]), result[1]);
//
//                for(Integer key : highscore.keySet()) {
//                    sb.append(key + " : " + highscore.get(key));
//                    sb.append("\n");
//                }
//            }

        } catch (IOException ex) {
            System.err.println("Something went wrong");
            ex.printStackTrace();
        }

        return sb.toString();
    }
}
