package com.flappy.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Settings {
    public static final Texture birdTexture = new Texture("gfx/bird/penguins.png");
    private static final Texture pipeTexture = new Texture("gfx/pipes/pipe_bottom.png");
    public static Skin mySkin = new Skin(Gdx.files.internal("skin/freezing/freezingui/freezing-ui.json"));
    public static int PIPE_SPACE = 300;
    public static int SPEED = 200;
    public static float BACKGROUND_SPEED = 25f;
    public static long SPAWNTIME = 2050000000;
    public static long SNOWFLAKE_SPAWNTIME = 100;
    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_WIDTH = 800;
    public static int BIRDGRAVITY = -15;
    public static int BIRDVELOCITY = 300;
    private static float SNOW_X_SPEED = 200;
    private static float SNOW_Y_SPEED = 10;
    public static final int BIRD_STARTING_POSITION_X = SCREEN_WIDTH / 2 - ((getBirdWidth() / 4) * 3);
    public static final int BIRD_STARTING_POSITION_Y = SCREEN_HEIGHT / 2 - getBirdHeight() / 2;
    public static final int SCORE_COUNT_X = BIRD_STARTING_POSITION_X - getPipeWidth();

    public static long getSnowflakeSpawnTime(){
        return SNOWFLAKE_SPAWNTIME;
    }
    public static float getSnowXSpeed(){
        return SNOW_X_SPEED;
    }

    public static float getSnowYSpeed(){
        return SNOW_Y_SPEED;
    }

    public static int getBirdHeight() {
        return birdTexture.getHeight();
    }

    public static int getBirdWidth() {
        return birdTexture.getWidth();
    }

    public static int getPipeWidth() {
        return pipeTexture.getWidth();
    }

    public static Texture getBirdTexture() {
        return birdTexture;
    }

    public static int getPipeSpace() {
        return PIPE_SPACE;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public static long getSpawnTime() {
        return SPAWNTIME;
    }

    public static void setDifficultySettings(int difficulty) {
        switch (difficulty) {
            case 1:
                PIPE_SPACE = 300;
                SPEED = 200;
                SPAWNTIME = 2050000000;
                BIRDGRAVITY = -15;
                BIRDVELOCITY = 300;
                break;
            case 2:
                PIPE_SPACE = 260;
                SPEED = 250;
                SPAWNTIME = 1900000000;
                BIRDGRAVITY = -17;
                BIRDVELOCITY = 325;
                break;
            case 3:
                PIPE_SPACE = 220;
                SPEED = 300;
                SPAWNTIME = 1200000000;
                BIRDGRAVITY = -20;
                BIRDVELOCITY = 350;
                break;
        }
    }
}
