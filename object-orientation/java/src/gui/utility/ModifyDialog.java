package gui.utility;

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

import gui.buttons.RectangleButton;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class ModifyDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	DatePicker datePicker;
	JLabel dataInizioLabel;
	RectangleButton okButton;
	JComboBox<String> timeComboBox;
	JLabel durataLabel;
	JSpinner spinner;
	JFormattedTextField dateTextField;
	JLabel oraInizioLabel;
	DateFormat dateFormat;
	DateFormat dateTimeFormat;
	DateFormat timeFormat;
	
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
		setBounds(100, 100, 754, 352);
		setTitle("Modifica Prenotazione");
		setResizable(false);
		setLocationRelativeTo(jframe);
		contentPane = new JPanel();
		contentPane.setBackground(Style.background_color_01);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		timeFormat = new SimpleDateFormat("HH:mm"); 
		
		dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		dataInizioLabel.setForeground(Style.entered_color_01);
		dataInizioLabel.setBounds(37, 47, 144, 33);
		contentPane.add(dataInizioLabel);
		
		oraInizioLabel = new JLabel("Ora Inizio");
		oraInizioLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		oraInizioLabel.setForeground(Style.entered_color_01);
		oraInizioLabel.setBounds(37, 123, 137, 33);
		contentPane.add(oraInizioLabel);
		
	    dateTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
		dateTextField.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		dateTextField.setValue(new Date());
		dateTextField.setEditable(false);
		dateTextField.setBounds(191, 52, 205, 28);
		contentPane.add(dateTextField);
		
		spinner = new JSpinner();
		spinner.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		spinner.setModel(new SpinnerNumberModel(1, 1, 24, 1));
		spinner.setBounds(318, 194, 78, 33);
		contentPane.add(spinner);
		
		durataLabel = new JLabel("Durata (Ore)");
		durataLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		durataLabel.setForeground(Style.entered_color_01);
		durataLabel.setBounds(37, 192, 110, 33);
		contentPane.add(durataLabel);
		
		timeComboBox = new JComboBox<String>();
		timeComboBox.setModel(new DefaultComboBoxModel<String> (new String[] {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"}));
		timeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		timeComboBox.setBounds(191, 127, 203, 28);
		contentPane.add(timeComboBox);
		
		datePicker = new DatePicker();
		datePicker.setLinkedTextField(dateTextField);
		datePicker.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
		datePicker.setBounds(437, 11, 291, 291);
		contentPane.add(datePicker);
		
		okButton = new RectangleButton();
		okButton.setText("OK");
		okButton.setFocusable(false);
		okButton.setBounds(154, 262, 119, 28);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        String strDate = dateFormat.format((Date) dateTextField.getValue());
		        String strTime = timeComboBox.getSelectedItem().toString();
				try {
					dataInizio = dateTimeFormat.parse(strDate + " " + strTime);
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

	public void setDataInizio(Date dataInizio) {
		dateTextField.setValue(dataInizio);
		timeComboBox.setSelectedItem(timeFormat.format(dataInizio));
	}

	public void setDurata(int durata) {
		spinner.setValue(durata);
	}

	
	
}
