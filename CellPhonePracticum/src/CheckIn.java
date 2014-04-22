import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
	
	private File file;
	private String DataForFile;
	
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
		JPanel finishPanel = finishOrCancel();
		JPanel errorPanel = showError();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(5, 1)); //First number is for amount of JPanels in control panel
		
		controlPanel.add(carrierOptions);
		controlPanel.add(datePanel);
		controlPanel.add(IMEIpanel);
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
			
			else if ( (IMEI.getText().equals(IMEIcheck.getText())) && !(IMEI.getText().length()==0) ) //Check if IMEI/MEID entered are equal
			{
				WriteToFile();
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
	 * Used to write data to file.
	 */
	public void WriteToFile()
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		
		try 
		{						
			GatherData();
			
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
			out.println(DataForFile);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally   //Closes files
		{
			try
			{
				if (out != null)
					out.close();
				else if (bw != null)
					bw.close();
				else if (fw != null)
					fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Creates string full of data that was entered.
	 */
	public void GatherData()
	{
		StringBuilder SB = new StringBuilder();
		DataForFile = null;
		
		//Looks for which carrier was selected.
		if (rdbtnAtt.isSelected())
		{
			file = new File ("C:/PhoneInfo/ATT.txt");
			//SB.append("AT&T|");
		}
		
		else if (rdbtnUsCellular.isSelected())
		{
			file = new File ("C:/PhoneInfo/USC.txt");
			//SB.append("USCellular|");
		}
		
		else if (rdbtnVerizon.isSelected())
		{
			file = new File ("C:/PhoneInfo/Verizon.txt");
			//SB.append("Verizon|");
		}

		//Gets all info into StringBuilder 
		SB.append(initialField.getText().toUpperCase() + "|");
		SB.append((String)comboBoxMonth.getSelectedItem() + "," + 
				  (String)comboBoxDay.getSelectedItem() + " " + 
				  (String)comboBoxYear.getSelectedItem() + "|");
		SB.append(IMEI.getText());
		
		DataForFile = SB.toString(); //Puts all info into DataForFile string to write to file.
	}
}
