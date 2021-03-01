package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Screens.JogoScreen;

public abstract class Inimigos extends Sprite{
	protected World world;
	protected JogoScreen screen;
	public Body b2Body;
	public Vector2 velocidade;
	public int id;
	
	public Inimigos(JogoScreen screen, float x, float y, int id) {
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x,y);
		defineInimigo();
		if(x < 4f) {
			velocidade = new Vector2(0.1f, 0f);
		}else {
			velocidade = new Vector2(-0.1f, 0f);
		}
		this.id = id;
		
	}
	
	protected abstract void defineInimigo();
	public abstract void inimigoHit();
	public abstract void marioDamage();
	public abstract void teletransporte2();
	public abstract void teletransporte();

	public void reverseVelocity(boolean x, boolean y) {
		if(x)
			velocidade.x = -velocidade.x;
		if(y)
			velocidade.y = -velocidade.y;
	}

	public abstract void update(float dt);

	public abstract void comecoTartarugaContato();
	public abstract void fimTartarugaContato();
	public abstract Boolean getContact();
	public abstract int getId();
	

	
}
