import info.gridworld.actor.*;
import info.gridworld.grid.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/* MWWorld extends the ActorWorld class. There is exactly one MinuWallWorld per game.
 * Unlike a regular ActorWorld, the MWWorld has private members that reference every Actor in the world.
 * This is done so that at any given time and in any method, the MWWorld knows exactly where all
 * of the Actors in the game are, and so that it doesn't have to search through the entire grid for Actors
 * every time step() is called.
 * MWWorld coordinates and controls everything inside the game. It overrides the ActorWorld's step() method,
 * so that it can control exactly what happens in each step. It handles the scores, the creation of the stoplight at the beginning,
 * the movement of the MinuWallers, the creation and annihilation of the MinuWalls that trail behind the MinuWaller,
 * the random creation of MinuPowerups in the grid, the life and death of MinuWallers, and the propagation of ExplosionBlocks
 * that are created when a MinuWaller dies. It also handles keyboard input and playing and stopping sound files.
 * MWWorld does NOT control the Instruction Manual.
 */

public class MWWorld extends ActorWorld {//controls the game by overriding methods from ActorWorld. Also sets up the game.
	
	private static Random rand = new Random();
	
	public static final int GRID_WIDTH = 42;
	public static final int GRID_LENGTH = 92;
	
	public static int SCORE_LIM = 10;
	
	private boolean isPaused, isFirstRound; // isFirstRound is used so that the intro music doesn't play until run his hit the first time.
	// this.exp tells whether there is an explosion going on right now in the game.
	// if there is an explosion, at least one of the players must have died in the current round.
	private boolean exp;
	// this.gameOver tells whether the game is over (someone has reached the score limit).
	private boolean gameOver;
	
	private int score1, score2; // the scores of the players
	
	/* The steps variable counts the number of steps since the start of the round. It is
	 * used to keep track of real-time events in the game.
	 */
	private int steps;
	
	/* The variables this.kfm and this.ked handle all the keyboard events
	 * in the game. The this.ked is initialized with an anonymous class in
	 * the MWWorld constructor. IMPORTANT: the KeyEventDispatcher handles the 
	 * turning of the MinuWaller, not the MinuWaller class.
	 */
	private KeyboardFocusManager kfm;
	private KeyEventDispatcher ked;
	/* The this.mwsh variable handles all the sounds in the game. It is an instance
	 * of MWSoundHandler.
	 */
	private MWSoundHandler mwsh;
	
	/* The following are the private members that allow the MWWorld to keep track of all of its
	 * Actors at any times. Outside of these, there should be no other Actors on the grid
	 * at any time.
	 */
	private MinuWaller p1, p2; // the MinuWallers
	private ArrayList<MinuWall> stoplight; // list of the MinuWalls that change color in the stoplight
	private ArrayList<MinuWall> stoplightBoundary; // list of the MinuWalls in the boundary of the stoplight
	private ArrayList<MinuWall> walls; // list of the MinuWalls at any time (that are not in the stoplight)
	private ArrayList<ExplosionBlock> expBlocks; // list of the ExplosionBlocks on the grid at any time
	private ArrayList<MinuPowerup> powers; // list of MinuPowerups on the grid at any time
	
	private BoundedGrid<Actor> grid; // for convenience, so we don't have to always say getGrid()
	
	// Initializes variables and does pre-game setup.
	// Before the game starts, this.startNew() must be called after the constructor.
	public MWWorld() {
		super(new BoundedGrid<Actor> (GRID_WIDTH, GRID_LENGTH));
		this.grid = (BoundedGrid<Actor>)(super.getGrid());
		this.isFirstRound = true;
		this.gameOver = false;
		this.mwsh = new MWSoundHandler();
		
		/* set up the KeyEventDispatcher. The method that handles KeyEvents
		 * is part of the MWWorld class (see below).
		 */
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
						 "Use the arrow keys(Player 2/Red) or the a/s/d/w keys(Player 1/Blue)"
						 + "to make the MinuWaller turn left or right.\nSet the slider below to the Fastest setting and resize this window to the size of the grid "
						 + "for the best gameplay experience."
						 );
		
		p1 = p2 = null;
		
