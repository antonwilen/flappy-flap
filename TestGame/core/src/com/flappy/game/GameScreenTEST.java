package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappy.game.bird.Bird;
import com.flappy.game.util.Settings;

import java.util.Iterator;

public class GameScreenTEST implements Screen {
	final Flap game;
	private Stage stage;
	private Table mainTable;
	private final int SCREEN_HEIGHT = 480;
	private final int SCREEN_WIDTH = 800;
	private final Sprite pipeTop;
	private final Sprite pipeBottom;
	private final Sprite pipeBody;
	private final Texture pipeTopImage;
	private final Texture pipeBottomImage;
	private final Texture pipeBodyImage;
	private Image birdImage;
	private final Sound thumpSound;
	private final Music backgroundMusic;
	private final OrthographicCamera camera;
	private final SpriteBatch batch;
	private final Bird bird;
	private final Array<Pipe> pipes;
	private Long lastPipeImage;
	// -- testing score count
	public Rectangle scoreCount;
	private Texture scoreImage;
	private final Sound plingSound;
	private int currentScore;
	Image birdImageTest;

	// -- testing score count

	public GameScreenTEST(final Flap game) {
		this.game = game;

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		mainTable = new Table();
		mainTable.setFillParent(true);
		mainTable.center();


		pipeTopImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_top.png"));
		pipeBottomImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_bottom.png"));
		pipeBodyImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_body.png"));
		pipeTop = new Sprite(pipeTopImage);
		pipeBody = new Sprite(pipeBodyImage);
		pipeBottom = new Sprite(pipeBottomImage);


		thumpSound = Gdx.audio.newSound(Gdx.files.internal("thump.wav"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		plingSound = Gdx.audio.newSound(Gdx.files.internal("sfx/pling.wav"));

		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		bird = new Bird(SCREEN_HEIGHT, SCREEN_WIDTH);

		pipes = new Array<>();

		birdImageTest = new Image(bird.getBirdImage());
		stage.addActor(birdImageTest);

		spawnPipe();
		//scoreCount();


	}

	private void scoreCount() {
		scoreCount = new Rectangle();
		scoreImage = new Texture("gfx/bird/penguins.png");

		scoreCount.y = SCREEN_HEIGHT - 100;
		scoreCount.x = Settings.SCORE_COUNT_X;
		scoreCount.height = SCREEN_HEIGHT;
		scoreCount.width = 10;

		lastPipeImage = TimeUtils.nanoTime();
	}

	private void spawnPipe() {
		int PIPE_SPACE = 300;

		Pipe pipeTop = new Pipe(pipeTopImage);
		pipeTop.pipe.y = MathUtils.random(PIPE_SPACE, SCREEN_HEIGHT - pipeTopImage.getHeight());
		pipeTop.pipe.x = SCREEN_WIDTH;
		pipeTop.pipe.width = pipeTopImage.getWidth();
		pipeTop.pipe.height = pipeTopImage.getHeight();

		Pipe pipeTopFill = new Pipe(pipeBodyImage);
		pipeTopFill.pipe.y = pipeTop.pipe.y + pipeTopImage.getHeight();
		pipeTopFill.pipe.x = SCREEN_WIDTH;
		pipeTopFill.pipe.width = pipeBodyImage.getWidth();
		pipeTopFill.pipe.height = SCREEN_HEIGHT - (pipeTopFill.pipe.y - SCREEN_HEIGHT);

		Pipe pipeBottom = new Pipe(pipeBottomImage);
		pipeBottom.pipe.y = pipeTop.pipe.y - PIPE_SPACE;
		pipeBottom.pipe.x = SCREEN_WIDTH;
		pipeBottom.pipe.width = pipeBottomImage.getWidth();
		pipeBottom.pipe.height = pipeBottomImage.getHeight();

		Pipe pipeBottomFill = new Pipe(pipeBodyImage);
		pipeBottomFill.pipe.y = 0;
		pipeBottomFill.pipe.x = SCREEN_WIDTH;
		pipeBottomFill.pipe.width = pipeBodyImage.getWidth();
		pipeBottomFill.pipe.height = pipeBottom.pipe.y;

		pipes.add(pipeTop);
		pipes.add(pipeBottom);
		pipes.add(pipeTopFill);
		pipes.add(pipeBottomFill);




		stage.addActor(pipeTop.getPipeImage());
		pipeTop.getPipeImage().setPosition(pipeTop.pipe.x,pipeTop.pipe.y);
		stage.addActor(pipeBottom.getPipeImage());
		pipeBottom.getPipeImage().setPosition(pipeBottom.pipe.x,pipeBottom.pipe.y);
		stage.addActor(pipeTopFill.getPipeImage());
		pipeTopFill.getPipeImage().setPosition(pipeTopFill.pipe.x,pipeTopFill.pipe.y);
		stage.addActor(pipeBottomFill.getPipeImage());
		pipeBottomFill.getPipeImage().setPosition(pipeBottomFill.pipe.x,pipeBottomFill.pipe.y);



		lastPipeImage = TimeUtils.nanoTime();


	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0.3f, 0.2f, 1.21f, 1);

		camera.update();
		BitmapFont font = new BitmapFont(Gdx.files.internal("8bitfont.fnt"), false);

		birdImageTest.setPosition(bird.getPosition().x, bird.getPosition().y);
		//batch.draw(bird.getBirdImage(), bird.getPosition().x, bird.getPosition().y);


		//batch.draw(scoreImage, scoreCount.x, scoreCount.y, scoreCount.width, scoreCount.height);

		for (Pipe pipe: pipes) {
			//batch.draw(pipe.getPipeTexture(), pipe.pipe.x, pipe.pipe.y, pipe.pipe.width, pipe.pipe.height);
			pipe.getPipeImage().setWidth(pipe.pipe.width);
			pipe.getPipeImage().setHeight(pipe.pipe.height);
			pipe.getPipeImage().setPosition(pipe.pipe.x,pipe.pipe.y);

		}



		//font.draw(batch, Integer.toString(currentScore), Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT - 20);
		//batch.end();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			bird.jump();
		}

		bird.update(Gdx.graphics.getDeltaTime(), SCREEN_HEIGHT);

			if (TimeUtils.nanoTime() - lastPipeImage > 2050000000) {
				spawnPipe();
			}

			for (Iterator<Pipe> iter = pipes.iterator(); iter.hasNext(); ) {
				Pipe pipe = iter.next();
				pipe.pipe.x -= 200 * Gdx.graphics.getDeltaTime();
				if (pipe.pipe.x + pipeTopImage.getWidth() < 0) iter.remove();

				/*if (pipe.pipe.overlaps(scoreCount)) {
					plingSound.play();
					currentScore++;
					iter.remove();
				}*/

				if (pipe.pipe.overlaps(bird.getBirdObject())) {
					thumpSound.play();
					game.setScreen(new GameOverScreen(game, currentScore));

					backgroundMusic.stop();
				}
			}
		stage.act(delta);
		stage.draw();
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
	public void dispose () {
		pipeTopImage.dispose();
		pipeBottomImage.dispose();
		pipeBodyImage.dispose();
		//bird. dispose??
		thumpSound.dispose();
		backgroundMusic.dispose();
		stage.dispose();
		//batch.dispose();
	}
}
