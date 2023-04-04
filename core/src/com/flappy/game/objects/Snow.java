package com.flappy.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class Snow {
    public Image snowflake;

    public Snow() {
        List<Image> snowflakes = new ArrayList<>();
        snowflakes.add(new Image(new Texture("gfx/snow/snow1.png")));
        snowflakes.add(new Image(new Texture("gfx/snow/snow2.png")));
        snowflakes.add(new Image(new Texture("gfx/snow/snow3.png")));

        this.snowflake = snowflakes.get(MathUtils.random(2));
    }

    public Image getSnowflake() {
        return snowflake;
    }
}
