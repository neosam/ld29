package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;

/**
 * Created by neosam on 26.04.14.
 */
public enum CollisionType {
    player, carbohydrate, alcohol, nothing;

    static CollisionType getCollisionType(Object userData) {
        if (userData == null) {
            return nothing;
        }
        if (userData instanceof Carbohydrate) {
            return carbohydrate;
        }
        if (userData instanceof Alcohol) {
            return alcohol;
        }
        if (userData instanceof Player) {
            return player;
        }
        return nothing;
    }
}
