package com.flappy.game;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Highscore {
    private List<String> highscoreList;
    private Map<Integer, List<Player>> difficultyMap = new HashMap<>();

    public Highscore() {
        try {
            highscoreList = Files.readAllLines(Path.of("highscore/highscore"));
            generateDifficultyMap();

        } catch (IOException ex) {
            System.err.println("Something went wrong with reading the highscore file");
        }
    }

    private void generateDifficultyMap() {

        for (int i = 0; i < 3; i++) {
            difficultyMap.put(i + 1, generateListOfPlayers(i + 1));
        }
    }

    private List<Player> generateListOfPlayers(int difficulty) {
        String[] highscore = highscoreList.get(difficulty - 1).split(",");
        List<Player> players = new ArrayList<>();

        for (int j = 0; j < 10; j += 2) {
            players.add(new Player(Integer.parseInt(highscore[j]), highscore[j + 1]));
        }

        return players;
    }

    public List<Player> getHighscore(int difficulty) {
        return difficultyMap.get(difficulty);
    }

    public boolean checkIfNewHighscore(int difficulty, Player player) {
        List<Player> players = difficultyMap.get(difficulty);

        for (Player p : players) {
            if (player.getScore() > p.getScore()) {
                return true;
            }
        }

        return false;
    }

    public void saveHighscore(int difficulty, Player player) {
        try {
            StringBuilder outputFile = new StringBuilder();

            List<Player> players = generateListOfPlayers(difficulty);
            players.add(player);

            Collections.sort(players, Collections.reverseOrder());

            for (int i = 0; i < 3; i++) {
                if (i == difficulty - 1) {
                    for (int j = 0; j < 5; j++) {
                        outputFile.append(players.get(j).getScore() + "," + players.get(j).getName() + ",");
                    }
                    outputFile.append("\n");
                } else {
                    outputFile.append(highscoreList.get(i));
                    outputFile.append("\n");
                }
            }

            Files.writeString(Path.of("highscore/highscore"), outputFile);

        } catch (IOException ex) {
            System.err.println("Something went wrong with writing the highscore file");
        }
    }

}
