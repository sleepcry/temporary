package com.ormgas.rokonpong;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.stickycoding.rokon.GameObject;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.modifiers.Blink;

public class CollisionHandler implements ContactListener
{

	@Override
	public void beginContact(Contact contact)
	{
		Fixture fixture1 = contact.getFixtureA();
		Fixture fixture2 = contact.getFixtureB();
		
		Body body1 = fixture1.getBody();
		Body body2 = fixture2.getBody();
		
		GameObject object1 = (Sprite)body1.getUserData();
		GameObject object2 = (Sprite)body2.getUserData();
		
		if(object1 instanceof GamePlayer && object2 != null)
		{
			GamePlayer char1 = (GamePlayer)object1;
			char1.encounter(object2);
		}
		if(object2 instanceof GamePlayer && object1 != null) {
			GamePlayer char2 = (GamePlayer)object2;
			char2.encounter(object1);
		}
		Log.d("rokonstudy","collision " +object1 + " " +object2);
		
	}

	@Override
	public void endContact(Contact contact)
	{
		// TODO Auto-generated method stub
	}

}
