package com.ormgas.rokonpong;

import java.nio.FloatBuffer;

import com.ormgas.rokonpong.status.PlayerStatus;
import com.stickycoding.rokon.GameObject;

public abstract class ProgressBar extends GameObject implements StatusMoniter{
	float maxValue,currentVal;
	float maxWidth;
	public ProgressBar(float x, float y, float width, float height,float maxValue,float currentVal) {
		super(x, y, width, height);
		this.maxValue = maxValue;
		this.currentVal = currentVal;
		maxWidth = width;
		this.setWidth(maxWidth*currentVal/maxValue);
	}
	public void setVal(float currentVal) {
		this.currentVal = currentVal;
		if(currentVal >= maxValue) {
			currentVal = maxValue;
		}
		if(currentVal < 0) {
			currentVal = 0;
		}
	}
	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
	public void onUpdate() {
		super.onUpdate();
		float ratio = currentVal/maxValue;
		float r,g,b;
		r = 1-ratio;
		g = ratio;
		b = r*g;
		setRGB(r,g,b);
		this.setWidth(maxWidth*currentVal/maxValue);
	}

}
