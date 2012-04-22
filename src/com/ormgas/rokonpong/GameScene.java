package com.ormgas.rokonpong;

import android.util.Log;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ormgas.rokonpong.statemachine.PlayerActionInterface;
import com.ormgas.rokonpong.status.PlayerStatus;
import com.stickycoding.rokon.GameObject;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.device.Accelerometer;
import com.stickycoding.rokon.device.OnAccelerometerChange;

public class GameScene extends Scene implements OnAccelerometerChange {
	private World mWorld;
	private Sprite[][] mRoad;

	private Sprite mSmallCloud;
	private Sprite mBigCloud;

	Wolf wolf;
	Bunny bunny;
	Hunter hunter;
	GamePlayer self;
	ProgressBar wolfLife,bunnyLife,hunterLife,selfPower;
	public static final int WIDTH = 4;
	public static final int HEIGHT = 2;

	public GameScene(SceneHandler sceneHandler) {
		super(5, new int[] { WIDTH * HEIGHT, 3, 4, 3,7 });
	}

	@Override
	public void onGameLoop() {
		mSmallCloud.x += 0.002f;
		if (mSmallCloud.x > MainActivity.screenWidth) {
			mSmallCloud.x = -mSmallCloud.getWidth() - 1;
		}

		mBigCloud.x += 0.005f;
		if (mBigCloud.x > MainActivity.screenWidth) {
			mBigCloud.x = -mBigCloud.getWidth() - 1;
		}
	}

	@Override
	public void onReady() {
//		setBackground(new FixedBackground(Textures.background));
		CreateWorld();
		CreateSprites();

		mWorld.setContactListener(new CollisionHandler());
		onResume();
	}

	@Override
	public void onPause() {
		Accelerometer.stopListening();
		// RokonMusic.stop();
	}

	@Override
	public void onResume() {
		Accelerometer.startListening(this);
		// RokonMusic.play("trmpledbeta.mp3", true);
	}

	private void CreateWorld() {
		mWorld = new World(new Vector2(0.0f, 0.0f), false);

		PolygonShape eastWestShape = new PolygonShape();
		eastWestShape.setAsBox(1.0f, MainActivity.screenHeight);

		PolygonShape northSouthShape = new PolygonShape();
		northSouthShape.setAsBox(MainActivity.screenWidth, 1.0f);

		BodyDef northDef = new BodyDef();
		northDef.type = BodyDef.BodyType.StaticBody;
		northDef.position.set(new Vector2(0, 0));
		Body northBody = mWorld.createBody(northDef);
		FixtureDef northFixture = new FixtureDef();
		northFixture.restitution = 0.1f;
		northFixture.friction = 0.1f;
		northFixture.shape = northSouthShape;
		northBody.createFixture(northFixture);

		BodyDef southDef = new BodyDef();
		southDef.type = BodyDef.BodyType.StaticBody;
		southDef.position.set(new Vector2(0, MainActivity.screenHeight));
		Body southBody = mWorld.createBody(southDef);
		FixtureDef southFixture = new FixtureDef();
		southFixture.restitution = 0.1f;
		southFixture.friction = 0.1f;
		southFixture.shape = northSouthShape;
		southBody.createFixture(southFixture);

		BodyDef eastDef = new BodyDef();
		eastDef.type = BodyDef.BodyType.StaticBody;
		eastDef.position.set(new Vector2(MainActivity.screenWidth, 0));
		Body eastBody = mWorld.createBody(eastDef);
		FixtureDef eastFixture = new FixtureDef();
		eastFixture.restitution = 0.1f;
		eastFixture.friction = 0.1f;
		eastFixture.shape = eastWestShape;
		eastBody.createFixture(eastFixture);

		BodyDef westDef = new BodyDef();
		westDef.type = BodyDef.BodyType.StaticBody;
		westDef.position.set(new Vector2(0, 0));
		Body westBody = mWorld.createBody(westDef);
		FixtureDef westFixture = new FixtureDef();
		westFixture.restitution = 0.1f;
		westFixture.friction = 0.1f;
		westFixture.shape = eastWestShape;
		westBody.createFixture(westFixture);

		this.setWorld(mWorld);
	}

