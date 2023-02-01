package gui.utility;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DateFormatter;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class ModifyDialog extends JDialog {

	private JPanel contentPane;
	DatePicker datePicker;
	JLabel dataInizioLabel;
	JButton okButton;
	JComboBox<String> timeComboBox;
	JLabel durataLabel;
	JSpinner spinner;
	JFormattedTextField dateTextField;
	JLabel oraInizioLabel;
	
	Date dataInizio;
	int durata;
	
	/**
	 * Create the frame.
	 */
	public ModifyDialog(JFrame jframe) {
		super(jframe);
		setModal (true);
		setAlwaysOnTop (true);
		setModalityType (ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 754, 334);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		dataInizioLabel.setForeground(new Color(0, 68, 140));
		dataInizioLabel.setBounds(37, 47, 144, 33);
		contentPane.add(dataInizioLabel);
		
		oraInizioLabel = new JLabel("Ora Inizio");
		oraInizioLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		oraInizioLabel.setForeground(new Color(0, 68, 140));
		oraInizioLabel.setBounds(37, 123, 137, 33);
		contentPane.add(oraInizioLabel);
		
	    dateTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
		dateTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateTextField.setValue(new Date());
		dateTextField.setEditable(false);
		dateTextField.setBounds(191, 52, 205, 28);
		contentPane.add(dateTextField);
		
		spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner.setModel(new SpinnerNumberModel(1, 1, 24, 1));
		spinner.setBounds(318, 194, 78, 33);
		contentPane.add(spinner);
		
		durataLabel = new JLabel("Durata (Ore)");
		durataLabel.setForeground(Color.RED);
		durataLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		durataLabel.setForeground(new Color(0, 68, 140));
		durataLabel.setBounds(37, 192, 110, 33);
		contentPane.add(durataLabel);
		
		timeComboBox = new JComboBox<String>();
		timeComboBox.setModel(new DefaultComboBoxModel<String> (new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00 ", "13:00 ", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}));
		timeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timeComboBox.setBounds(191, 127, 203, 28);
		contentPane.add(timeComboBox);
		
		datePicker = new DatePicker();
		datePicker.setFormattedTextField(dateTextField);
		datePicker.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
		datePicker.setBounds(441, 11, 283, 271);
		contentPane.add(datePicker);
		
		okButton = new JButton();
		okButton.setText("OK");
		okButton.setFocusable(false);
		okButton.setBounds(158, 254, 119, 28);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		        String strDate = dateFormat.format((Date) dateTextField.getValue());
				try {
					dataInizio = timeFormat.parse(strDate + " " + timeComboBox.getSelectedItem());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				durata = (int) spinner.getValue();
				setVisible(false);
			}
			
		});
		contentPane.add(okButton);
	}

	public Date getDataInizio() {
		return dataInizio;
	}


	public int getDurata() {
		return durata;
	}

	
	
}
