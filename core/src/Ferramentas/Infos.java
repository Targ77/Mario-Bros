package Ferramentas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioBros;

public class Infos {
	public Stage fase;
	private Viewport viewport;
	
	private static Integer pontosCount;
	private Integer tempoCount;
	private static Integer powCount;
	private static Integer vidaCount;
	private float tempo;
	private static float timerWin;
	
	
	private static Label  pontosCountLabel;
	private Label tempoCountLabel;
	private Label tempoLabel;
	private Label pontosLabel;
	private Label powLabel;
	private Label vidaLabel;
	private static Label powRestLabel;
	private static Label vidaRestLabel;

	
	public Infos(SpriteBatch sb) {
		pontosCount = 0;
		tempoCount = 0;
		powCount = 1;
		vidaCount = 3;
		
		viewport = new FitViewport(MarioBros.V_LARGURA, MarioBros.V_ALTURA, new OrthographicCamera());
		fase = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		pontosCountLabel = new Label(String.format("%06d", pontosCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		tempoCountLabel = new Label(String.format("%04d", tempoCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		powRestLabel = new Label(String.format("%02d", powCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		vidaRestLabel = new Label(String.format("%01d", vidaCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		tempoLabel = new Label("TEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		pontosLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		powLabel = new Label("POW RESTANDO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		vidaLabel = new Label("VIDA  RESTANDO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(vidaLabel).expandX().padTop(10);
		table.add(pontosLabel).expandX().padTop(10);
		table.add(powLabel).expandX().padTop(10);
		table.add(tempoLabel).expandX().padTop(10);
		table.row();
		table.add(vidaRestLabel).expandX();
		table.add(pontosCountLabel).expandX();
		table.add(powRestLabel).expandX();
		table.add(tempoCountLabel).expandX();
		fase.addActor(table);
	}
	
	public void update(float dt) {
		tempo += dt;
		timerWin += dt;
		if(tempo >= 1) {
			tempoCount++;
			tempoCountLabel.setText(String.format("%04d", tempoCount));
			tempo = 0;
		}
	}
	
	public static void addScore(int valor) {
		pontosCount += valor;
		pontosCountLabel.setText(String.format("%06d", pontosCount));
		if(pontosCount == 1600f ) {
			timerWin = 0;
		}
			
	}
	
	public static void powUpdate() {
		powCount --;
		powRestLabel.setText(String.format("%02d", powCount));
	}
	
	public static void vidaUpdate() {
		vidaCount --;
		if(vidaCount<0) {
			vidaRestLabel.setText(String.format("%02d", 0));
		}else {
			vidaRestLabel.setText(String.format("%02d", vidaCount));
		}
		
	}
	
	public static int getVida() {
		return vidaCount;
	}
	public static int getPow() {
		return powCount;
	}
	
	public static int getScore() {
		return pontosCount;
	}
	
	public static float getTimerWin() {
		return timerWin;
	}
}