		this.steps = 0;
		isPaused = false;
	}
	
	/* This is the KeyEvent handling method.
	 * It is given to the anonymous class in the MWWorld constructor, and handles
	 * the keyboard input.
	 */
	public boolean dispatch(KeyEvent event) {
		KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(event);
		String key = stroke.toString();
		
		if (key.equals("pressed P") || key.equals("pressed SPACE")) {
			this.togglePause();
		}
		
		/* cheats */
		if (key.equals("pressed 1"))
		{
			p1.setWillDie(true);
		}
		if (key.equals("pressed SLASH"))
		{
			p2.setWillDie(true);
		}
		
		if (key.equals("pressed Y")) {
			this.startNew();
			this.mwsh.stopBG();
		}
		if (key.equals("pressed N")) {
			this.startNew();
			score1 = 0;
			score2 = 0;
			if (!this.isPaused)
				super.setMessage(
								 "Score: red " + score2 + ", blue " + score1
								 );
			this.mwsh.stopBG();
		}
		
		// When the game is paused, the MinuWallers may not turn.
		if (this.isPaused) {
			return true;
		}
		
		// When steps reaches 240, the MinuWallers are allowed to turn.
		if (this.steps < 240) {
			return true;
		}
		
		/* handle arrow keys and WASD: */
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
	
	private String prevMessage; // ONLY to be used in togglePause()
	/* pauses or resumes the game */
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
	
	
	
	/* startNew() starts a new round. It sets up the stoplight and everything else. */
	public void startNew() {
		this.mwsh.stopBG();
		if (this.gameOver)
		{
			this.gameOver = false;
			super.setMessage(
							 "Score: red " + score2 + ", blue " + score1 +
							 "\nPress SPACE or P to pause, Y for a new round, or N for a new game (resets the score)."
							 );
		}
		ArrayList<Location> locList = grid.getOccupiedLocations();
		//this.mwsh.stopBG(); // start mario music
		if (! locList.isEmpty()) {
			for (Location loc : locList) {
				Actor a = grid.get(loc);
				a.removeSelfFromGrid();
			}
		}
		
		this.steps = 0; // reset the step counter
		
		// create the two MinuWaller players and add them to the grid
		this.p1 = new MinuWaller(Color.BLUE);
		this.p2 = new MinuWaller(); // Color.RED
		this.p1.setDirection(Location.WEST);
		this.p2.setDirection(Location.EAST);
		p1.setOpponent(p2);
		p2.setOpponent(p1);
		super.add(new Location(20, 87), p1);
		super.add(new Location(20, 4), p2);
		
		// set expBlocks, walls, and powers to empty sets
		this.expBlocks = new ArrayList<ExplosionBlock> ();
		this.walls = new ArrayList<MinuWall>();
		this.powers = new ArrayList<MinuPowerup>();
		
		// play the intro music
		if (!this.isFirstRound)
			this.mwsh.playIntro();
			
		this.drawStoplight(); // draw the stoplight
		
		this.exp = false; // there is no explosion going on
	}
	
	// this is the method for creating the stoplight, which is made of MinuWalls.
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
	
	/* This is the all-important step() method. Unlike ActorWorld's step() method,
	 * which simply loops through the grid and acquires a list of Actors and calls their act() methods,
	 * MWWorld's step() method handles all of the real-time events in the game. It has access 
	 * to all of the Actors on the grid at any time due to the MWWorld's private data members.
	 */
	@Override public void step() {
		if (steps == 2147483647)//maximum int limit
			steps = 999;
		
		if (this.isPaused) {
			return;
		}
		
		if (this.gameOver == true)
		{
			if (this.exp == false)
			{
				return;
			}
			
		}
		
		if (this.isFirstRound) {
			mwsh.playIntro();
			this.isFirstRound = false;
		}
		
		// start background music at step #305
		if (this.steps == 305 && !this.exp) {
			this.mwsh.startBG();
		}
		
		this.steps++;
		
		/* for the stoplight */
		boolean shouldContinue = ( this.handleStoplight() );
		if (!shouldContinue) {
			return;
		}
		
		/* This point is reached IFF the stoplight has turned green in the current round. */
		
		boolean p1WillDie = false, p2WillDie = false;
		
		if (this.p1.isAlive() && this.p2.isAlive()) {
			
			// create a random MinuPowerup if this.steps is in the set
			// 100 * (2n + 1), where n is a natural number
			if (this.steps >= 200 && this.steps % 200 == 100) {
				this.spawnRandomPowerup();
			}
			
			// causes the players to "act"
			this.actPlayers();
			
			p1WillDie = this.p1.willDie();
			p2WillDie = this.p2.willDie();
			
			// if at least one player is dead, set this.exp to true,
			// stop the background music, and play the explosion sound.
			if (p1WillDie || p2WillDie) {
				this.mwsh.stopBG();
				this.mwsh.playExp();
				this.exp = true;
			}
			
			if (p1WillDie && p2WillDie) {
				// if both players are about to die
				this.bothWillDie();
				
			} else if (p1WillDie != p2WillDie) {
				int num = (p1WillDie ? 1 : 2);
				// if one player will die
				this.oneWillDie(num);
			}
			
		} else { // if at least one player is ALREADY dead
			int i = 0;
			// make the ExplosionBlocks propagate, add anything new, and delete everything
			// that was removed from the grid.
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
			
			// IMPORTANT: when the explosion completes, a new round automatically starts
			this.expBlocks = newList;
			if (this.expBlocks.isEmpty() && gameOver == false) {
				this.exp = false;
				this.startNew();
			}
		}
		
		// make all the MinuPowerups act(), and remove anything that has been removed
		// from the grid
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
		
	}
	
	/* The following methods are used in MWWorld's step() method.
	 * They are private, and should ONLY be called in this.step().
	 */
    
	// creates MinuPowerup at a random location in the grid
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
	
	// Controls the stoplight based on the value of this.steps.
	// return value: whether the other Actors are allowed to act() yet
	private boolean handleStoplight() {
		if (steps == 1) {
			System.out.println("3...");
			//this.drawStoplight(); // will instead be done in startNew()
		} else if (steps == 115) {//30 orig
			System.out.println("2...");
			for (MinuWall w : this.stoplight) {
				w.setColor(Color.YELLOW);
			}
		} else if (steps == 200) {//60 orig
			System.out.println("1...GO!");
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
	
	// makes the MinuWallers "act()"
	private void actPlayers() {
		// go() returns a reference to the MinuWall
		// that was dropped by the player.
		MinuWall wall1 = this.p1.go();
		MinuWall wall2 = this.p2.go();
		
		// add the new MinuWalls to this.walls
		if (wall1 != null)
			this.walls.add(wall1);
		if (wall2 != null)
			this.walls.add(wall2);
		
		// call the MinuWalls' act() methods, which simply
		// decide whether they should be released yet.
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
	
	
	/* When both players are "scheduled" to die in a step,
	 * this method is called. (Obviously, if this method is called,
	 * then the game was a tie).
	 */
	private void bothWillDie() {
		System.out.println("Players crashed at the same time, so no one wins the round.");
		ArrayList<ExplosionBlock> p1List, p2List;
		Location loc1 = p1.getLocation(), loc2 = p2.getLocation();
		
		// place ExplosionBlocks as needed
		p1List = p1.placeExpBlocks(false);
		p1.removeSelfFromGrid();
		if (p2.getGrid() == this.getGrid()) { // in case p2 was removed
			p2List = p2.placeExpBlocks(false);
			p2.removeSelfFromGrid();
		} else {
			p2List = new ArrayList<ExplosionBlock>();
		}
		
		// both players are dead
		this.p1.setIsAlive(false);
		this.p2.setIsAlive(false);
		
		// add the new ExplosionBlocks to this.expBlocks
		for (ExplosionBlock eb : p1List) {
			this.expBlocks.add(eb);
		}
		for (ExplosionBlock eb : p2List) {
			this.expBlocks.add(eb);
		}
		
		// place black MinuWalls where the MinuWallers were
		Actor a = grid.get(loc1);
		if (a == null || !(a instanceof ExplosionBlock)) {
			(new MinuWall(Color.BLACK)).putSelfInGrid(this.grid, loc1);
		}
		a = grid.get(loc2);
		if (a == null || !(a instanceof ExplosionBlock)) {
			(new MinuWall(Color.BLACK)).putSelfInGrid(this.grid, loc2);
		}
	}
	
	/* When exactly one player is "scheduled" to die in a step, oneWillDie() is called.
	 * The "num" parameter gives the number of the loser, 1 or 2.
	 */
	private void oneWillDie(int num) { // num is in {1, 2}
		MinuWaller winner, loser;
		if (num == 1) {
			winner = this.p2;
			loser = this.p1;
			this.score2++;
			if (SCORE_LIM - score2 == 0)
				System.out.println("p2 wins the game!");
			else
				System.out.println("p2 wins the round! p2 needs " + (10 - score2) + " more wins to win the game.");
		} else {
			winner = this.p1;
			loser = this.p2;
			this.score1++;
			if (SCORE_LIM - score1 == 0)
				System.out.println("p1 wins the game!");
			else
				System.out.println("p1 wins the round! p1 needs " + (10 - score1) + " more wins to win the game.");
		}
		
		loser.setIsAlive(false); // the loser is dead
		
		// make the loser surround itself with a square of ExplosionBlocks,
		// and add them to this.expBlocks
		ArrayList<ExplosionBlock> list = loser.placeExpBlocks(true);
		for (ExplosionBlock eb : list) {
			this.expBlocks.add(eb);
		}
		
		Location loc = loser.getLocation();
		Color color = loser.getColor();
		loser.removeSelfFromGrid();
		// a MinuWall is placed in the loser's location, with the same Color
		(new MinuWall(color)).putSelfInGrid(this.grid, loc);
		super.setMessage(
						 "Score: red " + score2 + ", blue " + score1 +
						 "\nPress SPACE or P to pause, Y for a new round, or N for a new game (resets the score)."
						 );
		
		/* if someone reaches the score limit */
		if (this.score1 == SCORE_LIM || this.score2 == SCORE_LIM) {
			this.gameOver = true;
			if (this.steps < 300) {
				this.mwsh.stopBG();
				this.mwsh.startBG();
			}
			if (this.score1 == SCORE_LIM) {
				super.setMessage(
								 "Score: red " + score2 + ", blue " + score1 + " PLAYER 1 WINS!" +
								 "\nPress N or Y for a new game."
								 );
			} else if (this.score2 == SCORE_LIM) {
				super.setMessage(
								 "Score: red " + score2 + ", blue " + score1 + " PLAYER 2 WINS!" +
								 "\nPress N or Y for a new game."
								 );
			}
			this.score1 = 0;
			this.score2 = 0;
		}
	}
	
}

