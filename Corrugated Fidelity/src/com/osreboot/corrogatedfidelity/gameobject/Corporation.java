package com.osreboot.corrogatedfidelity.gameobject;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import com.osreboot.corrogatedfidelity.Main;
import com.osreboot.corrogatedfidelity.StatusManager;

public class Corporation {

	public static ArrayList<Corporation> corps = new ArrayList<Corporation>();

	private float priceVelocity = 0, price, priceBoundaryUp, priceBoundaryDown;
	private String name, abbreviation;
	private Texture texture;
	private boolean failed = false;

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
		if(!failed){
			price += priceVelocity/delta/10;
			if(price > priceBoundaryUp && main.getRandom().nextInt((int)(2000/delta) + 1) == 0) priceVelocity = -(float)main.getRandom().nextInt(500)/500f;
			if(price < priceBoundaryDown && main.getRandom().nextInt((int)(200/delta) + 1) == 0) priceVelocity = (float)main.getRandom().nextInt(500)/500f;
			if((price < priceBoundaryDown && main.getRandom().nextInt((int)(15000/delta) + 1) == 0) || price < 1){
				price = 0;
				failed = true;
				status.informFailure(this);
			}
			if(main.getRandom().nextInt((int)(4000/delta) + 1) == 0) priceVelocity = (float)main.getRandom().nextInt(500)/100f - 2.5f;
		}
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
