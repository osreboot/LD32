package com.osreboot.corrugatedfidelity;

import java.util.Random;

import com.osreboot.ridhvl.HvlFontUtil.HvlFontLayout;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2DBasic;

public class Main extends HvlTemplateInteg2DBasic{

	public enum GameState{
		PAUSED, RUNNING, WIN, LOSS
	}

	public static void main(String args[]){
		new Main();
	}

	private Framework framework;
	private GameState state;
	private InteractionManager interactionManager;
	private HvlFontPainter2D fontPainter;
	private SoundManager soundManager;
	private IntroManager introManager;
	private Random random;;

	public Main(){
		super(60, 1280, 720, "Corrugated Fidelity by Os_Reboot", "icon32", 40);
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

	public SoundManager getSoundManager(){
		return soundManager;
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
		
		getTextureLoader().loadResource("corpLudum");//11
		getTextureLoader().loadResource("corpDare");
		
		getTextureLoader().loadResource("target1");
		getTextureLoader().loadResource("target2");
		
		getTextureLoader().loadResource("corpSkew");//15
		getTextureLoader().loadResource("corpTek");
		getTextureLoader().loadResource("corpOs");
		
		getTextureLoader().loadResource("black");
		
		getTextureLoader().loadResource("sound");
		getTextureLoader().loadResource("soundM");//20
		
		getTextureLoader().loadResource("lights1");
		getTextureLoader().loadResource("lights2");
		
		getTextureLoader().loadResource("corpWatch");
		getTextureLoader().loadResource("corpHuh");
		getTextureLoader().loadResource("corpCarpet");
		getTextureLoader().loadResource("corpHover");
		getTextureLoader().loadResource("corpAccel");
		getTextureLoader().loadResource("corpCelsius");
		getTextureLoader().loadResource("corpConic");

		soundManager = new SoundManager(this);
		
		fontPainter = new HvlFontPainter2D(getTextureLoader().getResource(3), HvlFontLayout.SIMPLISTIC, 1024, 1024, 56, 72, 18);

		framework = new Framework();
		interactionManager = new InteractionManager();
		introManager = new IntroManager(this);
		state = GameState.RUNNING;

		framework.init(this);
	}

	@Override
	public void update(long delta){
		if(delta > 0){
			soundManager.update(this);
			if(state != GameState.WIN && state != GameState.LOSS) interactionManager.update(this, framework, delta);
			
			if(state == GameState.RUNNING){
				framework.updateRunning(this, delta);
				introManager.update(this, delta);
			}else if(state == GameState.WIN) framework.updateFinished(this, delta, true);
			else if(state == GameState.LOSS) framework.updateFinished(this, delta, false);
			else framework.updatePaused(this);
		}
	}

}
