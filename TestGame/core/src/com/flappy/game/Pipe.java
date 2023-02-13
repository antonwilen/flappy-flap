package com.flappy.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Pipe {
    public Rectangle pipe;
    private Texture pipeTexture;

    public Pipe(Texture pipeTexture){
        pipe = new Rectangle();
        this.pipeTexture = pipeTexture;
    }

    public Texture getPipeTexture() {
        return pipeTexture;
    }
}
