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


    /**
     * Create the panel.
     * @throws SQLException 
     */
    public StrumentoStatsPanel(Controller controller) throws SQLException{
        this.controller = controller;
        
        setPanelSettings();
        generateLabels();
        generateComboBox();
        setLayoutComponents();
        
        fillSedeComboBox();
        fillStrumentoComboBox();
        fillPeriodoComboBox();
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
        stats.setFont(new Font(Style.font_name_01, Font.PLAIN, 16));
        stats.setForeground(Style.foreground_color_01);
    }
    
    private void generateComboBox()  {
        sedeComboBox = new JComboBox<String>();
        sedeComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
        sedeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    fillStrumentoComboBox();
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        
        dateComboBox = new JComboBox<String>();
        dateComboBox.setFont(new Font(Style.font_name_02, Font.PLAIN, 12));
        dateComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    setStats();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    
    private void setLayoutComponents() {
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
        		.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        			.addGap(18)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(stats, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(strumentoLabel, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
        								.addComponent(sedeLabel, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
        							.addPreferredGap(ComponentPlacement.UNRELATED))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addComponent(periodoLabel, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        							.addGap(7)))
        					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(dateComboBox, 0, 480, Short.MAX_VALUE)
        						.addComponent(strumentoComboBox, 0, 480, Short.MAX_VALUE)
        						.addComponent(sedeComboBox, Alignment.LEADING, 0, 480, Short.MAX_VALUE))))
        			.addGap(37))
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
        			.addGap(160)
        			.addComponent(stats, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(42, Short.MAX_VALUE))
        );
        setLayout(groupLayout);

    }

    public void fillStrumentoComboBox() throws SQLException {
        String item = sedeComboBox.getSelectedItem().toString();
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
    
    public void setStats() throws SQLException {
        int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
        Strumento strumento = new Strumento(null, null, null);
        strumento.setId(id_strumento);

        String data = dateComboBox.getSelectedItem().toString();
        if(data.length()==4) {
            stats.setText("<html>" + controller.getStrumentoDao().getStatsByYear(strumento, data) + "</html>");
        } else {
            stats.setText("<html>" + controller.getStrumentoDao().getStatsByMonth(strumento, data) + "</html>");
        }
    }
}
