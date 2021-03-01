package Sprites;


import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.MarioBros;
import Ferramentas.Infos;
import Screens.JogoScreen;

public class Tartaruga extends Inimigos{
	private float stateTime;
	private float statePulo;
	private float guardaPulo;
	private Animation<TextureRegion> walk;
	private TextureRegion morta;
	private Array<TextureRegion> frames;
	private boolean modoCasco;

	private boolean podeSumir;
	private boolean deletada;
	private Sound mario_dano = Gdx.audio.newSound(Gdx.files.internal("mario_dano.wav"));
	private Sound coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
	DecimalFormat formatador = new DecimalFormat("0.0");
	private JogoScreen screen;
	public boolean contact;
	private int fila;
	private int tp;
	private float tempoCasco;

	

	public Tartaruga(JogoScreen screen, float x, float y, int id) {
		super(screen, x, y, id);
		this.screen = screen;
		
		frames = new Array<TextureRegion>();
		for(int i = 0; i < 4; i++) {
			frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), i * 16, 0, 16, 32));
		}
		walk = new Animation<TextureRegion>(0.2f, frames);
		morta = new TextureRegion(screen.getAtlas().findRegion("turtle"), 4 * 16, 0, 16, 32);
		stateTime = 0;
		tempoCasco = 0;
		statePulo = MathUtils.random(3, 8);
		guardaPulo = statePulo;
		
		setBounds(getX(), getY(), 16 / MarioBros.PPM, 32 / MarioBros.PPM);
		
		modoCasco = false;
		deletada = false;
		podeSumir = false;
		contact = false;
		fila = 0;
		tp = 0;
	}
	
	public void update(float dt) {	
		stateTime += dt;
		tempoCasco += dt;
		
		if(tempoCasco > 6) {
			modoCasco = false;
		}
		
		
		if(podeSumir && !deletada) {
			world.destroyBody(b2Body);
			Infos.addScore(200);
			coin.play();
			deletada = true;
		}
			
		if(!modoCasco) {
			setPosition(b2Body.getPosition().x - this.getWidth() / 2, b2Body.getPosition().y - this.getHeight() / 2);
			setRegion(getFrame(dt));
			
			if(formatador.format(statePulo).equals(formatador.format(stateTime))) {
				b2Body.applyLinearImpulse(new Vector2(0, 4.0f), b2Body.getWorldCenter(), true);
				
				statePulo = guardaPulo + stateTime;
			}
			
			if(b2Body.getLinearVelocity().x <= 0.3f && b2Body.getLinearVelocity().x >= -0.3f){
				b2Body.applyLinearImpulse(new Vector2(velocidade), b2Body.getWorldCenter(), true);
			}
		}
		if(!deletada) {
			setPosition(b2Body.getPosition().x - this.getWidth() / 2, b2Body.getPosition().y - this.getHeight() / 2);
			setRegion(getFrame(dt));			
		}


		

			
		if(tp == 1) {
			b2Body.setTransform(6.6f, 4.0f, 1);
			tp=0;
		}if(tp == 2) {
			b2Body.setTransform(1.3f, 4.0f, 1);
			tp=0;
		}

		

	}

	private TextureRegion getFrame(float dt) {
		TextureRegion region;
		if(modoCasco == true) {
			region = morta;
		}else {
			region = (TextureRegion) walk.getKeyFrame(stateTime, true);
		}
		
		if(velocidade.x > 0  && region.isFlipX() == false) {
			region.flip(true, false);
		}
		if(velocidade.x < 0 && region.isFlipX() == true) {
			region.flip(true, false);
		}
		return region;
	}

	@Override
	protected void defineInimigo() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		//Definindo um corpo dinamico afetado pela fisica.
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2Body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();	
		//Escolhendo o formato de circulo e seu tamanho
		CircleShape shape = new CircleShape();
		shape.setRadius(8/ MarioBros.PPM);
		
		//Definindo o numero de Bits dos inimigos e quais Bits este inimigo pode colidir
		fdef.filter.categoryBits = MarioBros.INIMIGO_BIT;
		fdef.filter.maskBits = MarioBros.CHAO_BIT |
				MarioBros.POW_BIT|MarioBros.TIJOLO_BIT|
				MarioBros.PAREDE_BIT|MarioBros.MARIO_BIT|
				MarioBros.TELETRASNPORTE_BIT|MarioBros.TELETRASNPORTE2_BIT;

		fdef.shape = shape;
		b2Body.createFixture(fdef).setUserData(this);
	}
	
	public void draw(Batch batch) {
		if(!podeSumir) {
			super.draw(batch);
		}
			
	}
	
	

	@Override
	public void inimigoHit() {
		modoCasco = true;
		tempoCasco = 0f;

	}
	

	
	
	@Override
	public void marioDamage() {
		if(Infos.getVida() > 0 && !modoCasco) {
			
			if(Infos.getVida() > 1) {
				screen.marioHit();
				mario_dano.play();
			}
			Infos.vidaUpdate();
			
		}
		
		if(modoCasco) {
			podeSumir = true;
			stateTime = 0;
		}
	}

	@Override
	public void comecoTartarugaContato() {
		contact = true;		
		fila++;

	}

	@Override
	public void fimTartarugaContato() {
		fila--;
		if(fila == 0)
			contact = false;

		

	}

	@Override
	public Boolean getContact() {
		
		return contact;
	}


	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void teletransporte2() {
		tp = 1;
	}

	@Override
	public void teletransporte() {

		tp = 2;
	}





}
