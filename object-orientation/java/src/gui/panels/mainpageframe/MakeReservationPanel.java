package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import control.Controller;
import dao.SedeDao;
import dao.StrumentoDao;
import dto.Sede;
import dto.Strumento;
import gui.buttons.RectangleButton;
import gui.utility.DatePicker;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
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
	private JLabel successLabel;
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
		setErrorMessageColor(Color.RED);
		
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
		List<Sede> sedi = new SedeDao(controller.getDatabaseConnection()).getAll();
		
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
		String item = sedeComboBox.getSelectedItem().toString();
		List<Strumento> strumenti;
		StrumentoDao sDao = new StrumentoDao(controller.getDatabaseConnection());
		if(item.indexOf(":") != -1) {
			Sede sede = new Sede(null);
			sede.setId(Integer.parseInt(item.split(":")[0]));
			strumenti = sDao.getStrumentoBySede(sede);
		}else {
			strumenti = sDao.getAll();
		}
		
		if(strumenti.size() > 0) {
			String[] stringStrumenti = new String[strumenti.size()];
			for(int x=0; x<strumenti.size(); x++) {
				int id = strumenti.get(x).getId();
				String desc = strumenti.get(x).getDescrizione();
				String post_nome = strumenti.get(x).getPostazione().getNome();
				String sede_indirizzo = strumenti.get(x).getPostazione().getSede().getIndirizzo();
				String lab_nome = strumenti.get(x).getPostazione().getSede().getLaboratorio().getNome();
				stringStrumenti[x] = id + ": " +desc + ", "+ lab_nome + sede_indirizzo + post_nome;
			}
		strumentoComboBox.setModel(new DefaultComboBoxModel<String> (stringStrumenti));
		}
	}
	
	public void setPanelSettings() {
		setBackground(Color.WHITE);
	}
	
	private void generateDatePicker() {
		datePicker = new DatePicker();
		datePicker.setFormattedTextField(dateTextField);
		datePicker.setBorder(new LineBorder(Color.GRAY));
		datePicker.setVisible(false);
	}
	
	private void generateTextFields() {
		dateTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
		dateTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		
		effettuaPrenotazioneButton = new RectangleButton(new Color(0, 40, 83), new Color(0, 68, 140), new Color(90, 120, 200));
		effettuaPrenotazioneButton.setText("Effettua Prenotazione");
		effettuaPrenotazioneButton.setForeground(Color.white);
		effettuaPrenotazioneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeReservation();
			}
		});
	}
	
	private void generateLabels() {
		headerLabel = new JLabel();
		headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setText("Effetua Prenotazione");
		headerLabel.setFont(new Font("Century Gothic", Font.ITALIC, 25));
		headerLabel.setBackground(Color.GRAY);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		sedeLabel = new JLabel("Sede");
		sedeLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		sedeLabel.setForeground(new Color(0, 68, 140));
		
		strumentoLabel = new JLabel("Strumento");
		strumentoLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		strumentoLabel.setForeground(new Color(0, 68, 140));
		
		dataInizioLabel = new JLabel("Data Inizio");
		dataInizioLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		dataInizioLabel.setForeground(new Color(0, 68, 140));
		
		oraInizioLabel = new JLabel("Ora Inizio");
		oraInizioLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		oraInizioLabel.setForeground(new Color(0, 68, 140));
		
		durataLabel = new JLabel("Durata (Ore)");
		durataLabel.setForeground(Color.RED);
		durataLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		durataLabel.setForeground(new Color(0, 68, 140));
		
		errorLabel = new JLabel("ERROR");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(0, 196, 440, 31);
	}
	
	private void generateComboBox() {
		strumentoComboBox = new JComboBox<String>();
		strumentoComboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		sedeComboBox = new JComboBox<String>();
		sedeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
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
		timeComboBox.setModel(new DefaultComboBoxModel<String> (new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00 ", "13:00 ", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}));
		timeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
	}
	
	private void generateSpinner() {
		spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner.setModel(new SpinnerNumberModel(1, 1, 24, 1));
	}
	
	private void setLayoutComponents() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 851, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(sedeLabel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
						.addComponent(dataInizioLabel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addComponent(oraInizioLabel)
						.addComponent(durataLabel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addComponent(strumentoLabel))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(timeComboBox, 0, 308, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(dateTextField, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(dateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(strumentoComboBox, Alignment.LEADING, 0, 306, Short.MAX_VALUE)
										.addComponent(sedeComboBox, 0, 306, Short.MAX_VALUE))
									.addGap(2)))
							.addGap(6))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
					.addGap(67))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(298)
					.addComponent(effettuaPrenotazioneButton, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGap(285))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(200, Short.MAX_VALUE)
					.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE)
					.addGap(188))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
								.addComponent(sedeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(sedeLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(strumentoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(strumentoLabel))
							.addGap(17)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(dateTextField, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
								.addComponent(dataInizioLabel)
								.addComponent(dateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(timeComboBox, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
								.addComponent(oraInizioLabel, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(durataLabel)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addGap(161))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
							.addGap(56)))
					.addGap(5)
					.addComponent(errorLabel)
					.addGap(18)
					.addComponent(effettuaPrenotazioneButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
	
	private void makeReservation() {
		int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
		Strumento strumento = new Strumento(null, null, null);
		strumento.setId(id_strumento);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
        String strDate = dateFormat.format((Date) dateTextField.getValue());  
        Date dataInizio = null;
		try {
			dataInizio = timeFormat.parse(strDate + " " + timeComboBox.getSelectedItem());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int durata = (int) spinner.getValue();
		System.out.println(dataInizio + " " + durata);
		controller.makeReservationMainpageFrame(strumento, dataInizio, durata);
	}
}