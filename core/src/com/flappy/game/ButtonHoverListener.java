package com.flappy.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class ButtonHoverListener extends ClickListener { //This is a listener that enlarges buttons on hover
    private Button button;

    public ButtonHoverListener(Button button){
        this.button = button;
    }


    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
        button.setTransform(true);
        button.scaleBy(0.075f);
        button.setOrigin(Align.center);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
        button.setTransform(true);
        button.scaleBy(-0.075f);
        button.setOrigin(Align.center);
    }
}
