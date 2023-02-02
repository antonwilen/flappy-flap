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
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private Long lastDropTime;
	
	@Override
	public void create () {
		dropImage = new Texture(Gdx.files.internal("pipe_top.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 480 / 2 - 64 / 2;
		bucket.width = 64;
		bucket.height = 64;

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
		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			jump = 15;
		}
		if(jump > 0) {
			bucket.y += 1000 * Gdx.graphics.getDeltaTime();
			jump--;
		}

		if(bucket.y < 0) bucket.y = 0;
		if(bucket.y > 480 - 64) bucket.y = 480 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 2050000000) spawnRaindrop();

		bucket.y -= 600 * Gdx.graphics.getDeltaTime();

		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
			Rectangle raindrop = iter.next();
			raindrop.x -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.x + 64 < 0) iter.remove();

			if(raindrop.overlaps(bucket)) {
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
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
