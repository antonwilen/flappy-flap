package com.flappy.game;

import com.badlogic.gdx.graphics.Texture;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private static final int GRAVITY = -15;
    private final Vector2 position;
    private final Vector2 velocity;
    private final Animation birdAnimation;
    private final Texture texture;
    Rectangle birdObject;

    public Bird(int SCREEN_HEIGHT, int SCREEN_WIDTH){

        texture = new Texture("penguins.png");
        birdAnimation = new Animation(new TextureRegion(texture), 2, 0.5f);

         birdObject = new Rectangle();
         birdObject.y = SCREEN_HEIGHT / 2 - 57 / 2;
         birdObject.x = SCREEN_WIDTH / 2 - 57 / 2;
         birdObject.width = texture.getWidth();
         birdObject.height = texture.getHeight() / 2;

         position = new Vector2(birdObject.x, birdObject.y);
         velocity = new Vector2(0,0);

    }

    public void update(float dt) {
        birdAnimation.update(dt);
        if(position.y > 0) {
            velocity.add(0, GRAVITY);
        }

        velocity.scl(dt);
        position.add(0, velocity.y);
        velocity.scl(1/dt);

        if(position.y < 0) {
            position.y = 0;
        }
    }

    public void jump() {
        velocity.y = 200;
    }


    public void setBirdY(int y){
        birdObject.y = y;
    }
    public void addToBirdY(float y){
        birdObject.y += y;
    }

    public Rectangle getBirdObject(){
        return birdObject;
    }

    public Vector2 getPosition() {
        return position;
    }
    public float getBirdY(){
        return birdObject.y;
    }
    public float getBirdX(){
        return birdObject.x;
    }

    public TextureRegion getBirdImage(){
      return birdAnimation.getFrame();
    }

}
