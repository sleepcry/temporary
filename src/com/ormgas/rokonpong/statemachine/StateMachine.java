package com.ormgas.rokonpong.statemachine;

import java.util.HashMap;

import com.ormgas.rokonpong.GamePlayer;
import com.stickycoding.rokon.Updateable;

public class StateMachine implements Updateable{

	State currentState;
	
	HashMap<CharacterState,State> stateMap = new HashMap<CharacterState,State>();
	
	protected StateMachine(GamePlayer character) {
		stateMap.put(CharacterState.REST, new StateRest(character,this));
		stateMap.put(CharacterState.ATTACK, new StateAttack(character,this));
		stateMap.put(CharacterState.MOVEAWAY, new StateMoveAway(character,this));
		stateMap.put(CharacterState.MOVECLOSE, new StateMoveClose(character,this));
		stateMap.put(CharacterState.FREEMOVE, new StateFreeMove(character,this));
		changeTo(CharacterState.REST);
	}
	
	public static StateMachine getStateMachine(GamePlayer character) {
		return new StateMachine(character);
	}
	@Override
	public void onUpdate() {
		currentState.onUpdate();		
	}
	
	public void changeState(CharacterState newState) {
		currentState.changeState(newState);		
	}
	protected void changeTo(CharacterState newState) {
		currentState = stateMap.get(newState);	
	}
	
}
