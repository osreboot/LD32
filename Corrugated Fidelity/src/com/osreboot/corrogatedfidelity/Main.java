package com.osreboot.corrogatedfidelity;

import java.util.Random;

import com.osreboot.ridhvl.HvlFontUtil.HvlFontLayout;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2DBasic;

public class Main extends HvlTemplateInteg2DBasic{

	public enum GameState{
		PAUSED, RUNNING
	}

	public static void main(String args[]){
		new Main();
	}

	private Framework framework;
	private GameState state;
	private InteractionManager interactionManager;
	private HvlFontPainter2D fontPainter;
	private Random random;;

	public Main(){
		super(60, 1280, 720, "Corrugated Fidelity by Os_Reboot", 20);
	}

	public Random getRandom(){
		return random;
	}

	public void setState(GameState stateArg){
		state = stateArg;
	}

	public GameState getState(){
		return state;
	}

	public HvlFontPainter2D getFontPainter(){
		return fontPainter;
	}

	public InteractionManager getInteractionManager(){
		return interactionManager;
	}

	@Override
	public void initialize(){
		random = new Random();
		getTextureLoader().loadResource("white");
		getTextureLoader().loadResource("background");
		getTextureLoader().loadResource("backgroundblur");
		getTextureLoader().loadResource("font");

		getTextureLoader().loadResource("corpTemplate");
		getTextureLoader().loadResource("corpHaze");
		getTextureLoader().loadResource("corpGradient");
		
		getTextureLoader().loadResource("plasma");
		getTextureLoader().loadResource("slider");
		getTextureLoader().loadResource("cover");
		getTextureLoader().loadResource("backcover");

		fontPainter = new HvlFontPainter2D(getTextureLoader().getResource(3), HvlFontLayout.SIMPLISTIC, 1024, 1024, 56, 72, 18);

		framework = new Framework();
		interactionManager = new InteractionManager();
		state = GameState.RUNNING;

		framework.init(this);
	}

	@Override
	public void update(long delta){
		if(delta > 0){
			interactionManager.update(this, framework, delta);
			if(state == GameState.RUNNING) framework.updateRunning(this, delta);
			else framework.updatePaused(this);
		}
	}

}
