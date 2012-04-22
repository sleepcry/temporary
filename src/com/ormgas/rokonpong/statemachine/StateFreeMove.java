package com.ormgas.rokonpong.statemachine;

import java.util.List;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.PlayerManager;
import com.ormgas.rokonpong.status.Judgement;

public class StateFreeMove extends State {

	protected StateFreeMove(GamePlayer character,StateMachine sm) {
		super(character,sm);
	}
	public void onUpdate() {
		//current action
		if (Judgement.nonsense() && character.usePower(character.getStatus().accPower)) {
			Vector2 dir = new Vector2();
			dir.x = (float) Math.random() - 0.5f;
			dir.y = (float) Math.random() - 0.5f;
			character.getBody().applyLinearImpulse(character.calcPower(dir.x, dir.y),
					character.getBody().getPosition());
		} 
		//next action
		//if should seek some enemy, seek one
		if (Judgement.shouldSeek(character)) {
			List<GamePlayer> lst = PlayerManager.seekPlayer(character.getBody()
					.getPosition(), 3);
			if (lst != null && lst.size() > 0 ) {
				GamePlayer followingTarget = null;
				float dst = 1000.0f;
				for (int i = 0; i < lst.size(); i++) {
					GamePlayer p = lst.get(i);
					if(p == character)continue;
					float anotherDst = p.getBody().getPosition()
							.dst(character.getBody().getPosition());
					if (anotherDst < dst ) {
						followingTarget = p;
						dst = anotherDst;
					}
				}
				if (followingTarget != null) {
					character.setTarget(followingTarget);
					changeState(CharacterState.MOVECLOSE);
				}
			}
		}
		//some chance to end free walk
		else if(Judgement.nonsense()){
			sm.changeTo(CharacterState.REST);
		}
		Log.d("sm", "FREEMOVE");
	}
	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);		
	}

}
