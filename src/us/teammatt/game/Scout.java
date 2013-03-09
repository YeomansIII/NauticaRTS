package us.teammatt.game;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class Scout extends Unit {

	private int playerX,playerY;
	private int pTileX,pTileY,mpixX,mpixY;
	private int endX, endY;
	static int hp = 35;
	static int damage = 2;
	static int reloadTime = 1000000000;
	static int cost = 50;
	static int speed = 10;
	Image image,bullet;
	Path path = null;
	AStarPathFinder pathFinder;
	Polygon scoutPoly;
	Thread thread;
	Boolean move = false,midmove=false,shoot=false;
	ViewPort viewport;
	ArrayList<Unit> selectedUnits, allUnits;
	ArrayList<Integer> bulletList;
	Unit shootAt;

	public Scout(BlockMap map, ViewPort view,ArrayList<Unit> selectedUnits,ArrayList<Unit> allUnits) throws SlickException {
		this.selectedUnits = selectedUnits;
		this.allUnits = allUnits;
		bulletList = new ArrayList<Integer>();
		image = new Image("data/boat.png");
		bullet = new Image("data/bullet.png");
		image.setRotation(90);
		viewport = view;
		pTileX=25;
		pTileY=120;
		System.out.println("Viewport X: "+viewport.getX()+"  Viewport Y: "+viewport.getY());
		playerX=(pTileX-viewport.getX())*8;
		playerY=(pTileY-viewport.getY())*8;
		System.out.println("playerX: "+playerX+"  playerY: "+playerY);
		pathFinder = new AStarPathFinder(map, 1200, true);
		
		scoutPoly = new Polygon(new float[] { playerX, playerY + 20,
				playerX + 25, playerY + 20, playerX + 25, playerY + 45,
				playerX, playerY + 45 });
		System.out.println("Scout Created! " + this);
		thread = new Thread() {

			public synchronized void run() { 
				while(true) { System.out.print("");
				scoutPoly.setX((pTileX * 8)-(viewport.getX()*8));
				scoutPoly.setY((pTileY * 8)-(viewport.getY()*8));
				playerX = (int) scoutPoly.getX();
				playerY = (int) ((scoutPoly.getY())-20);
					if(move) {
						int startX = Math.round((pTileX));
						int startY = Math.round((pTileY));
						int direction = 0;
						endX = Math.round((endX / 8)+viewport.getX());
						endY = Math.round((endY / 8)+viewport.getY());
						path = pathFinder.findPath(null, startX, startY, endX,
								endY);
						if (path != null) {
							
							for (int i = 0; i < path.getLength(); i++) {
								
								if(path.getLength()>i+1) {
								if(path.getX(i)-path.getX(i+1)==0 && path.getY(i)-path.getY(i+1)==1) {
									image.setRotation(0);
									direction = 0;
									pTileY-=1;
								}
								else if(path.getX(i)-path.getX(i+1)==-1 && path.getY(i)-path.getY(i+1)==1) {
									image.setRotation(45);
									direction = 1;
									pTileX+=1;
									pTileY-=1;
								}
								else if(path.getX(i)-path.getX(i+1)==-1 && path.getY(i)-path.getY(i+1)==0) {
									image.setRotation(90);
									direction = 2;
									pTileX+=1;
								}
								else if(path.getX(i)-path.getX(i+1)==-1 && path.getY(i)-path.getY(i+1)==-1) {
									image.setRotation(135);
									direction = 3;
									pTileX+=1;
									pTileY+=1;
								}
								//else if(lastLocX-path.getX(i)==0 && lastLocY-path.getY(i)==1) {
								//	image.setRotation(180);
								//	direction = 4;
								//}
								else if(path.getX(i)-path.getX(i+1)==1 && path.getY(i)-path.getY(i+1)==-1) {
									image.setRotation(225);
									direction = 5;
									pTileX-=1;
									pTileY+=1;
								}
								else if(path.getX(i)-path.getX(i+1)==1 && path.getY(i)-path.getY(i+1)==0) {
									image.setRotation(270);
									direction = 6;
									pTileX-=1;
								}
								else if(path.getX(i)-path.getX(i+1)==1 && path.getY(i)-path.getY(i+1)==1) {
									image.setRotation(315);
									direction = 7;
									pTileX-=1;
									pTileY-=1;
								}
								else {
									image.setRotation(180);
									direction = 4;
									pTileY+=1;
								}
								}
								mpixX=pTileX*8;
								mpixY=pTileY*8;
								for(int p=0; p < 9; p++) {
									//System.out.println("mpix  X: "+mpixX+"  Y: "+mpixY);
									for(int k=0;k<30;k++) {
										scoutPoly.setX((mpixX)-(viewport.getX()*8));
										scoutPoly.setY((mpixY)-(viewport.getY()*8));
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										
										try {
											Thread.sleep(1);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
									if(direction==0) {
										
										mpixY-=1;
										scoutPoly.setX((pTileX*8)-(viewport.getX()*8));
										scoutPoly.setY((pTileY * 8)-(viewport.getY()*8)-p);
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==1) {
										mpixX+=1;
										mpixY-=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8)+p);
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8)-p);
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==2) {
										mpixX+=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8)+p);
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8));
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==3) {
										mpixX+=1;
										mpixY+=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8)+p);
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8)+p);
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==4) {
										mpixY+=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8));
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8)+p);
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==5) {
										mpixX-=1;
										mpixY+=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8)-p);
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8)+p);
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==6) {
										mpixX-=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8)-p);
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8));
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
									else if(direction==7) {
										mpixX-=1;
										mpixY-=1;
										scoutPoly.setX((path.getX(i) * 8)-(viewport.getX()*8)-p);
										scoutPoly.setY((path.getY(i) * 8)-(viewport.getY()*8)-p);
										playerX = (int) scoutPoly.getX();
										playerY = (int) ((scoutPoly.getY())-20);
										}
								}
								//System.out.println("playerX: "+playerX+"  playerY: "+playerY);
								if(midmove) {
									i=path.getLength();
									midmove=false;
								}
							}
							move = false;
						}
					}
					if(shoot) {
						while(shoot) {
							bulletList.add(1);
							bulletList.add(0);
							for(int i=0;i<bulletList.size();i+=2) {
								if(!(bulletList.get(i)>100)) {
								bulletList.set(i,bulletList.get(i)+4);
								}
								else {
									bulletList.remove(i); bulletList.remove(i+1); }
							}
						}
					}
				}
			}

		};

		thread.start();
	}

	public void select() {

	}

	public void unSelect() {

	}

	@Override
	public void move(int endX, int endY) {
		if(move) {
			midmove=true;
		}
		move = true;
		this.endX = endX;
		this.endY = endY;
		System.out.println("move called");
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, playerX, playerY);
		g.draw(scoutPoly);
		if(shoot) {
			for(int i=0;i<bulletList.size()-4;i+=2) {
				g.drawImage(bullet,playerX+bulletList.get(i),playerX+bulletList.get(i+1));
			}
		}
		if (path != null) {
			for (int i = 1; i < path.getLength(); i++) {
				g.drawLine((path.getX(i - 1) * 8)-(viewport.getX()*8), (path.getY(i - 1) * 8)-(viewport.getY()*8),
						(path.getX(i) * 8)-(viewport.getX()*8), (path.getY(i) * 8)-(viewport.getY()*8));
			}
		}
	}

	@Override
	public boolean clickIn(int x, int y) {
		if (x < scoutPoly.getMaxX() && x > scoutPoly.getMinX()
				&& y < scoutPoly.getMaxY() && y > scoutPoly.getMinY()) {
			return true;
		}
		return false;
	}

	@Override
	public void attack(boolean attack, int x, int y) {
		if(attack) {
			for(int i=0;i<allUnits.size();i++) {
				if(allUnits.get(i).clickIn(x, y)) {
					shootAt = allUnits.get(i);
					shoot = true;
				}
			}
		}
		else {
			shoot = false;
		}
	}

}
