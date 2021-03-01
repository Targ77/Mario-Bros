package Ferramentas;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MarioBros;

import Screens.JogoScreen;
import Sprites.Chao;
import Sprites.Pow;
import Sprites.Tijolo;

public class MundoCreator {
	public MundoCreator(JogoScreen screen) {
		World mundo = screen.getWorld();
		TiledMap map = screen.getMap();
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//chao
		for(RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
			
			new Chao(screen, rect);
		}
		//parede
		for(RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
			
			body = mundo.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = MarioBros.PAREDE_BIT;
			body.createFixture(fdef);
		}
		
		//teletrasporte
		for(RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
			
			body = mundo.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = MarioBros.TELETRASNPORTE_BIT;
			body.createFixture(fdef);
		}
		
		//teletrasporte2
		for(RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
			
			body = mundo.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / MarioBros.PPM, rect.getHeight() / 2 / MarioBros.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = MarioBros.TELETRASNPORTE2_BIT;
			body.createFixture(fdef);
		}
		

		//tijolos
		for(RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			new Tijolo(screen, rect);
		}
		
		//pow
		for(RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
			com.badlogic.gdx.math.Rectangle rect = ((RectangleMapObject) object).getRectangle();		
			
			new Pow(screen, rect);
		}
	}
}
