package us.teammatt.game;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

public class Scout extends Unit {

	static int hp = 35;
	static int damage = 2;
	static int reloadTime = 1000000000;
	static int cost = 50;
	static int speed = 10;
	static int offset = 20;
	static String image = "data/boat.png";
	static int[] stats = {hp,damage,reloadTime,cost,speed,offset};
	
	public Scout(BlockMap map, ViewPort view, ArrayList<Unit> selectedUnits,
			ArrayList<Unit> allUnits) throws SlickException {
		super(map, view, selectedUnits, allUnits,stats,image);
	}


}
