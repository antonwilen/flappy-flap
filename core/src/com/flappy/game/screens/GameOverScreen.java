package com.flappy.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappy.game.util.Difficulty;
import com.flappy.game.Game;
import com.flappy.game.player.Highscore;
import com.flappy.game.player.Player;
import com.flappy.game.util.ButtonHoverListener;
import com.flappy.game.util.Settings;

public class GameOverScreen implements Screen {
    final Game game;
    private final Stage stage;
    Table table;
    Table buttonTable;
    Button playButton;
    Button quitButton;
    Skin mySkin;
    private final Difficulty currentDifficulty;
    Highscore highscore;
    Image background;
    Player player;
    private float timer;

    public GameOverScreen(final Game game, Difficulty difficulty, Highscore highscore, Player player) {
        this.game = game;
        this.player = player;
        this.currentDifficulty = difficulty;
        this.highscore = highscore;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.center();
        buttonTable = new Table();
        buttonTable.setFillParent(true);

        background = new Image(new Texture("gfx/background.png"));
        background.setScaling(Scaling.fill);
        stage.addActor(background);

        mySkin = new Skin(Gdx.files.internal("skin/freezing/freezingui/freezing-ui.json"));

        playButton = new TextButton("Play again", mySkin);
        playButton.setSize(200, 100);
        playButton.setPosition(Settings.SCREEN_WIDTH / 2f - playButton.getWidth() / 2, Settings.SCREEN_HEIGHT / 2f + 50);
        playButton.addListener(new InputListener() {
            @Override

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                newGame();
                return true;
            }
        });
        playButton.addListener(new ButtonHoverListener(playButton));

        quitButton = new TextButton("Exit", mySkin);
        quitButton.setSize(200, 100);
        quitButton.setPosition(Settings.SCREEN_WIDTH / 2f - quitButton.getWidth() / 2, Settings.SCREEN_HEIGHT / 2f - 50);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game, new Player(player.getName())));
                return true;
            }
        });

        quitButton.addListener(new ButtonHoverListener(quitButton));

        BitmapFont font = new BitmapFont(Gdx.files.internal("fishfingers.fnt"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;

        Label currentScore = new Label("Your score: " + player.getScore(), labelStyle);

        BitmapFont font_small = new BitmapFont(Gdx.files.internal("fishfingers_small.fnt"), false);
        Label.LabelStyle labelStyle_small = new Label.LabelStyle();
        labelStyle_small.font = font_small;
        labelStyle_small.fontColor = Color.BLACK;

        Label highScores = new Label("Highscores:\n\n" + highscore.getHighscore(currentDifficulty.getDifficultyNumber()), labelStyle_small);
        highScores.setPosition(0, Settings.SCREEN_HEIGHT / 2f + 40);

        table.add(currentScore).expand().center().left().padLeft(80);

        buttonTable.add(playButton).padBottom(10);
        buttonTable.row();
        buttonTable.add(quitButton);

        table.add(highScores).expand().center().right().padRight(80);

        Label instructions = new Label("PRESS SPACE TO PLAY AGAIN!             OR ' Q ' TO EXIT", labelStyle_small);
        instructions.setPosition(Settings.SCREEN_WIDTH / 2f - instructions.getWidth() / 2, 10);

        stage.addActor(table);
        stage.addActor(buttonTable);
        stage.addActor(instructions);
    }

    @Override
    public void show() {
        timer = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());

        // prevents the player from starting the game immediately after dying
        timer+=delta;
        checkForInput();

        stage.act(delta);
        stage.draw();
    }

    private void checkForInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && timer > 1) {
            newGame();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            game.setScreen(new MainMenuScreen(game, new Player(player.getName())));
        }
    }

    private void newGame() {
        game.setScreen(new GameScreen(game, currentDifficulty, highscore, new Player(player.getName())));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        mySkin.dispose();
        stage.dispose();
    }
}