import info.gridworld.actor.*;
import info.gridworld.grid.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MinuWaller extends Actor {//this is what the player controls with the arrow keys. It moves around the grid and leaves behind a trail of MinuWalls.
	
	private boolean alive;//private data members
	private boolean aboutToDie;
	private boolean hasTurned;
	private int count;//counter for delay, if counter is less than delay, don't do anything.
    
	private MinuWaller opponent;//needed if changing something in the opponent such as making them die.
	
	private int delay;//delay value before doing anything
	private int wallLife; //how long the "tail"/MinuWall's last before disappearing.
	
	public MinuWaller() {
		setColor(Color.RED);//defaults set
		delay = 2;
		count = 0;
		alive = true;
		aboutToDie = false;
		hasTurned = false;
		wallLife = MinuWall.LIFETIME;//default static lifetime in MinuWall.
	}
	
	public void setOpponent(MinuWaller mw) { this.opponent = mw; }//sets other player to opponent
	public MinuWaller getOpponent() { return this.opponent; }//gets opponent
	
	public void setDelay(int val) { this.delay = val; }//for speed powerups
	public int getDelay() { return this.delay; }//returns delay
	
	public void setWallLife(int val) { this.wallLife = val; }//sets how long the wall lasts
	public int getWallLife() { return this.wallLife; }//returns wallLife
	
	public MinuWaller(Color theColor) {//color constructor, calls its own default constructor and sets a color.
		this();
		this.setColor(theColor);
	}
	
	public boolean isAlive() { return this.alive;}//booleans
	public boolean willDie() { return this.aboutToDie;}
	public void setWillDie(boolean val) { this.aboutToDie = val; }
	
	public void setIsAlive(boolean val) { this.alive = val;}
	
	public MinuWall go() {//called in MWWorld to start making the MinuWaller move.
		count++;//doesn't move until count is equal to the delay value.
		if (count < delay) {
			return null;
		} else {
			count = 0;
		}
		
		if (this.canMove()) {
			
			return ( this.move() );//if it can move, calls the move method, which returns the wall that was created when this MinuWaller moved.
		} else {                     //The MWWorld class keeps track of all actors within the grid.
			return null;
		}
	}
	
	public boolean canMove()//checks if this can move.
	{
		Grid<Actor> gr = getGrid();
		if (gr == null)//if there is no grid, the MinuWaller can't move.
			return false;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		if (!gr.isValid(next)) {//if the next location is off the grid, then this is set to die, and this cannot move.
			this.setWillDie(true);
			return false;
		}
		Actor neighbor = gr.get(next);
		if (neighbor == null) {//if there is nothing in front of this, then this can move.
			return true;
		} else if (neighbor instanceof MinuPowerup) {
            ((MinuPowerup)neighbor).editPlayer(this);//if there is a Powerup in front of this, this gets "edited", which changes some private data members in this.
			return true;// this can move if there is an instanceof a MinuPowerup in front.
		} else if (neighbor instanceof MinuWaller)
		{
			this.setWillDie(true);//if two MinuWallers collide, they both die.
			opponent.setWillDie(true);
			return false;
		} else if (neighbor instanceof MinuWall)//if next neighbor is a wall, this will die.
		{
			this.setWillDie(true);
			return false;
		}
		
		return true;
	}
	
	public MinuWall move()//this method is only called if this MinuWaller can move.
	{
		Grid<Actor> gr = getGrid();//if no grid, no move.
		if (gr == null)
			return null;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());//moves to next adjacent Location.
		if (gr.isValid(next)) {
			super.moveTo(next);
		}
		else
			removeSelfFromGrid();//if for some reason the next Location is not valid, this removes itself from the grid.
		MinuWall wall = new MinuWall(getColor());//MinuWalls are left behind by the same color as the MinuWaller.
		wall.setLifetime(this.wallLife);//sets wall lifetime, may cause some MinuWalls to be separated from the tail.
		wall.putSelfInGrid(gr, loc);//puts the wall in grid
		this.setHasTurned(false);//after this MinuWaller moves, it can turn.
		return wall;//the wall is returned because MWWorld needs to keep track of all actors within the grid. MWWorld needs to keep track of all the MinuWalls to tell them to act or remove self.
	}
	
	public boolean hasTurned()//checks if this has turned yet within this movement.
	{
		return hasTurned;
	}
	
	public void setHasTurned(boolean a)//sets hasTurned.
	{
		hasTurned = a;
	}
	
	
	
	// creates ExplosionBlocks (for when player dies)
	// returns ArrayList of ExplosionBlocks
	public ArrayList<ExplosionBlock> placeExpBlocks(boolean lost) {//lost is whether this MinuWaller lost the round or whether the round ended in a tie.
		Location loc = this.getLocation();                          //if lost == false, tie, if lost == true, then this MinuWaller died.
		Grid<Actor> gr = this.getGrid();
		ArrayList<ExplosionBlock> list = new ArrayList<ExplosionBlock>();//keeps track of all the ExplosionBlocks created in this method.
		ExplosionBlock block;//used to instantiate blocks all around the losing MinuWaller or tied MinuWallers.
		
		for (int dir = 0; dir <= 315; dir += 45) {//puts needed ExplosionBlocks around the MinuWallers
			Location next = loc.getAdjacentLocation(dir);
			if (gr.isValid(next)) {//only puts in the grid if the Location is valid
				block = new ExplosionBlock(dir);
				if (lost) {
					block.setColor(this.getColor());//if this MinuWaller lost, then set the ExplosionBlocks to the color of the MinuWaller
				} else {
					block.setColor(Color.BLACK);//if the round is tied, set color of the ExplosionBlocks to black.
				}
				block.putSelfInGrid(gr, next);//puts the ExplosionBlock in the grid, and adds to the list of ExplosionBlocks, which is returned.
				list.add(block);
			}
		}
		return list;
	}
	
	
}

