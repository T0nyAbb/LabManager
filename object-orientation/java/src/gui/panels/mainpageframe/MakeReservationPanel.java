package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import control.Controller;
import dto.Sede;
import dto.Strumento;
import gui.buttons.RectangleButton;
import gui.utility.DatePicker;
import gui.utility.Style;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;

public class MakeReservationPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private DatePicker datePicker;
	private JFormattedTextField dateTextField;
	private JLabel headerLabel;
	private JButton dateButton;
	private JLabel sedeLabel;
	private JLabel strumentoLabel;
	private JLabel dataInizioLabel;
	private JLabel oraInizioLabel;
	private JLabel durataLabel;
	private JLabel errorLabel;
	private JSpinner spinner;
	private JComboBox<String> strumentoComboBox;
	private JComboBox<String> sedeComboBox;
	private JComboBox<String> timeComboBox;
	private JButton effettuaPrenotazioneButton;

	public MakeReservationPanel(Controller controller) throws SQLException {
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
		setErrorMessageColor(Style.foreground_color_error);
		
		fillSedeComboBox();
		fillStrumentoComboBox();
	}
	
	public void showErrorMessage(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}
	
	public void clearErrorMessage() {
		errorLabel.setVisible(false);
	}
	
	public void setErrorMessageColor(Color c) {
		errorLabel.setForeground(c);
	}
	
	public void fillSedeComboBox() throws SQLException {
		List<Sede> sedi = controller.getSedeDao().getAll();
		
		if(sedi.size() > 0) {
			String[] stringSedi = new String[sedi.size()+1];
			stringSedi[0] = "Tutte le sedi";
			for(int x=1; x<sedi.size()+1; x++) {
				int id = sedi.get(x-1).getId();
				String indirizzo = sedi.get(x-1).getIndirizzo();
				String nome_lab = sedi.get(x-1).getLaboratorio().getNome();
				stringSedi[x] = id +": "+ nome_lab + ", "+ indirizzo;
			}
		sedeComboBox.setModel(new DefaultComboBoxModel<String> (stringSedi));
		}
	}
	
	public void fillStrumentoComboBox() throws SQLException {
		
		if(sedeComboBox.getSelectedItem() == null) {
			strumentoComboBox.setModel(new DefaultComboBoxModel<String> (new String[0]));
		}
		else {
			String item = (String) sedeComboBox.getSelectedItem();
			List<Strumento> strumenti;
			if(item.indexOf(":") != -1) {
				Sede sede = new Sede(null);
				sede.setId(Integer.parseInt(item.split(":")[0]));
				strumenti = controller.getStrumentoDao().getStrumentoBySede(sede);
			}else {
				strumenti = controller.getStrumentoDao().getAll();
			}
	
			if(strumenti.size() > 0) {
				String[] stringStrumenti = new String[strumenti.size()];
				for(int x=0; x<strumenti.size(); x++) {
					int id = strumenti.get(x).getId();
					String desc = strumenti.get(x).getDescrizione();
					String post_nome = strumenti.get(x).getPostazione().getNome();
					String sede_indirizzo = strumenti.get(x).getPostazione().getSede().getIndirizzo();
					String lab_nome = strumenti.get(x).getPostazione().getSede().getLaboratorio().getNome();
					stringStrumenti[x] = id + ": " +desc + ", "+ lab_nome + ", " + sede_indirizzo + ", " + post_nome;
				}
			strumentoComboBox.setModel(new DefaultComboBoxModel<String> (stringStrumenti));
			}
		}
	}
	
	public void setPanelSettings() {
		setBackground(Style.background_color_01);
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
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		sedeLabel = new JLabel("Sede");
		sedeLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		sedeLabel.setForeground(Style.entered_color_01);
		
		strumentoLabel = new JLabel("Strumento");
		strumentoLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
		strumentoLabel.setForeground(Style.entered_color_01);
		
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
		
		errorLabel = new JLabel("ERROR");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        errorLabel.setForeground(Style.foreground_color_error);
        errorLabel.setBounds(0, 196, 440, 31);
	}
	
	private void generateComboBox() {
		strumentoComboBox = new JComboBox<String>();
		strumentoComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
		
		sedeComboBox = new JComboBox<String>();
		sedeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
		sedeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fillStrumentoComboBox();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		timeComboBox = new JComboBox<String>();
		timeComboBox.setModel(new DefaultComboBoxModel<String> (new String[] {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",  "15:00", "15:30",  "16:00", "16:30",  "17:00", "17:30",  "18:00", "18:30",  "19:00", "19:30",  "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"}));
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
				.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(sedeLabel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraInizioLabel)
								.addComponent(durataLabel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
								.addComponent(strumentoLabel))
							.addGap(28)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(timeComboBox, 0, 415, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(dateTextField, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(dateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(strumentoComboBox, Alignment.LEADING, 0, 413, Short.MAX_VALUE)
											.addComponent(sedeComboBox, 0, 413, Short.MAX_VALUE))
										.addGap(2)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(9)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)))
							.addGap(18))
						.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
					.addGap(14)
					.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(224)
					.addComponent(effettuaPrenotazioneButton, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
					.addGap(210))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(sedeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(strumentoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(17)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(dateTextField, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
										.addComponent(dateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addComponent(timeComboBox, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addGap(27)
									.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
									.addGap(26))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(sedeLabel)
									.addGap(18)
									.addComponent(strumentoLabel)
									.addGap(17)
									.addComponent(dataInizioLabel)
									.addGap(18)
									.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(durataLabel)
									.addGap(154)))
							.addGap(91))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(43))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(480, Short.MAX_VALUE)
					.addComponent(effettuaPrenotazioneButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
		);
		setLayout(groupLayout);
	}
	
	private void makeReservation() {
		if(strumentoComboBox.getSelectedItem() != null) {
			int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
			Strumento strumento = new Strumento(null, null, null);
			strumento.setId(id_strumento);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ITALY);
	        String strDate = dateFormat.format((Date) dateTextField.getValue());  
	        String dataInizioString;
			Date dataInizio = null;
			String ora = (String) timeComboBox.getSelectedItem();
			dataInizioString = strDate + " " + ora;
			try{
				dataInizio = timeFormat.parse(dataInizioString);
			} catch(ParseException e) {
				e.printStackTrace();
			}
	
			int durata = (int) spinner.getValue();
			controller.insertPrenotazione(strumento, dataInizio, durata);
		}
	}
}
