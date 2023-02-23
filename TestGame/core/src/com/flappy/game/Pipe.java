package com.flappy.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pipe {
    public Rectangle pipe;
    private Texture pipeTexture;
    private Image pipeImage;

    public Pipe(Texture pipeTexture){
        pipe = new Rectangle();
        pipeImage = new Image(pipeTexture);
        this.pipeTexture = pipeTexture;
    }

    public Image getPipeImage(){
        return pipeImage;
    }
    public Texture getPipeTexture() {
        return pipeTexture;
    }
}
