import info.gridworld.actor.*;
import info.gridworld.grid.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MinuWaller extends Actor {
     
     private boolean alive;
     private boolean aboutToDie;
     private boolean hasTurned;
     private int count;
     
     //  private boolean invincible;
     private int icount;
     private Color prevColor;
    // private static int ICOUNT_LIMIT = -1;//100;
     
     private MinuWaller opponent;
     
     private int delay;
	private int wallLife; 
     
     public MinuWaller() {
          setColor(Color.RED);
		delay = 2;
          count = 0;
          icount = 0;
          alive = true;
          aboutToDie = false;
          hasTurned = false;
		wallLife = MinuWall.LIFETIME;
          //invincible = false;
     } 
     
     public void setOpponent(MinuWaller mw) { this.opponent = mw; }
     public MinuWaller getOpponent() { return this.opponent; }
     
     //    public void setInvincible(boolean val) { this.invincible = val; }
     //  public boolean getInvincible() { return this.invincible; }
     
     public void setDelay(int val) { this.delay = val; }
     public int getDelay() { return this.delay; }

	public void setWallLife(int val) { this.wallLife = val; }
	public int getWallFile() { return this.wallLife; }
     
     public MinuWaller(Color theColor) {
          this();
          this.setColor(theColor);
     } 
     
     public boolean isAlive() { return this.alive;}  
     public boolean willDie() { return this.aboutToDie;}
     public void setWillDie(boolean val) { this.aboutToDie = val; }   
     
     public void setIsAlive(boolean val) { this.alive = val;}  
     
     public MinuWall go() {
          count++;
          if (count < delay) {
               return null;
          } else {
               count = 0;
          } 
          
          /*if (this.invincible) {
           this.icount++;
           if (this.icount == ICOUNT_LIMIT) {
           this.icount = 0;
           this.invincible = false;
           this.setColor(prevColor);
           }
           }*/
          
          if (this.canMove()) {

               return ( this.move() );
          } else {
               //if (!this.invincible)
               //   this.aboutToDie = true;
               return null;
          } 
     } 
     
     public boolean canMove()
     {
          Grid<Actor> gr = getGrid();
          if (gr == null)
               return false;
          Location loc = getLocation();
          Location next = loc.getAdjacentLocation(getDirection());
          if (!gr.isValid(next)) {
               /*if (this.invincible) {
                return true;
                } else {*/
               this.setWillDie(true);
               return false;
               // }
          }
          Actor neighbor = gr.get(next);
          if (neighbor == null) {
               return true;
          } else if (neighbor instanceof MinuPowerup) {
               ((MinuPowerup)neighbor).editPlayer(this);
               return true;
          } else if (neighbor instanceof MinuWaller)
          {
               this.setWillDie(true);
               opponent.setWillDie(true);
               return false;
          } else if (neighbor instanceof MinuWall)
          {
               this.setWillDie(true);
               return false;
          }
          
          /*else if (this.invincible) {
           if (neighbor instanceof MinuWaller && ((MinuWaller)neighbor).getInvincible() == false) {
           MinuWaller mw = (MinuWaller)neighbor;
           mw.setWillDie(true);
           this.aboutToDie = false;
           return false;
           }
           else if (neighbor instanceof MinuWaller && ((MinuWaller)neighbor).getInvincible() == true
           && gr.isValid(neighbor.getLocation().getAdjacentLocation(neighbor.getDirection()))){
           if (gr.get(neighbor.getLocation().getAdjacentLocation(neighbor.getDirection())) == null)
           {
           MinuWaller mw = (MinuWaller)neighbor;
           mw.setWillDie(true);
           this.aboutToDie = false;
           }
           
           else{
           MinuWaller mw = (MinuWaller)neighbor;
           mw.setWillDie(true);
           this.aboutToDie = true;
           }
           return false;
           }
           return true;
           } else {
           return false;
           }*/
          
          return true;
     } 
     
     public MinuWall move()
     {
          Grid<Actor> gr = getGrid();
          if (gr == null)
               return null;
          Location loc = getLocation();
          Location next = loc.getAdjacentLocation(getDirection());
          if (gr.isValid(next)) {
               super.moveTo(next);
          }/* else if (this.invincible) {
           Location togo;
           int row = loc.getRow(), col = loc.getCol();
           if (next.getRow() == MWWorld.GRID_WIDTH)
           row = 0;
           else if (next.getRow() == -1) 
           row = MWWorld.GRID_WIDTH-1;
           if (next.getCol() == MWWorld.GRID_LENGTH)
           col = 0;
           else if (next.getCol() == -1) 
           col = MWWorld.GRID_LENGTH-1;
           togo = new Location(row, col);
           super.moveTo(togo);
           //return null;
           }*/
          else
               removeSelfFromGrid();
          MinuWall wall = new MinuWall(getColor());
		wall.setLifetime(this.wallLife);
          wall.putSelfInGrid(gr, loc);
          this.setHasTurned(false);
          return wall;
     } 
     
     public boolean hasTurned()
     {
          return hasTurned;
     } 
     
     public void setHasTurned(boolean a)
     {
          hasTurned = a;
     } 
     
     /* public void becomeInvincible() {
      this.invincible = true;
      this.prevColor = super.getColor();
      this.icount = 0;
      if (this.prevColor.equals(Color.BLUE)) {
      super.setColor(Color.CYAN);
      } else if (this.prevColor.equals(Color.RED)) {
      super.setColor(Color.MAGENTA);
      } else {
      ;//super.setColor(Color.GREEN); // this isn't supposed to happen
      }
      }*/
     
// creates ExplosionBlocks (for when player dies)
// returns ArrayList of ExplosionBlocks
     public ArrayList<ExplosionBlock> placeExpBlocks(boolean lost) {
          Location loc = this.getLocation();
          Grid<Actor> gr = this.getGrid();
          ArrayList<ExplosionBlock> list = new ArrayList<ExplosionBlock>();
          ExplosionBlock block;
          
          for (int dir = 0; dir <= 315; dir += 45) {
               Location next = loc.getAdjacentLocation(dir);
               if (gr.isValid(next)) {
                    block = new ExplosionBlock(dir);
                    if (lost) {
                         block.setColor(this.getColor());
                    } else {
                         block.setColor(Color.BLACK);
                    } 
                    block.putSelfInGrid(gr, next);
                    list.add(block);
               } 
          } 
          return list;
     } 
     
     
} 

