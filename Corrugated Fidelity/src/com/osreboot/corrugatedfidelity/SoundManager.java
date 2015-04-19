package com.osreboot.corrugatedfidelity;

import org.newdawn.slick.openal.SoundStore;

import com.osreboot.ridhvl.loader.HvlSoundLoader;

public class SoundManager {

	private HvlSoundLoader sound;

	private boolean muted = false;

	public enum SoundEvent{
		CLICK, DROP, PICKUP, ERROR, SLIDE, FAILURE
	}

	public void setMuted(boolean mutedArg){
		muted = mutedArg;
	}
	
	public boolean isMuted(){
		return muted;
	}

	public SoundManager(Main main){
		sound = new HvlSoundLoader(10);
		sound.loadResource("click");
		sound.loadResource("drop");
		sound.loadResource("pickup");
		sound.loadResource("error");
		sound.loadResource("sale");
		sound.loadResource("slider");
		sound.loadResource("failure");
	}

	public void update(Main main){
		SoundStore.get().poll(0);

	}

	public void playSound(SoundEvent event){
		if(!muted){
			switch(event){
			case CLICK: sound.getResource(4).playAsSoundEffect(1.5f, 0.5f, false); break;
			case DROP: sound.getResource(1).playAsSoundEffect(1, 1, false); break;
			case PICKUP: sound.getResource(2).playAsSoundEffect(1, 1, false); break;
			case ERROR: sound.getResource(3).playAsSoundEffect(1, 0.25f, false); break;
			case SLIDE: sound.getResource(5).playAsSoundEffect(1, 0.5f, false); break;
			case FAILURE: sound.getResource(6).playAsSoundEffect(1, 0.5f, false); break;
			default: break;
			}
		}
	}

}
