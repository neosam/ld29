package com.gollersoft.ld29;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private SceneManager sceneManager;
	
	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        sceneManager = new SceneManager();
        final Scene moveAroundScene = new MoveAroundScene("moveScene");
        sceneManager.registerScene(moveAroundScene);
        final Scene endingScene = new EndingScreen("ending");
        sceneManager.registerScene(endingScene);
        sceneManager.setActiveScene("moveScene");
	}

	@Override
	public void render () {
		sceneManager.render();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        sceneManager.resize(width, height);
    }
}
