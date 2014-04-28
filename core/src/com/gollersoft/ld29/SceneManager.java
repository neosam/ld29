package com.gollersoft.ld29;
import com.badlogic.gdx.Gdx;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Stores and manages the scenes.
 *
 * Can switch between scenes and will redirect the render events
 * to the active scene.
 * Created by neosam on 26.04.14.
 */
public class SceneManager {
    private final AbstractMap<String, Scene> sceneMap = new HashMap<String, Scene>();
    private Scene activeScene;
    private int width, height;

    public SceneManager() {
    }

    /**
     * Add new scene.
     * @param scene
     */
    public void registerScene(Scene scene) {
        scene.setSceneManager(this);
        sceneMap.put(scene.getName(), scene);
    }

    /**
     * Activate scene by its name.
     * @param name
     */
    public void setActiveScene(String name) {
        final Scene scene = sceneMap.get(name);
        Gdx.app.log("SceneManager", "Starting new scene: " + name);
            if (scene == null) {
            Gdx.app.error("SceneManager", "Could not load scene " + name);
            return;
        }
        if (activeScene != null) {
            activeScene.dispose();
        }
        activeScene = scene;
        scene.create();
        scene.resize(width, height);
    }

    public void render() {
        activeScene.render();
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        activeScene.resize(width, height);
    }

    public Scene getScene(String name) {
        return sceneMap.get(name);
    }
}
