import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PhoneList extends JFrame
{
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cellphonelog";
		
	//Database credentials
	static final String USER = "root";
	static final String PASS = "";
	
	private JRadioButton rdbtnAtt, rdbtnUsCellular, rdbtnVerizon;
	private JTextField txtError;
	private JTextArea textArea;
	
	
	public PhoneList() 
	{	
		createControlPanel();
	}
	
	public void createControlPanel()
	{
		textArea = new JTextArea("");
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		JPanel carrierOptions = createCarrierOptions(); 
		JPanel errorPanel = showError();
		JPanel finishPanel = finishOrCancel();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(3, 1)); //First number is for amount of JPanels in control panel
		
		//controlPanel.add(scrollPane);
		controlPanel.add(carrierOptions);
		controlPanel.add(errorPanel);
		controlPanel.add(finishPanel);
		
		//getContentPane().add(controlPanel, BorderLayout.SOUTH);
		
		
		//getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		//Sets its size and display to true
		JDialog dialog = new JDialog(this, true);
		dialog.setTitle("Search for phones");
		dialog.setLocationByPlatform(true);
		dialog.add(scrollPane, BorderLayout.CENTER);
		dialog.add(controlPanel, BorderLayout.SOUTH);
		dialog.setSize(600, 400);                    
		dialog.setVisible(true);
		
	}
	
	/*
	 * Creates a panel to choose which carrier to search
	 */
	public JPanel createCarrierOptions()
	{
		JPanel toolPanel = new JPanel();
		toolPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Carrier", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		rdbtnAtt = new JRadioButton("AT&T");
		rdbtnUsCellular = new JRadioButton("US Cellular");
		rdbtnVerizon = new JRadioButton("Verizon");
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAtt);
		group.add(rdbtnUsCellular);
		group.add(rdbtnVerizon);
		group.clearSelection();
		
		toolPanel.add(rdbtnAtt);
		toolPanel.add(rdbtnUsCellular);
		toolPanel.add(rdbtnVerizon);
		
		return toolPanel;
	}
	
	/*
	 * Creates panel to display errors.
	 */
	public JPanel showError()
	{
		JPanel errorPanel = new JPanel();
		
		txtError = new JTextField();
		txtError.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtError.setColumns(50);
		txtError.setForeground(Color.RED);
		txtError.setEditable(false);
		errorPanel.add(txtError);
		
		return errorPanel;
	}
	
	/*
	 * Creates panel for the Finish and Cancel buttons.
	 */
	public JPanel finishOrCancel()
	{
		JPanel finishPanel = new JPanel();
		
		JButton btnSearch = new JButton("Search");
		ActionListener listener = new ClickSearch();
		btnSearch.addActionListener(listener);
		finishPanel.add(btnSearch);
		
		JButton btnCancel = new JButton("Cancel");
		ActionListener listener2 = new ClickCancel();
		btnCancel.addActionListener(listener2);
		finishPanel.add(btnCancel);
		
		return finishPanel;
	}
	
	class ClickSearch implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			txtError.setText(""); 
			
			if ( !rdbtnAtt.isSelected() && !rdbtnUsCellular.isSelected() && !rdbtnVerizon.isSelected() ) //Check for carrier
				txtError.setText("ERROR: Select a carrier.");
			else 
				SearchPhones();
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
	 * function to connect to database and query phones for the carrier selected
	 */
	public void SearchPhones()
	{
		String tempCarrier = null;
		
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Connect to DB
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			stmt = conn.createStatement();
			
			String SQL = "SELECT IMEI, Initials, LogDate, Model, Carrier FROM cell_phones " +
					 	 "WHERE Sold = '0' AND Carrier = (?) " +
					 	 "ORDER BY Carrier ASC, Model ASC";
		
			//Looks for which carrier was selected.
			if (rdbtnAtt.isSelected())
			{
				tempCarrier = "AT&T";
			}
			
			else if (rdbtnUsCellular.isSelected())
			{
				tempCarrier = "US Cellular";
			}
			
			else if (rdbtnVerizon.isSelected())
			{
				tempCarrier = "Verizon";
			}
			
			//Gets carrier selected into SQL statement
			PreparedStatement pStmt = conn.prepareStatement(SQL);
			pStmt.setString(1, tempCarrier);
			
			ResultSet rs = pStmt.executeQuery();
		
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
			
			textArea.append("\n\n");
			
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