	private void CreateSprites() {
		// layer 0
		mRoad = new Sprite[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++)
			for (int j = 0; j < HEIGHT; j++) {
				mRoad[i][j] = new Sprite(i * MainActivity.screenWidth / WIDTH,
						j * MainActivity.screenHeight / HEIGHT,
						MainActivity.screenWidth / WIDTH,
						MainActivity.screenHeight / HEIGHT);
				mRoad[i][j].setTextureTile(Textures.grass, 0);
				this.add(0, mRoad[i][j]);
			}
		// layer 1
		// TODO: some other landscape
		// layer 2
		wolf = new Wolf.Builder().getWolf();
		hunter = new Hunter.Builder().getHunter();
		bunny = new Bunny.Builder().getBunny();
		add(2,wolf);
		add(2,hunter);
		add(2,bunny);
		PlayerManager.addChar(wolf);
		PlayerManager.addChar(hunter);
		PlayerManager.addChar(bunny);
		wolf.setAuto();
		hunter.setAuto();
		self = bunny.manual();
		bunny.setAuto();
		
		//layer 3
		mSmallCloud = new Sprite(1.0f, 0.5f, Textures.smallcloud.getWidth()
				* MainActivity.scaleFactorX * 2,
				Textures.smallcloud.getHeight() * MainActivity.scaleFactorX * 2);
		mSmallCloud.setTexture(Textures.smallcloud);
		this.add(3, mSmallCloud);

		mBigCloud = new Sprite(5.4f, 1.0f, Textures.bigcloud.getWidth()
				* MainActivity.scaleFactorX * 2, Textures.bigcloud.getHeight()
				* MainActivity.scaleFactorX * 2);
		mBigCloud.setTexture(Textures.bigcloud);
		this.add(3, mBigCloud);
		
		//layer 4
		float x=0.25f,y=0.1f,w=4.5f,h=0.5f,g=1f,d=0.25f;	
		float width = MainActivity.calcWidth(Textures.hero.getWidth() / 12);
		float height = MainActivity.calcHeight(Textures.hero.getHeight() / 8);
		GameObject head = new GameObject(x-d,y-d,width,height);
		head.setTextureTile(Textures.hero,Wolf.BASE+25);
		wolfLife = new ProgressBar(x,y,w,h,wolf.status.maxLife,wolf.status.currentLife) {

			@Override
			public void statusChanged(PlayerStatus status) {
				setVal(status.currentLife);
				Log.d("status changed","wolf life:" + status.currentLife);
			}
			
		};
		wolf.addMoniter(wolfLife);
		add(4,wolfLife);
		add(4,head);
		x += w + g;
		bunnyLife = new ProgressBar(x,y,w,h,bunny.status.maxLife,bunny.status.currentLife) {

			@Override
			public void statusChanged(PlayerStatus status) {
				setVal(status.currentLife);
				Log.d("status changed","bunny life:" + status.currentLife);
			}
			
		};
		head = new GameObject(x-d,y-d,width,height);
		head.setTextureTile(Textures.hero,Bunny.BASE+25);
		bunny.addMoniter(bunnyLife);
		add(4,bunnyLife);
		add(4,head);
		x += w + g;
		hunterLife = new ProgressBar(x,y,w,h,hunter.status.maxLife,hunter.status.currentLife) {

			@Override
			public void statusChanged(PlayerStatus status) {
				setVal(status.currentLife);
				Log.d("status changed","hunter life:" + status.currentLife);
			}
			
		};
		head = new GameObject(x-d,y-d,width,height);
		head.setTextureTile(Textures.hero,Hunter.BASE+25);
		hunter.addMoniter(hunterLife);
		add(4,hunterLife);
		add(4,head);
		selfPower = new ProgressBar(0.1f,MainActivity.screenHeight-0.6f,MainActivity.screenWidth-0.2f,
				0.5f,self.status.maxPower,self.status.currentPower) {

			@Override
			public void statusChanged(PlayerStatus status) {
				setVal(status.currentPower);
				Log.d("status changed","self power:" + status.currentPower);
			}
			
		};
		self.addMoniter(selfPower);
		add(4,selfPower);
	}

	@Override
	public void onTouchDown(float x, float y, MotionEvent event,
			int pointerCount, int pointerId) {
		// Sounds.laser.play();
		Vector2 pos = new Vector2(event.getX() * MainActivity.scaleFactorX,
				event.getY() * MainActivity.scaleFactorY);
		self.accelerate(pos);		
	}

	@Override
	public void onTouchMove(float x, float y, MotionEvent event,
			int pointerCount, int pointerId) {
	}

	@Override
	public void onTouchUp(float x, float y, MotionEvent event,
			int pointerCount, int pointerId) {
		self.stopAccelerate();
	}

	@Override
	public void onAccelerometerChange(float x, float y, float z) {
	}

	@Override
	public void onShake(float intensity) {
		
	}

}
