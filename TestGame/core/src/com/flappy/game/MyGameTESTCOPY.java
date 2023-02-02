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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGameTESTCOPY extends ApplicationAdapter {
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
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		birdImage = new Texture(Gdx.files.internal("penguin.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		bird = new Bird(birdImage);

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
		Rectangle raindrop = new Rectangle();
		raindrop.y = MathUtils.random(0, 480 - 64);
		raindrop.x = 800;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
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
