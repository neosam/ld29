package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by neosam on 26.04.14.
 */
public class LivingManager {
    private final AbstractMap<String, Living> livingMap = new HashMap<String, Living>();
    private final Set<Living> livingSet = new HashSet<Living>();
    private final Set<Living> removeNextStep = new HashSet<Living>();


    public void addLiving(String name, Living living) {
        if (name != null) {
            livingMap.put(name, living);
        }
        if (living == null) {
            Gdx.app.error("LivingManager", "addLiving living object was null!");
            return;
        }
        livingSet.add(living);
    }

    public void addLiving(Living living) {
        addLiving(null, living);
    }

    public Living removeLiving(String name) {
        if (!livingMap.containsKey(name)) {
            Gdx.app.error("LivingManager", "Key " + name + " not found in living map");
            return null;
        }
        final Living living = livingMap.remove(name);
        livingSet.remove(living);
        living.remove();
        return living;
    }

    public Living removeLiving(Living living) {
        livingSet.remove(living);
        living.remove();
        /* Map has a memory leak now... */
        return living;
    }

    public void markToRemove(Living living) {
        removeNextStep.add(living);
    }

    public void step() {
        for (Living living: removeNextStep) {
            removeLiving(living);
        }
        for (Living living: livingSet) {
            living.step();
        }
    }

    public void render() {
        for (Living living: livingSet) {
            living.render();
        }
    }
}
