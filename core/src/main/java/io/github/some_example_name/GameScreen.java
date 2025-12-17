package io.github.some_example_name;

import static com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    public GameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
        myGdxGame.stepWorld();
        draw();
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        myGdxGame.batch.end();
    }
}
