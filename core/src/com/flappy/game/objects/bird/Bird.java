package com.flappy.game.objects.bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.flappy.game.util.Settings;


public class Bird {
    private static final int GRAVITY = Settings.BIRDGRAVITY;
    private final Vector2 position;
    private final Vector2 velocity;
    private final Animation birdAnimation;
    private final Texture texture;
    private final Image birdActor;
    Rectangle birdObject;
    boolean alive;

    public Bird() {
        alive = true;
        texture = Settings.getBirdTexture();
        birdAnimation = new Animation(new TextureRegion(texture), 4, 0.5f);
        birdActor = new Image(new TextureRegionDrawable(getBirdImage()));
        birdObject = new Rectangle();
        birdObject.width = texture.getWidth() / 4f;
        birdObject.height = texture.getHeight();
        position = new Vector2(Settings.BIRD_STARTING_POSITION_X, Settings.BIRD_STARTING_POSITION_Y);
        velocity = new Vector2(0, 0);
    }

    public void update(float dt, int screenHeight) {
        birdObject.y = position.y;
        birdObject.x = position.x;

        birdAnimation.update(dt);
        if (position.y > 0) {
            velocity.add(0, GRAVITY);
        }

        velocity.scl(dt);
        position.add(0, velocity.y);
        velocity.scl(1 / dt);

        if (alive) {
            if (position.y < -7) {
                position.y = -7;
            }
        }

        if (position.y > screenHeight - texture.getHeight() + 30) {
            position.y = screenHeight - texture.getHeight() + 30;
        }
    }

    public Image getBirdActor() {
        return birdActor;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void die() {
        alive = false;
        addToBirdY(GRAVITY / 2f);
    }

    public void jump() {
        if (alive) {
            velocity.y = Settings.BIRDVELOCITY;
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public void addToBirdY(float y) {
        position.y += y;
    }

    public Rectangle getBirdObject() {
        return birdObject;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getBirdImage() {
        return birdAnimation.getFrame();
    }

}
