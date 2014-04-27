package com.gollersoft.ld29;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 26.04.14.
 */
public class Alcohol extends Living {
    private final Texture texture;

    public Alcohol(World world) {
        super(world);
        texture = new Texture("skull.png");
        body.getFixtureList().get(0).getShape().setRadius(0.2f);


    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, getX() - 0.25f, getY() - 0.25f, 0.5f, 0.5f);
    }
}
