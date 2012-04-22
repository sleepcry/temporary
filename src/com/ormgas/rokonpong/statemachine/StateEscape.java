package com.ormgas.rokonpong.statemachine;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.status.Judgement;
import com.stickycoding.rokon.GameObject;

public class StateEscape extends State {

	protected StateEscape(GamePlayer character, StateMachine sm) {
		super(character, sm);
	}

	private boolean shouldStopEscape() {
		if(player.getStatus().currentPower <= 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean safe() {
		GameObject target = player.getTarget();
		if (target != null && target.isAlive()) {
			Vector2 targetPos = new Vector2(target.x, target.y);
			if (targetPos.dst(player.getBody().getPosition()) >= player
					.getStatus().safeDst) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);
	}

	@Override
	protected void action() {
		GameObject target = player.getTarget();
		if (target != null && target.isAlive()) {
			Vector2 targetPos = new Vector2(target.x, target.y);
			player.getBody().applyLinearImpulse(
					player.calcPower(player.calcEscapeRoute(player.getBody()
							.getPosition().x - targetPos.x, player.getBody()
							.getPosition().y - targetPos.y)), targetPos);
		}
	}

	@Override
	protected void update() {
		GameObject targetObj = player.getTarget();
		if(targetObj == null || !targetObj.isAlive() || safe() || shouldStopEscape()) {
			player.setTarget(null);
			changeState(CharacterState.REST);			
		}
		Log.d("sm", "ESCAPE");	
	}

}
