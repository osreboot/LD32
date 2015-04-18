package com.osreboot.corrogatedfidelity;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.newdawn.slick.Color;

import com.osreboot.corrogatedfidelity.KeybindManager.FunctionType;
import com.osreboot.corrogatedfidelity.gameobject.Card;
import com.osreboot.corrogatedfidelity.gameobject.Corporation;

public class Framework {

	private ObjectiveManager objective;
	private StatusManager status;

	private long plasmaProgress = 0;

	public void init(Main main){
		new Card(main.getWidth()/8*3, main.getHeight()/2);
		new Card(main.getWidth()/2, main.getHeight()/2);
		new Card(main.getWidth()/8*5, main.getHeight()/2);

		new Corporation(main, "haze", "HZE", main.getTextureLoader().getResource(5));
		new Corporation(main, "gradient", "GNT", main.getTextureLoader().getResource(6));
		new Corporation(main, "gradient2", "GN2", main.getTextureLoader().getResource(6));
		new Corporation(main, "gradient3", "GN3", main.getTextureLoader().getResource(6));
		new Corporation(main, "gradient4", "GN4", main.getTextureLoader().getResource(6));
		new Corporation(main, "gradient5", "GN5", main.getTextureLoader().getResource(6));

		objective = new ObjectiveManager();
		status = new StatusManager(main);
	}

	public void updateRunning(Main main, long delta){
		plasmaProgress = plasmaProgress < 720 ? plasmaProgress + (delta/10) : 0;
		hvlDrawQuad(0, plasmaProgress - 720, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(7));
		hvlDrawQuad(0, plasmaProgress, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(7));

		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(1));

		for(Card c : Card.cards) c.update(main, delta);
		for(Corporation c : Corporation.corps) c.update(main, status, delta);

		objective.update(main, delta);
		
		float extension = main.getInteractionManager().getMenuExtension();
		if(main.getInteractionManager().getMenuExtension() != 0) hvlDrawQuad((main.getWidth()/8*6) - extension, 0, 2048, 2048, 0f, 0f, 1f, 1f, main.getTextureLoader().getResource(10), new Color(1f, 1f, 1f, 1f));

		if(main.getInteractionManager().getMenuExtension() > 0){
			for(Corporation c : Corporation.corps){
				hvlDrawQuad((main.getWidth()/8*6) - extension + (main.getWidth()/64*11) + (Corporation.corps.indexOf(c)*main.getWidth()/16), (main.getHeight()/64*15), main.getWidth()/16, main.getHeight()/16, 0, 0, 0.5f, 0.25f, c.getTexture(), Util.getVelocityColor(c));//TODO arrows to indicate company status
				main.getFontPainter().hvlDrawWord((int)c.getPrice() + "", (main.getWidth()/8*6) - extension + (main.getWidth()/64*11) + (Corporation.corps.indexOf(c)*main.getWidth()/16), (main.getHeight()/64*15) + main.getHeight()/16, main.getWidth()/16, main.getHeight()/16, Util.getVelocityColor(c));
			}
		}
		
		hvlDrawQuad(-main.getInteractionManager().getMenuExtension(), 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(8));

		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(9));

		status.update(main, delta);

		if(KeybindManager.isFunctionTriggering(FunctionType.HELP)){
			if(main.getInteractionManager().isMenuOpen()){
				main.getFontPainter().hvlDrawWord("click an icon to purchase stock", main.getWidth()/16*5.5f, main.getHeight()/32*5, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("red - falling price   green - rising price", main.getWidth()/16*4.5f, main.getHeight()/32*29.5f, 0.25f, new Color(1f, 1f, 1f));
			}else{
				main.getFontPainter().hvlDrawWord("drop stock", main.getWidth()/16*2f, main.getHeight()/16*7.5f, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("here to", main.getWidth()/16*2f, main.getHeight()/16*8.5f, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("sell", main.getWidth()/16*2f, main.getHeight()/16*9.5f, 0.25f, new Color(1f, 1f, 1f));
				
				main.getFontPainter().hvlDrawWord("drop stock", main.getWidth()/32*24f, main.getHeight()/16*7, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("here and", main.getWidth()/32*24f, main.getHeight()/16*8, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("buy from", main.getWidth()/32*24f, main.getHeight()/16*9, 0.25f, new Color(1f, 1f, 1f));
				main.getFontPainter().hvlDrawWord("side menu", main.getWidth()/32*24f, main.getHeight()/16*10, 0.25f, new Color(1f, 1f, 1f));
			}
		}
	}

	public void updatePaused(Main main){
		hvlDrawQuad(0, 0, 1280, 720, 0, 0, 1280f/2048f, 720f/2048f, main.getTextureLoader().getResource(2));
		main.getFontPainter().hvlDrawWord("paused", main.getWidth()/16, main.getHeight()/16, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("information", main.getWidth()/16*7, main.getHeight()/16*3, 0.5f, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("corporation name", main.getWidth()/16*6, main.getHeight()/16*5, 0.5f, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("quantity of stock owned", main.getWidth()/16*6, main.getHeight()/16*9, 0.5f, new Color(1f, 1f, 1f));
		main.getFontPainter().hvlDrawWord("current stock value", main.getWidth()/16*6, main.getHeight()/16*11, 0.5f, new Color(1f, 1f, 1f));

		main.getFontPainter().hvlDrawWord("hold [h] while in-game for more help", main.getWidth()/32*3, main.getHeight()/16*15, 0.5f, new Color(1f, 1f, 1f));
	}

	public ObjectiveManager getObjectiveManager(){
		return objective;
	}

}
