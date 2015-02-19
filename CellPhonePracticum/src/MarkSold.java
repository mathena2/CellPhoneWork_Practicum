import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.*;


public class MarkSold extends JFrame 
{
	private JTextField txtEnterIMEI;
	private JTextField textIMEI;
	private JTextField textError;
	
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cellphonelog";
		
	//Database credentials
	static final String USER = "root";
	static final String PASS = "";
	
	
	public MarkSold() 
	{	
		createControlPanel();
	}
	
	public void createControlPanel()
	{
		JPanel IMEIpanel = createEnterIMEI();
		JPanel finishPanel = finishOrCancel();
		JPanel errorPanel = showInfo();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(3, 1)); //First number is for amount of JPanels in control panel
		
		controlPanel.add(IMEIpanel);
		controlPanel.add(finishPanel);
		controlPanel.add(errorPanel);
		
		
		//getContentPane().add(controlPanel, BorderLayout.NORTH);
		
		//Sets its size and display to true
		JDialog dialog = new JDialog(this, true);
		dialog.setTitle("Sold Phone");
		dialog.setLocationByPlatform(true);
		dialog.add(controlPanel);
		dialog.setSize(500, 200);                    
		dialog.setVisible(true);
	
	}
	
	/*
	 * Creates panel to enter IMEI number
	 */
	public JPanel createEnterIMEI()
	{
		JPanel IMEIarea = new JPanel();
		
		txtEnterIMEI = new JTextField();
		txtEnterIMEI.setEditable(false);
		txtEnterIMEI.setText("Enter IMEI/MEID:");
		IMEIarea.add(txtEnterIMEI);
		txtEnterIMEI.setColumns(12);
		
		textIMEI = new JTextField();
		IMEIarea.add(textIMEI);
		textIMEI.setColumns(20);
		
		return IMEIarea;
	}
	
	/*
	 * Creates panel for the Finish and Cancel buttons.
	 */
	public JPanel finishOrCancel()
	{
		JPanel finishPanel = new JPanel();
		
		JButton btnFinish = new JButton("Finish");
		ActionListener listener = new ClickFinish();
		btnFinish.addActionListener(listener);
		finishPanel.add(btnFinish);
		
		JButton btnCancel = new JButton("Cancel");
		ActionListener listener2 = new ClickCancel();
		btnCancel.addActionListener(listener2);
		finishPanel.add(btnCancel);
		
		return finishPanel;
	}
	
	/*
	 * Creates panel to display information.
	 */
	public JPanel showInfo()
	{
		JPanel errorPanel = new JPanel();
		
		textError = new JTextField();
		textError.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textError.setColumns(40);
		textError.setForeground(new Color(51, 204, 51));
		textError.setEditable(false);
		errorPanel.add(textError);
		
		return errorPanel;
	}
	
	class ClickFinish implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			WriteToDB();
			textError.setText("Phone with IMEI/MEID (" +
			textIMEI.getText() + ") has been updated as sold.");
			textIMEI.setText("");
		}
	}
	
	class ClickCancel implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			dispose();
		}
	}
	
	/*
	 * Function to connect to database and mark a specific phone as sold
	 */
	public void WriteToDB()
	{
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connect to DB
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
			
			String SQL = "UPDATE cell_phones " +
						 "SET Sold = 1 WHERE IMEI in (?)";
			
			//Gets IMEI entered in text field to select in database
			PreparedStatement pStmt = conn.prepareStatement(SQL);
			pStmt.setString(1, textIMEI.getText());
			
			pStmt.executeUpdate();
			
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
