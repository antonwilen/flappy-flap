package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappy.game.util.Settings;

import java.util.Set;

public class GameOverScreenTEST implements Screen {
    final Flap game;
    private int score;
    private Stage stage;
    Table table;
    Table scoreTable;
    Button playButton;
    Button quitButton;
    Skin mySkin;
    TextureAtlas atlas;
    private Difficulty currentDifficulty;
    Highscore highscore;


    public GameOverScreenTEST(final Flap game, int score, Difficulty difficulty){
        this.game = game;
        this.score = score;
        this.currentDifficulty = difficulty;
        highscore = new Highscore(score, difficulty.getDifficultyNumber());
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.center();
        scoreTable = new Table();
        scoreTable.setFillParent(true);
        scoreTable.padLeft(Settings.SCREEN_WIDTH/-2f);

        mySkin = new Skin(Gdx.files.internal("skin/freezing/freezingui/freezing-ui.json"));

        playButton = new TextButton("Play",mySkin);
        playButton.setSize(200,100);
        playButton.setPosition(Settings.SCREEN_WIDTH/2-playButton.getWidth()/2,Settings.SCREEN_HEIGHT/2+50);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                newGame();
                return true;
            }
        });



        quitButton = new TextButton("Exit",mySkin);
        quitButton.setSize(200,100);
        quitButton.setPosition(Settings.SCREEN_WIDTH/2-quitButton.getWidth()/2,Settings.SCREEN_HEIGHT/2-50);
        quitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });

        Label highScores = new Label("Highscores: \n" + highscore.receiveHighscore(),mySkin);
        //highScores.setFontScale(1.5f);
        highScores.setPosition(0,Settings.SCREEN_HEIGHT/2+40);

        Label currentScore = new Label("Your score: " + score,mySkin);
        //currentScore.setFontScale(2f);
        //currentScore.setPosition(Settings.SCREEN_WIDTH/2-currentScore.getWidth()/2,0);

        table.add(playButton);
        table.row();
        table.add(quitButton);

        scoreTable.add(currentScore);
        scoreTable.row();
        scoreTable.add(highScores);



        stage.addActor(table);
        stage.addActor(scoreTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            newGame();
        }
        stage.act(delta);
        stage.draw();
    }

    private void newGame(){
        game.setScreen(new GameScreenTEST(game, currentDifficulty));
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
