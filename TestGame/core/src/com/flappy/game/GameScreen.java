package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.flappy.game.bird.Bird;

import java.util.Iterator;

public class GameScreen implements Screen {
	final Flap game;
	private final int SCREEN_HEIGHT = 480;
	private final int SCREEN_WIDTH = 800;
	private final Texture pipeTopImage;
	private final Texture pipeBottomImage;
	private final Texture pipeBodyImage;
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

	// -- testing score count

	public GameScreen (final Flap game) {
		this.game = game;

		pipeTopImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_top.png"));
		pipeBottomImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_bottom.png"));
		pipeBodyImage = new Texture(Gdx.files.internal("gfx/pipes/pipe_body.png"));

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

		spawnPipe();
		scoreCount();
	}

	private void scoreCount() {
		scoreCount = new Rectangle();
		scoreImage = new Texture("gfx/bird/penguins.png");

		scoreCount.y = 0;
		scoreCount.x = SCREEN_WIDTH / 2 - 150;
		scoreCount.height = SCREEN_HEIGHT;
		scoreCount.width = 10;

		lastPipeImage = TimeUtils.nanoTime();
	}

	private void spawnPipe() {
		int PIPE_SPACE = 200;

		Pipe pipeTop = new Pipe(pipeTopImage);
		pipeTop.pipe.y = MathUtils.random(200, SCREEN_HEIGHT - pipeTopImage.getHeight());
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


		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bird.getBirdImage(), bird.getPosition().x, bird.getPosition().y);
		batch.draw(scoreImage, scoreCount.x, scoreCount.y, scoreCount.width, scoreCount.height);

		font.draw(batch, Integer.toString(currentScore), 40, 40);

		for (Pipe pipe: pipes) {
			batch.draw(pipe.getPipeTexture(), pipe.pipe.x, pipe.pipe.y, pipe.pipe.width, pipe.pipe.height);
		}
		batch.end();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			bird.jump();
		}

		bird.update(Gdx.graphics.getDeltaTime(), SCREEN_HEIGHT);
			//}
			//if(jump > 0) {
			//	bird.addToBirdY(450 * Gdx.graphics.getDeltaTime());
			//	jump--;
			//}

			//if(bird.getBirdY()< 0) bird.setBirdY(0);
			//if(bird.getBirdY() > 480 - 64) bird.setBirdY(480 - 64);

			if (TimeUtils.nanoTime() - lastPipeImage > 2050000000) {
				spawnPipe();
				//scoreCount();
			}

			//bird.addToBirdY(-250 * Gdx.graphics.getDeltaTime());

			for (Iterator<Pipe> iter = pipes.iterator(); iter.hasNext(); ) {
				Pipe pipe = iter.next();
				pipe.pipe.x -= 200 * Gdx.graphics.getDeltaTime();
				if (pipe.pipe.x + pipeTopImage.getWidth() < 0) iter.remove();

				if (pipe.pipe.overlaps(scoreCount)) {
					plingSound.play();
					currentScore++;
					iter.remove();
				}

				if (pipe.pipe.overlaps(bird.getBirdObject())) {
					thumpSound.play();
					game.setScreen(new GameOverScreen(game));
					//bird.addToBirdY(-bird.getBirdY() * Gdx.graphics.getDeltaTime());
					//iter.remove();
					backgroundMusic.stop();
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
	public void dispose () {
		pipeTopImage.dispose();
		pipeBottomImage.dispose();
		pipeBodyImage.dispose();
		//bird. dispose??
		thumpSound.dispose();
		backgroundMusic.dispose();
		batch.dispose();
	}
}
