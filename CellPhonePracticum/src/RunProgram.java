import java.io.IOException;

import javax.swing.JFrame;


public class RunProgram
{
	
	public static void main(String[] args) throws IOException 
	{
	         
		 MainScreen mainProgram = new MainScreen();            
		 mainProgram.setTitle("Connection Center");
		 mainProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //Sets its size and display to true
		 mainProgram.setSize(700, 400);
		 mainProgram.setLocationByPlatform(true);
		 mainProgram.setVisible(true);
	}
}
