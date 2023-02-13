package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private final int SCREEN_HEIGHT = 480;
    private final int SCREEN_WIDTH = 800;
    final Flap game;
    Texture play;
    Texture quit;
    Texture highscore;
    Texture background;
    OrthographicCamera camera;
    public MainMenuScreen(final Flap game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);

        play = new Texture(Gdx.files.internal("play.png"));
        quit = new Texture(Gdx.files.internal("quit.png"));
        highscore = new Texture(Gdx.files.internal("highscore.png"));
        background = new Texture(Gdx.files.internal("wallpaper.jpg"));
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
        game.batch.draw(play,SCREEN_WIDTH/2-play.getWidth()/2,SCREEN_HEIGHT/2+50);
        game.batch.draw(quit,SCREEN_WIDTH/2-quit.getWidth()/2,SCREEN_HEIGHT/2-50);
        game.batch.draw(highscore,SCREEN_WIDTH/2-(highscore.getWidth()/2),420);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            dispose();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
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
