package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import control.Controller;
import dto.Prenotazione;
import gui.buttons.RectangleButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

public class HandleReservationPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel headerLabel;
	private JList<String> list;
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
		setBackground(new Color(255, 255, 255));
	}
	
	public void showErrorMessage(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}
	
	public void clearErrorMessage() {
		errorLabel.setVisible(false);
	}
	
	private void generateLabels() {
		headerLabel = new JLabel("Gestisci Prenotazione");
		headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Century Gothic", Font.ITALIC, 25));
		headerLabel.setBackground(Color.GRAY);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
		
		selezionaLabel = new JLabel("Seleziona una delle prenotazioni effettuate");
		selezionaLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		
		errorLabel = new JLabel("Error");
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(0, 196, 440, 31);
	}
	
	private void generateLists() {
		list = new JList<String>();
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setBorder(new LineBorder(Color.GRAY, 2, true));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		loadListContent();
		
//		JScrollPane listScroller = new JScrollPane(list);
//		listScroller.setViewportView(list);
//		listScroller.setPreferredSize(new Dimension(250, 80));
	}
	
	public void loadListContent() {
		try {
			prenotazioni = (ArrayList<Prenotazione>) controller.getPrenotazioneDao().getByUtente(controller.getLoggedUser());
			if(prenotazioni.size()>0) {
				String[] stringArr = new String[prenotazioni.size()];
				for(int x=0; x<prenotazioni.size(); ++x) {
					stringArr[x] = prenotazioni.get(x).getDataInizio() +
							" [" + prenotazioni.get(x).getDurata() + " ore] - " + prenotazioni.get(x).getStrumento().getDescrizione() +
							" - " + prenotazioni.get(x).getStrumento().getPostazione().getSede().getIndirizzo() + "-" +
							prenotazioni.get(x).getStrumento().getPostazione().getNome();
				}
				list.setListData(stringArr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void generateButtons() {
		modificaButton = new RectangleButton();
		modificaButton.setText("Modifica");
		modificaButton.setForeground(Color.white);
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyPrenotazione();
			}
		});
		
		eliminaButton = new RectangleButton();
		eliminaButton.setText("Elimina");
		eliminaButton.setForeground(Color.white);
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
				.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(list, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(selezionaLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
							.addComponent(eliminaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(33))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(225)
					.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(237, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(modificaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(eliminaButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(selezionaLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(errorLabel)
					.addContainerGap())
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
		try {
			controller.getPrenotazioneDao().delete(prenotazioni.get(listIndex));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
