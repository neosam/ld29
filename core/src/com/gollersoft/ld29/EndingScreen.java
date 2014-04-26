package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by neosam on 26.04.14.
 */
public class EndingScreen implements Scene {
    private BitmapFont font;
    private int alcoholCounter;
    private OrthographicCamera fontCamera;
    private SpriteBatch batch;
    private final String name;
    private SceneManager sceneManager;


    public EndingScreen(String name) {
        this.name = name;
    }

    @Override
    public void create() {
        font = new BitmapFont(new FileHandle("font.fnt"), new FileHandle("font.png"), false);
        fontCamera = new OrthographicCamera(1000, 1000);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(fontCamera.combined);
        batch.begin();
        font.draw(batch, String.format("You made beer with %.2f" +
                "%% alcohol!  Prost!", alcoholCounter / 10f), -350, 0);
        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAlcoholCounter() {
        return alcoholCounter;
    }

    public void setAlcoholCounter(int alcoholCounter) {
        this.alcoholCounter = alcoholCounter;
    }
}
