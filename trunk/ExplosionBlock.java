import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.*;
import java.awt.*;
import java.util.*;

public class ExplosionBlock extends Actor {

	private boolean willExplode;
	
	public void setWillExplode(boolean val) { this.willExplode = val; }
	public boolean getWillExplode() { return this.willExplode; }
     
     public ExplosionBlock(int dir) {
          super();
          this.setDirection(dir);
		  this.willExplode = false;
     } 
     
     private ExplosionBlock makeExpBlockAt(int direction, Location next) {
          ExplosionBlock block = new ExplosionBlock(direction);
          block.setColor(this.getColor());
          block.putSelfInGrid(this.getGrid(), next);
          return block;
     } 
     
     // act() does nothing for ExplosionBlocks
     // instead of act(), MWWorld calls the propagate() method.
    // public void act() } 

/* propagate() causes the ExplosionBlocks to propagate outwards */
public ArrayList<ExplosionBlock> propagate() { 
     if (this.getGrid() == null || this.willExplode == true)
          return new ArrayList<ExplosionBlock>(); 
     int dir = this.getDirection();
     Location loc = this.getLocation();
     Grid<Actor> gr = this.getGrid();
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
			   	((ExplosionBlock)a).setWillExplode(true);
			}
     } 
     
     //this.removeSelfFromGrid();
	 this.willExplode = true;
     return list;
} 

} 

