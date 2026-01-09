package io.github.some_example_name.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.game.GameImages;

public class LiveView extends View {
    Texture texture;
    public static int livePadding = 6;
    public String text;
    public int leftLives;

    public LiveView(float x, float y){
        super(x, y);
        texture = new Texture(GameImages.LIFE_IMG_PATH);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        leftLives = 0;
    }
    public void setLeftLives(int leftLives){
        this.leftLives = leftLives;
    }
    @Override
    public void draw(SpriteBatch batch){
        if(leftLives > 0)batch.draw(texture, x + (texture.getWidth() + livePadding), y, width, height);
        if(leftLives > 1) batch.draw(texture, x, y, width, height);
        if(leftLives > 2) batch.draw(texture, x + 2 * (texture.getWidth() + livePadding), y, width, height);
    }
}
