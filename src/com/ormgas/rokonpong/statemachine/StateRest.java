package com.ormgas.rokonpong.statemachine;

import java.util.List;

import android.util.Log;

import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.PlayerManager;
import com.ormgas.rokonpong.status.Judgement;

public class StateRest extends State {

	protected StateRest(GamePlayer character, StateMachine sm) {
		super(character, sm);
	}

	@Override
	public void onUpdate() {
		//if should seek a enemy, seek one
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
					if (anotherDst < dst) {
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
		//if should freely move, look forward to some fortune
		else if(Judgement.shouldFreeMove(character)){
			changeState(CharacterState.FREEMOVE);
		}

		Log.d("sm", "REST");

	}

	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);
	}

}
