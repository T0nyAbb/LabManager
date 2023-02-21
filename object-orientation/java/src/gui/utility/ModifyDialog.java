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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;

import control.Controller;
import dto.Strumento;
import gui.buttons.RectangleButton;
import gui.utility.date.DatePicker;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JDialog;

public class ModifyDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private DatePicker datePicker;
	private JLabel dataInizioLabel;
	private RectangleButton okButton;
	private ReservationTimeComboBox timeComboBox;
	private JLabel durataLabel;
	private JSpinner spinner;
	private JFormattedTextField dateTextField;
	private JLabel oraInizioLabel;
	private DateFormat dateFormat;
	private DateFormat dateTimeFormat;
	private DateFormat timeFormat;
	private int idStrumento;
	
	private Date dataInizio;
	private int durata;
	
	public ModifyDialog(Controller controller, JFrame jframe) {
		super(jframe);
		this.controller = controller;
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
	
	public void setIdStrumento(int idStrumento) {
		this.idStrumento = idStrumento;
	}
	
	private void generateTextFields() {
		dateTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
		dateTextField.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		dateTextField.setValue(new Date());
		dateTextField.setEditable(false);
		dateTextField.setBounds(191, 52, 205, 28);
		dateTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				loadTimeComboBox();				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}
		});
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
		
		timeComboBox = new ReservationTimeComboBox(controller);
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

	private void loadTimeComboBox() {
		Strumento s = new Strumento(null, null);
		s.setId(idStrumento);
		try {
			timeComboBox.fillForStrumentoAndDate(s, new SimpleDateFormat("yyyy-MM-dd").parse(dateTextField.getText()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
	
}
