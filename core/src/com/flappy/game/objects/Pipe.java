package com.flappy.game.objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pipe {
    public Rectangle pipe;
    private final Image pipeImage;

    private boolean scored; // Everytime a collision with Scorecounter occurs, it checks if the pipe has already scored

    public Pipe(Texture pipeTexture){
        pipe = new Rectangle();
        pipeImage = new Image(pipeTexture);
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
}
