package com.gollersoft.ld29;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 26.04.14.
 */
public class Player extends Living {
    private int points = 1000;

    public Player(World world) {
        super(world);
    }

    public void ateCarbohydrate() {
        points += 80;
    }

    public void hitAlcohol() {
        points -= 1000;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public void step() {
        super.step();
        points--;
    }
}
