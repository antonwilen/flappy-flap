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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGame extends ApplicationAdapter {
	private final int SCREEN_HEIGHT = 480;
	private final int SCREEN_WIDTH = 800;
	private Texture dropImage;
	private Texture birdImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Bird bird;
	private Array<Rectangle> raindrops;
	private Long lastDropTime;
	
	@Override
	public void create () {
		dropImage = new Texture(Gdx.files.internal("pipe_top.png"));
		birdImage = new Texture(Gdx.files.internal("penguin.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		bird = new Bird(birdImage, SCREEN_HEIGHT, SCREEN_WIDTH);


		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	int jump = 0;

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bird.getBirdImage(), bird.getBirdX(), bird.getBirdY());
		for (Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			jump = 15;
		}
		if(jump > 0) {
			bird.addToBirdY(1000 * Gdx.graphics.getDeltaTime());
			jump--;
		}

		if(bird.getBirdY()< 0) bird.setBirdY(0);
		if(bird.getBirdY() > 480 - 64) bird.setBirdY(480 - 64);

		if(TimeUtils.nanoTime() - lastDropTime > 2050000000) spawnRaindrop();

		bird.addToBirdY(-600 * Gdx.graphics.getDeltaTime());

		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
			Rectangle raindrop = iter.next();
			raindrop.x -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.x + 64 < 0) iter.remove();

			if(raindrop.overlaps(bird.getBirdObject())) {
				dropSound.play();
				iter.remove();
			}
		}
	}

	private void spawnRaindrop() {
		Rectangle pipeTop = new Rectangle();
		pipeTop.y = MathUtils.random(200, SCREEN_HEIGHT - 64);
		pipeTop.x = SCREEN_WIDTH;
		pipeTop.width = 134;
		pipeTop.height = 64;

		Rectangle pipeTopFill = new Rectangle();
		pipeTopFill.y = pipeTop.y + 64;
		pipeTopFill.x = SCREEN_WIDTH;
		pipeTopFill.width = 100;
		pipeTopFill.height = 200;

		Rectangle pipeBottom = new Rectangle();
		pipeBottom.y = pipeTop.y - 200;
		pipeBottom.x = SCREEN_WIDTH;
		pipeBottom.width = 134;
		pipeBottom.height = 64;

		Rectangle pipeBottomFill = new Rectangle();
		pipeBottomFill.y = pipeBottom.y - 64;
		pipeBottomFill.x = SCREEN_WIDTH;
		pipeBottomFill.width = 134;
		pipeBottomFill.height = 64;

		raindrops.add(pipeTop);
		raindrops.add(pipeBottom);
		raindrops.add(pipeTopFill);
		raindrops.add(pipeBottomFill);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		birdImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
