package io.github.some_example_name.game;

import com.badlogic.gdx.utils.TimeUtils;

import io.github.some_example_name.GameState;

public class GameSession {
    public GameState state;
    long pauseStartTime;
    long sessionStartTime;
    long nextTrashSpawnTime;


    public void startGame() {
        state = GameState.PLAYING;
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_PLATE_APPEARANCE_COOL_DOWN
            * getTrashPeriodCoolDown());

    }
    public void pauseGame(){
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame(){
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }


    public boolean shouldSpawnTrash(){
        if(nextTrashSpawnTime <= TimeUtils.millis()){
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_PLATE_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }



    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime) / 350);
    }
}
