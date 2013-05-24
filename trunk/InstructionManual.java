import javax.swing.JOptionPane;

public class InstructionManual {

	private JOptionPane dialogBox;//window for putting the buttons and text on
	private String [] optionsYesNo;//array with two Strings, "Yes" and "No", for the window to make buttons with
	private int retVal;//the return value that the showOptionDialog method returns, created by java.
	
	public InstructionManual(JOptionPane s) {//constructor that is passed a JOptionPane for the messages and buttons to be displayed on.
		
		this.dialogBox = s;
		
		this.optionsYesNo = new String[2];//array with two values used for the JOptionPane buttons.
		this.optionsYesNo[0] = new String("Yes");
		this.optionsYesNo[1] = new String("No");

	}
	
	public void showInstructions() {//displays the instructions if the user selects yes.
						
		this.retVal = this.dialogBox.showOptionDialog(null, "Would you like instructions?", "MinuWalls",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, optionsYesNo, optionsYesNo[0]);//the array above is passed to teh showOptionDialog method, which displays the elements inside and makes the default option yes.
		
		if (retVal == JOptionPane.YES_OPTION) {
			
			this.showInstructionDialog();//shows the instruction if user selects yes.
			
		} else if (retVal == JOptionPane.NO_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);//displays a message if user selects no
		} else if (retVal == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.",//if user clicks the x button then displays a message.
				"MinuWalls", JOptionPane.PLAIN_MESSAGE);
		}

    }

	private void showInstructionDialog() {
		int res;
		res = dialogBox.showConfirmDialog(null, "Welcome to MinuWalls, the best GridWorld game on Earth!\n"//displays the text in the JOptionPane.
			+ "Let's start with Game Instructions...",
			"Welcome to MinuWalls!", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {//if window is closed display message
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "Avoid the walls that trail from your opponent's bike. "
			+ "It's similar to Tron, or Light Bike.",
			"Instructions #1", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "For Player 1, the controls are the Arrow Keys." + '\n' 
			+ "The arrow keys' directions are self-explanatory.",
			"Instructions #2", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "For Player 2, the controls are WASD." + '\n' 
			+ "W moves up, A moves left, S moves down, D moves right.",
			"Instructions #3", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "There are powerups in the game as well. "
			+ "Collect them and you will be able to gain an advantage or disadvantage.",
			"Instructions #4", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "The game begins when the stoplight turns green. " + '\n'
			+ "The first person to reach 10 points wins!",
			"Instructions #5", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
         
		return;
	}	

}

