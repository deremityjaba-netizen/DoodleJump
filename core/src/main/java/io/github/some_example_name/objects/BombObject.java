package io.github.some_example_name.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import io.github.some_example_name.game.GameSettings;

public class BombObject extends GameObject {
    private int livesLeft;
    public BombObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.BOMB_BIT,  world);
        body.setLinearVelocity(new Vector2(0, -GameSettings.PLATE_VELOSITY));
        body.setGravityScale(1);
        livesLeft = 1;

    }
    @Override
    protected float getDensity() {
        return  20;
    }
    public boolean isInFrame(){

        return  getY() + height / 2 > 0;
    }
    @Override
    public void hit(){
        livesLeft -= 1;
    }
    public boolean isAlive(){
        return livesLeft > 0;
    }
}
