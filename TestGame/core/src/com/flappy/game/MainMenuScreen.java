package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappy.game.util.Settings;


public class MainMenuScreen implements Screen {
    final Flap game;
    private Stage stage;
    Table table;
    Button playButton;
    Button quitButton;
    Button difficultyButton;
    Skin mySkin;
    Difficulty difficulty;
    TextureAtlas atlas;



    public MainMenuScreen(final Flap game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new com.badlogic.gdx.scenes.scene2d.ui.Table();
        table.setFillParent(true);
        table.center();

        mySkin = new Skin(Gdx.files.internal("skin/freezing/freezingui/freezing-ui.json"));

        playButton = new TextButton("Play",mySkin);
        playButton.setSize(200,100);
        playButton.setPosition(Settings.SCREEN_WIDTH/2-playButton.getWidth()/2,Settings.SCREEN_HEIGHT/2+50);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new GameScreenTEST(game,difficulty));
                return true;
            }
        });



        quitButton = new TextButton("Quit",mySkin);
        quitButton.setSize(200,100);
        quitButton.setPosition(Settings.SCREEN_WIDTH/2-quitButton.getWidth()/2,Settings.SCREEN_HEIGHT/2-50);
        quitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.exit();
                return true;
            }
        });
        difficulty = new Difficulty();
        difficultyButton = new Button(mySkin);
        difficultyButton.setSize(200,100);
        difficultyButton.setPosition(Settings.SCREEN_WIDTH/2-difficultyButton.getWidth()/2,Settings.SCREEN_HEIGHT/2-150);
        Label difficultyLabel = new Label(difficulty.getDifficulty(),mySkin,"textButton");

        difficultyButton.add(difficultyLabel);
        difficultyButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                difficulty.next();
                difficultyLabel.setText(difficulty.getDifficulty());
                return true;
            }
        });

        table.add(playButton);
        table.row();
        table.add(difficultyButton);
        table.row();
        table.add(quitButton);


        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        stage.dispose();
    }
}