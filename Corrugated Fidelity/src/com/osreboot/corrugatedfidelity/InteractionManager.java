package com.osreboot.corrugatedfidelity;

import org.lwjgl.input.Mouse;

import com.osreboot.corrugatedfidelity.KeybindManager.FunctionType;
import com.osreboot.corrugatedfidelity.Main.GameState;
import com.osreboot.corrugatedfidelity.SoundManager.SoundEvent;
import com.osreboot.corrugatedfidelity.gameobject.Card;
import com.osreboot.corrugatedfidelity.gameobject.Corporation;
import com.osreboot.ridhvl.HvlMath;

public class InteractionManager {

	private boolean triggeredPause = false;

	private Card selected;
	private float xos, yos;

	private boolean menuOpen = false, clicked = false, keyTriggered = false;

	private float menuExtension = 0;

	public void update(Main main, Framework framework, long delta){
		if(KeybindManager.isFunctionTriggering(FunctionType.PAUSE)){
			if(!triggeredPause){
				main.setState(main.getState() == GameState.PAUSED ? GameState.RUNNING : GameState.PAUSED);
				triggeredPause = true;
			}
		}else triggeredPause = false;

		if(main.getState() != GameState.PAUSED){
			menuExtension = HvlMath.stepTowards(menuExtension, delta*2, main.getInteractionManager().isMenuOpen() ? main.getWidth()/8*6 : 0);

			if(Mouse.isInsideWindow()){//TODO drag or toggle
				if(!menuOpen){
					if(selected == null){
						if(Mouse.isButtonDown(0)){
							for(Card c : Card.cards) if(c.isInBounds(Mouse.getX(), main.getHeight() - Mouse.getY())){
								selected = c;
								xos = Mouse.getX() - c.getX();
								yos = main.getHeight() - Mouse.getY() - c.getY();
								break;
							}
						}
					}else{
						selected.setX(Math.max(main.getWidth()/8, Math.min(Mouse.getX() - xos, main.getWidth() - (main.getWidth()/8))));
						selected.setY(Math.max(main.getHeight()/4, Math.min(main.getHeight() - Mouse.getY() - yos, main.getHeight() - (main.getHeight()/8))));
						if(!Mouse.isButtonDown(0)){
							selected = null;
							main.getSoundManager().playSound(SoundEvent.DROP);
						}
					}
				}else{
					if(menuExtension == main.getWidth()/8*6){
						if(!clicked && Mouse.isButtonDown(0)){
							if(Mouse.getX() > main.getWidth()/8 && Mouse.getX() < main.getWidth()/8*7 && main.getHeight() - Mouse.getY() > main.getHeight()/4 && main.getHeight() - Mouse.getY() < main.getHeight()/8*6){
								for(Corporation c : Corporation.corps){
									if(!c.isFailed() && Mouse.getX() > (main.getWidth()/64*11) + ((Corporation.corps.indexOf(c)%11)*main.getWidth()/16) &&
											Mouse.getX() < (main.getWidth()/64*11) + ((Corporation.corps.indexOf(c)%11)*main.getWidth()/16) + main.getWidth()/16 &&
											main.getHeight() - Mouse.getY() > (main.getHeight()/64*15) + (Corporation.corps.indexOf(c)/11*main.getHeight()/8) && //TODO multiply by row
											main.getHeight() - Mouse.getY() < (main.getHeight()/64*15) + (Corporation.corps.indexOf(c)/11*main.getHeight()/8) + main.getHeight()/8){
										for(Card card : Card.cards){
											if(card.isOnGround() && card.getX() > main.getWidth()/4*3){
												card.syncToCorporation(main, c, framework.getObjectiveManager());
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				if(KeybindManager.isFunctionTriggering(FunctionType.MENU)){
					if(!keyTriggered){
						menuOpen = !menuOpen;
						main.getSoundManager().playSound(SoundEvent.SLIDE);
						selected = null;
					}
					keyTriggered = true;
				}else keyTriggered = false;
				if(Mouse.isButtonDown(0)){
					if(Mouse.getX() > main.getWidth()/16*14 && !clicked && (menuExtension == main.getWidth()/8*6 || menuExtension == 0)){
						menuOpen = !menuOpen;
						main.getSoundManager().playSound(SoundEvent.SLIDE);
						selected = null;
					}
					clicked = true;
				}else clicked = false;
			}
		}
	}

	public boolean isMenuOpen(){
		return menuOpen;
	}

	public float getMenuExtension(){
		return menuExtension;
	}

	public Card getSelected(){
		return selected;
	}

}
