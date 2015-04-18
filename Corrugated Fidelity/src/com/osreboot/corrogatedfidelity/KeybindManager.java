package com.osreboot.corrogatedfidelity;

import org.lwjgl.input.Keyboard;

public class KeybindManager {
	
	public static final int pause1 = Keyboard.KEY_ESCAPE;
	public static final int pause2 = Keyboard.KEY_P;
	public static final int help1 = Keyboard.KEY_H;
	public static final int menu1 = Keyboard.KEY_SPACE;
	
	public enum FunctionType{
		PAUSE, HELP, MENU
	}
	
	public static boolean isFunctionTriggering(FunctionType type){
		switch(type){
			case PAUSE: return Keyboard.isKeyDown(pause1) || Keyboard.isKeyDown(pause2);
			case HELP: return Keyboard.isKeyDown(help1);
			case MENU: return Keyboard.isKeyDown(menu1);
			default: return false;
		}
	}
	
}
