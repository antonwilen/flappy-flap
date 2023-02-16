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

public class MainMenuScreen implements Screen {
    // TODO make a timer class
    private final int SCREEN_HEIGHT = 480;
    private final int SCREEN_WIDTH = 800;
    final Flap game;
    Texture play;
    Texture quit;
    Texture highscore;
    Sprite playButton;
    Sprite quitButton;
    Texture background;
    OrthographicCamera camera;
    float pauseTime;
    boolean startGame;
    public MainMenuScreen(final Flap game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);

        play = new Texture(Gdx.files.internal("play.png"));
        quit = new Texture(Gdx.files.internal("quit.png"));
        playButton = new Sprite(play);
        quitButton = new Sprite(quit);
        playButton.setPosition(SCREEN_WIDTH/2-play.getWidth()/2,SCREEN_HEIGHT/2+50);
        quitButton.setPosition(SCREEN_WIDTH/2-quit.getWidth()/2,SCREEN_HEIGHT/2-50);

        highscore = new Texture(Gdx.files.internal("highscore.png"));
        background = new Texture(Gdx.files.internal("wallpaper.jpg"));

        pauseTime = 0.0f;

        startGame = false;
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
        quitButton.draw(game.batch);
        game.batch.draw(highscore,SCREEN_WIDTH/2-(highscore.getWidth()/2),420);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
        }


        float endPauseTime =0.85f;
        pauseTime += delta;


        // Checks if the user has clicked somewhere on the screen
        if(Gdx.input.justTouched()){
            System.out.println("hello");
            Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            Vector2 touch2 = new Vector2(touch.x,touch.y);

            // Checks if the user has clicked the play button

            if(playButton.getBoundingRectangle().contains(touch2)){
                playButton.setScale(1.2f);
                startGame = true;
            }
        }
            if(pauseTime>endPauseTime && startGame){
                game.setScreen(new GameScreen(game));
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
