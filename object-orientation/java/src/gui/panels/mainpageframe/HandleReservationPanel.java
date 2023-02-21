package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import control.Controller;
import dto.Prenotazione;
import gui.buttons.RectangleButton;
import gui.utility.Style;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

public class HandleReservationPanel extends JPanel{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel headerLabel;
	private JList<String> list;
	private JScrollPane listScroller;
	private ArrayList<Prenotazione> prenotazioni;
	private RectangleButton modificaButton;
	private RectangleButton eliminaButton;
	private JLabel selezionaLabel;
	private JLabel errorLabel;
	
	
	public HandleReservationPanel(Controller controller) {
		this.controller = controller;
		
		setPanelSettings();
		generateLabels();
		generateLists();
		generateButtons();
		setLayoutComponents();
		clearErrorMessage();
	}
	
	private void setPanelSettings() {
		setBackground(Style.background_color_01);
	}
	
	public void showErrorMessage(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}
	
	public void clearErrorMessage() {
		errorLabel.setText("");
	}
	
	private void generateLabels() {
		headerLabel = new JLabel("Gestisci Prenotazioni");
		headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Style.background_color_01);
		headerLabel.setFont(new Font(Style.font_name_01, Font.ITALIC, 25));
		headerLabel.setBackground(Style.background_color_03);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		selezionaLabel = new JLabel("Seleziona una delle prenotazioni effettuate");
		selezionaLabel.setForeground(Style.entered_color_01);
		selezionaLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 20));
		
		errorLabel = new JLabel("Error");
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
        errorLabel.setForeground(Style.foreground_color_error);
        errorLabel.setBounds(0, 196, 440, 31);
	}
	
	private void generateLists() {
		list = new JList<String>();
		list.setFont(new Font(Style.font_name_01, Font.PLAIN, 16));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		loadListContent();
		
		listScroller = new JScrollPane(list);
		listScroller.setViewportBorder(new LineBorder(Style.background_color_03, 1, true));
		listScroller.setViewportView(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
	}
	
	public void loadListContent() {
		try {
			prenotazioni = (ArrayList<Prenotazione>) controller.getPrenotazioneDao().getByUtente(controller.getLoggedUser());
			if(prenotazioni.size()>0) {
				DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				String[] stringArr = new String[prenotazioni.size()];
				for(int x=0; x<prenotazioni.size(); ++x) {
					Prenotazione p = prenotazioni.get(x);
					stringArr[x] = timeFormat.format(p.getDataInizio()) +
							" [" + p.getDurata() + " ore] - " + p.getStrumento().getId() + ": "+ p.getStrumento().getDescrizione() +
							" - " + p.getStrumento().getPostazione().getSede().getIndirizzo() + ", Postazione " +
							p.getStrumento().getPostazione().getNome();
				}
				list.setListData(stringArr);
			}else {
				list.setListData(new String[0]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void generateButtons() {
		modificaButton = new RectangleButton();
		modificaButton.setText("Modifica");
		modificaButton.setDefaultColor(Style.default_color_03);
		modificaButton.setEnteredColor(Style.entered_color_03);
		modificaButton.setPressedColor(Style.pressed_color_03);
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyPrenotazione();
			}
		});
		
		eliminaButton = new RectangleButton();
		eliminaButton.setText("Elimina");
		eliminaButton.setDefaultColor(Style.default_color_03);
		eliminaButton.setEnteredColor(Style.entered_color_03);
		eliminaButton.setPressedColor(Style.pressed_color_03);
		eliminaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePrenotazione();
			}
		});
	}
	
	private void setLayoutComponents() {
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(listScroller, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(selezionaLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
							.addComponent(eliminaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(34))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(94)
					.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
					.addGap(111))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eliminaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(selezionaLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listScroller, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
		);
		setLayout(groupLayout);
	}
	
	private void deletePrenotazione() {
		int listIndex = list.getSelectedIndex();
		if(listIndex != -1)
			controller.deletePrenotazione(prenotazioni.get(listIndex));
	}
	
	private void modifyPrenotazione() {
		int listIndex = list.getSelectedIndex();
		if(listIndex != -1) {
			controller.updatePrenotazione(prenotazioni.get(listIndex));
		}
	}
}
