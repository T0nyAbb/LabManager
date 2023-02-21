package gui.panels.mainpageframe;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import control.Controller;
import dto.Sede;
import dto.Strumento;
import gui.buttons.RectangleButton;
import gui.utility.Style;


import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultComboBoxModel;

public class MakeReservationStrumentoPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel headerLabel;
	private JLabel sedeLabel;
	private JLabel strumentoLabel;
	JLabel schedaTecnicaLabel;
	private JComboBox<String> strumentoComboBox;
	private JComboBox<String> sedeComboBox;
	private List<Strumento> strumenti;
	private RectangleButton nextButton;
	private JList<String> strumentoList;
	private JScrollPane listScroller;

	public MakeReservationStrumentoPanel(Controller controller) throws SQLException {
		this.controller = controller;
		
		setPanelSettings();
		generateLabels();
		generateButtons();
		generateComboBox();
		generateList();
		setLayoutComponents();
		
		fillSedeComboBox();
		fillStrumentoComboBox();
		loadListContent();
		setSchedaTecnicaLabelText("");
		changeNextButtonState();
	}
	
	public void setSchedaTecnicaLabelText(String msg) {
		schedaTecnicaLabel.setText(msg);
	}
	
	public void loadListContent() {
		try {
			if(strumentoComboBox.getSelectedItem() != null && sedeComboBox.getSelectedItem() != null) {
				String desc = strumentoComboBox.getSelectedItem().toString();
				String sede = sedeComboBox.getSelectedItem().toString();
				
				int descIndex = strumentoComboBox.getSelectedIndex();
				int sedeIndex = sedeComboBox.getSelectedIndex();
				
				Sede s = null;
				if(sedeIndex != 0) {
					s = new Sede(null);
					s.setId(Integer.parseInt(sede.split(":")[0]));
				}
				
				if(s != null && descIndex != 0) {
					strumenti = controller.getStrumentoDao().getStrumentoBySedeAndDescrizione(s, desc);
				}
				else if(descIndex != 0) {
					strumenti = controller.getStrumentoDao().getStrumentoByDescrizione(desc);
				}
				else if(s != null) {
					strumenti = controller.getStrumentoDao().getStrumentoBySede(s);
				}
				else {
					strumenti = controller.getStrumentoDao().getAll();
				}

				String[] stringArr = new String[strumenti.size()];
				for(int x=0; x<strumenti.size(); ++x) {
					Strumento st = strumenti.get(x);
					stringArr[x] = st.getId() + ": " + st.getDescrizione() + " - " + st.getPostazione().getSede().getIndirizzo() + ", Postazione " + st.getPostazione().getNome();
				}
				strumentoList.setListData(stringArr);
			}else {
				strumentoList.setListData(new String[0]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		List<String> descrizioni = controller.getStrumentoDao().getDescrizioneFromAll();
	
		if(descrizioni.size() > 0) {
			String[] stringDesc = new String[descrizioni.size()+1];
			stringDesc[0] = "Tutti gli strumenti";
			for(int x=1; x<descrizioni.size()+1; x++) {
				stringDesc[x] = descrizioni.get(x-1);
			}
		strumentoComboBox.setModel(new DefaultComboBoxModel<String> (stringDesc));
		}
	}
	
	public void setPanelSettings() {
		setBackground(Style.background_color_01);
	}
	
	private void generateButtons() {
		nextButton = new RectangleButton();
		nextButton.setDefaultColor(Style.default_color_03);
		nextButton.setEnteredColor(Style.entered_color_03);
		nextButton.setPressedColor(Style.pressed_color_03);;
		nextButton.setText("Prosegui");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNextPage();
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
		
		schedaTecnicaLabel = new JLabel("New label");
		schedaTecnicaLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 16));
	}
	
	private void generateComboBox() {
		strumentoComboBox = new JComboBox<String>();
		strumentoComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
		strumentoComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadListContent();
			}
		});
		
		sedeComboBox = new JComboBox<String>();
		sedeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
		sedeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadListContent();
			}
		});
	}
	
	private void generateList() {
		strumentoList = new JList<String>();
		strumentoList.setFont(new Font(Style.font_name_01, Font.PLAIN, 14));
		strumentoList.setBorder(new EmptyBorder(0, 0, 0, 0));
		strumentoList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				showSchedaTecnica();
				changeNextButtonState();
			}
		});
		
		listScroller = new JScrollPane(strumentoList);
		listScroller.setViewportBorder(new LineBorder(Style.background_color_03, 1, true));
		listScroller.setViewportView(strumentoList);
		listScroller.setPreferredSize(new Dimension(250, 80));
	}
	
	private void changeNextButtonState() {
		if(strumentoList.getSelectedValue() == null) {
			nextButton.setEnabled(false);
		}else {
			nextButton.setEnabled(true);
		}
		
	}

	private void setLayoutComponents() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(listScroller, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
						.addComponent(schedaTecnicaLabel, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(sedeLabel, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sedeComboBox, 0, 331, Short.MAX_VALUE)
							.addGap(32)
							.addComponent(strumentoLabel, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(strumentoComboBox, 0, 278, Short.MAX_VALUE)))
					.addGap(45))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(608, Short.MAX_VALUE)
					.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sedeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(strumentoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(strumentoLabel)
						.addComponent(sedeLabel))
					.addGap(18)
					.addComponent(listScroller, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(schedaTecnicaLabel, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
	
	private void showSchedaTecnica() {
		if(strumentoList.getSelectedIndex() != -1) {
			try {
				int id = Integer.valueOf(strumentoList.getSelectedValue().toString().split(":")[0]);
				Strumento st = controller.getStrumentoDao().getById(id);
				setSchedaTecnicaLabelText(st.getSchedaTecnica());
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		else {
			setSchedaTecnicaLabelText("Scheda tecnica non disponibile.");
		}
		setSchedaTecnicaLabelText("<html>"+schedaTecnicaLabel.getText()+"</html>");
	}
	
	private void openNextPage() {
		controller.showMakeReservationDate(Integer.valueOf(strumentoList.getSelectedValue().toString().split(":")[0]));
	}
}
