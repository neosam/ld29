package com.gollersoft.ld29;

/**
 * Created by neosam on 26.04.14.
 */
public interface Scene {
    public void create();
    public void render();
    public void dispose();
    public void resize(int width, int height);
    public void setSceneManager(SceneManager sceneManager);
    public String getName();
}
