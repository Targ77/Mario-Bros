package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MarioBros;

import Screens.JogoScreen;


public class Tijolo extends ObjetosInterativos{
	private Sound tijoloEfeito = Gdx.audio.newSound(Gdx.files.internal("brick.wav"));
	public boolean contact;
	public int id;
	JogoScreen screen;
	public Tijolo(JogoScreen screen, Rectangle bounds) {
		super(screen, bounds);
		this.screen = screen;
		fixture.setUserData(this);
		setCategoryFilter(MarioBros.TIJOLO_BIT);
		contact = false;
		id = -1;
		
	}

	@Override
	public void onHeadHit() {
		tijoloEfeito.play();
		if(contact)
			screen.verificaInimigos(id);
	}

	@Override
	public void comecoContato(int id) {
		
		contact = true;
		this.id = id;
		
		
	}

	@Override
	public void fimContato() {
		
		contact = false;
		id = -1;
	}

	@Override
	public void marioPula() {
		screen.podePular();
		
	}

	@Override
	public void marioNaoPula() {
		screen.naoPular();
		
	}
	

		
}
