package io.github.some_example_name;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

public class PlateObject extends  GameObject{
    public PlateObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.PLATE_BIT, world);
        //body.setLinearVelocity(new Vector2(0, -GameSettings.PLATE_VELOSITY));
        body.setGravityScale(0);
    }

    @Override
    protected float getDensity() {
        return 0;
    }
}
