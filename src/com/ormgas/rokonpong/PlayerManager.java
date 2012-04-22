package com.ormgas.rokonpong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class PlayerManager {
	static List<GamePlayer> lstChar = new ArrayList<GamePlayer>();
	
	public static void addChar(GamePlayer character) {
		if(character!=null)lstChar.add(character);
	}
	public static List<GamePlayer> seekPlayer(Vector2 pos, float range){
		List<GamePlayer> lst=  new ArrayList<GamePlayer>();
		if(range <= 0 ) {
			lst.addAll(lstChar);
			return lst;
		}
		for(int i=0;i<lstChar.size();i++) {
			if(lstChar.get(i).body.getPosition().dst(pos) <= range) {
				lst.add(lstChar.get(i));
			}
		}
		return lst;
	}
	public static void removePlayer(GamePlayer player) {
		lstChar.remove(player);
	}
}
