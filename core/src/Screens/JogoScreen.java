package Screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioBros;

import Ferramentas.Infos;
import Ferramentas.MundoContact;
import Ferramentas.MundoCreator;
import Sprites.Inimigos;
import Sprites.Mario;
import Sprites.Tartaruga;
import Sprites.Goomba;



public class JogoScreen implements Screen{
	private MarioBros game;
	//camera
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	private Infos info;
	
	//musicas
	public Music musica = Gdx.audio.newMusic(Gdx.files.internal("mario_music.mp3"));
	private Music puloSound = Gdx.audio.newMusic(Gdx.files.internal("jump.wav"));
	private float volume = (float) 0.2;
	private float volume2 = (float) 0.3;
	
	//condicionais
	private Boolean pulo = false;
	private int fila = 0;
	
	
	
	//Mapa
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Variaveis do Box2d
	private World mundo;
	private Box2DDebugRenderer b2dr;
	private Mario mario;
	private TextureAtlas imagensPack;
	
	//array de tartarugas
	private Array<Inimigos> inimigos;
	
	
	DecimalFormat formatador = new DecimalFormat("0.0");
	
	public JogoScreen(MarioBros game) {
		imagensPack = new TextureAtlas("Mario_and_Enemies.pack");
		this.game = game;

		gameCam = new OrthographicCamera();
		gamePort = new FitViewport(MarioBros.V_LARGURA / MarioBros.PPM, MarioBros.V_ALTURA / MarioBros.PPM, gameCam);
		info = new Infos(game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("gameMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		mundo = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		

		
		new MundoCreator(this);
		mario = new Mario(this);
		mundo.setContactListener(new MundoContact());
		

		inimigos = new Array<Inimigos>();
		createTartaruga();
		
		playMusica();
	}
	
	public void createTartaruga() {

			inimigos.add(new Tartaruga(this, 7.3f, 4.0f, 1));
			inimigos.add(new Tartaruga(this, 0.3f, 4.0f, 2));
			inimigos.add(new Tartaruga(this, 6.3f, 4.0f, 3));
			inimigos.add(new Tartaruga(this, 1.3f, 4.0f, 4));	
		
			inimigos.add(new Goomba(this, 7.6f, 3.0f, 5));
			inimigos.add(new Goomba(this, 0.32f, 3.0f, 6));			
			
			inimigos.add(new Goomba(this, 6.6f, 2.0f, 7));
			inimigos.add(new Goomba(this, 1.32f, 2.0f, 8));
			

	}
	
	public void powHit(){

		for (Inimigos inimigo : inimigos) {
			inimigo.inimigoHit();
		}

	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public World getWorld() {
		return mundo;
	}
	
	
	public TextureAtlas getAtlas() {
		return imagensPack;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	private void handleInput(float dt) {
		if(mario.estadoAtual != Mario.State.DEAD) {
			
			if(pulo) {
				if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
					mario.b2body.applyLinearImpulse(new Vector2(0, 5.0f), mario.b2body.getWorldCenter(), true);
					puloSound.setVolume(volume2);
					puloSound.play();
				}				
			}

			
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2body.getLinearVelocity().x <= 2){
				mario.b2body.applyLinearImpulse(new Vector2(0.1f, 0), mario.b2body.getWorldCenter(), true);
			}
	
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2body.getLinearVelocity().x >= -2){
				mario.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), mario.b2body.getWorldCenter(), true);
			}
		}
	}
	
	public void update(float dt) {
		
		handleInput(dt);
		mundo.step(1/60f, 6, 2);
		mario.update(dt);
		for (Inimigos inimigo : inimigos) {
			inimigo.update(dt);
		}
			
		info.update(dt);
		renderer.setView(gameCam);
	}

	@Override
	public void render(float delta) {
		update(delta);


		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//mapa render
		renderer.render();
		
		//box2D
		b2dr.render(mundo, gameCam.combined);
		
		
		game.batch.setProjectionMatrix(gameCam.combined);
		game.batch.begin();
		mario.draw(game.batch);

		for (Inimigos inimigo : inimigos) {
			inimigo.draw(game.batch);
		}

		
		game.batch.end();
		
		game.batch.setProjectionMatrix(info.fase.getCamera().combined);
		info.fase.draw();
		if(gameOver()) {
			game.setScreen(new GameOverScreen(game));
			dispose();
		}
		
		if(youWin()) {
			game.setScreen(new YouWinScreen(game));
			dispose();
		}


	}
	
	public boolean gameOver() {
		if(mario.estadoAtual == Mario.State.DEAD && mario.getStateTimer() > 2.5f){
			return true;
		}
		return false;
	}
	public boolean youWin() {
		if(Infos.getScore() == 1600f && Infos.getTimerWin() > 1.0f){
			return true;
		}
		return false;
	}
	
	public void playMusica() {
		musica.setVolume(volume);
		musica.setLooping(true);
		musica.play();
		
	}
	public void pauseMusica() {
		musica.pause();
		
	}
	public void marioHit() {
		mario.marioHit();
	}
	public void verificaInimigos(int id) {
		for (Inimigos inimigo : inimigos) {
			if(inimigo.getContact() && inimigo.getId() == id) {
				inimigo.inimigoHit();
			}
		}
	}
	


	public void podePular() {
		pulo = true;		
		fila++;

	}


	public void naoPular() {
		fila--;
		if(fila == 0)
			pulo = false;
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void dispose() {
		musica.dispose();
		map.dispose();
		renderer.dispose();
		mundo.dispose();
		b2dr.dispose();
		
		
	}
	
}
