package com.osreboot.corrugatedfidelity;

import java.util.ArrayList;

import org.newdawn.slick.Color;

public class FadingWord {

	public static ArrayList<FadingWord> words = new ArrayList<FadingWord>();
	
	private float elapsed, speed, x, y, scale;
	private boolean running;
	private String word;
	private Color color;
	
	public FadingWord(String wordArg, float xArg, float yArg, float scaleArg, float speedArg, boolean runningArg){
		word = wordArg;
		x = xArg;
		y = yArg;
		scale = scaleArg;
		speed = speedArg;
		color = new Color(1f, 1f, 1f, 1f);
		running = runningArg;
	}
	
	public void update(Main main, long delta){
		if(running){
			elapsed += delta*speed;
			if(elapsed > 3000){
				elapsed = 0;
				running = false;
			}
		}
		if(elapsed < 1000) color.a = elapsed/1000f;
		else if(elapsed >= 1000 && elapsed < 2000) color.a = 1f;
		else color.a = (1000 - (elapsed - 2000))/1000f;
		main.getFontPainter().hvlDrawWord(word, x, y, scale, color);
	}
	
	public void setRunning(boolean runningArg){
		running = runningArg;
	}
	
	public void setProgress(float progress){
		elapsed = progress;
	}
	
	public void setWord(String wordArg){
		word = wordArg;
	}
	
	public void setColor(Color colorArg){
		color = colorArg;
	}
	
}
