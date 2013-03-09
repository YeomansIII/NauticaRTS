package us.teammatt.game;

import org.newdawn.slick.Graphics;


public abstract class Unit{
	
	public abstract void move(int endX, int endY);
	
	public abstract void select();
	
	public abstract void unSelect();
	
	public abstract void draw(Graphics g);
	
	public abstract boolean clickIn(int x, int y);
	
	public abstract void attack(boolean attack, int x, int y);
	
	
}
