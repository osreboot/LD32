package com.osreboot.corrugatedfidelity;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import org.newdawn.slick.Color;

public class IntroManager {

	private FadingWord objective, help;
	
	public IntroManager(Main main){
		objective = new FadingWord("bankrupt all competition", main.getWidth()/64*6.75f, main.getHeight()/16*7.75f, 0.75f, 1f, false);
		objective.setColor(new Color(1f, 0.1f, 0.1f));
		
		help = new FadingWord("press [p] for help", main.getWidth()/64*27, main.getHeight()/16*3.5f, 0.25f, 0.5f, false);
		help.setColor(new Color(0.1f, 1f, 0.1f));
	}

	private long timer = 0;
	private float counter = 0;

	public void update(Main main, long delta){
		if(timer < 10) timer += delta; else{
			timer = 0;
			counter++;
		}
		if(counter < 300){
			hvlDrawQuad(0, 0, 2048, 2048, main.getTextureLoader().getResource(18), new Color(1f, 1f, 1f, 1f - (counter/300)));
		}
		if(counter < 150){
			main.getFontPainter().hvlDrawWord("corrugated fidelity", main.getWidth()/64*6, main.getHeight()/8*3, new Color(0.1f, 1f, 0.1f, 1f - (counter/150)));
			main.getFontPainter().hvlDrawWord("by osreboot", main.getWidth()/64*24, main.getHeight()/8*4, 0.5f, new Color(0.1f, 1f, 0.1f, 1f - (counter/150)));
		}
		if(counter == 150) objective.setRunning(true);
		objective.update(main, delta);
		if(counter == 250) help.setRunning(true);
		help.update(main, delta);
	}

}
