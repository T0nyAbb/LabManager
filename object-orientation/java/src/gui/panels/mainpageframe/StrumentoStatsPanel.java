package gui.panels.mainpageframe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

import dto.Sede;
import dto.Strumento;
import gui.buttons.RectangleButton;
import gui.utility.CalendarDialog;
import gui.utility.Style;
import control.Controller;
import javax.swing.LayoutStyle.ComponentPlacement;

public class StrumentoStatsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	
    private JComboBox<String> strumentoComboBox;
    private JComboBox<String> dateComboBox;
    private JComboBox<String> sedeComboBox;
    
    private JLabel strumentoLabel;
    private JLabel sedeLabel;
    private JLabel periodoLabel;
    private JLabel stats;
    private JLabel headerLabel;
    private JButton mostraCalendarioButton;


    /**
     * Create the panel.
     * @throws SQLException 
     */
    public StrumentoStatsPanel(Controller controller) throws SQLException{
        this.controller = controller;
        
        setPanelSettings();
        generateLabels();
        generateComboBox();
        generateButtons();
        setLayoutComponents();  
        
        fillSedeComboBox();
        fillStrumentoComboBox();
        fillPeriodoComboBox();
        loadStats();
    }
    
    public void setPanelSettings() {
        setBackground(Style.background_color_01);
    }

    private void generateLabels() {
        headerLabel = new JLabel();
        headerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		headerLabel.setForeground(Style.background_color_01);
		headerLabel.setText("Statistiche Prenotazioni");
		headerLabel.setFont(new Font(Style.font_name_01, Font.ITALIC, 25));
		headerLabel.setBackground(Style.background_color_03);
		headerLabel.setOpaque(true);
		headerLabel.setBorder(new EmptyBorder(10, 25, 10, 25));
    	
        sedeLabel = new JLabel("Seleziona Sede");
        sedeLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
        sedeLabel.setForeground(Style.entered_color_01);
        
        strumentoLabel = new JLabel("Seleziona Strumento");
        strumentoLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
        strumentoLabel.setForeground(Style.entered_color_01);
        
        periodoLabel = new JLabel("Seleziona Periodo");
        periodoLabel.setFont(new Font(Style.font_name_01, Font.PLAIN, 18));
        periodoLabel.setForeground(Style.entered_color_01);
        
        stats = new JLabel("");
        stats.setFont(new Font(Style.font_name_01, Font.ITALIC, 18));
        stats.setForeground(Style.foreground_color_01);
    }
    
    private void generateButtons() {
		mostraCalendarioButton = new RectangleButton();
		mostraCalendarioButton.setText("Mostra calendario");
		mostraCalendarioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();
			}
		});
    }
    
    private void generateComboBox()  {
        sedeComboBox = new JComboBox<String>();
        sedeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
        sedeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    fillStrumentoComboBox();
                    loadStats();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        
        strumentoComboBox = new JComboBox<String>();
        strumentoComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
        strumentoComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    fillPeriodoComboBox();
                    loadStats();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        
        dateComboBox = new JComboBox<String>();
        dateComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
        dateComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                loadStats();
            }
        });

    }
    
    private void setLayoutComponents() {
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(55)
        			.addComponent(stats, GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
        			.addGap(76))
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(18)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(mostraCalendarioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(strumentoLabel, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
        								.addComponent(sedeLabel, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
        							.addPreferredGap(ComponentPlacement.UNRELATED))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addComponent(periodoLabel, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        							.addGap(7)))
        					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(dateComboBox, 0, 494, Short.MAX_VALUE)
        						.addComponent(sedeComboBox, Alignment.LEADING, 0, 494, Short.MAX_VALUE)
        						.addComponent(strumentoComboBox, Alignment.LEADING, 0, 507, Short.MAX_VALUE))))
        			.addContainerGap())
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(headerLabel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        			.addGap(27)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(sedeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(sedeLabel))
        			.addGap(18)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(strumentoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(strumentoLabel))
        			.addGap(18)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(periodoLabel)
        				.addComponent(dateComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(30)
        			.addComponent(mostraCalendarioButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
        			.addComponent(stats, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
        			.addGap(89))
        );
        setLayout(groupLayout);

    }

    public void fillStrumentoComboBox() throws SQLException {
    	if(sedeComboBox.getSelectedItem() == null) {
            strumentoComboBox.setModel(new DefaultComboBoxModel<String>(new String[0]));
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
	            strumentoComboBox.setModel(new DefaultComboBoxModel<String>(stringStrumenti));
	        }
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
            sedeComboBox.setModel(new DefaultComboBoxModel<String>(stringSedi));
        }
    }
    
    public void fillPeriodoComboBox() throws SQLException {
    	if(strumentoComboBox.getSelectedItem() == null) {
            dateComboBox.setModel(new DefaultComboBoxModel<String>(new String[0]));
    	}
    	else {
    		List<String> date;
	        List<String> year;
	        int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
	        Strumento strumento = new Strumento(null, null, null);
	        strumento.setId(id_strumento);
	        date = controller.getStrumentoDao().getAvailableMonthsForStats(strumento);
	        year = controller.getStrumentoDao().getAvailableYearsForStats(strumento);
	        date.addAll(year);
	        
	        if(date.size() > 0) {
	            String[] stringDate = new String[date.size()];
	            for(int x=0; x<date.size(); x++) {
	                stringDate[x] = date.get(x);
	            }
	            dateComboBox.setModel(new DefaultComboBoxModel<String>(stringDate));
	        }
    	}
    }
    
    public void loadStats() {
    	if(strumentoComboBox.getSelectedItem() == null || dateComboBox.getSelectedItem() == null) {
    		stats.setText("");
    	}
    	else {
    		String strumentoSelectedItem = (String) strumentoComboBox.getSelectedItem();
        	String dateSelectedItem = (String) dateComboBox.getSelectedItem();
	        Strumento strumento = new Strumento(null, null, null);
	        strumento.setId(Integer.parseInt(strumentoSelectedItem.split(":")[0]));
	        try {
		        if(dateSelectedItem.length()==4) {
		            stats.setText("<html>" + controller.getStrumentoDao().getStatsByYear(strumento, dateSelectedItem) + "</html>");
		        } else {
		            stats.setText("<html>" + controller.getStrumentoDao().getStatsByMonth(strumento, dateSelectedItem) + "</html>");
		        }
	        }
	        catch(SQLException e) {
	        	e.printStackTrace();
	        }
    	}
    }
    
	private void showCalendar() {
		Strumento s = new Strumento(null, null);
		if(strumentoComboBox.getSelectedItem() != null) {
	        int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
			s.setId(id_strumento);
			new CalendarDialog(this, controller, s.getId());
		}
	}
}
