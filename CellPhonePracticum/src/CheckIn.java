import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class CheckIn extends JFrame 
{
	private JRadioButton rdbtnAtt, rdbtnUsCellular, rdbtnVerizon;
	private JTextField txtEnter;
	private JTextField IMEI;
	private JTextField txtReenter;
	private JTextField IMEIcheck;
	private JComboBox comboBoxMonth, comboBoxDay, comboBoxYear;
	private JTextField txtError;
	
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
		
		getContentPane().add(controlPanel, BorderLayout.CENTER);
	
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
		datePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Date", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
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
		
		txtEnter = new JTextField();
		txtEnter.setEditable(false);
		txtEnter.setText("Enter:");
		IMEIpanel.add(txtEnter);
		txtEnter.setColumns(5);
		
		IMEI = new JTextField();
		IMEIpanel.add(IMEI);
		IMEI.setColumns(15);
		
		txtReenter = new JTextField();
		txtReenter.setEditable(false);
		txtReenter.setText("Re-enter:");
		IMEIpanel.add(txtReenter);
		txtReenter.setColumns(6);
		
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
			if ( !rdbtnAtt.isSelected() && !rdbtnUsCellular.isSelected() && !rdbtnVerizon.isSelected() )
				txtError.setText("ERROR: Select a carrier.");
			
			else if ( (IMEI.getText().equals(IMEIcheck.getText())) && !(IMEI.getText().length()==0) )
			{
				System.out.println(comboBoxDay.getSelectedItem().toString()); //Test print out, will write to file later
				dispose();
			}
			else if (IMEI.getText().length()==0 && IMEIcheck.getText().length()==0)
				 txtError.setText("ERROR: Please enter in IMEI/MEID numbers.");
			else
			txtError.setText("ERROR: IMEI/MEID numbers do no match.");
		}
	}
	
	class ClickCancel implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			dispose();
		}
	}
}
