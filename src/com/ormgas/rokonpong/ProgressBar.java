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
	}
	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
	public void onUpdate() {
		super.onUpdate();
		float ratio = currentVal/maxValue;
		float r,g,b;
		if(ratio > 0.7f) {
			//green
			r = 0.0f;
			g = 1.0f;
			b = 0.0f;
		}else if(ratio >0.3f) {
			//yellow
			r = 1.0f;
			g = 0.7f;
			b = 0.0f;
		}else {
			//red
			r = 1.0f;
			g = 0.0f;
			b = 0.0f;		
		}
		setRGB(r,g,b);
		this.setWidth(maxWidth*currentVal/maxValue);
	}

}
