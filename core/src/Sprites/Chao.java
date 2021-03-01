package Sprites;


import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MarioBros;

import Screens.JogoScreen;


public class Chao extends ObjetosInterativos{
	JogoScreen screen;
	public Chao(JogoScreen screen, Rectangle bounds) {
		super(screen, bounds);
		this.screen = screen;
		fixture.setUserData(this);
		setCategoryFilter(MarioBros.CHAO_BIT);

			
	}



	@Override
	public void comecoContato(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void fimContato() {
		// TODO Auto-generated method stub
	}



	@Override
	public void onHeadHit() {
		// TODO Auto-generated method stub
		
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