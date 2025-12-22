package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class DoodleObject extends  GameObject{
    private int livesLeft;
    long lastJumpTime;

    public DoodleObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.DOODLE_BIT, world);
        //body.setLinearDamping(10);
        livesLeft = 3;
    }
    public boolean needToJump(){
        if(isTouched && TimeUtils.millis() - lastJumpTime >= GameSettings.JUMPING_COOL_DOWN){
            lastJumpTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    public void jump(float force) {
        body.applyLinearImpulse(new Vector2(0, force), new Vector2(getX(),getY()), true);

    }
    public void putInFrame(){


        if(getX() < (-width / 2f)){
            setX(GameSettings.SCREEN_WIDTH);
        }
        if(getX() > (GameSettings.SCREEN_WIDTH + width / 2f)){
            setX(0);
        }

    }
    @Override
    public  void draw(SpriteBatch batch){
        putInFrame();
        super.draw(batch);
    }
    public void move(Vector3 vector3){
        float fx = (vector3.x - getX()) * GameSettings.DOODLE_FORCE_RATIO;
        float fy = (vector3.y - getY()) * GameSettings.DOODLE_FORCE_RATIO;

        body.applyForceToCenter(
            new Vector2(
                (vector3.x - getX()) * GameSettings.DOODLE_FORCE_RATIO,
                (vector3.y - getY()) * GameSettings.DOODLE_FORCE_RATIO
            ),
            true
        );

    }

    @Override
    public void hit(){
        if(getY() <= (height / 2f)){
            livesLeft -= 3;
        }
    }
    public boolean isAlive(){
        return livesLeft > 0;
    }
    public int getLivesLeft(){
        return livesLeft;
    }
}
