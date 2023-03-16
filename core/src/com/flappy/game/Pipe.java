package com.flappy.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pipe {
    public Rectangle pipe;
    private Texture pipeTexture;
    private Image pipeImage;
    // TODO FIX STUPID WAY TO ADD POINTS
    private boolean scored; // Everytime a collision with scorecounter occurs, it checks if the pipe has already scored

    public Pipe(Texture pipeTexture){
        pipe = new Rectangle();
        pipeImage = new Image(pipeTexture);
        this.pipeTexture = pipeTexture;
        scored = false;
    }
    public void score(){
        scored = true;
    }

    public boolean isScored(){
        return scored;
    }

    public Image getPipeImage(){
        return pipeImage;
    }
    public Texture getPipeTexture() {
        return pipeTexture;
    }
}
