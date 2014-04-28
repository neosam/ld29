package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by neosam on 27.04.14.
 */
public class IntroScene implements Scene {
    private BitmapFont font;
    private OrthographicCamera fontCamera;
    private SpriteBatch batch;
    private final String name;
    private SceneManager sceneManager;
    private Texture background;
    private int width, heigth;


    public IntroScene(String name) {
        this.name = name;
    }

    @Override
    public void create() {
        font = new BitmapFont();
        fontCamera = new OrthographicCamera(1000, 1000);
        batch = new SpriteBatch();
        background = new Texture("intro.png");

        Gdx.input.setInputProcessor(new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            sceneManager.setActiveScene("moveScene");

            return true;
        }
    });
}

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(fontCamera.combined);
        batch.begin();
        batch.draw(background, -500, -400, 1000, 800);
        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.heigth = height;
    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public String getName() {
        return name;
    }
}
