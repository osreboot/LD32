package com.osreboot.corrogatedfidelity.gameobject;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.corrogatedfidelity.Main;
import com.osreboot.corrogatedfidelity.ObjectiveManager;
import com.osreboot.corrogatedfidelity.Util;
import com.osreboot.ridhvl.HvlMath;

public class Card {

	public static ArrayList<Card> cards = new ArrayList<Card>();
	public static final float width = 100, height = 150;

	private float x, y, z;
	private Corporation corp;
	private int shares;
	private Color color = new Color(1f, 1f, 1f);

	public Card(float xArg, float yArg){
		x = xArg;
		y = yArg;
		z = 0;

		shares = 0;

		cards.add(this);
	}

	public boolean isInBounds(float xArg, float yArg){
		return xArg > x - (width/2) && xArg < x + (width/2) && yArg > y - (height/2) && yArg < y + (height/2);
	}

	public void update(Main main, long delta){
		if(corp != null){
			color = Util.getVelocityColor(corp);
		}else color = new Color(1f, 1f, 1f);
		
		if(main.getInteractionManager().getSelected() == this) z = HvlMath.stepTowards(z, delta/3, 20); else z = HvlMath.stepTowards(z, delta/3, 0);

		hvlRotate(x, y - z, (x - main.getWidth()/2)/40);
		if(corp != null) main.getFontPainter().hvlDrawWord(corp.getName(), x - (width/2), y - (height/4) - z, width, height/4, color);
		if(corp != null) main.getFontPainter().hvlDrawWord(shares + "", x - (width/2), y - z, width, height/4, color);
		if(corp != null) main.getFontPainter().hvlDrawWord((int)corp.getPrice() + "", x - (width/2), y + (height/4) - z, width, height/4, color);
		hvlDrawQuad(x - (width/2), y - (height/2) - z, width, height, 0.5f, 0f, 1, 0.75f, corp == null ? main.getTextureLoader().getResource(4) : corp.getTexture(), color);
		hvlResetRotation();

		/*hvlRotate(x, y - 5 - z, (x - main.getWidth()/2)/40);
		if(corp != null) main.getFontPainter().hvlDrawWord(corp.getName(), x - (width/2), y - (height/4) + 5 - z, width, height/4, new Color(1f, 1f, 1f));
		if(corp != null) main.getFontPainter().hvlDrawWord(shares + "", x - (width/2), y + 5 - z, width, height/4, new Color(1f, 1f, 1f));
		hvlDrawQuad(x - (width/2), y - (height/2) + 5 - z, width, height, 0.5f, 0f, 1, 0.75f, corp == null ? main.getTextureLoader().getResource(4) : corp.getTexture(), new Color(1f, 1f, 1f));
		hvlResetRotation();*/
	}

	public void syncToCorporation(Corporation corpArg, ObjectiveManager objective){
		if(corp == corpArg){
			if(objective.buyShare(corpArg)) shares++;
		}else{
			boolean exists = false;
			for(Card c : cards) if(c != this && c.getCorporation() == corpArg) exists = true;
			if(corp == null && !exists && objective.buyShare(corpArg)){
				corp = corpArg;
				shares++;
			}
		}
	}

	public void sellFromCorporation(ObjectiveManager objective){
		if(corp != null && shares > 0){
			objective.sellShare(corp);
			shares = Math.max(shares - 1, 0);
			if(shares < 1) corp = null;
		}
	}

	public Corporation getCorporation(){
		return corp;
	}

	public void setCorporation(Corporation corpArg){
		corp = corpArg;
	}

	public float getX(){
		return x;
	}

	public int getShares(){
		return shares;
	}

	public void setShares(int sharesArg){
		shares = sharesArg;
	}

	public float getY(){
		return y;
	}
	
	public boolean isOnGround(){
		return z == 0;
	}

	public void setX(float xArg){
		x = xArg;
	}

	public void setY(float yArg){
		y = yArg;
	}

}
