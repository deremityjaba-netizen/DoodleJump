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
import io.github.some_example_name.managers.MemoryManager;
import io.github.some_example_name.objects.DoodleObject;
import io.github.some_example_name.game.ForceSource;
import io.github.some_example_name.game.GameImages;
import io.github.some_example_name.MyGdxGame;
import io.github.some_example_name.game.GameSession;
import io.github.some_example_name.game.GameSettings;
import io.github.some_example_name.objects.PlateObject;
import io.github.some_example_name.view.ButtonView;
import io.github.some_example_name.view.ImageView;
import io.github.some_example_name.view.MovingBackgroundView;
import io.github.some_example_name.view.RecordListViews;
import io.github.some_example_name.view.TextView;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    GameSession gameSession;
    MovingBackgroundView backgroundView;
    TextView forceText;
    ForceSource forceSource;
    ButtonView continueButton;
    ButtonView pauseButton;
    ButtonView homeButton;
    TextView pauseTextView;
    TextView recordsTextView;
    RecordListViews recordsListView;
    ButtonView homeButton2;
    TextView scoreTextView;
    ImageView fullBlackoutView;
    DoodleObject doodleObject;
    ArrayList<PlateObject> plateArray;


    ContactManager contactManager;

    public GameScreen(MyGdxGame myGdxGame) {
        gameSession = new GameSession();


        doodleObject = new DoodleObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 4 / 5,
            40, 50, GameImages.DOODLE_PNG_PATH, myGdxGame.world);
        plateArray = new ArrayList<>();
        plateArray.add(new PlateObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 4 / 5,
            200, 20, GameImages.PLATE_PNG_PATH, myGdxGame.world));


        contactManager = new ContactManager(myGdxGame.world);
        forceSource = new ForceSource(1, 4, 1);

        forceText = new TextView(myGdxGame.commonBlackFont, 305, 1215);
        scoreTextView = new TextView(myGdxGame.commonBlackFont, 50, 1215);
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, 282, 842, "Pause");
        recordsListView = new RecordListViews(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");

        fullBlackoutView = new ImageView(0, 0, GameImages.FULL_BLACK_BG_IMG_PATH);
        backgroundView = new MovingBackgroundView(GameImages.BACKGROUND_IMG_PATH);

        pauseButton = new ButtonView(605, 1200,
            46, 54,
            GameImages.PAUSE_ICON_IMG_PATH);
        homeButton = new ButtonView(
            138, 695,
            200, 70,
            myGdxGame.commonBlackFont,
            GameImages.BUTTON_SHORT_IMG_PATH,
            "Home"
        );
        homeButton2 = new ButtonView(
            280, 365,
            160, 70,
            myGdxGame.commonBlackFont,
            GameImages.BUTTON_SHORT_IMG_PATH,
            "Home"
        );
        continueButton = new ButtonView(
            393, 695,
            200, 70,
            myGdxGame.commonBlackFont,
            GameImages.BUTTON_SHORT_IMG_PATH,
            "Continue"
        );
        this.myGdxGame = myGdxGame;

    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {
        handleInput();
        if (gameSession.state == GameState.PLAYING) {

            if (gameSession.shouldSpawnTrash()) {
                PlateObject plateObject = new PlateObject(100 / 2 + GameSettings.PADDING_HORISONTAL + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * GameSettings.PADDING_HORISONTAL - 100)),
                    GameSettings.SCREEN_HEIGHT + 10 / 2,
                    100, 10, GameImages.PLATE_PNG_PATH, myGdxGame.world);
                plateArray.add(plateObject);
            }
            //forceText.setText("Force: " + forceSource.getForce());
            updateTrash();
            if (doodleObject.needToJump()) {

                doodleObject.jump(8);
                if (myGdxGame.audioManager.isSoundOn) {
                    myGdxGame.audioManager.shootSound.play(0.2f);
                }
                System.out.println(forceSource.getForce());
            }
            if (!doodleObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
            forceSource.update();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            backgroundView.move();
            myGdxGame.stepWorld();
        }


        draw();
    }


    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);


        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);

        //forceText.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);

        doodleObject.draw(myGdxGame.batch);
        for (PlateObject plate : plateArray) plate.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);

        //myGdxGame.debugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        }else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
        myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        switch (gameSession.state) {
            case PLAYING:
                if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    gameSession.pauseGame();
                }
                        doodleObject.move(myGdxGame.touch);


                    break;
            case PAUSED:
                if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    gameSession.resumeGame();
                }
                if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                }

            break;
            case ENDED:

                if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                }
                break;
        }
        }
    }

    private void updateTrash() {
        for (int i = 0; i < plateArray.size(); i++) {
            boolean hasToBeDestroyed = !plateArray.get(i).isInFrame();


            if (hasToBeDestroyed) {
                System.out.println("puwiehpf;iu");
                myGdxGame.world.destroyBody(plateArray.get(i).body);
                plateArray.remove(i--);
            }
        }

    }
    private void restartGame() {

        for (int i = 0; i < plateArray.size(); i++) {
            myGdxGame.world.destroyBody(plateArray.get(i).body);
            plateArray.remove(i--);
        }


        if (doodleObject != null) {
            myGdxGame.world.destroyBody(doodleObject.body);
        }

        doodleObject = new DoodleObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 9 / 10,
            40, 50, GameImages.DOODLE_PNG_PATH, myGdxGame.world);

        plateArray.add(new PlateObject(
            GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT * 9 / 10,
            200, 20, GameImages.PLATE_PNG_PATH, myGdxGame.world));

        gameSession.startGame();
    }
}
