package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.flappy.game.bird.Bird;

import java.util.Iterator;

public class MyGame extends ApplicationAdapter {
	private final int SCREEN_HEIGHT = 480;
	private final int SCREEN_WIDTH = 800;
	private Texture pipeTopImage;
	private Texture pipeBottomImage;
	private Texture pipeBodyImage;
	private Texture birdImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Bird bird;
	private Array<Pipe> pipes;
	private Long lastPipeImage;

	@Override
	public void create () {
		birdImage = new Texture(Gdx.files.internal("penguin.png"));
		pipeTopImage = new Texture(Gdx.files.internal("pipe_top.png"));
		pipeBottomImage = new Texture(Gdx.files.internal("pipe_bottom.png"));
		pipeBodyImage = new Texture(Gdx.files.internal("pipe_body.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		bird = new Bird();

		pipes = new Array<>();

		spawnPipe();
	}

	int jump = 0;

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bird.getBirdImage(), bird.getBirdX(), bird.getBirdY());
		for (Pipe pipe: pipes) {
			batch.draw(pipe.getPipeTexture(), pipe.pipe.x, pipe.pipe.y, pipe.pipe.width, pipe.pipe.height);
		}
		batch.end();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			jump = 15;
		}
		if(jump > 0) {
			bird.addToBirdY(450 * Gdx.graphics.getDeltaTime());
			jump--;
		}

		if(bird.getBirdY()< 0) bird.setBirdY(0);
		if(bird.getBirdY() > 480 - 64) bird.setBirdY(480 - 64);

		if(TimeUtils.nanoTime() - lastPipeImage > 2050000000) spawnPipe();

		bird.addToBirdY(-250 * Gdx.graphics.getDeltaTime());

		for (Iterator<Pipe> iter = pipes.iterator(); iter.hasNext();) {
			Pipe pipe = iter.next();
			pipe.pipe.x -= 200 * Gdx.graphics.getDeltaTime();
			if(pipe.pipe.x + pipeTopImage.getWidth() < 0) iter.remove();

			if(pipe.pipe.overlaps(bird.getBirdObject())) {
				dropSound.play();
				//bird.addToBirdY(-bird.getBirdY() * Gdx.graphics.getDeltaTime());
				//iter.remove();
			}
		}
	}

	private void spawnPipe() {
		int PIPE_SPACE = 250;

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
	public void dispose () {
		pipeTopImage.dispose();
		pipeBottomImage.dispose();
		pipeBodyImage.dispose();
		birdImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
