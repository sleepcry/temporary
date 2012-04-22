package com.ormgas.rokonpong;

import com.ormgas.rokonpong.status.PlayerStatus;

public interface StatusMoniter {
	public void statusChanged(PlayerStatus status);
}
