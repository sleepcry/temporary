package com.ormgas.rokonpong.status;

import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.Wolf;
import com.stickycoding.rokon.GameObject;

public class Judgement {
	public static boolean shouldFight(GamePlayer player) {
		GameObject target = player.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}
		int should = 0;
		if (target instanceof GamePlayer) {
			// probability of being silly
			if (nonsense()) {
				return nonsense();
			}
			GamePlayer targetPlayer = (GamePlayer) target;
			PlayerStatus myStatus = player.getStatus();
			PlayerStatus targetStatus = targetPlayer.getStatus();
			// see the life
			if (myStatus.currentLife > targetStatus.currentLife) {
				should += 10;
			} else {
				should -= 10;
			}
			// see the power
			if (myStatus.currentPower > targetStatus.currentPower) {
				should += 20;
			} else {
				should -= 20;
			}
			// see attack range
			float dst = targetPlayer.getBody().getPosition()
					.dst(player.getBody().getPosition());
			if (myStatus.attackRange <= dst && targetStatus.attackRange > dst) {
				should += 40;
			} else if (myStatus.attackRange <= dst
					&& targetStatus.attackRange > dst) {
				should -= 40;
			}

			// see low life
			if (targetStatus.currentLife <= 10) {
				should += 60;
			} else if (myStatus.currentLife <= 10) {
				should -= 60;
			}
		} else {
			should -= 1;
		}
		if (should > 0) {
			return true;
		} else if (should < 0) {
			return false;
		} else {
			return (int) (Math.random() * 4) % 4 == 0;
		}
	}

	public static boolean shouldEscape(GamePlayer player) {
		GameObject target = player.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}
		if (target instanceof GamePlayer) {
			// probability of being silly
			if (nonsense()) {
				return nonsense();
			}
			GamePlayer targetPlayer = (GamePlayer) target;
			PlayerStatus myStatus = player.getStatus();
			PlayerStatus targetStatus = targetPlayer.getStatus();
			// see power
			if (myStatus.currentPower <= 3 * myStatus.accPower) {
				return false;
			}
			// with power enough, see life
			if (myStatus.currentLife < 10 && targetStatus.currentLife >= 10) {
				return true;
			}
			if (myStatus.currentLife
					/ (targetStatus.attack * targetPlayer
							.getAttackFactor(player)) > targetStatus.currentLife
					/ (myStatus.attack * player.getAttackFactor(targetPlayer))) {
				return false;
			}
		}

		return false;
	}

	public static boolean shouldSeek(GamePlayer player) {
		if (player.getStatus().currentPower > 10) {
			return nonsense();
		}
		return false;
	}

	public static boolean shouldHide(GamePlayer player) {
		return nonsense();
	}

	public static boolean shouldFreeMove(GamePlayer player) {
		if (player.getStatus().currentPower > 30) {
			return nonsense();
		}
		return false;
	}

	public static boolean nonsense() {
		return (int) (Math.random() * 2) % 2 == 0;
	}
}
