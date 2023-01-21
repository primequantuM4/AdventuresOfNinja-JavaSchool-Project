package main;

import javax.swing.JFrame;

public class Game extends JFrame {
		JFrame window = new JFrame("The Adventures of Ninja");
		GamePanel gp;
		public  Game(){
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.pack();
			window.setSize(768, 576);

			window.setLocationRelativeTo(null);
			window.setResizable(true);
			startNewGame();
	  }  
	  public void startNewGame(){
		
	    window.setVisible(false);
	    if (gp != null) {
	    	window.remove(gp);
	    }
	    

	    gp = new GamePanel(this);
	    
	    gp.currentState = gp.playingState;
	    window.add(gp);
	    window.setVisible(true);

	  }

}
