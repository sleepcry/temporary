package com.ormgas.rokonpong.statemachine;

public interface PlayerActionInterface {
	public abstract boolean seek();
	public abstract boolean escape();
	public abstract boolean fight();
	public abstract boolean freewalk();
	
	public static enum Action{
		SEEK,ESCAPE,FIGHT,FREEWALK
	}
}
