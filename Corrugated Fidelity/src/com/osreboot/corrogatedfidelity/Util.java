package com.osreboot.corrogatedfidelity;

import org.newdawn.slick.Color;

import com.osreboot.corrogatedfidelity.gameobject.Corporation;

public class Util {

	public static Color getVelocityColor(Corporation corp){
		if(corp.isFailed()) return new Color(1f, 0.1f, 0.1f);
		else if(corp.getVelocity() > 0.1f) return new Color(0.6f, 1f, 0.6f);
		else if(corp.getVelocity() < -0.1f) return new Color(1f, 0.6f, 0.6f);
		else return new Color(1f, 1f, 1f);
	}
	
}
