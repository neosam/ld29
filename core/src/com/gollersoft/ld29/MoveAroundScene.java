package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by neosam on 26.04.14.
 */
public class MoveAroundScene implements Scene {
    private World world;
    private Player player;
    private OrthographicCamera camera;
    private float cameraSize = 10f;
    private String name;
    private Box2DDebugRenderer debugRenderer;
    private LivingManager livingManager;
    private RandomLivingAdder randomLivingAdder;

    public MoveAroundScene(String name) {
        this.name = name;
    }

    @Override
    public void create() {
        Gdx.app.debug("MoveAroundScene", "create");
        world = new World(new Vector2(0, 0), true);
        player = new Player(world);
        camera = new OrthographicCamera();
        debugRenderer = new Box2DDebugRenderer();
        livingManager = new LivingManager();
        livingManager.addLiving("player", player);
        randomLivingAdder = new RandomLivingAdder(Living.class, livingManager, world);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        player.setMoveLeft(true);
                        return true;
                    case Input.Keys.RIGHT:
                        player.setMoveRight(true);
                        return true;
                    case Input.Keys.UP:
                        player.setMoveUp(true);
                        return true;
                    case Input.Keys.DOWN:
                        player.setMoveDown(true);
                        return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        player.setMoveLeft(false);
                        return true;
                    case Input.Keys.RIGHT:
                        player.setMoveRight(false);
                        return true;
                    case Input.Keys.UP:
                        player.setMoveUp(false);
                        return true;
                    case Input.Keys.DOWN:
                        player.setMoveDown(false);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        livingManager.step();
        randomLivingAdder.step();
        world.step(1/60f, 6, 2);
        livingManager.render();
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug("MoveAroundScene", "resize");
        camera.setToOrtho(true, cameraSize, cameraSize * height / width);
        camera.position.x = 0;
        camera.position.y = 0;
        camera.update();
    }

    @Override
    public String getName() {
        return name;
    }
}
