package com.flappy.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappy.game.player.Player;
import com.flappy.game.screens.MainMenuScreen;

public class Game extends com.badlogic.gdx.Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public void create(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this, new Player()));
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
