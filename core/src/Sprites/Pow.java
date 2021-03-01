package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MarioBros;

import Ferramentas.Infos;
import Screens.JogoScreen;

public class Pow extends ObjetosInterativos{
	private static TiledMapTileSet tileSet;
	private final int POW_1 = 28;
	private Sound powLigado = Gdx.audio.newSound(Gdx.files.internal("powLigado.wav"));
	private Sound powDesligado = Gdx.audio.newSound(Gdx.files.internal("powDesligado.wav"));
	private JogoScreen screen;
	public Pow(JogoScreen screen, Rectangle bounds) {
		super(screen, bounds);
		this.screen = screen;
		tileSet = map.getTileSets().getTileSet("tileset_gutter");
		fixture.setUserData(this);
		setCategoryFilter(MarioBros.POW_BIT);
	}


	@Override
	public void onHeadHit() {
		if(Infos.getPow() > 0) {
			Infos.powUpdate();		
			powLigado.play();
			screen.powHit();
			
		}
		if(Infos.getPow() == 0){
			getCell().setTile(tileSet.getTile(POW_1));
			powDesligado.play();
		}

		
		
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
	public void marioPula() {
		screen.podePular();
		
	}


	@Override
	public void marioNaoPula() {
		screen.naoPular();
		
	}


}
