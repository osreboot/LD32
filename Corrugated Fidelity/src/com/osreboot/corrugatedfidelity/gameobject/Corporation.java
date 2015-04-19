package com.osreboot.corrugatedfidelity.gameobject;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import com.osreboot.corrugatedfidelity.Main;
import com.osreboot.corrugatedfidelity.StatusManager;
import com.osreboot.corrugatedfidelity.SoundManager.SoundEvent;

public class Corporation {

	public static ArrayList<Corporation> corps = new ArrayList<Corporation>();

	private float priceVelocity = 0, price, priceBoundaryUp, priceBoundaryDown;
	private String name, abbreviation;
	private Texture texture;
	private boolean failed = false;

	private long heat = 0;
	
	public Corporation(Main main, String nameArg, String abbreviationArg, Texture textureArg){
		name = nameArg;
		abbreviation = abbreviationArg;
		texture = textureArg;

		priceVelocity = (float)main.getRandom().nextInt(1000)/100f;
		priceBoundaryUp = (float)main.getRandom().nextInt(10000)/100f;
		priceBoundaryDown = priceBoundaryUp/10f;
		price = priceBoundaryDown + (float)main.getRandom().nextInt(1000)/100f + 5f;

		corps.add(this);
	}

	public void update(Main main, StatusManager status, long delta){
		if(heat > 0) heat = Math.max(0, heat - delta); else if(heat < 0) heat = Math.min(0, heat + delta);
		
		if(!failed){
			price += priceVelocity/delta/10;
			if(price > priceBoundaryUp && main.getRandom().nextInt((int)(2000/delta) + 1) == 0) priceVelocity = -(float)main.getRandom().nextInt(500)/500f;
			if(price < priceBoundaryDown && main.getRandom().nextInt((int)(200/delta) + 1) == 0) priceVelocity += (float)main.getRandom().nextInt(500)/500f;
			if(price < priceBoundaryDown && main.getRandom().nextInt((int)(10000/delta) + 1) == 0) priceVelocity += (float)main.getRandom().nextInt(500)/100f;
			if((price < priceBoundaryDown && main.getRandom().nextInt((int)(20000/delta) + 1) == 0) || price < 1){
				price = 0;
				failed = true;
				status.informFailure(this);
				main.getSoundManager().playSound(SoundEvent.FAILURE);
			}
			if(heat > 1000){
				if(main.getRandom().nextInt((int)(400/delta) + 1) == 0) priceVelocity += (float)main.getRandom().nextInt(500)/100f;
			}else if(heat < -1000){
				if(main.getRandom().nextInt((int)(400/delta) + 1) == 0) priceVelocity -= (float)main.getRandom().nextInt(500)/100f;
			}
			if(main.getRandom().nextInt((int)(4000/delta) + 1) == 0) priceVelocity = (float)main.getRandom().nextInt(500)/100f - 2.5f;
		}
	}
	
	public void addHeat(Main main){
		heat += main.getRandom().nextInt(500);
	}
	
	public void subtractHeat(Main main){
		heat -= main.getRandom().nextInt(1000);
	}

	public boolean isFailed(){
		return failed;
	}

	public String getName(){
		return name;
	}

	public String getAbbreviation(){
		return abbreviation;
	}

	public Texture getTexture(){
		return texture;
	}

	public int getPrice(){
		return (int)price;
	}

	public float getVelocity(){
		return priceVelocity;
	}

}
