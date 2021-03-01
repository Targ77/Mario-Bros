package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.JogoScreen;

public class MarioBros extends Game {
	public SpriteBatch batch;
	public static final int V_LARGURA = 800;
	public static final int V_ALTURA = 480;
	public static final float PPM = 100;
	
	public static final short NENHUM_BIT = 0;
	public static final short CHAO_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short TIJOLO_BIT = 4;
	public static final short POW_BIT = 8;
	public static final short PAREDE_BIT = 16;
	public static final short INIMIGO_BIT = 32;
	public static final short MARIO_HEAD = 64;
	public static final short TELETRASNPORTE_BIT = 128;
	public static final short TELETRASNPORTE2_BIT = 256;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new JogoScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
}
	

