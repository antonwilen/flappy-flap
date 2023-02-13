package com.flappy.game;

import com.badlogic.gdx.graphics.Texture;


import com.badlogic.gdx.math.Rectangle;

public class Bird {
    private Texture birdImage;
    Rectangle birdObject;

    public Bird(Texture birdImage, int SCREEN_HEIGHT, int SCREEN_WIDTH){
        // Default test values

         birdObject = new Rectangle();
         birdObject.y = SCREEN_HEIGHT / 2 - 64 / 2;
         birdObject.x = SCREEN_WIDTH / 2 - 64 / 2;
         birdObject.width = 64;
         birdObject.height = 64;
         this.birdImage = birdImage;
    }


    public void setBirdY(int y){
        birdObject.y = y;
    }
    public void addToBirdY(float y){
        birdObject.y += y;
    }
    public void setBirdX(int x){
        birdObject.x = x;
    }

    public Rectangle getBirdObject(){
        return birdObject;
    }
    public float getBirdY(){
        return birdObject.y;
    }
    public float getBirdX(){
        return birdObject.x;
    }

    public Texture getBirdImage(){
      return birdImage;
    }

}
