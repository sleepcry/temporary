package com.ormgas.rokonpong.statemachine;

import java.util.ArrayList;
import java.util.Collections;

import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.statemachine.PlayerActionInterface.Action;
import com.ormgas.rokonpong.status.PlayerStatus;
import com.stickycoding.rokon.Updateable;

public abstract class State implements Updateable{
	GamePlayer player;
	StateMachine sm;
	ArrayList<PlayerAction> lstActions = new ArrayList<PlayerAction>(); 
	
	protected State(GamePlayer character,StateMachine sm) {
		this.player = character;
		this.sm = sm;
	}
	
	public abstract void changeState(CharacterState newState);
	
	public void addAction(Action a, int priority,boolean repeat) {
		for(int i=0;i<lstActions.size();i++) {
			if(a == lstActions.get(i).action) {
				lstActions.get(i).priority = priority;
				lstActions.get(i).repeat = repeat;
				return;
			}
		}
		lstActions.add(new PlayerAction(a,priority,player,repeat));
	}
	public void addPriority(Action a,int d) {
		for(int i=0;i<lstActions.size();i++) {
			if(a == lstActions.get(i).action) {
				lstActions.get(i).addPriority(d);
				return;
			}
		}
	}
	public void minusPriority(Action a,int d) {
		for(int i=0;i<lstActions.size();i++) {
			if(a == lstActions.get(i).action) {
				lstActions.get(i).minusPriority(d);
				return;
			}
		}
	}
	public void sort() {
		Collections.sort(lstActions);
	}
	public boolean removeAction(PlayerAction a) {
		return lstActions.remove(a);
	}
	public void makeHighPriority(PlayerAction a) {
		if(!lstActions.contains(a)) {
			lstActions.add(a);
		}
		a.addPriority(PlayerAction.MAX_PRORITY);
	}
	public void makeLowPriority(PlayerAction a) {
		if(!lstActions.contains(a)) {
			lstActions.add(a);
		}
		a.addPriority(PlayerAction.MIN_PRORITY);
	}
	public void onUpdate() {
		action();
		update();
	}
	protected abstract void action();		
	protected abstract void update();
	protected boolean attacked() {
		return (player.getStatus().status&PlayerStatus.MASK_ATTACKED) == PlayerStatus.MASK_ATTACKED;
	}
	
}
