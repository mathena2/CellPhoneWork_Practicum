import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;


public class CellPhoneLog extends JFrame 
{
	private static final long serialVersionUID = 1L;

	public CellPhoneLog() 
	{
		createMenu();
		createControlPanel();	
	}
	
	/*
	 * Creates the control panel to put JPanels of phone options and tools inside of it.
	 */
	public void createControlPanel()
	{
		JPanel phoneOptions = createPhoneOptions();
		JPanel toolOptions = createToolOptions();  
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 1)); //First number is for amount of JPanels in control panel
		
		controlPanel.add(phoneOptions);
		controlPanel.add(toolOptions); 
		
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
	
	}
	
	/*
	 * Creates a panel for various phone options.
	 */
	public JPanel createPhoneOptions()
	{
		JPanel phonePanel = new JPanel();
		phonePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Phone Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		JButton btnLogList = new JButton("Log List");
		phonePanel.add(btnLogList);
		
		JButton btnCheckIn = new JButton("Check In");
		phonePanel.add(btnCheckIn);
		
		JButton btnMarkSold = new JButton("Mark Sold");
		phonePanel.add(btnMarkSold);
		
		return phonePanel;
	}
	
	/*
	 * Creates Tool panel to be put inside of control panel.
	 */
	public JPanel createToolOptions()
	{
		JPanel toolPanel = new JPanel();
		toolPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Tools", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnRepsList = new JButton("Rep's List");
		toolPanel.add(btnRepsList);
		
		JButton btnPaperPrintOuts = new JButton("Paper Print Out's");
		toolPanel.add(btnPaperPrintOuts);
		
		return toolPanel;
	}
	
	/*
	 * Creates a Menu bar at top of screen.
	 */
	public void createMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
	}

}
