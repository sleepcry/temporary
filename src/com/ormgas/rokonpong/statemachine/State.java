package com.ormgas.rokonpong.statemachine;

import com.ormgas.rokonpong.GamePlayer;
import com.stickycoding.rokon.Updateable;

public abstract class State implements Updateable{
	GamePlayer character;
	StateMachine sm;
	
	protected State(GamePlayer character,StateMachine sm) {
		this.character = character;
		this.sm = sm;
	}
	public abstract void changeState(CharacterState newState);
	
}
