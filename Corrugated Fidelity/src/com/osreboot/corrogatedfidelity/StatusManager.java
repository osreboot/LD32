package com.osreboot.corrogatedfidelity;

import org.newdawn.slick.Color;

import com.osreboot.corrogatedfidelity.gameobject.Corporation;

public class StatusManager {
	
	private FadingWord word;
	
	public StatusManager(Main main){
		word = new FadingWord("", main.getWidth()/8, main.getHeight()/8*6, 0.5f, 1, false);
	}
	
	public void update(Main main, long delta){
		word.update(main, delta);
	}
	
	public void informFailure(Corporation corp){
		word.setRunning(true);
		word.setWord("update: " + corp.getName() + " has failed!");
		word.setColor(new Color(1f, 0.1f, 0.1f));
	}
	
}
