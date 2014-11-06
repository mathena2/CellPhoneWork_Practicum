import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class CheckIn extends JFrame 
{
	//JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cellphonelog";
	
	//Database credentials
	static final String USER = "root";
	static final String PASS = "";
	
	private JRadioButton rdbtnAtt, rdbtnUsCellular, rdbtnVerizon;
	private JTextField IMEI;
	private JTextField IMEIcheck;
	private JComboBox comboBoxMonth, comboBoxDay, comboBoxYear;
	private JTextField txtError;
	private JLabel lblEnter;
	private JLabel lblReenter;
	private JTextField initialField;
	private JLabel lblInitial;
	private JLabel label;
	
	private String tempDate, tempCarrier;
	private JLabel lblEnterModel;
	private JTextField phoneModel;
	
	public CheckIn() 
	{
		createControlPanel();
	}
	
	
	/*
	 * Creates the control panel to put JPanels of phone information to be put into log list.
	 */
	public void createControlPanel()
	{
		JPanel carrierOptions = createCarrierOptions(); 
		JPanel datePanel = createDatePanel();
		JPanel IMEIpanel = createIMEIpanel();
		JPanel modelPanel = createModelPanel();
		JPanel finishPanel = finishOrCancel();
		JPanel errorPanel = showError();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(6, 1)); //First number is for amount of JPanels in control panel
		
		controlPanel.add(carrierOptions);
		controlPanel.add(datePanel);
		controlPanel.add(IMEIpanel);
		
		controlPanel.add(modelPanel);
		controlPanel.add(errorPanel);
		controlPanel.add(finishPanel);
		
		//Sets its size and display to true
		JDialog dialog = new JDialog(this, true);
		dialog.setTitle("Check in Phone");
		dialog.setLocationByPlatform(true);
		dialog.add(controlPanel);
		dialog.setSize(600, 400);                    
		dialog.setVisible(true);
	}
	
	/*
	 * Creates a panel to pick which carrier to check phone in for.
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
	 * Creates panel to pick date of phone check in.
	 */
	public JPanel createDatePanel()
	{
		JPanel datePanel = new JPanel();
		datePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Initial/Date", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		lblInitial = new JLabel("Initial:");
		lblInitial.setHorizontalAlignment(SwingConstants.LEFT);
		datePanel.add(lblInitial);
		
		initialField = new JTextField();
		datePanel.add(initialField);
		initialField.setColumns(6);
		
		label = new JLabel("          "); //Space filler
		datePanel.add(label);
		
		comboBoxMonth = new JComboBox();
		comboBoxMonth.setModel(new DefaultComboBoxModel(new String[] {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"}));
		datePanel.add(comboBoxMonth);
		
		comboBoxDay = new JComboBox();
		comboBoxDay.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		datePanel.add(comboBoxDay);
		
		comboBoxYear = new JComboBox();
		comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {"2014", "2015", "2016", "2017", "2018"}));
		datePanel.add(comboBoxYear);

		return datePanel;
	}
	
	/*
	 * Creates panel to enter phone model.
	 */
	public JPanel createModelPanel()
	{
		JPanel modelPanel = new JPanel();
		modelPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblEnterModel = new JLabel("Enter:");
		modelPanel.add(lblEnterModel);
		
		phoneModel = new JTextField();
		modelPanel.add(phoneModel);
		phoneModel.setColumns(20);
		
		return modelPanel;
	}
	
	/*
	 * Creates panel to enter in IMEI/MEID numbers.
	 */
	public JPanel createIMEIpanel()
	{
		JPanel IMEIpanel = new JPanel();
		IMEIpanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "IMEI/MEID Numbers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		lblEnter = new JLabel("Enter:");
		IMEIpanel.add(lblEnter);
		
		IMEI = new JTextField();
		IMEIpanel.add(IMEI);
		IMEI.setColumns(15);
		
		lblReenter = new JLabel("Re-enter:");
		IMEIpanel.add(lblReenter);
		
		IMEIcheck = new JTextField();
		IMEIpanel.add(IMEIcheck);
		IMEIcheck.setColumns(15);
		
		return IMEIpanel;
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
	
	class ClickFinish implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if ( !rdbtnAtt.isSelected() && !rdbtnUsCellular.isSelected() && !rdbtnVerizon.isSelected() ) //Check for carrier
				txtError.setText("ERROR: Select a carrier.");
			
			else if ( initialField.getText().length()==0 )      //Check for initials
				txtError.setText("ERROR: Enter your initials.");
			
			else if ( phoneModel.getText().length()==0 )  //Check for model
				txtError.setText("ERROR: Enter phone model.");
			
			else if ( (IMEI.getText().equals(IMEIcheck.getText())) && !(IMEI.getText().length()==0) ) //Check if IMEI/MEID entered are equal
			{
				WriteToDB();
				dispose();
			}
			else if (IMEI.getText().length()==0 && IMEIcheck.getText().length()==0) //Check if IMEI/MEID entered
				 txtError.setText("ERROR: Enter in IMEI/MEID numbers.");
			else
			txtError.setText("ERROR: IMEI/MEID numbers do no match."); //Check if IMEI/MEID numbers match
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
	 * Used to write data to the DB.
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
			
			String SQL = "INSERT INTO cell_phones (IMEI, Initials, LogDate, Model, Sold, Carrier)" +
						 "VALUES (?, ?, ?, ?, 0, ?)";
			
			//Puts date into the correct format
			tempDate = (String)comboBoxYear.getSelectedItem() 
					    + "-" + ((int)comboBoxMonth.getSelectedIndex() +1)
						+ "-" + (String)comboBoxDay.getSelectedItem();
			
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
			
			PreparedStatement pStmt = conn.prepareStatement(SQL);
			pStmt.setString(1, IMEI.getText());
			pStmt.setString(2, initialField.getText().toUpperCase());
			pStmt.setString(3, tempDate);
			pStmt.setString(4, phoneModel.getText().toUpperCase());
			pStmt.setString(5, tempCarrier);
			
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
