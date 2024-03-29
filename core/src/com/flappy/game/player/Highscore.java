package com.flappy.game.player;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Highscore {
    private List<String> highscoreList;
    private final Map<Integer, List<Player>> difficultyMap = new HashMap<>();

    public Highscore() {
        try {
            Path path = Path.of(Gdx.files.internal("highscore/highscore").path());

            checkIfHighscoreFileExists(path);
            highscoreList = Files.readAllLines(path);
            generateDifficultyMap();

        } catch (IOException ex) {
            System.err.println("Something went wrong with reading the highscore file");
        }
    }

    private void checkIfHighscoreFileExists(Path path) throws IOException {
        String scores = "0,----,";

        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        if (!Files.exists(path)) {
            StringBuilder newScores = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                newScores.append(scores.repeat(10));
                newScores.append("\n");
            }
            Files.writeString(path, newScores);
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

        for (int j = 0; j < 20; j += 2) {
            players.add(new Player(Integer.parseInt(highscore[j]), highscore[j + 1]));
        }

        return players;
    }

    public String getHighscore(int difficulty) {
        generateDifficultyMap();
        return formatHighscore(difficultyMap.get(difficulty));
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

            List<String> newHighscoreList = new ArrayList<>();
            List<Player> players = generateListOfPlayers(difficulty);
            players.add(player);

            players.sort(Collections.reverseOrder());

            for (int i = 0; i < 3; i++) {
                StringBuilder highscores = new StringBuilder();

                if (i == difficulty - 1) {
                    for (int j = 0; j < 10; j++) {
                        outputFile.append(players.get(j).getScore()).append(",").append(players.get(j).getName()).append(",");
                        highscores.append(players.get(j).getScore()).append(",").append(players.get(j).getName()).append(",");
                    }
                } else {
                    outputFile.append(highscoreList.get(i));
                    highscores.append(highscoreList.get(i));
                }
                outputFile.append("\n");
                newHighscoreList.add(highscores.toString());
            }

            highscoreList = newHighscoreList;

            Files.writeString(Path.of(Gdx.files.internal("highscore/highscore").path()), outputFile);


        } catch (IOException ex) {
            System.err.println("Something went wrong with writing the highscore file");
        }
    }

    private static String formatHighscore(List<Player> highscore) {
        StringBuilder sb = new StringBuilder();

        for (Player p : highscore) {
            sb.append(p.getScore()).append(": ").append(p.getName());
            sb.append("\n");
        }

        return sb.toString();
    }
}
