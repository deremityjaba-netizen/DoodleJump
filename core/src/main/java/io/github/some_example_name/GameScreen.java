package io.github.some_example_name;

import static com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    DoodleObject doodleObject;
    PlateObject plateObject;
    ContactManager contactManager;
    public GameScreen(MyGdxGame myGdxGame){
        doodleObject = new DoodleObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 2 / 3,
            40, 50, GameImages.DOODLE_PNG_PATH, myGdxGame.world);
        plateObject = new PlateObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT / 3,
            200, 10, GameImages.PLATE_PNG_PATH, myGdxGame.world);
        contactManager = new ContactManager(myGdxGame.world);
        this.myGdxGame = myGdxGame;

    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
        handleInput();
        myGdxGame.stepWorld();

        draw();
    }


    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        doodleObject.draw(myGdxGame.batch);
            plateObject.draw(myGdxGame.batch);

        myGdxGame.batch.end();

    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        } else {
            if (doodleObject.needToJump()) {
                doodleObject.jump(10);
            }
        }


    }
}

