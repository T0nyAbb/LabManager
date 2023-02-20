package gui.utility;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import javax.swing.text.DateFormatter;

import control.Controller;
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
	
	private DatePicker datePicker;
	private JLabel dataInizioLabel;
	private RectangleButton okButton;
	private JComboBox<String> timeComboBox;
	private JLabel durataLabel;
	private JSpinner spinner;
	private JFormattedTextField dateTextField;
	private JLabel oraInizioLabel;
	private DateFormat dateFormat;
	private DateFormat dateTimeFormat;
	private DateFormat timeFormat;
	
	private Date dataInizio;
	private int durata;
	
	public ModifyDialog(JFrame jframe) {
		super(jframe);
		setDialogSettings();
		generateLabels();
		generateButtons();
		generateTextFields();
		generateSpecialComponents();
		setLocationRelativeTo(jframe);
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
		datePicker.displayDate(dataInizio);
	}

	public void setDurata(int durata) {
		spinner.setValue(durata);
	}
	
	private void generateTextFields() {
		dateTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
		dateTextField.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		dateTextField.setValue(new Date());
		dateTextField.setEditable(false);
		dateTextField.setBounds(191, 52, 205, 28);
		getContentPane().add(dateTextField);
		
	}

	private void setDialogSettings() {
		setModal (true);
		setAlwaysOnTop (true);
		setModalityType (ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 754, 352);
		setTitle("Modifica Prenotazione");
		setResizable(false);
		getContentPane().setBackground(Style.background_color_01);
		getContentPane().setLayout(null);
	}

	private void generateLabels() {
		dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		dataInizioLabel.setForeground(Style.entered_color_01);
		dataInizioLabel.setBounds(37, 47, 144, 33);
		getContentPane().add(dataInizioLabel);
		
		oraInizioLabel = new JLabel("Ora Inizio");
		oraInizioLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		oraInizioLabel.setForeground(Style.entered_color_01);
		oraInizioLabel.setBounds(37, 123, 137, 33);
		getContentPane().add(oraInizioLabel);
		
		durataLabel = new JLabel("Durata (Ore)");
		durataLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		durataLabel.setForeground(Style.entered_color_01);
		durataLabel.setBounds(37, 192, 110, 33);
		getContentPane().add(durataLabel);
	}
	
	private void generateButtons() {
		okButton = new RectangleButton();
		okButton.setText("OK");
		okButton.setFocusable(false);
		okButton.setBounds(154, 262, 119, 28);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        confirm();
			}
		});
		getContentPane().add(okButton);
	}
	
	private void generateSpecialComponents() {

		dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		timeFormat = new SimpleDateFormat("HH:mm"); 
		
		spinner = new JSpinner();
		spinner.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		spinner.setModel(new SpinnerNumberModel(1, 1, 24, 1));
		spinner.setBounds(318, 194, 78, 33);
		getContentPane().add(spinner);
		
		timeComboBox = new JComboBox<String>();
		timeComboBox.setModel(new DefaultComboBoxModel<String> (new String[] {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"}));
		timeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		timeComboBox.setBounds(191, 127, 203, 28);
		getContentPane().add(timeComboBox);
		
		datePicker = new DatePicker();
		datePicker.setLinkedTextField(dateTextField);
		datePicker.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
		datePicker.setBounds(437, 11, 291, 291);
		getContentPane().add(datePicker);
		
	}
	
	
	private void confirm() {
		String strDate = dateFormat.format((Date) dateTextField.getValue());
        String strTime = timeComboBox.getSelectedItem().toString();
		try {
			dataInizio = dateTimeFormat.parse(strDate + " " + strTime);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		durata = (int) spinner.getValue();
		setVisible(false);
	};

	
	
}
