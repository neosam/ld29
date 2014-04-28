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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by neosam on 26.04.14.
 */
public class MoveAroundScene implements Scene {
    private World world;
    private Player player;
    private OrthographicCamera camera;
    private OrthographicCamera fontCamera;
    private float cameraSize = 10f;
    private float borderSize = 0.25f;
    private String name;
    private Box2DDebugRenderer debugRenderer;
    private LivingManager livingManager;
    private RandomLivingAdder randomCarbohydrateAdder;
    private RandomLivingAdder randomAlcoholAdder;
    private BitmapFont font;
    private SpriteBatch batch;
    private int alcoholContent = 0;
    private SceneManager sceneManager;
    private Texture background;
    private int width, height;

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
        randomCarbohydrateAdder = new RandomLivingAdder(Carbohydrate.class, livingManager, world, cameraSize, borderSize);
        randomAlcoholAdder = new RandomLivingAdder(Alcohol.class, livingManager, world, cameraSize, borderSize);
        randomAlcoholAdder.possibility = randomCarbohydrateAdder.possibility / 5;
        randomAlcoholAdder.setRandomLivingAdderCallback(new RandomLivingAdderCallback() {
            @Override
            public void inserted() {
                alcoholContent++;
            }
        });

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        fontCamera = new OrthographicCamera(1000, 1000);
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        addBorderTop();
        addBorderBottom();
        addBorderLeft();
        addBorderRight();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                    case Input.Keys.A:
                        player.setMoveLeft(true);
                        return true;
                    case Input.Keys.RIGHT:
                    case Input.Keys.D:
                        player.setMoveRight(true);
                        return true;
                    case Input.Keys.UP:
                    case Input.Keys.W:
                        player.setMoveUp(true);
                        return true;
                    case Input.Keys.DOWN:
                    case Input.Keys.S:
                        player.setMoveDown(true);
                        return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                    case Input.Keys.A:
                        player.setMoveLeft(false);
                        return true;
                    case Input.Keys.RIGHT:
                    case Input.Keys.D:
                        player.setMoveRight(false);
                        return true;
                    case Input.Keys.UP:
                    case Input.Keys.W:
                        player.setMoveUp(false);
                        return true;
                    case Input.Keys.DOWN:
                    case Input.Keys.S:
                        player.setMoveDown(false);
                        return true;
                }
                return false;
            }
        });

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                final Object userData1 = contact.getFixtureA().getBody().getUserData();
                final Object userData2 = contact.getFixtureB().getBody().getUserData();
                final CollisionType collision1 = CollisionType.getCollisionType(userData1);
                final CollisionType collision2 = CollisionType.getCollisionType(userData2);
                
                if (collision1 != CollisionType.player && collision2 != CollisionType.player) {
                    return;
                }
                final CollisionType collision;
                final Living living;
                final Player player;
                if (collision1 == CollisionType.player) {
                    collision = collision2;
                    living = (Living) userData2;
                    player = (Player) userData1;
                } else {
                    collision = collision1;
                    living = (Living) userData1;
                    player = (Player) userData2;
                }

                if (collision == CollisionType.carbohydrate) {
                    livingManager.markToRemove(living);
                    player.ateCarbohydrate();
                }
                if (collision == CollisionType.alcohol) {
                    livingManager.markToRemove(living);
                    player.hitAlcohol();
                    alcoholContent--;
                    MoveAroundScene.this.looseCheck();
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private void looseCheck() {
        if (player.getPoints() < 0) {
            final EndingScreen endlingScene = (EndingScreen) sceneManager.getScene("ending");
            endlingScene.setAlcoholCounter(alcoholContent);
            sceneManager.setActiveScene("ending");
        }
    }

    private void addBorderBottom() {
        BodyDef borderBodyDef = new BodyDef();
        borderBodyDef.position.set(new Vector2(0, cameraSize / 2));
        Body groundBody = world.createBody(borderBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(cameraSize, borderSize);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }

    private void addBorderTop() {
        BodyDef borderBodyDef = new BodyDef();
        borderBodyDef.position.set(new Vector2(0, -cameraSize / 2));
        Body groundBody = world.createBody(borderBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(cameraSize, borderSize);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }

    private void addBorderLeft() {
        BodyDef borderBodyDef = new BodyDef();
        borderBodyDef.position.set(new Vector2(-cameraSize / 2, 0));
        Body groundBody = world.createBody(borderBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(borderSize, cameraSize);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }

    private void addBorderRight() {
        BodyDef borderBodyDef = new BodyDef();
        borderBodyDef.position.set(new Vector2(cameraSize / 2, 0));
        Body groundBody = world.createBody(borderBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(borderSize, cameraSize);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        looseCheck();
        livingManager.step();
        randomCarbohydrateAdder.step();
        randomAlcoholAdder.step();
        world.step(1 / 60f, 6, 2);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(background, cameraSize / 2, cameraSize / 2, cameraSize, cameraSize);
        batch.draw(background, -cameraSize / 2 * 2200 / 1080, -cameraSize / 2, cameraSize * 2200 / 1080, cameraSize);
        livingManager.render(batch);
        batch.end();

        batch.setProjectionMatrix(fontCamera.combined);
        batch.begin();
        font.draw(batch, player.getPoints() + "", 400, 450);
        font.draw(batch, String.format("%.2f %%", alcoholContent / 5f), -400, 450);
        batch.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug("MoveAroundScene", "resize");
        camera.setToOrtho(false, cameraSize * width / height, cameraSize);
        camera.position.x = 0;
        camera.position.y = 0;
        camera.update();
        this.width = width;
        this.height = height;
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
