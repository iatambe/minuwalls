import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.*;
import java.awt.*;
import java.util.*;

public class ExplosionBlock extends Actor {//ExplosionBlocks are put into a grid when a MinuWaller crashes, resulting in a propagating square of ExplosionBlocks.

	private boolean willExplode; //whether will explode or not in the current step
	public void setWillExplode(boolean val) { this.willExplode = val; }//The getter of the previous method
	public boolean getWillExplode() { return this.willExplode; }
    
     public ExplosionBlock(int dir) {//takes the direction that it will explode in.
          super();
          this.setDirection(dir);
		  this.willExplode = false;
     } 
     //We are creating the explosion that spreads throughout the grid
     private ExplosionBlock makeExpBlockAt(int direction, Location next) {
          ExplosionBlock block = new ExplosionBlock(direction);
          block.setColor(this.getColor()); //Its the same color as the player :) 
          block.putSelfInGrid(this.getGrid(), next); //it'll take the NEXT locatioj
          return block;
     } 
     
     // act() does nothing for ExplosionBlocks
     // instead of act(), MWWorld calls the propagate() method.
    // public void act() } 

/* propagate() causes the ExplosionBlocks to propagate outwards */
public ArrayList<ExplosionBlock> propagate() { 
     if (this.getGrid() == null || this.willExplode == true) //We're using a boolean statemment to contain the explosion and make sure it doesnt surpass the grid
          return new ArrayList<ExplosionBlock>(); 
     int dir = this.getDirection();  //We're getting the data and storing it in an array list. <---
     Location loc = this.getLocation(); //<-----
     Grid<Actor> gr = this.getGrid(); //<=---- ARRAY_LIST ITEMS
     ArrayList<ExplosionBlock> list = new ArrayList<ExplosionBlock>();
     ExplosionBlock eb;
     
     if (dir % 90 == 45) { // if dir is a "diagonal" direction
          Location next1 = loc.getAdjacentLocation(dir - 45);
          if (gr.isValid(next1)) {
               Actor a = gr.get(next1); 
               if (a == null || !(a instanceof ExplosionBlock)) {
                    eb = this.makeExpBlockAt(dir - 45, next1);
                    list.add(eb);
               } else if (a != null && a instanceof ExplosionBlock) {
			   		((ExplosionBlock)a).setWillExplode(true);
				}
          } 
          Location next2 = loc.getAdjacentLocation(dir + 45);
          if (gr.isValid(next2)) {
               Actor a = gr.get(next2); 
               if (a == null || !(a instanceof ExplosionBlock)) {
                    eb = this.makeExpBlockAt(dir + 45, next2);
                    list.add(eb);
               } else if (a != null && a instanceof ExplosionBlock) {
			   		((ExplosionBlock)a).setWillExplode(true);
				}
          } 
     } 
     
     Location next = loc.getAdjacentLocation(dir);
     if (gr.isValid(next)) {
          Actor a = gr.get(next);
          if (a == null || !(a instanceof ExplosionBlock)) {
               eb = this.makeExpBlockAt(dir, next);
               list.add(eb);
          } else if (a != null && a instanceof ExplosionBlock) {
			   	((ExplosionBlock)a).setWillExplode(true); //We don't want a cluttered area with blocks landing on other blocks so we check a condition.
			}
     } 
     
     //this.removeSelfFromGrid();
	 this.willExplode = true;
     return list;
} 

} 

