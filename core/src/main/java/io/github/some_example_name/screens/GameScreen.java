package io.github.some_example_name.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

import io.github.some_example_name.GameState;
import io.github.some_example_name.managers.ContactManager;
import io.github.some_example_name.objects.DoodleObject;
import io.github.some_example_name.game.ForceSource;
import io.github.some_example_name.game.GameImages;
import io.github.some_example_name.MyGdxGame;
import io.github.some_example_name.game.GameSession;
import io.github.some_example_name.game.GameSettings;
import io.github.some_example_name.objects.PlateObject;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    GameSession gameSession;
    ForceSource forceSource;
    DoodleObject doodleObject;
    ArrayList<PlateObject> plateArray;


    ContactManager contactManager;
    public GameScreen(MyGdxGame myGdxGame){
        gameSession = new GameSession();
        doodleObject = new DoodleObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 4 / 5,
            40, 50, GameImages.DOODLE_PNG_PATH, myGdxGame.world);
        plateArray = new ArrayList<>();
        plateArray.add(  new PlateObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 4 / 5,
            200, 10, GameImages.PLATE_PNG_PATH, myGdxGame.world));
        contactManager = new ContactManager(myGdxGame.world);
        forceSource = new ForceSource(1, 4, 1);
        this.myGdxGame = myGdxGame;

    }
    @Override
    public void show() {
        gameSession.startGame();
    }
    @Override
    public void render(float delta){
        handleInput();
        if (gameSession.state == GameState.PLAYING) {

            if (gameSession.shouldSpawnTrash()) {
                PlateObject plateObject = new PlateObject(100 / 2 + GameSettings.PADDING_HORISONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORISONTAL - 100)),
                    GameSettings.SCREEN_HEIGHT + 10/ 2,
                    100, 10, GameImages.PLATE_PNG_PATH, myGdxGame.world);
                plateArray.add(plateObject);
            }
            updateTrash();
        }
        forceSource.update();
        myGdxGame.stepWorld();

        draw();
    }


    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);


        myGdxGame.batch.begin();
        doodleObject.draw(myGdxGame.batch);
        for (PlateObject plate : plateArray) plate.draw(myGdxGame.batch);

        myGdxGame.debugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);
        myGdxGame.batch.end();

    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        } else {
            if (doodleObject.needToJump()) {

                doodleObject.jump(7 + forceSource.getForce());
                System.out.println(forceSource.getForce());
            }
        }


    }

    private void updateTrash() {
        for (int i = 0; i < plateArray.size(); i++) {
            boolean hasToBeDestroyed =  !plateArray.get(i).isInFrame();


            if (hasToBeDestroyed) {
                System.out.println("puwiehpf;iu");
                myGdxGame.world.destroyBody(plateArray.get(i).body);
                plateArray.remove(i--);
            }
        }
    }
}

