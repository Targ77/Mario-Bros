package Ferramentas;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MarioBros;

import Sprites.ObjetosInterativos;
import Sprites.Inimigos;


public class MundoContact implements ContactListener{

	@Override
	//Aqui é reconhecido todos os contatos, e é chamada as funções necessarias
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		

		switch (cDef) {
			case MarioBros.MARIO_HEAD | MarioBros.POW_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.POW_BIT) {
					((ObjetosInterativos)fixA.getUserData()).onHeadHit();
				}else {
					((ObjetosInterativos)fixB.getUserData()).onHeadHit();
				}
				break;
				
			case MarioBros.MARIO_HEAD | MarioBros.TIJOLO_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.TIJOLO_BIT) {
					((ObjetosInterativos)fixA.getUserData()).onHeadHit();
				}else {
					((ObjetosInterativos)fixB.getUserData()).onHeadHit();
				}
				break;
				
			case MarioBros.INIMIGO_BIT | MarioBros.MARIO_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.INIMIGO_BIT) {
					((Inimigos) fixA.getUserData()).marioDamage();
					
				}else {
					((Inimigos) fixB.getUserData()).marioDamage();
				}
				break;
				
				//Passa para o tijolo o id do inimigo que entrou em contato
			case MarioBros.INIMIGO_BIT | MarioBros.TIJOLO_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.INIMIGO_BIT) {
						((Inimigos)fixA.getUserData()).comecoTartarugaContato();
						((ObjetosInterativos)fixB.getUserData()).comecoContato(((Inimigos)fixA.getUserData()).getId());
				}else {
						((ObjetosInterativos)fixA.getUserData()).comecoContato(((Inimigos)fixB.getUserData()).getId());
						((Inimigos)fixB.getUserData()).comecoTartarugaContato();
				}

				break;
			case MarioBros.INIMIGO_BIT | MarioBros.PAREDE_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.INIMIGO_BIT) {
					((Inimigos)fixA.getUserData()).reverseVelocity(true, false);
				}else {
					((Inimigos)fixB.getUserData()).reverseVelocity(true, false);
				}
				break;
			case MarioBros.INIMIGO_BIT | MarioBros.INIMIGO_BIT:
					((Inimigos)fixA.getUserData()).reverseVelocity(true, false);
					((Inimigos)fixB.getUserData()).reverseVelocity(true, false);
				break;
			case MarioBros.INIMIGO_BIT | MarioBros.TELETRASNPORTE_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.INIMIGO_BIT) {
					((Inimigos)fixA.getUserData()).teletransporte2();
				}else {
					((Inimigos)fixB.getUserData()).teletransporte2();
				}
				break;
			case MarioBros.INIMIGO_BIT | MarioBros.TELETRASNPORTE2_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.INIMIGO_BIT) {
					((Inimigos)fixA.getUserData()).teletransporte();
				}else {
					((Inimigos)fixB.getUserData()).teletransporte();
				}
				break;
				//Criando as condições de pulo do mario
			case MarioBros.MARIO_BIT | MarioBros.POW_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.POW_BIT) {
					((ObjetosInterativos)fixA.getUserData()).marioPula();
				}else {
					((ObjetosInterativos)fixB.getUserData()).marioPula();
				}
				break;
			case MarioBros.MARIO_BIT | MarioBros.TIJOLO_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.TIJOLO_BIT) {
					((ObjetosInterativos)fixA.getUserData()).marioPula();
				}else {
					((ObjetosInterativos)fixB.getUserData()).marioPula();
				}
				break;
			case MarioBros.MARIO_BIT | MarioBros.CHAO_BIT:
				if(fixA.getFilterData().categoryBits == MarioBros.CHAO_BIT) {
					((ObjetosInterativos)fixA.getUserData()).marioPula();
				}else {
					((ObjetosInterativos)fixB.getUserData()).marioPula();
				}
				break;
				
		}
		
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		switch (cDef) {
		//Passando false para o contato da tartaruga com os tijolos
		case MarioBros.INIMIGO_BIT | MarioBros.TIJOLO_BIT:
			if(fixA.getFilterData().categoryBits == MarioBros.INIMIGO_BIT) {
					((Inimigos)fixA.getUserData()).fimTartarugaContato();
					((ObjetosInterativos)fixB.getUserData()).fimContato();
			}else {
					((ObjetosInterativos)fixA.getUserData()).fimContato();
					((Inimigos)fixB.getUserData()).fimTartarugaContato();
			}
			break;
			
			//Criando as condições de pulo do mario
		case MarioBros.MARIO_BIT | MarioBros.POW_BIT:
			if(fixA.getFilterData().categoryBits == MarioBros.POW_BIT) {
				((ObjetosInterativos)fixA.getUserData()).marioNaoPula();
			}else {
				((ObjetosInterativos)fixB.getUserData()).marioNaoPula();
			}
			break;
		case MarioBros.MARIO_BIT | MarioBros.TIJOLO_BIT:
			if(fixA.getFilterData().categoryBits == MarioBros.TIJOLO_BIT) {
				((ObjetosInterativos)fixA.getUserData()).marioNaoPula();
			}else {
				((ObjetosInterativos)fixB.getUserData()).marioNaoPula();
			}
			break;
		case MarioBros.MARIO_BIT | MarioBros.CHAO_BIT:
			if(fixA.getFilterData().categoryBits == MarioBros.CHAO_BIT) {
				((ObjetosInterativos)fixA.getUserData()).marioNaoPula();
			}else {
				((ObjetosInterativos)fixB.getUserData()).marioNaoPula();
			}
			break;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
