package com.ormgas.rokonpong;

import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;
import android.view.Display;

public class MainActivity extends RokonActivity
{
	public static final float screenWidth = 16.0f;
	public static final float screenHeight = 9.6f;
	public static float scaleFactorX = 0.0f;
	public static float scaleFactorY = 0.0f;
	
	private static SceneHandler sceneHandler;

    /** Called when the activity is first created. */
    public void onCreate()
    { 
    	debugMode();
    	forceFullscreen();
    	forceLandscape();
    	
       	Display display = getWindowManager().getDefaultDisplay();
    	scaleFactorX = screenWidth / display.getWidth();
    	scaleFactorY = screenHeight / display.getHeight();
    	
    	this.setGameSize(screenWidth, screenHeight);
    	setDrawPriority(DrawPriority.PRIORITY_NORMAL);
    	setGraphicsPath("textures/");
    	createEngine();
    }
    
    public void onLoadComplete()
    {
    	Textures.load();
    	Sounds.load();
    	
    	CreateScenes();
    }
    public static final float calcWidth(int width) {
    	return width* scaleFactorX;
    }
    public static final float calcHeight(int height) {
    	return height* scaleFactorY;
    }
    private void CreateScenes()
    {
    	sceneHandler = new SceneHandler(this);

    	sceneHandler.AddScene("StartScene", new StartScene(sceneHandler));
        sceneHandler.AddScene("GameScene", new GameScene(sceneHandler));

        sceneHandler.SetScene("StartScene");
    }
}