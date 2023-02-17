package com.flappy.game.util;

import com.badlogic.gdx.graphics.Texture;

public class Settings {
    private static final Texture birdTexture = new Texture("gfx/bird/penguins.png");
    private static final Texture pipeTexture = new Texture("gfx/pipes/pipe_bottom.png");
    public static final int SCREEN_HEIGHT = 480; // denna är bara kopplad till poängen och positionen på pingvinen än så länge.
    public static final int SCREEN_WIDTH = 800; // denna är bara kopplad till poängen och positionen på pingvinen än så länge.
    public static final int BIRD_STARTING_POSITION_X = SCREEN_WIDTH / 2 - ((getBirdWidth() / 4) * 3);
    public static final int BIRD_STARTING_POSITION_Y = SCREEN_HEIGHT / 2 - getBirdHeight() / 2;
    public static final int SCORE_COUNT_X = BIRD_STARTING_POSITION_X - getPipeWidth();

    public static int getBirdHeight() {
        return birdTexture.getHeight();
    }

    public static int getBirdWidth() {
        return birdTexture.getWidth();
    }

    public static int getPipeWidth() {
        return pipeTexture.getWidth();
    }
}
