// Driver for MinuWalls.
import javax.swing.JOptionPane;//for instructions 


public class MinuWalls {//driver for our game. Sets up the MWWorld and displays it and instructions if needed.
     
	public static void main(String [] args) {
		JOptionPane optionPane = new JOptionPane();//makes window for instructions.
		System.setProperty("info.gridworld.gui.selection", "hide");//if a person clicks on a square in the grid, it won't stay bolded.
		InstructionManual manual = new InstructionManual(optionPane);//new InstructionManual in the window.
		manual.showInstructions();//shows instructions.
	
		
		MWWorld game = new MWWorld();//makes the game.
		game.startNew();
		game.show();
     	
    
	}
     
}

