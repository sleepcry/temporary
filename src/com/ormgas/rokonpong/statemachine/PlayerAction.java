package com.ormgas.rokonpong.statemachine;

import com.ormgas.rokonpong.statemachine.PlayerActionInterface.Action;

public class PlayerAction implements Comparable<PlayerAction>{
	public static final int MAX_PRORITY = 100;
	public static final int MIN_PRORITY = 0;
	final Action action;
	PlayerActionInterface actionObj;
	int priority;
	boolean repeat = false;
	
	public PlayerAction(Action a, int priority,PlayerActionInterface obj,boolean repeatable) {
		action = a;
		this.priority = priority;
		if(priority <= MIN_PRORITY) {
			priority = MIN_PRORITY;
		}
		if(priority >= MAX_PRORITY) {
			priority = MAX_PRORITY;
		}
		actionObj = obj;
		repeat = repeatable;
	}
	
	public void addPriority(int d) {
		priority += d;
		if(priority >= MAX_PRORITY) {
			priority = MAX_PRORITY;
		}
	}
	public void minusPriority(int d) {
		priority -= d;
		if(priority <= MIN_PRORITY) {
			priority = MIN_PRORITY;
		}
	}
	@Override
	public int compareTo(PlayerAction another) {
		if(priority > another.priority) {
			return 1;
		}else if(priority < another.priority) {
			return -1;
		}else {
			return 0;
		}
	}
	public boolean run() {
		switch(action) {
		case SEEK:
			return actionObj.seek();
		case ESCAPE:
			return actionObj.escape();
		case FIGHT:
			return actionObj.fight();
		case FREEWALK:
			return actionObj.freewalk();
		}
		return false;
	}
}
