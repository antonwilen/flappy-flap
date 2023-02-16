package com.flappy.game.bird;

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

        texture = new Texture("gfx/bird/penguins.png");
        birdAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);

         birdObject = new Rectangle();
         birdObject.width = texture.getWidth() / 4;
         birdObject.height = texture.getHeight();

         position = new Vector2(SCREEN_WIDTH / 2 - texture.getHeight() / 2, SCREEN_HEIGHT /2 - texture.getWidth() / 4);
         velocity = new Vector2(0,0);



    }

    public void update(float dt, int screenHeight) {
        birdObject.y = position.y;
        birdObject.x = position.x;

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

        if(position.y > screenHeight) {
            position.y = screenHeight;
        }
    }

    public void jump() {
        velocity.y = 300;
    }


    public void setBirdY(int y){
        position.y = y;
    }
    public void addToBirdY(float y){
        position.y += y;
    }

    public Rectangle getBirdObject(){
        return birdObject;
    }

    public Vector2 getPosition() {
        return position;
    }
    public float getBirdY(){
        return position.y;
    }
    public float getBirdX(){
        return position.x;
    }

    public TextureRegion getBirdImage(){
      return birdAnimation.getFrame();
    }

}