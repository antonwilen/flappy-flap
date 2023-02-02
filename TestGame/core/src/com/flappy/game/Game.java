package com.flappy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.w3c.dom.Text;

public class Game {
    private final int SCREEN_HEIGHT = 480;
    private final int SCREEN_WIDTH = 600;

    private Texture birdTexture;
    private Texture pipeTopTexture;
    private Texture pipeBottomTexture;
    private Texture pipeFillTexture;

    private final float pipeSpaceWidth = 4 * SCREEN_WIDTH / 6;
    private final float pipeSpaceHeight = SCREEN_HEIGHT / 3;

    private Vector2 pipes[];


}
