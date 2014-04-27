package com.gollersoft.ld29;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 26.04.14.
 */
public class Carbohydrate extends Living {
    private final Texture texture;

    public Carbohydrate(World world) {
        super(world);
        texture = new Texture("apple.png");
        body.getFixtureList().get(0).getShape().setRadius(0.35f);

    }

    @Override
    public void render(Batch batch) {
        batch.draw(texture, getX() - 0.25f, getY() - 0.25f, 0.5f, 0.5f);
    }
}
