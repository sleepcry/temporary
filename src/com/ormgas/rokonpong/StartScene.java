package com.ormgas.rokonpong;

import android.view.MotionEvent;

import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Time;
import com.stickycoding.rokon.background.FixedBackground;

public class StartScene extends Scene {
	private SceneHandler theSceneHandler;

	private boolean sceneDone = false;
	private float endTick = 0.0f;
	private Sprite spirit;

	public StartScene(SceneHandler sceneHandler) {
		super(1, 3);
		theSceneHandler = sceneHandler;
	}

	@Override
	public void onGameLoop() {
		// After 5 seconds, or on touch a sprite, go to next scene.
		// This aint working
		if (Time.getTicks() > endTick)
			sceneDone = true;

		if (sceneDone)
			theSceneHandler.SetScene("GameScene");
	}

	@Override
	public void onReady() {
		this.setBackground(new FixedBackground(Textures.splashSpirit));
		
		endTick = Time.getTicks() + 5;
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onTouchUp(float x, float y, MotionEvent event,
			int pointerCount, int pointerId) {
		sceneDone = true;
	}

}
