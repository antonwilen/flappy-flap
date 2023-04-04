package com.flappy.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappy.game.*;
import com.flappy.game.objects.bird.Bird;
import com.flappy.game.objects.Pipe;
import com.flappy.game.player.Highscore;
import com.flappy.game.player.Player;
import com.flappy.game.util.ButtonHoverListener;
import com.flappy.game.util.Difficulty;
import com.flappy.game.util.Settings;

import static com.flappy.game.util.Settings.*;

public class GameScreen implements Screen {
    final Game game;
    private final Difficulty difficulty;
    private final Stage stage;
    private Texture pipeTopImage;
    private Texture pipeBottomImage;
    private Texture pipeBodyImage;
    private Sound thumpSound;
    private Music backgroundMusic;
    private final OrthographicCamera camera;
    private final Bird bird;
    private final Array<Pipe> pipes;
    private Long lastPipeImage;
    public Rectangle scoreCount;
    private Sound plingSound;
    private int currentScore;
    Background background;
    Label label;
    Group foreGround;
    Group backGround;
    Highscore highscore;
    TextButton submitButton;
    Player player;
    RotateToAction action = new RotateToAction();
    Table nameInput;
    Image popupBackground;


    public GameScreen(final Game game, Difficulty difficulty, Highscore highscore, Player player) {
        this.game = game;
        this.difficulty = difficulty;
        this.highscore = highscore;
        this.player = player;
        Settings.setDifficultySettings(difficulty.getDifficultyNumber());

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        BitmapFont font_small = new BitmapFont(Gdx.files.internal("fishfingers_small.fnt"), false);
        Label.LabelStyle labelStyle_small = new Label.LabelStyle();
        labelStyle_small.font = font_small;
        labelStyle_small.fontColor = Color.BLACK;

        Label yayText = new Label("Yay!", labelStyle_small);
        Label newHighscoreText = new Label("You got a new highscore", labelStyle_small);
        Label enterNameText = new Label("Enter your name:", labelStyle_small);

        TextField playerNameInput = new TextField("", mySkin);
        playerNameInput.setText(player.getName());
        playerNameInput.setPosition(25, 75);
        playerNameInput.setSize(90, 30);

        nameInput = new Table();
        nameInput.add(yayText);
        nameInput.row();
        nameInput.add(newHighscoreText);
        nameInput.row();
        nameInput.add(enterNameText);
        nameInput.row();
        nameInput.add(playerNameInput);
        nameInput.row();

        submitButton = new TextButton("OK", mySkin);
        submitButton.setSize(nameInput.getWidth() / 2, nameInput.getHeight() / 4);
        submitButton.addListener(new ButtonHoverListener(submitButton));
        submitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                StringBuilder name = new StringBuilder();

                for (int i = 0; i < playerNameInput.getText().length() && i < 10; i++) {
                    if (Character.isAlphabetic(playerNameInput.getText().charAt(i))) {
                        name.append(playerNameInput.getText().charAt(i));
                    }
                }


                player.setName(name.toString());

                System.out.println(player.getName());
                highscore.saveHighscore(difficulty.getDifficultyNumber(), player);
                game.setScreen(new GameOverScreen(game, difficulty, highscore, player));
                return true;
            }
        });

        nameInput.add(submitButton);
        nameInput.setSize(SCREEN_WIDTH / 3f, SCREEN_HEIGHT / 3.5f);
        nameInput.setPosition(SCREEN_WIDTH / 2f - nameInput.getWidth() / 2f, SCREEN_HEIGHT / 2f - nameInput.getHeight() / 2);

        popupBackground = new Image(new Texture(Gdx.files.internal("gfx/popup_background.png")));
        popupBackground.setHeight(nameInput.getHeight());
        popupBackground.setWidth(nameInput.getWidth());
        popupBackground.setPosition(nameInput.getX(), nameInput.getY());

        backGround = new Group();
        foreGround = new Group();

        createBackground();
        initializePipeTextures();
        initializeSounds();
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        bird = new Bird();
        pipes = new Array<>();

        spawnPipe();
        scoreCount();
        makeScoreLabel();

        foreGround.addActor(bird.getBirdActor());
        foreGround.addActor(label);
        stage.addActor(backGround);
        stage.addActor(foreGround);

    }

    private void createBackground() {
        background = new Background();
        backGround.addActor(background.getBackground1());
        backGround.addActor(background.getBackground2());
    }

    private void initializePipeTextures() {
        pipeTopImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_top.png"));
        pipeBottomImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_bottom.png"));
        pipeBodyImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_body.png"));
    }

    private void initializeSounds() {
        thumpSound = Gdx.audio.newSound(Gdx.files.internal("thump.wav"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        plingSound = Gdx.audio.newSound(Gdx.files.internal("sfx/pling.wav"));
    }

    private void makeScoreLabel() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("8bitfont.fnt"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        label = new Label(Integer.toString(currentScore), labelStyle);
        label.setPosition(Settings.SCREEN_WIDTH / 2f, Settings.SCREEN_HEIGHT - 33);
    }

    @Override
    public void render(float delta) {
        // Game engine stuff
        ScreenUtils.clear(0.3f, 0.2f, 1.21f, 1);
        camera.update();

        // Updating various game objects
        background.update();
        handleInput();
        updatePipes();
        updateBird();
        newPipe();
        checkCollision();
        label.setText(currentScore);

        action = new RotateToAction();
        float actionRotateFactor = 15;
        if (bird.getVelocity().y > 0) {
            actionRotateFactor = 10;
        }
        action.setRotation(bird.getVelocity().y / actionRotateFactor);
        action.setDuration(actionRotateFactor / 20);
        bird.getBirdActor().addAction(action);


        // "Finalizing"
        stage.act(delta);
        stage.draw();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bird.jump();
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            bird.jump();
        }
    }

    private void updatePipes() {
        for (Pipe pipe : pipes) {
            updatePipe(pipe);
        }
    }

    private void updatePipe(Pipe pipe) {
        pipe.getPipeImage().setWidth(pipe.pipe.width);
        pipe.getPipeImage().setHeight(pipe.pipe.height);
        pipe.getPipeImage().setPosition(pipe.pipe.x, pipe.pipe.y);
    }

    private void updateBird() {
        bird.getBirdActor().setPosition(bird.getPosition().x, bird.getPosition().y);
        bird.getBirdActor().setDrawable(new TextureRegionDrawable(bird.getBirdImage()));
        bird.update(Gdx.graphics.getDeltaTime(), Settings.SCREEN_HEIGHT);
    }

    private void newPipe() {
        if (TimeUtils.nanoTime() - lastPipeImage > Settings.getSpawnTime()) {
            spawnPipe();
        }
    }

    private void checkCollision() {
        for (Pipe pipe : pipes) {
            pipe.pipe.x -= Settings.getSPEED() * Gdx.graphics.getDeltaTime();
            if (pipe.pipe.overlaps(bird.getBirdObject())) {
                player.setScore(currentScore);
                if (bird.isAlive()) {
                    thumpSound.play();
                    backgroundMusic.stop();
                }
                if (highscore.checkIfNewHighscore(difficulty.getDifficultyNumber(), player)) {
                    stage.addActor(popupBackground);
                    stage.addActor(nameInput);

                    Settings.BACKGROUND_SPEED = 0;
                    Settings.SPEED = 0;
                    bird.die();
                } else {
                    game.setScreen(new GameOverScreen(game, difficulty, highscore, player));
                }
            }
            if (pipe.pipe.overlaps(scoreCount) && !(pipe.isScored()) && pipe.pipe.x > Settings.SCORE_COUNT_X) {
                plingSound.play();
                currentScore++;
                pipe.score();
            }
        }
    }

    private void scoreCount() {
        scoreCount = new Rectangle();
        scoreCount.y = Settings.SCREEN_HEIGHT;
        scoreCount.x = Settings.SCORE_COUNT_X;
        scoreCount.height = Settings.SCREEN_HEIGHT;
        scoreCount.width = 50;
        lastPipeImage = TimeUtils.nanoTime();
    }

    private void spawnPipe() {
        Pipe pipeTop = new Pipe(pipeTopImage);
        pipeTop.pipe.y = MathUtils.random(Settings.PIPE_SPACE, Settings.SCREEN_HEIGHT - pipeTopImage.getHeight());
        pipeTop.pipe.x = Settings.SCREEN_WIDTH;
        pipeTop.pipe.width = pipeTopImage.getWidth();
        pipeTop.pipe.height = pipeTopImage.getHeight();

        Pipe pipeTopFill = new Pipe(pipeBodyImage);
        pipeTopFill.pipe.y = pipeTop.pipe.y + pipeTopImage.getHeight();
        pipeTopFill.pipe.x = Settings.SCREEN_WIDTH;
        pipeTopFill.pipe.width = pipeBodyImage.getWidth();
        pipeTopFill.pipe.height = Settings.SCREEN_HEIGHT - (pipeTopFill.pipe.y - Settings.SCREEN_HEIGHT);

        Pipe pipeBottom = new Pipe(pipeBottomImage);
        pipeBottom.pipe.y = pipeTop.pipe.y - Settings.getPipeSpace();
        pipeBottom.pipe.x = Settings.SCREEN_WIDTH;
        pipeBottom.pipe.width = pipeBottomImage.getWidth();
        pipeBottom.pipe.height = pipeBottomImage.getHeight();

        Pipe pipeBottomFill = new Pipe(pipeBodyImage);
        pipeBottomFill.pipe.y = 0;
        pipeBottomFill.pipe.x = Settings.SCREEN_WIDTH;
        pipeBottomFill.pipe.width = pipeBodyImage.getWidth();
        pipeBottomFill.pipe.height = pipeBottom.pipe.y;

        pipes.add(pipeTop);
        pipes.add(pipeBottom);
        pipes.add(pipeTopFill);
        pipes.add(pipeBottomFill);

        backGround.addActor(pipeTop.getPipeImage());
        pipeTop.getPipeImage().setPosition(pipeTop.pipe.x, pipeTop.pipe.y);
        backGround.addActor(pipeBottom.getPipeImage());
        pipeBottom.getPipeImage().setPosition(pipeBottom.pipe.x, pipeBottom.pipe.y);
        backGround.addActor(pipeTopFill.getPipeImage());
        pipeTopFill.getPipeImage().setPosition(pipeTopFill.pipe.x, pipeTopFill.pipe.y);
        backGround.addActor(pipeBottomFill.getPipeImage());
        pipeBottomFill.getPipeImage().setPosition(pipeBottomFill.pipe.x, pipeBottomFill.pipe.y);

        lastPipeImage = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

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
        dispose();
    }

    @Override
    public void dispose() {
        pipeTopImage.dispose();
        pipeBottomImage.dispose();
        pipeBodyImage.dispose();
        thumpSound.dispose();
        backgroundMusic.dispose();
        stage.dispose();
    }
}
