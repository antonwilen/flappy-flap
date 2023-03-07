package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.flappy.game.util.Settings;

public class Background {
    private Image background1;
    private Image background2;

    public Image getBackground1() {
        return background1;
    }

    public Image getBackground2() {
        return background2;
    }

    public Background() {
        background1 = new Image(new Texture("gfx/background.png"));
        background2 = new Image(new Texture("gfx/background.png"));
    }

    public void update() {
        background1.setHeight(Gdx.graphics.getHeight());
        background1.setWidth(Gdx.graphics.getWidth());

        background2.setHeight(Gdx.graphics.getHeight());
        background2.setWidth(Gdx.graphics.getWidth());
        //background.setPosition(background.getX()*Gdx.graphics.getDeltaTime()*1000000f, background.getY());
        background1.setPosition(background1.getX() - Settings.BACKGROUND_SPEED * Gdx.graphics.getDeltaTime(), 0f);
        background2.setPosition(background1.getX() + background2.getWidth(), 0f);

        if (background1.getX() < -(background1.getWidth())) {
            background1.setPosition(0f, 0f);
        }
    }
}
