package io.github.some_example_name;

import static io.github.some_example_name.GameSettings.POSITION_ITERATIONS;
import static io.github.some_example_name.GameSettings.SCREEN_HEIGHT;
import static io.github.some_example_name.GameSettings.SCREEN_WIDTH;
import static io.github.some_example_name.GameSettings.STEP_TIME;
import static io.github.some_example_name.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public World world;
    float accumulator = 0;
    public Vector3 touch ;
    public GameScreen gameScreen;
    public OrthographicCamera camera;

    @Override
    public void create() {
        Box2D.init();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        world = new World(new Vector2(0,-9.8f), true);
        batch = new SpriteBatch();

        gameScreen = new GameScreen(this);
        setScreen(gameScreen);

    }
    public  void stepWorld(){
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += delta;

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }



    @Override
    public void dispose() {
        batch.dispose();

    }
}
