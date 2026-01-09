package io.github.some_example_name.objects;

import static io.github.some_example_name.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameObject {
    public short cBits;
    //public  short mBits;
    public int width;
    public int height;
    public Texture texture;
    public Body body;
    protected  boolean isTouched;


    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, getX() - (width/2f), getY() -(height/2f), width, height );

    }
    GameObject(String texturePath, int x, int y, int width, int height, short cBits, /*short mBits,*/ World world){
        this.cBits = cBits;
        //this.mBits = mBits;
        this.width = width;
        this.height = height;

        texture = new Texture(texturePath);
        body = createBody(x, y, width, height, world);

    }
    public void hit(){

    }
    public void setTouched(boolean value){
        isTouched = value;
    }

    protected float getDensity(){
        return 0.1f;
    }

    private Body createBody(float x, float y, float width, float height, World world){

        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        PolygonShape  boxShape = new PolygonShape();
        boxShape.setAsBox(width * SCALE * 0.5f, height * SCALE * 0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = cBits;
        //fixtureDef.filter.maskBits = mBits;
        fixtureDef.shape = boxShape;
        fixtureDef.density = getDensity();
        fixtureDef.friction = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        boxShape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;

    }
    public void dispose(){
        texture.dispose();
    }
}
