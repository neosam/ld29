package com.gollersoft.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by neosam on 26.04.14.
 */
public class Living {
    private Body body;

    private boolean moveRight = false,
            moveLeft = false,
            moveUp = false,
            moveDown = false;

    private float maxVelocity = 6;
    private float moveAcceleration = 160;

    public Living(World world) {
        initializeBody(world);
    }

    /**
     * Inititalizes the body with a circle shape and default values.
     * @param world
     */
    void initializeBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(0.25f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);
        body.setLinearDamping(maxVelocity);
        circle.dispose();
    }

    private void doMovement() {
        if (moveRight) {
            body.applyForceToCenter(moveAcceleration * body.getMass(), 0, true);
        }
        if (moveLeft) {
            body.applyForceToCenter(-moveAcceleration * body.getMass(), 0, true);
        }
        if (moveUp) {
            body.applyForceToCenter(0, -moveAcceleration * body.getMass(), true);
        }
        if (moveDown) {
            body.applyForceToCenter(0, moveAcceleration * body.getMass(), true);
        }
    }


    private void doVelocityMaxCheck() {
        final float velX = body.getLinearVelocity().x;
        final float velY = body.getLinearVelocity().y;
        float finalVelX = Math.min(velX, maxVelocity);
        float finalVelY = Math.min(velY, maxVelocity);
        finalVelX = Math.max(finalVelX, -maxVelocity);
        finalVelY = Math.max(finalVelY, -maxVelocity);
        body.setLinearVelocity(finalVelX, finalVelY);
    }

    public void step() {
        doMovement();
        doVelocityMaxCheck();
    }


    public void render() {

    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }

    float getX() {
        return body.getPosition().x;
    }

    float getY() {
        return body.getPosition().y;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public float getMoveAcceleration() {
        return moveAcceleration;
    }

    public void setMoveAcceleration(float moveAcceleration) {
        this.moveAcceleration = moveAcceleration;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }
}
