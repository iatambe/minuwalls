import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.*;
import java.awt.*;
import java.util.*;

/* ExplosionBlock is an Actor that propagates outwards in a square until it reaches
 * the boundaries of the world. ExplosionBlocks are created when a MinuWaller dies.
 */

public class ExplosionBlock extends Actor {//ExplosionBlocks are put into a grid when a MinuWaller crashes, resulting in a propagating square of ExplosionBlocks.

	private boolean willExplode; //whether will explode or not in the current step
	public void setWillExplode(boolean val) { this.willExplode = val; }
	public boolean getWillExplode() { return this.willExplode; }
   
  /* The direction variable (inherited from Actor) determines how the ExplosionBlock behaves.
  * If direction is in the set {0, 90, 180, 270}, lateral directions,
  * then the ExplosionBlock only creates a new ExplosionBlock in the Location in front of it and disappears.
  * If direction is in the set {45, 135, 225, 315}, diagonal directions,
  * then the ExplosionBlock creates new ExplosionBlocks in front of it and in the two Locations 
  * adjacent to that location.
  */
    
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
     
  /* !! Instead of using the act() method, the ExplosionBlock has a propagate method. We did
   * this so that we could use the method to return a value instead of keeping it void.
   */

/* propagate() causes the ExplosionBlocks to propagate outwards.
 * It returns a list of the new ExplosionBlocks it has created.
 * It is designed so that if 2 ExplosionBlocks collide (such as from two explosions happening at the same time),
 * then the two ExplosionBlocks annihilate each other.
 */
public ArrayList<ExplosionBlock> propagate() { 
    /* If this ExplosionBlock is removed or has already been condemned to death, then
     * exit from the method and return an empty list.
     */
     if (this.getGrid() == null || this.willExplode == true)
          return new ArrayList<ExplosionBlock>(); 
     
     int dir = this.getDirection(); 
     Location loc = this.getLocation(); 
     Grid<Actor> gr = this.getGrid(); 
     ArrayList<ExplosionBlock> list = new ArrayList<ExplosionBlock>();
     ExplosionBlock eb;
     
     if (dir % 90 == 45) { // if dir is a "diagonal" direction,
         // create the adjacent blocks
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
     
     // create the ExplosionBlock in the Location in front of this one.
     Location next = loc.getAdjacentLocation(dir);
     if (gr.isValid(next)) {
          Actor a = gr.get(next);
          if (a == null || !(a instanceof ExplosionBlock)) {
               eb = this.makeExpBlockAt(dir, next);
               list.add(eb);
          } else if (a != null && a instanceof ExplosionBlock) {
			   	((ExplosionBlock)a).setWillExplode(true);
			}
     } 
     
     //this.removeSelfFromGrid();
	 this.willExplode = true;
     return list;
} 

} 

