package com.osreboot.corrugatedfidelity;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.ArrayList;
import java.util.Collections;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import com.osreboot.corrugatedfidelity.KeybindManager.FunctionType;
import com.osreboot.corrugatedfidelity.gameobject.Card;
import com.osreboot.corrugatedfidelity.gameobject.Corporation;

public class Framework {

	private ObjectiveManager objective;
	private StatusManager status;

	private long plasmaProgress = 0;

	public void init(Main main){
		new Card(main.getWidth()/8*3, main.getHeight()/2);
		new Card(main.getWidth()/2, main.getHeight()/2);
		new Card(main.getWidth()/8*5, main.getHeight()/2);
		
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for(int i = 0; i <= 14; i++){
			indexes.add(i);
		}
		
		Collections.shuffle(indexes);
		for(int index : indexes){
			switch(index){
			case 1: new Corporation(main, "haze", "HZE", main.getTextureLoader().getResource(5)); break;
			case 2: new Corporation(main, "ludum", "LUD", main.getTextureLoader().getResource(11)); break;
			case 3: new Corporation(main, "dare", "DAR", main.getTextureLoader().getResource(12)); break;
			case 4: new Corporation(main, "gradient", "GNT", main.getTextureLoader().getResource(6)); break;
			case 5: new Corporation(main, "sale", "SLE", main.getTextureLoader().getResource(15)); break;
			case 6: new Corporation(main, "tek", "TEK", main.getTextureLoader().getResource(16)); break;
			case 7: new Corporation(main, "or", "OR", main.getTextureLoader().getResource(17)); break;
			case 8: new Corporation(main, "watch", "WTH", main.getTextureLoader().getResource(23)); break;
			case 9: new Corporation(main, "huh", "HUH", main.getTextureLoader().getResource(24)); break;
			case 10: new Corporation(main, "sight", "CPT", main.getTextureLoader().getResource(25)); break;
			case 11: new Corporation(main, "hover", "HVR", main.getTextureLoader().getResource(26)); break;
			case 12: new Corporation(main, "accel", "ACL", main.getTextureLoader().getResource(27)); break;
			case 13: new Corporation(main, "celsius", "CEL", main.getTextureLoader().getResource(28)); break;
			case 14: new Corporation(main, "conic", "CON", main.getTextureLoader().getResource(29)); break;
			}
		}

		
		
		objective = new ObjectiveManager();
		status = new StatusManager(main);
	}

