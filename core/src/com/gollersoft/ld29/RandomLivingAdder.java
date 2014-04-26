package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by neosam on 26.04.14.
 */
public class RandomLivingAdder {
    private final Class<Living> livingClass;
    private LivingManager livingManager;
    private World world;
    float possibility = 0.05f;

    public RandomLivingAdder(Class<Living> livingClass, LivingManager livingManager, World world) {
        this.livingClass = livingClass;
        this.livingManager = livingManager;
        this.world = world;
    }

    public void step() {
        if (Math.random() < possibility) {
            addNewLiving();
        }
    }

    private void addNewLiving() {
        try {
            final Constructor<Living> constructor = livingClass.getConstructor(World.class);
            final Living living = constructor.newInstance(world);
            livingManager.addLiving(living);
        } catch (Exception ex) {
            Gdx.app.error("RandomLivingAdder", "Could not invocate the constructor");
        }

    }
}
