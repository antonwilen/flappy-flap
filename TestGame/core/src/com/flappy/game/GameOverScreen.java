package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen implements Screen {
    private final int SCREEN_HEIGHT = 480;
    private final int SCREEN_WIDTH = 800;
    final Flap game;
    Texture play;
    Texture quit;
    Texture highscore;
    Texture background;
    Sprite playButton;
    OrthographicCamera camera;
    public GameOverScreen(final Flap game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);



        play = new Texture(Gdx.files.internal("gameover/play.png"));
        playButton = new Sprite(play);
        playButton.setPosition(SCREEN_WIDTH/2-playButton.getWidth()/2,SCREEN_HEIGHT/2+50);

        background = new Texture(Gdx.files.internal("gameover/wallpaper.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background,0,0);

        playButton.draw(game.batch);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
        }

        // Checks if the user has clicked somewhere on the screen
        if(Gdx.input.justTouched()){
            System.out.println("hello");
            Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touch2 = new Vector2(touch.x,touch.y);

            // Checks if the user has clicked the play button
            if(playButton.getBoundingRectangle().contains(touch2)){
                game.setScreen(new GameScreen(game));
                System.out.println("hey");
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