	public void updateRunning(Main main, long delta){
		plasmaProgress = plasmaProgress < 720 ? plasmaProgress + (delta/10) : 0;
		hvlDrawQuad(0, plasmaProgress - 720, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(7));
		hvlDrawQuad(0, plasmaProgress, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(7));

		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(21), plasmaProgress % 90 < 45 ? new Color(0f, 0f, 0f) : new Color(0f, 0f, 1f));
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(22), plasmaProgress % 90 > 45 ? new Color(0f, 0f, 0f) : new Color(1f, 0.1f, 0.1f));
		
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(1));

		for(Card card : Card.cards){
			if(card.isOnGround() && card.getX() > main.getWidth()/4*3 && card.getCorporation() == null){
				hvlRotate(card.getX(), card.getY(), plasmaProgress/2);
				hvlDrawQuad(card.getX() - 32, card.getY() - 32, 64, 64, main.getTextureLoader().getResource(13), new Color(0.6f, 1f, 0.6f, 0.3f));
				hvlResetRotation();
				break;
			}
		}
		for(Card c : Card.cards){
			if(c.isOnGround() && c.getX() < main.getWidth()/4){
				hvlDrawQuad(c.getX() - 32, c.getY() - 32, 64, 64, main.getTextureLoader().getResource(14), new Color(0.6f, 1f, 0.6f, 0.3f));
			}
		}
		
		for(Card c : Card.cards) c.update(main, delta);
		for(Corporation c : Corporation.corps) c.update(main, status, delta);
		
		objective.update(main, delta);
		
		float extension = main.getInteractionManager().getMenuExtension();
		if(main.getInteractionManager().getMenuExtension() != 0) hvlDrawQuad((main.getWidth()/8*6) - extension, 0, 2048, 2048, 0f, 0f, 1f, 1f, main.getTextureLoader().getResource(10), new Color(1f, 1f, 1f, 1f));

		if(main.getInteractionManager().getMenuExtension() > 0){
			for(Corporation c : Corporation.corps){
				hvlDrawQuad(
						(main.getWidth()/8*6) - extension + (main.getWidth()/64*11) + ((Corporation.corps.indexOf(c)%11)*main.getWidth()/16), 
						(main.getHeight()/64*15) + (Corporation.corps.indexOf(c)/11*main.getHeight()/8), 
						main.getWidth()/16,
						main.getHeight()/16, 
						0, 0, 0.5f, 0.25f, c.getTexture(), Util.getVelocityColor(c));//TODO arrows to indicate company status
				main.getFontPainter().hvlDrawWord((int)c.getPrice() + "", (main.getWidth()/8*6) - extension + (main.getWidth()/64*11) + ((Corporation.corps.indexOf(c)%11)*main.getWidth()/16), 
						(main.getHeight()/64*15) + main.getHeight()/16 + (Corporation.corps.indexOf(c)/11*main.getHeight()/8), 
						main.getWidth()/16, 
						main.getHeight()/16, Util.getVelocityColor(c));
			}
		}
		
		hvlDrawQuad(-main.getInteractionManager().getMenuExtension(), 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(8));

		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(9));

		status.update(main, delta);

		if(KeybindManager.isFunctionTriggering(FunctionType.HELP)){
			if(main.getInteractionManager().isMenuOpen()){
				main.getFontPainter().hvlDrawWord("click an icon to purchase stock", main.getWidth()/16*5.5f, main.getHeight()/32*5, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("red - falling price   green - rising price", main.getWidth()/16*4.5f, main.getHeight()/32*29.5f, 0.25f, new Color(1f, 1f, 1f));
				
				hvlRotate(main.getWidth()/8*7, main.getHeight()/2, 90);
				main.getFontPainter().hvlDrawWord("click here to close menu", main.getWidth()/32*24f, main.getHeight()/16*6.5f, 0.25f, new Color(1f, 1f, 1f));
				hvlResetRotation();
			}else{
				main.getFontPainter().hvlDrawWord("drag stock", main.getWidth()/16*2f, main.getHeight()/16*7.5f, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("here to", main.getWidth()/16*2f, main.getHeight()/16*8.5f, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("sell", main.getWidth()/16*2f, main.getHeight()/16*9.5f, 0.25f, new Color(1f, 1f, 1f));
				
				main.getFontPainter().hvlDrawWord("drag stock", main.getWidth()/32*24f, main.getHeight()/16*7, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("here and", main.getWidth()/32*24f, main.getHeight()/16*8, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("buy from", main.getWidth()/32*24f, main.getHeight()/16*9, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("side menu", main.getWidth()/32*24f, main.getHeight()/16*10, 0.25f, new Color(1f, 1f, 1f));
				
				hvlRotate(main.getWidth()/8*7, main.getHeight()/2, 90);
				main.getFontPainter().hvlDrawWord("click here to open side menu", main.getWidth()/32*24f, main.getHeight()/16*6.5f, 0.25f, new Color(1f, 1f, 1f));
				hvlResetRotation();
			}
		}
	}

	private boolean muteClicked = false;
	
	public void updatePaused(Main main){
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(2));
		main.getFontPainter().hvlDrawWord("paused", main.getWidth()/16, main.getHeight()/16, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("information", main.getWidth()/16*7, main.getHeight()/16*3, 0.5f, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("corporation name", main.getWidth()/16*6, main.getHeight()/16*5, 0.5f, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("quantity of stock owned", main.getWidth()/16*6, main.getHeight()/16*9, 0.5f, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("current stock value", main.getWidth()/16*6, main.getHeight()/16*11, 0.5f, new Color(1f, 1f, 1f));

		main.getFontPainter().hvlDrawWord("hold [h] while in-game for more help", main.getWidth()/32*3, main.getHeight()/16*15, 0.5f, new Color(1f, 1f, 1f));
		
		hvlDrawQuad(main.getWidth()/16*15, main.getHeight()/32, 64, 64, main.getTextureLoader().getResource(main.getSoundManager().isMuted() ? 20 : 19), new Color(1f, 1f, 1f));
		if(Mouse.isButtonDown(0)){
			if(!muteClicked){
				if(Mouse.getX() > main.getWidth()/16*15 && main.getHeight() - Mouse.getY() > main.getHeight()/32 &&
						Mouse.getX() < (main.getWidth()/16*15) + 64 && main.getHeight() - Mouse.getY() < (main.getHeight()/32) + 64){
					main.getSoundManager().setMuted(!main.getSoundManager().isMuted());
					muteClicked = true;
				}
			}
		}else muteClicked = false;
	}

	public ObjectiveManager getObjectiveManager(){
		return objective;
	}
	
	public void updateFinished(Main main, long delta, boolean win){
		plasmaProgress = plasmaProgress < 720 ? plasmaProgress + (delta/10) : 0;
		hvlDrawQuad(0, plasmaProgress - 720, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(7));
		hvlDrawQuad(0, plasmaProgress, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(7));

		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(21), plasmaProgress % 45 > 20 ? new Color(0f, 0f, 0f) : new Color(1f, 0.1f, 0.1f));
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(22), plasmaProgress % 45 > 20 ? new Color(0f, 0f, 0f) : new Color(1f, 0.1f, 0.1f));
		
		
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(1));

		for(Card c : Card.cards) c.update(main, delta);

		objective.update(main, delta);
		
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(8));

		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(9));
		
		status.update(main, delta);
		
		if(win){
			main.getFontPainter().hvlDrawWord("nice job!", main.getWidth()/16*5, main.getHeight()/8*3, new Color(0.1f, 1f, 0.1f));
			main.getFontPainter().hvlDrawWord("all opposition is gone", main.getWidth()/16*6f, main.getHeight()/8*4, 0.25f, new Color(0.1f, 1f, 0.1f));
			main.getFontPainter().hvlDrawWord("you can now retire in wealth", main.getWidth()/16*3f, main.getHeight()/8*5f, 0.5f, new Color(0.1f, 1f, 0.1f));
		}else{
			main.getFontPainter().hvlDrawWord("failure", main.getWidth()/16*5.5f, main.getHeight()/8*3, new Color(1f, 0.1f, 0.1f));
			main.getFontPainter().hvlDrawWord("you ran out of resources to invest", main.getWidth()/16*5f, main.getHeight()/8*4, 0.25f, new Color(1f, 0.1f, 0.1f));
			main.getFontPainter().hvlDrawWord("make smarter investments next time", main.getWidth()/16*2f, main.getHeight()/8*5, 0.5f, new Color(1f, 0.1f, 0.1f));
		}
	}

}
