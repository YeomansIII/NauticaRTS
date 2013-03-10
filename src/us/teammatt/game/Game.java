package us.teammatt.game;



import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
 
public class Game extends BasicGame {
 
	Image player;
	private Polygon playerPoly;
	public static BlockMap map;
	Color color = Color.white;
	boolean pressedS,pressedD;
	ArrayList<Unit> selectedUnits;
	ArrayList<Unit> allUnits;
	Graphics gr = null;
	ViewPort viewport;
	GameContainer contain;
	
	public Game() {
		super("Dude");
	}
 
	public void init(GameContainer container) throws SlickException {
		//SpriteSheet sheet = new SpriteSheet("data/karbonator.png",32,32);
		contain = container;
		pressedS = false;
		selectedUnits = new ArrayList<Unit>();
		allUnits = new ArrayList<Unit>();
		viewport = new ViewPort(0, 85);
		map = new BlockMap("data/map.tmx");
		container.setTargetFrameRate(60);
		for (int x = 0; x < map.getWidthInTiles(); x++) {
			System.out.println();
			/* for (int y = 0; y < map.getHeightInTiles(); y++) {
				int i = map.getTileId(x, y, 0);
		System.out.print(" " + i + ","); 
	} */
		}
	}
 
	public void update(GameContainer container, int delta) throws SlickException {
	
		if (container.getInput().isKeyDown(Input.KEY_S) && !pressedS) {
			pressedS = true;
			Scout temp = new Scout(map,viewport,selectedUnits,allUnits);
			allUnits.add(temp);
		}
		if (container.getInput().isKeyDown(Input.KEY_D) && !pressedD) {
			pressedD = true;
			Destroyer temp = new Destroyer(map,viewport,selectedUnits,allUnits);
			allUnits.add(temp);
		}
		if(!container.getInput().isKeyDown(Input.KEY_S)) {
			pressedS = false;
		}
		if(!container.getInput().isKeyDown(Input.KEY_D)) {
			pressedD = false;
		}
		int mouseX = container.getInput().getMouseX();
		int mouseY = container.getInput().getMouseY();
		if(mouseY<7) {
			viewport.setY(-1);
		}
		if(mouseY>793) {
			viewport.setY(1);
		}
		if(mouseX<7) {
			viewport.setX(-1);
		}
		if(mouseX>793) {
			viewport.setX(1);
		}
	}
 
	public boolean entityCollisionWith() throws SlickException {
		for (int i = 0; i < BlockMap.entities.size(); i++) {
			Block entity1 = (Block) BlockMap.entities.get(i);
			if (playerPoly.intersects(entity1.poly)) {
				return true;
			}       
		}       
		return false;
	}
 
	public void render(GameContainer container, Graphics g)  {
		BlockMap.tmap.render(-1*(viewport.getX()*8),-1*(viewport.getY()*8));
		for(int i = 0;i<allUnits.size();i++) {
			allUnits.get(i).draw(g);
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		System.out.println("B: "+button+" X: "+x+" Y: "+y);
		if(button == 0) {
			boolean selectAny = false;
			 for(int i=0;i<allUnits.size();i++) {
				 if(allUnits.get(i).clickIn(x, y)) {
					 selectedUnits.add(allUnits.get(i));
					 selectAny = true;
				 }
			 }
			 if(!selectAny) {
				 selectedUnits.clear();
			 }
		}
		if(button == 1) {
			if (contain.getInput().isKeyDown(Input.KEY_A)){
				for(int i=0;i<selectedUnits.size();i++) {
					selectedUnits.get(i).attack(true,x,y);
					System.out.println("Attacking: "+x+", "+y);
				}
			}
			else if(selectedUnits.size()>0) {
				int sideStep = selectedUnits.size()*16;
				for(int i=0;i<selectedUnits.size();i++) {
					selectedUnits.get(i).move(x+sideStep, y+sideStep);
					sideStep-=16;
				}
			}
			else {
				for(int i=0;i<selectedUnits.size();i++) {
				selectedUnits.get(i).attack(false,0,0);
				}
			}
		}
	}
	
	
	public static void main(String[] argv) throws SlickException {

		AppGameContainer container = 
			new AppGameContainer(new Game(), 800, 800, false);
		container.start();
}
}