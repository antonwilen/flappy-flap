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
    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_WIDTH = 800;
    public static int BIRDGRAVITY = -15;
    public static int BIRDVELOCITY = 300;
    private static String background1 = "gfx/background.png";
    private static String background2 = "gfx/background.png";
    private static String snowground1 = "gfx/snow/snow-easy.png";
    private static String snowground2 = "gfx/snow/snow-easy.png";
    private static float snowground1Speed = 250f;
    private static float snowground2Speed = 350f;
    private static final float inputDelay = 0.5f;
    public static final int BIRD_STARTING_POSITION_X = SCREEN_WIDTH / 2 - ((getBirdWidth() / 4) * 3);
    public static final int BIRD_STARTING_POSITION_Y = SCREEN_HEIGHT / 2 - getBirdHeight() / 2;
    public static final int SCORE_COUNT_X = BIRD_STARTING_POSITION_X - getPipeWidth();


    public static String getSnowground1() { return snowground1; }
    public static String getSnowground2() { return snowground2; }
    public static float getInputDelay(){
        return inputDelay;
    }
    public static float getSnowground1Speed() { return snowground1Speed; }
    public static float getSnowground2Speed() { return snowground2Speed; }
    public static String getBackground1() { return background1; }
    public static String getBackground2() { return background2; }

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
                snowground1Speed = 200f;
                snowground2Speed = 300f;
                snowground1 = "gfx/snow/snow-light.png";
                snowground2 = "gfx/snow/snow-easy.png";
                break;
            case 2:
                PIPE_SPACE = 230;
                SPEED = 250;
                SPAWNTIME = 1800000000;
                BIRDGRAVITY = -17;
                BIRDVELOCITY = 325;
                snowground1Speed = 250f;
                snowground2Speed = 350f;
                snowground1 = "gfx/snow/snow-easy.png";
                snowground2 = "gfx/snow/snow-medium.png";
                background1 = "gfx/background2.png";
                background2 = "gfx/background2.png";
                break;
            case 3:
                PIPE_SPACE = 220;
                SPEED = 280;
                SPAWNTIME = 1400000000;
                BIRDGRAVITY = -19;
                BIRDVELOCITY = 340;
                snowground1Speed = 300f;
                snowground2Speed = 400f;
                background1 = "gfx/background3.png";
                background2 = "gfx/background3.png";
                snowground1 = "gfx/snow/snow-medium.png";
                snowground2 = "gfx/snow/snow-hard.png";
                break;
        }
    }
}
