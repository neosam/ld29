package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by neosam on 26.04.14.
 */
public class RandomLivingAdder {
    private final Class livingClass;
    private LivingManager livingManager;
    private World world;
    float possibility = 0.02f;
    private float cameraSize;
    private float borderSize;
    private RandomLivingAdderCallback randomLivingAdderCallback;

    public RandomLivingAdder(Class livingClass, LivingManager livingManager, World world,
                             float cameraSize, float borderSize) {
        this.livingClass = livingClass;
        this.livingManager = livingManager;
        this.world = world;
        this.cameraSize = cameraSize;
        this.borderSize = borderSize;
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
            final float minX = (-cameraSize + borderSize) / 2;
            final float maxX = -minX;
            final float minY = minX;
            final float maxY = maxX;
            final float posX = (float) (Math.random() * (maxX - minX) + minX);
            final float posY = (float) (Math.random() * (maxY - minY) + minY);
            living.setPosition(posX, posY);
            livingManager.addLiving(living);
            if (randomLivingAdderCallback != null) {
                randomLivingAdderCallback.inserted();
            }
        } catch (Exception ex) {
            Gdx.app.error("RandomLivingAdder", "Could not invocate the constructor");
        }

    }

    public RandomLivingAdderCallback getRandomLivingAdderCallback() {
        return randomLivingAdderCallback;
    }

    public void setRandomLivingAdderCallback(RandomLivingAdderCallback randomLivingAdderCallback) {
        this.randomLivingAdderCallback = randomLivingAdderCallback;
    }
}
