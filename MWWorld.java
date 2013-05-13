import info.gridworld.actor.*;
import info.gridworld.grid.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MWWorld extends ActorWorld {
     
     private static Random rand = new Random();
     
     public static final int GRID_WIDTH = 42;
     public static final int GRID_LENGTH = 92;
     
     private boolean isPaused, isFirstRound; // isFirstRound is used so that the intro music doesn't play until run his hit the first time.
     private boolean exp; // whether there is an explosion happening right now
     
     private MinuWaller p1, p2;
     private int score1, score2;
     private int steps;
     
     private KeyboardFocusManager kfm;
     private KeyEventDispatcher ked;
     private MWSoundHandler mwsh;
     
     /* these are ArrayLists, so the MWWorld can keep track of
      * all of its Actors at any time.
      */
     private ArrayList<MinuWall> stoplight;
     private ArrayList<MinuWall> stoplightBoundary; 
     private ArrayList<MinuWall> walls;
     private ArrayList<ExplosionBlock> expBlocks;
     private ArrayList<MinuPowerup> powers;
     
     private BoundedGrid<Actor> grid; // for convenience
     
     public MWWorld() {
          super(new BoundedGrid<Actor> (GRID_WIDTH, GRID_LENGTH));
          this.grid = (BoundedGrid<Actor>)(super.getGrid());
          this.isFirstRound = true;
          this.mwsh = new MWSoundHandler();
          
          this.kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
          this.ked = new KeyEventDispatcher() { // an anonymous class :P
               public boolean dispatchKeyEvent(KeyEvent event) {
                    return MWWorld.this.dispatch(event);
               } 
          };
          this.kfm.addKeyEventDispatcher(this.ked);
          
          this.score1 = this.score2 = 0;
          
          stoplight = null;
          stoplightBoundary = null;
          expBlocks = null;
          powers = null;
          
          super.setMessage(
                           "Use the arrow keys(Player 1/Red) or the a/s/d/w keys(Player 2/Blue)"
                                + "to make the MinuWaller turn left or right."
                          );
          
          p1 = p2 = null;
          
          this.steps = 0;
          isPaused = false;
     } 
     
     private String prevMessage; // only to be used in togglePause()
     public void togglePause() {
          if (this.isPaused) {
               super.setMessage(this.prevMessage);
               this.isPaused = false;
          } else {
               this.prevMessage = new String(super.getMessage());
               this.setMessage("Game is paused -- press \"P\" or SPACEBAR to resume");
               this.isPaused = true;
          } 
     }
     
     public void spawnRandomPowerup() {
          MinuPowerup mp;
          int val = rand.nextInt(100) + 1;
          int r, c;
          Location loc;
          
          if (val <= 10) {
               mp = new InstantWinPowerup();
          } else if (val <= 30) {
               mp = new NormalizePowerup();
          } else if (val <= 60) {
               mp = new FasterPowerup();
          } else if (val <= 90) {
               mp = new SlowerPowerup();
          } else if (val <= 100) {
               mp = new DeathPowerup();
          } else { 
               mp = new DeathPowerup();
          }
          
          do {
               r = rand.nextInt(GRID_WIDTH);
               c = rand.nextInt(GRID_LENGTH);
               loc = new Location(r, c);
          } while (grid.get(loc) != null);
          
          mp.putSelfInGrid(this.grid, loc);
          // 2x2 grid of speed-changing powerups
          if (mp instanceof FasterPowerup) {
               for (int dir = 0; dir <= 90; dir += 45) {
                    Location other = loc.getAdjacentLocation(dir);
                    if (this.grid.isValid(other) && this.grid.get(other)==null) {
                         MinuPowerup another = new FasterPowerup();
                         another.putSelfInGrid(this.grid, other);
                         this.powers.add(another);
                    }
               }
          }
          if (mp instanceof SlowerPowerup) {
               for (int dir = 0; dir <= 90; dir += 45) {
                    Location other = loc.getAdjacentLocation(dir);
                    if (this.grid.isValid(other) && this.grid.get(other)==null) {
                         MinuPowerup another = new SlowerPowerup();
                         another.putSelfInGrid(this.grid, other);
                         this.powers.add(another);
                    }
               }
          }
          if (mp instanceof NormalizePowerup) {
               for (int dir = 0; dir <= 90; dir += 45) {
                    Location other = loc.getAdjacentLocation(dir);
                    if (this.grid.isValid(other) && this.grid.get(other)==null) {
                         MinuPowerup another = new NormalizePowerup();
                         another.putSelfInGrid(this.grid, other);
                         this.powers.add(another);
                    }
               }
          }
          // end 2x2 code
          this.powers.add(mp);
          return;
     }
     
     public void startNew() {  
          this.mwsh.stopBG();
          ArrayList<Location> locList = grid.getOccupiedLocations();
          //this.mwsh.stopBG(); // start mario music
          if (! locList.isEmpty()) {
               for (Location loc : locList) {
                    Actor a = grid.get(loc);
                    a.removeSelfFromGrid();
               } 
          } 
          
          this.steps = 0;
          this.p1 = new MinuWaller(Color.BLUE);
          this.p2 = new MinuWaller(); // Color.RED
          
          // for testing
        //  p2.becomeInvincible();
          //p1.becomeInvincible();
          
          this.p1.setDirection(Location.WEST);
          this.p2.setDirection(Location.EAST);
          p1.setOpponent(p2);
          p2.setOpponent(p1);
          super.add(new Location(20, 87), p1);
          super.add(new Location(20, 4), p2);
          
          this.expBlocks = new ArrayList<ExplosionBlock> ();
          this.walls = new ArrayList<MinuWall>();
          this.powers = new ArrayList<MinuPowerup>();
          
          if (!this.isFirstRound)
               mwsh.playIntro();
          this.drawStoplight();
          
          this.exp = false;
     } 
     
     public void drawStoplight() {
          this.stoplight = new ArrayList<MinuWall>();
          this.stoplightBoundary = new ArrayList<MinuWall>();
          for (int i = GRID_LENGTH/2 - 3; i <= GRID_LENGTH/2 + 2; i++) {
               MinuWall wall1 = new MinuWall(Color.BLACK), wall2 = new MinuWall(Color.BLACK);
               wall1.setCounter(-1); wall2.setCounter(-1);
               this.stoplightBoundary.add(wall1);
               this.stoplightBoundary.add(wall2);
               super.add(new Location(GRID_WIDTH/2 - 4, i), wall1);
               super.add(new Location(GRID_WIDTH/2 + 3, i), wall2);
          } 
          for (int i = GRID_WIDTH/2 - 3; i <= GRID_WIDTH/2 + 2; i++) {
               MinuWall wall1 = new MinuWall(Color.BLACK), wall2 = new MinuWall(Color.BLACK);
               wall1.setCounter(-1); wall2.setCounter(-1);
               this.stoplightBoundary.add(wall1);
               this.stoplightBoundary.add(wall2);
               super.add(new Location(i, GRID_LENGTH/2 - 3), wall1);
               super.add(new Location(i, GRID_LENGTH/2 + 2), wall2);
          } 
          for (int i = GRID_LENGTH/2 - 2; i <= GRID_LENGTH/2 + 1; i++) {
               for (int j = GRID_WIDTH/2 - 3; j <= GRID_WIDTH/2 + 2; j++) {
                    MinuWall wall = new MinuWall(Color.RED);
                    wall.setCounter(-1);
                    this.stoplight.add(wall);
                    super.add(new Location(j, i), wall);
               } 
          } 
     } 
     
     @Override public void step() {
          if (this.isPaused) {
               return;
          } 
          
         
          
          if (this.isFirstRound){
               mwsh.playIntro();
               this.isFirstRound = false;
          } 
          
          steps++;
          
          if (this.steps == 300) {
               this.mwsh.startBG();
          } 
          
          /* for the stoplight */
          boolean shouldContinue = this.handleStoplight();
          if (!shouldContinue) {
               return;
          } 
          
          /* This point is reached if the stoplight is green */
          
          boolean p1WillDie = false, p2WillDie = false;
          
          if (this.p1.isAlive() && this.p2.isAlive()) {
               
               // random Powerup maker
               if (this.steps >= 200 && this.steps % 400 == 1) {//% 150 == 100
                    this.spawnRandomPowerup();
               }
               
               this.actPlayers();
               
               p1WillDie = p1.willDie();
               p2WillDie = p2.willDie();
               
               if (p1WillDie || p2WillDie) {
                    this.mwsh.stopBG();
                    this.mwsh.playExp();
                    this.exp = true;
               } 
               
               if (p1WillDie && p2WillDie) {
                    
                    this.bothWillDie();     
                    
               } else if (p1WillDie != p2WillDie) {
                    int num = (p1WillDie ? 1 : 2);
                    
                    this.oneWillDie(num);
               } 
               
          } else { // if at least one player is dead
               int i = 0;
               ArrayList<ExplosionBlock> newList = new ArrayList<ExplosionBlock>();
               while (i < this.expBlocks.size()) {
                    ExplosionBlock eb = this.expBlocks.get(i);
                    if (eb.getWillExplode() == true) {
                         if (eb.getGrid() == this.grid) {
                              eb.removeSelfFromGrid();
                         } 
                         this.expBlocks.remove(i);
                         continue;
                    } 
                    ArrayList<ExplosionBlock> tempList = eb.propagate();
                    eb.removeSelfFromGrid();
                    // by now eb is removed from the grid
                    for (ExplosionBlock temp : tempList) {
                         newList.add(temp);
                    } 
                    i++;
               } 
               this.expBlocks = newList;
               if (this.expBlocks.isEmpty()) {
                    this.exp = false;
                    this.startNew();
               } 
          } 
          
          int i = 0;
          while (i < this.powers.size()) {
               MinuPowerup mp = this.powers.get(i);
               if (mp.getGrid() == this.grid) {
                    i++;
                    if (!this.exp) {
                         mp.act();
                    }
               } else {
                    this.powers.remove(i);
               }
          }
          
     } // end step()
     
     /* The following methods are used in the world's step().
      * They are private, and should only be called in this.step().
      */
     
     // Controls the stoplight based on the value of this.steps.
     // return value: whether the other Actors may act() yet
     private boolean handleStoplight() {
          if (steps == 1) {
               //this.drawStoplight(); // will instead be done in startNew()
          } else if (steps == 115) {//30 orig
               for (MinuWall w : this.stoplight) {
                    w.setColor(Color.YELLOW);
               } 
          } else if (steps == 200) {//60 orig
               for (MinuWall w : this.stoplight) {
                    w.setColor(Color.GREEN);
               } 
               
          } else if (steps == 240) {//85 orig
               for (MinuWall w : this.stoplight) {
                    if (w.getGrid() == this.grid)
                         w.removeSelfFromGrid();
               } 
               for (MinuWall w : this.stoplightBoundary) {
                    if (w.getGrid() == this.grid)
                         w.removeSelfFromGrid();
               } 
               this.stoplight = null;
               this.stoplightBoundary = null;
               
               
          } 
          return (steps >= 200);//60 orig
     } 
     
     // makes the players "act()"
     private void actPlayers() {
          MinuWall wall1 = this.p1.go();
          MinuWall wall2 = this.p2.go();
          
          if (wall1 != null)
               this.walls.add(wall1);
          if (wall2 != null) 
               this.walls.add(wall2);
          
          int i = 0;
          while (i <= this.walls.size()-1) {
               MinuWall wall = this.walls.get(i);
               if (wall.getGrid() == this.grid) {
                    wall.act();
               } else {
                    this.walls.remove(i);
                    continue;
               }
               if (wall.getGrid() != this.grid) {
                    this.walls.remove(i);
               } else {
                    i++;
               } 
          } 
     } 
     
     
     // when both players are "scheduled" to die in a step
     private void bothWillDie() {
          ArrayList<ExplosionBlock> p1List, p2List;
          Location loc1 = p1.getLocation(), loc2 = p2.getLocation();
          
          p1List = p1.placeExpBlocks(false);
          p1.removeSelfFromGrid();
          if (p2.getGrid() == this.getGrid()) { // in case p2 was removed
               p2List = p2.placeExpBlocks(false);
               p2.removeSelfFromGrid();        
          } else {
               p2List = new ArrayList<ExplosionBlock>();
          } 
          this.p1.setIsAlive(false);
          this.p2.setIsAlive(false);
          
          for (ExplosionBlock eb : p1List) {
               this.expBlocks.add(eb);
          } 
          for (ExplosionBlock eb : p2List) {
               this.expBlocks.add(eb);
          } 
          
          Actor a = grid.get(loc1);
          if (a == null || !(a instanceof ExplosionBlock)) {
               (new MinuWall(Color.BLACK)).putSelfInGrid(this.grid, loc1);
          }
          a = grid.get(loc2);
          if (a == null || !(a instanceof ExplosionBlock)) {
               (new MinuWall(Color.BLACK)).putSelfInGrid(this.grid, loc2);
          }
     } 
     
     // when exactly one player is "scheduled" to die in a step
     // the num parameter gives the number of the loser
     private void oneWillDie(int num) { // num = 1 or 2
          MinuWaller winner, loser;
          if (num == 1) {
               winner = this.p2;
               loser = this.p1;
               this.score2++;
          } else { // if (num == 2)
               winner = this.p1;
               loser = this.p2;
               this.score1++;
          } 
          
          loser.setIsAlive(false);
          ArrayList<ExplosionBlock> list = loser.placeExpBlocks(true);
          for (ExplosionBlock eb : list) {
               this.expBlocks.add(eb);
          } 
          
          Location loc = loser.getLocation();
          Color color = loser.getColor();
          loser.removeSelfFromGrid();
          (new MinuWall(color)).putSelfInGrid(this.grid, loc);
          super.setMessage(
                           "Score: red " + score2 + ", blue " + score1 +
                           "\nPress SPACE or P to pause, Y for a new round, or N for a new game (resets the score)."
                          );
     }
     
     /* This is the KeyEvent handling method.
      * It is given to the anonymous class in the MWWorld constructor.
      */
     public boolean dispatch(KeyEvent event) {       
          KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(event);
          String key = stroke.toString();
          
          if (key.equals("pressed P") || key.equals("pressed SPACE")) {
               this.togglePause();
          }
          
          if (key.equals("pressed 1"))
          {
               p1.setWillDie(true);
          }
          
          if (key.equals("pressed SLASH"))
          {
               p2.setWillDie(true);
          }
          
          if (key.equals("pressed Y")) {
               //if (!p1.isAlive() || !p2.isAlive())
               this.startNew();
               this.mwsh.stopBG();
          } 
          if (key.equals("pressed N")) {
               this.startNew();
               score1 = score2 = 0;
               if (!this.isPaused)    
                    super.setMessage(
                                     "Score: red " + score2 + ", blue " + score1
                                    );
               this.mwsh.stopBG();
          } 
          if (this.isPaused) {
               return true;
          } 
          if (this.steps < 240) {//85 orig. At how many steps it can start turning
               return true;
          } 
          
          if (p1.hasTurned() == false && p2.hasTurned() ==  false)
          {
               if (key.equals("pressed UP") && p1.getDirection() != 180)
               {
                    p1.setDirection(0);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed RIGHT") && p1.getDirection() != 270)  
               {
                    p1.setDirection(90);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed DOWN") && p1.getDirection() != 0) 
               {
                    p1.setDirection(180);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed LEFT") && p1.getDirection() != 90)  
               {
                    p1.setDirection(270);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed W") && p2.getDirection() != 180) 
               {
                    p2.setDirection(0); 
                    p2.setHasTurned(true);
               } 
               if (key.equals("pressed D") && p2.getDirection() != 270)  
               {
                    p2.setDirection(90); 
                    p2.setHasTurned(true);
               } 
               if (key.equals("pressed S") && p2.getDirection() != 0)  
               {
                    p2.setDirection(180); 
                    p2.setHasTurned(true);
               } 
               if (key.equals("pressed A") && p2.getDirection() != 90)  
               {
                    p2.setDirection(270); 
                    p2.setHasTurned(true);
               } 
          } 
          
          if (p1.hasTurned() == false && p2.hasTurned() ==  true)
          {
               if (key.equals("pressed UP") && p1.getDirection() != 180)
               {
                    p1.setDirection(0);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed RIGHT") && p1.getDirection() != 270)  
               {
                    p1.setDirection(90);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed DOWN") && p1.getDirection() != 0) 
               {
                    p1.setDirection(180);  
                    p1.setHasTurned(true);
               } 
               if (key.equals("pressed LEFT") && p1.getDirection() != 90)  
               {
                    p1.setDirection(270);  
                    p1.setHasTurned(true);
               } 
          } 
          
          if (p1.hasTurned() == true && p2.hasTurned() ==  false)
          {
               if (key.equals("pressed W") && p2.getDirection() != 180) 
               {
                    p2.setDirection(0); 
                    p2.setHasTurned(true);
               } 
               if (key.equals("pressed D") && p2.getDirection() != 270)  
               {
                    p2.setDirection(90); 
                    p2.setHasTurned(true);
               } 
               if (key.equals("pressed S") && p2.getDirection() != 0)  
               {
                    p2.setDirection(180); 
                    p2.setHasTurned(true);
               } 
               if (key.equals("pressed A") && p2.getDirection() != 90)  
               {
                    p2.setDirection(270); 
                    p2.setHasTurned(true);
               } 
          } 
          return true; 
     }  
     
} 

