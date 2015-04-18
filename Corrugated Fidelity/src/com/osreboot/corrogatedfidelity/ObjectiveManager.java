package com.osreboot.corrogatedfidelity;

import org.newdawn.slick.Color;

import com.osreboot.corrogatedfidelity.gameobject.Card;
import com.osreboot.corrogatedfidelity.gameobject.Corporation;

public class ObjectiveManager {

	private int inventory = 10;

	public void update(Main main, long delta){
		main.getFontPainter().hvlDrawWord(inventory + ";", main.getWidth()/8, main.getWidth()/8, 0.5f, new Color(1f, 1f, 1f));

		for(Card c : Card.cards){
			if(c.getCorporation() != null && c.isOnGround() && c.getX() < main.getWidth()/4){
				if((c.getCorporation().getVelocity() > 0 && main.getRandom().nextInt((int)(500/delta) + 1) == 0) || 
						(c.getCorporation().getVelocity() < 0 && main.getRandom().nextInt((int)(3000/delta) + 1) == 0)|| 
						c.getCorporation().isFailed())
					c.sellFromCorporation(this);
			}
		}
	}

	public boolean buyShare(Corporation corp){
		if(inventory >= corp.getPrice()){
			inventory -= corp.getPrice();
			return true;
		}return false;
	}

	public void sellShare(Corporation corp){
		inventory += corp.getPrice();
	}

}
