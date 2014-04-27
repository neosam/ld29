package com.gollersoft.ld29;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 26.04.14.
 */
public class Player extends Living {
    private final Texture texture;
    private int points = 1000;

    public Player(World world) {
        super(world);
        texture = new Texture("hero.png");
        body.getFixtureList().get(0).getShape().setRadius(0.15f);
        body.getFixtureList().get(0).setDensity(2);
}

    public void ateCarbohydrate() {
        points += 80;
    }

    public void hitAlcohol() {
        points -= 750;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public void step() {
        super.step();
        points--;
    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, getX() - 0.25f, getY() - 0.25f, 0.5f, 0.5f);
    }
}
