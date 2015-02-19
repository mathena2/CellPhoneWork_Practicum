import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.sql.*;


public class MainScreen extends JFrame 
{
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cellphonelog";
			
	//Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public MainScreen() 
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
		JPanel morningOptions = createMorningTasks();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(3, 1)); //First number is for amount of JPanels in control panel
		
		controlPanel.add(phoneOptions);
		controlPanel.add(morningOptions);
		controlPanel.add(toolOptions); 
		
		
		getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		textArea = new JTextArea("");
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	
	}
	
	/*
	 * Creates a panel for various phone options.
	 */
	public JPanel createPhoneOptions()
	{
		JPanel phonePanel = new JPanel();
		phonePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Phone Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnCheckIn = new JButton("Check In");
		ActionListener listener2 = new ClickCheckIn();
		btnCheckIn.addActionListener(listener2);
		
		JButton btnPhoneList = new JButton("Phone List");
		ActionListener listener3 = new ClickPhoneList();
		btnPhoneList.addActionListener(listener3);
		
		
		phonePanel.add(btnPhoneList);
		phonePanel.add(btnCheckIn);
		
		JButton btnMarkSold = new JButton("Mark Sold");
		ActionListener listener4 = new ClickMarkSold();
		btnMarkSold.addActionListener(listener4);
		phonePanel.add(btnMarkSold);
		
		return phonePanel;
	}
	
	/*
	 * Creates panel of morning tasks to be completed.
	 */
	public JPanel createMorningTasks()
	{
		JPanel morningPanel = new JPanel();
		morningPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Morning Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnLogList = new JButton("Log List");
		ActionListener listener2 = new ClickLogList();
		btnLogList.addActionListener(listener2);
		morningPanel.add(btnLogList);
		
		JButton btnSoldPhones = new JButton("Sold Phones");
		morningPanel.add(btnSoldPhones);
		
		return morningPanel;
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
	
	class ClickCheckIn implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			CheckIn checkInScreen = new CheckIn();            
		}
	}
	
	class ClickPhoneList implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//PhoneList phoneListScreen = new PhoneList();
			textArea.append("Test \n\n");
		}
	}
	
	class ClickMarkSold implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			MarkSold markSoldScreen = new MarkSold();
		}
	}
	
	class ClickLogList implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//textArea.append("Test \n\n");
			GetLogList();
		}
	}
	
	class ClickSoldPhones implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			textArea.append("Test \n\n");
		}
	}
	
	class ClickRepList implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			textArea.append("Test \n\n");
		}
	}
	
	class ClickPrintOuts implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			textArea.append("Test \n\n");
		}
	}
	
	public void GetLogList()
	{
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connect to DB
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
			
			String SQL = "SELECT IMEI, Initials, LogDate, Model, Carrier FROM cell_phones " +
						 "WHERE Sold = '0' " +
						 "ORDER BY Carrier ASC, Model ASC";
			
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next())
			{
				String IMEI     = rs.getString("IMEI"); 
				String initials = rs.getString("Initials");
				String logDate  = rs.getString("LogDate");
				String model    = rs.getString("Model");
				String carrier  = rs.getString("Carrier");
				
				textArea.append("IMEI/MEID: " + IMEI);
				textArea.append(" Initials: " + initials);
				textArea.append(" Log Date: " + logDate);
				textArea.append(" Model: " + model);
				textArea.append(" Carrier: " + carrier + "\n");
			}
			rs.close();
			
		}catch(SQLException se){
			//handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try
			{
				if(stmt != null)
					conn.close();
			}catch(SQLException se){
				//Do nothing
			}
			try
			{
				if (conn != null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
	}

}
