package com.flappy.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappy.game.Game;
import com.flappy.game.player.Highscore;
import com.flappy.game.player.Player;
import com.flappy.game.util.ButtonHoverListener;
import com.flappy.game.util.Difficulty;
import com.flappy.game.util.Settings;

public class MainMenuScreen implements Screen {
    final Game game;
    private final Stage stage;
    Table table;
    Button playButton;
    Button quitButton;
    Button difficultyButton;
    Skin mySkin;
    Difficulty difficulty;
    Image background;
    Image logo;
    Highscore highscore;
    Label difficultyLabel;

    public MainMenuScreen(final Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new com.badlogic.gdx.scenes.scene2d.ui.Table();
        table.setFillParent(true);
        table.center();

        mySkin = new Skin(Gdx.files.internal("skin/freezing/freezingui/freezing-ui.json"));
        highscore = new Highscore();

        background = new Image(new Texture("gfx/background.png"));
        background.setScaling(Scaling.fill);
        stage.addActor(background);

        logo = new Image(new Texture("gfx/logo.png"));
        logo.setSize(100,200);
        table.add(logo).width(262).height(175);
        table.row();

        playButton = new TextButton("Play",mySkin);
        playButton.setSize(200,100);
        playButton.setPosition(Settings.SCREEN_WIDTH/2f-playButton.getWidth()/2,Settings.SCREEN_HEIGHT/2f+50);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                newGame();
                return true;
            }
        });

        playButton.addListener(new ButtonHoverListener(playButton));

        quitButton = new TextButton("Quit",mySkin);
        quitButton.setSize(200,100);
        quitButton.setPosition(Settings.SCREEN_WIDTH/2f-quitButton.getWidth()/2,Settings.SCREEN_HEIGHT/2f-50);
        quitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.exit();
                return true;
            }
        });

        quitButton.addListener(new ButtonHoverListener(quitButton));

        difficulty = new Difficulty();
        difficultyButton = new Button(mySkin);
        difficultyButton.setSize(200,100);
        difficultyButton.setPosition(Settings.SCREEN_WIDTH/2f-difficultyButton.getWidth()/2,Settings.SCREEN_HEIGHT/2f-150);
        difficultyLabel = new Label(difficulty.getDifficulty(),mySkin,"textButton");

        difficultyButton.add(difficultyLabel);
        difficultyButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                difficulty.next();
                difficultyLabel.setText(difficulty.getDifficulty());
                return true;
            }
        });

        difficultyButton.addListener(new ButtonHoverListener(difficultyButton));

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

        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            newGame();
        }

        // Difficulty
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            difficulty.setDifficulty(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            difficulty.setDifficulty(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            difficulty.setDifficulty(3);
        }
        difficultyLabel.setText(difficulty.getDifficulty());

        stage.act(delta);
        stage.draw();
    }

    private void newGame(){
        game.setScreen(new GameScreen(game, difficulty, highscore, new Player()));
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