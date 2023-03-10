package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import control.Controller;

import dto.Strumento;
import gui.buttons.RectangleButton;
import gui.utility.ReservationTimeComboBox;
import gui.utility.Style;
import gui.utility.date.DatePicker;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.Locale;
import javax.swing.JSpinner;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.SpinnerNumberModel;


public class MakeReservationDatePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private DatePicker datePicker;
	private JFormattedTextField dateTextField;
	private JLabel headerLabel;
	private JButton dateButton;
	private JLabel dataInizioLabel;
	private JLabel oraInizioLabel;
	private JLabel durataLabel;
	private JLabel errorLabel;
	private JSpinner spinner;
	private ReservationTimeComboBox timeComboBox;
	private RectangleButton effettuaPrenotazioneButton;
	private RectangleButton goBackButton;
	private JLabel strumentoLabel;
	private JLabel strumentoLabel2;
	private int idStrumento;

	/**
	 * @wbp.parser.constructor
	 */
	public MakeReservationDatePanel(Controller controller) throws SQLException {
		this.controller = controller;
		
		setPanelSettings();
		generateTextFields();
		generateLabels();
		generateButtons();
		generateComboBox();
		generateSpinner();
		generateDatePicker();
		setLayoutComponents();
		clearErrorMessage();
		loadTimeComboBox();
		setErrorMessageColor(Style.foreground_color_error);
		
	}

	
	public void setStrumentoLabelText(String text) {
		strumentoLabel2.setText(text);
	}
	
	public void showErrorMessage(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}
	
	public void clearErrorMessage() {
		errorLabel.setText("");
	}
	
	public void setErrorMessageColor(Color c) {
		errorLabel.setForeground(c);
	}
	
	public void setPanelSettings() {
		setBackground(Style.background_color_01);
	}
	
	public int getIdStrumento() {
		return idStrumento;
	}
	
	public void setIdStrumento(int idStrumento) {
		this.idStrumento = idStrumento;
		try {
			Strumento s = controller.getStrumentoDao().getById(idStrumento);
			setStrumentoLabelText(s.getId() + ": " + s.getDescrizione());
		}catch(SQLException e) {
			setStrumentoLabelText("Descrizione non presente.");
		}
	}
	
	private void generateDatePicker() {
		datePicker = new DatePicker();
		datePicker.setLinkedTextField(dateTextField);
		datePicker.setBorder(new LineBorder(Style.background_color_04, 1, true));
		datePicker.setVisible(false);
	}
	
	private void generateTextFields() {
		dateTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
		dateTextField.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		dateTextField.setValue(new Date());
		dateTextField.setEditable(false);
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
	}
	
	private void generateButtons() {
		dateButton = new JButton();
		dateButton.setText("...");
		dateButton.setFocusable(false);
		dateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(datePicker.isVisible())
					datePicker.setVisible(false);
				else
					datePicker.setVisible(true);
			}
		});
		
		effettuaPrenotazioneButton = new RectangleButton();
		effettuaPrenotazioneButton.setText("Effettua Prenotazione");
		effettuaPrenotazioneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeReservation();
				loadTimeComboBox();
			}
		});
		

		goBackButton = new RectangleButton("Indietro");
		goBackButton.setDefaultColor(Style.default_color_03);
		goBackButton.setEnteredColor(Style.entered_color_03);
		goBackButton.setPressedColor(Style.pressed_color_03);
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMakeReservationStrumento();
			}
		});
	}
	
	private void generateLabels() {
		headerLabel = new JLabel();
		headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Style.background_color_01);
		headerLabel.setText("Effettua Prenotazione");
		headerLabel.setFont(new Font(Style.font_name_01, Font.ITALIC, 25));
		headerLabel.setBackground(Style.background_color_03);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 45));
		
		dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		dataInizioLabel.setForeground(Style.entered_color_01);
		
		oraInizioLabel = new JLabel("Ora Inizio");
		oraInizioLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		oraInizioLabel.setForeground(Style.entered_color_01);
		
		durataLabel = new JLabel("Durata (Ore)");
		durataLabel.setForeground(Color.RED);
		durataLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		durataLabel.setForeground(Style.entered_color_01);
		
		strumentoLabel = new JLabel("Strumento");
		strumentoLabel.setForeground(Style.entered_color_01);
		strumentoLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		
		strumentoLabel2 = new JLabel("Descrizione non presente");
		strumentoLabel2.setForeground(Style.foreground_color_01);
		strumentoLabel2.setFont(new Font(Style.font_name_01, Font.PLAIN, 16));
		
		errorLabel = new JLabel("ERROR");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        errorLabel.setForeground(Style.foreground_color_error);
        errorLabel.setBounds(0, 196, 440, 31);
	}
	
	private void generateComboBox() {
		timeComboBox = new ReservationTimeComboBox(controller);
		timeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
	}
	
	private void generateSpinner() {
		spinner = new JSpinner();
		spinner.setFont(new Font(Style.font_name_02, Font.PLAIN, 14));
		spinner.setModel(new SpinnerNumberModel(1, 1, 24, 1));
	}
	
	private void setLayoutComponents() {
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(goBackButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(216)
					.addComponent(effettuaPrenotazioneButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(318))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(durataLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(oraInizioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(dataInizioLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(strumentoLabel, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(dateTextField, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(dateButton))
								.addComponent(timeComboBox, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
								.addComponent(strumentoLabel2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
					.addGap(62))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(strumentoLabel2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(strumentoLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(dataInizioLabel)
								.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(dateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(timeComboBox, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(durataLabel)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addComponent(datePicker, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
					.addGap(146)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(effettuaPrenotazioneButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addComponent(goBackButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addGap(15))
		);
		setLayout(groupLayout);
	}
	
	private void makeReservation() {
		try{
			if(idStrumento != 0 && !dateTextField.getValue().toString().isBlank()) {
			Strumento strumento = new Strumento(null, null, null);
			strumento.setId(idStrumento);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ITALY);
	        String strDate = dateFormat.format((Date) dateTextField.getValue());  
	        String dataInizioString;
			Date dataInizio = null;
			String ora = (String) timeComboBox.getSelectedItem();
			dataInizioString = strDate + " " + ora;
			dataInizio = timeFormat.parse(dataInizioString);
			
	
			int durata = (int) spinner.getValue();
			controller.insertPrenotazione(strumento, dataInizio, durata);
			}
		} catch(ParseException e) {
			clearErrorMessage();
		}
	}
	
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
