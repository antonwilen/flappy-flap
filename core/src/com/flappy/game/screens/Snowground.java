package com.flappy.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class Snowground {
    private final Image background1;
    private final Image background2;

    public Image getSnowground1() {
        return background1;
    }

    public Image getSnowground2() {
        return background2;
    }



    public Snowground(String fileLocation) {
        background1 = new Image(new Texture(fileLocation));
        background2 = new Image(new Texture(fileLocation));
        background1.setScaling(Scaling.fill);
        background2.setScaling(Scaling.fill);
    }

    public void update(float speed) {
        background1.setHeight(Gdx.graphics.getHeight());
        background1.setWidth(Gdx.graphics.getWidth());

        background2.setHeight(Gdx.graphics.getHeight());
        background2.setWidth(Gdx.graphics.getWidth());
        //background.setPosition(background.getX()*Gdx.graphics.getDeltaTime()*1000000f, background.getY());
        background1.setPosition( 0f,background1.getY() - speed * Gdx.graphics.getDeltaTime());
        background2.setPosition(0f, background1.getY() + background2.getHeight());

        if (background1.getY() < -(background1.getHeight())) {
            background1.setPosition(0f, 0f);
        }
    }
}
