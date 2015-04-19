package com.osreboot.corrugatedfidelity;

import org.newdawn.slick.Color;

import com.osreboot.corrugatedfidelity.Main.GameState;
import com.osreboot.corrugatedfidelity.SoundManager.SoundEvent;
import com.osreboot.corrugatedfidelity.gameobject.Card;
import com.osreboot.corrugatedfidelity.gameobject.Corporation;

public class ObjectiveManager {

	private int inventory = 10;

	public void update(Main main, long delta){
		main.getFontPainter().hvlDrawWord(inventory + ";", main.getWidth()/16*10f, main.getHeight()/8*6.5f, 0.5f, new Color(1f, 1f, 1f));

		int totalValue = 0;
		for(Card c : Card.cards){
			if(c.getCorporation() != null && c.isOnGround() && c.getX() < main.getWidth()/4){
				if((c.getCorporation().getVelocity() > 0 && main.getRandom().nextInt((int)(500/delta) + 1) == 0) || 
						(c.getCorporation().getVelocity() < 0 && main.getRandom().nextInt((int)(1000/delta) + 1) == 0)|| 
						c.getCorporation().isFailed())
					c.sellFromCorporation(main, this);
			}
			if(c.getCorporation() != null) totalValue += c.getShares()*c.getCorporation().getPrice();
		}
		
		if(inventory == 0 && totalValue == 0) main.setState(GameState.LOSS);
		
		boolean win = true;
		for(Corporation c : Corporation.corps){
			if(!c.isFailed()){
				win = false;
				break;
			}
		}
		if(win) main.setState(GameState.WIN);
	}

	public boolean buyShare(Main main, Corporation corp){
		corp.addHeat(main);
		if(inventory >= corp.getPrice()){
			inventory -= corp.getPrice();
			main.getSoundManager().playSound(SoundEvent.PICKUP);
			return true;
		}else{
			main.getSoundManager().playSound(SoundEvent.ERROR);
			return false;
		}
	}

	public void sellShare(Main main, Corporation corp){
		corp.subtractHeat(main);
		main.getSoundManager().playSound(SoundEvent.CLICK);
		inventory += corp.getPrice();
	}

}
