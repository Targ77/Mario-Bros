package Sprites;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MarioBros;

import Ferramentas.Infos;
import Screens.JogoScreen;
public class Mario extends Sprite{
	public enum State{CAINDO, PULANDO, PARADO, CORRENDO, DEAD };
	public State estadoAtual;
	public State estadoAnterior;;
	private JogoScreen screen;
	
	public World mundo;
	public Body b2body;
	
	private TextureRegion marioParado;
	private Animation<TextureRegion> marioCorrendo;
	private Animation<TextureRegion> marioPulando;
	private TextureRegion marioDead;
	private Sound deadSound;
	
	
	
	private float stateTimer;
	private boolean correndoDireita;
	private boolean marioIsDead;
	
	
	
	public Mario(JogoScreen screen) {
		super(screen.getAtlas().findRegion("big_mario"));
		this.mundo = screen.getWorld();
		estadoAtual = State.PARADO;
		estadoAnterior = State.PARADO;
		stateTimer = 0;
		correndoDireita = true;
		this.screen = screen;
		deadSound = Gdx.audio.newSound(Gdx.files.internal("mariodie.wav"));
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 4; i++) {
			frames.add(new TextureRegion(getTexture(), (i * 16 + 1), 29, 16, 32));
		}
		marioCorrendo = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
		
		for(int i = 4; i < 6; i++) {
			frames.add(new TextureRegion(getTexture(), (i * 16 + 1), 29, 16, 32));
		}
		marioPulando = new Animation<TextureRegion>(0.1f, frames);
		
		marioParado = new TextureRegion(getTexture(), 1, 29, 16, 32);
		
		marioDead = new TextureRegion(getTexture(), (6*16 + 1), 29, 16, 32);
		
		defineMario();
		setBounds(0, 0, 16 / MarioBros.PPM, 32 / MarioBros.PPM );
		setRegion(marioParado);
		
	}
	
	public void update(float dt) {
		if(Infos.getVida() == 0 ) {
			marioDead();
		}
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
		setRegion(getFrame(dt));
	}
	
	public TextureRegion getFrame(float dt) {
		estadoAtual = getState();
		
		TextureRegion region;		
		switch(estadoAtual) {
		case DEAD:
			region = marioDead;
			break;
		case PULANDO:
			region = (TextureRegion) marioPulando.getKeyFrame(stateTimer);
			break;
		case CAINDO:
		case CORRENDO:
			region = (TextureRegion) marioCorrendo.getKeyFrame(stateTimer, true);
			break;
		case PARADO:
		default:
			region = marioParado;
			break;
		}
		if((b2body.getLinearVelocity().x < 0 || !correndoDireita) && !region.isFlipX()) {
			region.flip(true, false);
			correndoDireita = false;
		}else if((b2body.getLinearVelocity().x > 0 || correndoDireita) && region.isFlipX()) {
			region.flip(true, false);
			correndoDireita = true;
		}
		stateTimer = estadoAtual == estadoAnterior ? stateTimer + dt : 0;
		estadoAnterior = estadoAtual;
		return region;
	}

	private State getState() {
		if(marioIsDead) {
			return State.DEAD;
		}
			
		if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && estadoAnterior == State.PULANDO)) {
			return State.PULANDO;
			}else if(b2body.getLinearVelocity().y < 0) {
				return State.CAINDO;
			}else if(b2body.getLinearVelocity().x != 0) {
				return State.CORRENDO;
			}else {
				return State.PARADO;
			}
			
		}
			
		
	

	public void defineMario() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(384 / MarioBros.PPM, 32 / MarioBros.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = mundo.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(14 / MarioBros.PPM);
		fdef.filter.categoryBits = MarioBros.MARIO_BIT;
		fdef.filter.maskBits = MarioBros.CHAO_BIT |MarioBros.POW_BIT|MarioBros.TIJOLO_BIT|MarioBros.INIMIGO_BIT|MarioBros.PAREDE_BIT;
		
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-4 / MarioBros.PPM, 17 / MarioBros.PPM), new Vector2(4 / MarioBros.PPM, 17 / MarioBros.PPM));
		fdef.shape = head;
		fdef.filter.categoryBits = MarioBros.MARIO_HEAD;
		fdef.isSensor = true;
		
		b2body.createFixture(fdef).setUserData("head");
	}
	public boolean isDead() {
		return marioIsDead;
	}
	
	public float getStateTimer() {
		return stateTimer;
	}
	
	public void marioHit() {
		b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
	}
	
	public void marioDead() {
		Infos.vidaUpdate();
		screen.pauseMusica();
		deadSound.play();
		marioIsDead = true;
		Filter filter = new Filter();
		filter.maskBits = MarioBros.NENHUM_BIT;
		for(Fixture fixture : b2body.getFixtureList())
			fixture.setFilterData(filter);
		b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
		
	}
	
}
