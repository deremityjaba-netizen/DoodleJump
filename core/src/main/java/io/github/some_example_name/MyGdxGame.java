package io.github.some_example_name;

import static io.github.some_example_name.game.GameSettings.POSITION_ITERATIONS;
import static io.github.some_example_name.game.GameSettings.SCREEN_HEIGHT;
import static io.github.some_example_name.game.GameSettings.SCREEN_WIDTH;
import static io.github.some_example_name.game.GameSettings.STEP_TIME;
import static io.github.some_example_name.game.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import io.github.some_example_name.game.GameImages;
import io.github.some_example_name.managers.AudioManager;
import io.github.some_example_name.screens.GameScreen;
import io.github.some_example_name.screens.MenuScreen;
import io.github.some_example_name.screens.SettingsScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public Box2DDebugRenderer debugRenderer;
    public World world;
    float accumulator = 0;
    public Vector3 touch;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public SettingsScreen settingsScreen;
    public AudioManager audioManager;
    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;
    public BitmapFont largeWhiteFont;
    public OrthographicCamera camera;

    @Override
    public void create() {
        Box2D.init();
        largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameImages.FONT_PATH);
        commonBlackFont = FontBuilder.generate(30, Color.BLACK, GameImages.FONT_PATH);
        commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameImages.FONT_PATH);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        world = new World(new Vector2(0,-9.8f), true);
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        audioManager = new AudioManager();

        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);
        setScreen(menuScreen);

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
        debugRenderer.dispose();
    }
}
