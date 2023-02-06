package gui.panels.mainpageframe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.GroupLayout.Alignment;

import dao.StrumentoDao;
import dto.Sede;
import dto.Strumento;
import control.Controller;

public class StrumentoStatsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
    private JComboBox<String> strumentoComboBox;
    private JComboBox<String> dateComboBox;

    private JComboBox<String> sedeComboBox;
    private JTextField textField;
    private JLabel strumentoLabel;

    private JLabel sedeLabel;
    private JLabel periodoLabel;

    private JLabel stats;


    /**
     * Create the panel.
     */
    public StrumentoStatsPanel(Controller controller) throws SQLException {
        this.controller = controller;
        setPanelSettings();
        generateTextFields();
        generateLabels();
        generateComboBox();
        setLayoutComponents();
    }
    public void setPanelSettings() {
        setBackground(Color.WHITE);
    }

    private void generateLabels() {
        sedeLabel = new JLabel("Seleziona Sede");
        sedeLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        sedeLabel.setForeground(new Color(0, 68, 140));
        strumentoLabel = new JLabel("Seleziona Strumento");
        strumentoLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        strumentoLabel.setForeground(new Color(0, 68, 140));
        periodoLabel = new JLabel("Seleziona Periodo");
        periodoLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        periodoLabel.setForeground(new Color(0, 68, 140));
        stats = new JLabel("");
        stats.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        stats.setForeground(new Color(0, 0, 0));
    }
    private void generateComboBox()  {
        sedeComboBox = new JComboBox<String>();
        sedeComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        strumentoComboBox = new JComboBox<String>();
        strumentoComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        dateComboBox = new JComboBox<String>();
        dateComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sedeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    fillStrumentoComboBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        strumentoComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    fillPeriodoComboBox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
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
    private void generateTextFields() {
        textField = new JTextField();
        textField.setEditable(false);
        textField.setColumns(20);

    }
    private void setLayoutComponents() {
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(65)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(strumentoLabel)
                                                .addGap(18)
                                                .addComponent(strumentoComboBox, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(180, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(strumentoLabel)
                                        .addComponent(strumentoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(66)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(181, Short.MAX_VALUE))
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
            strumentoComboBox.setModel(new DefaultComboBoxModel<String> (stringStrumenti));
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
    public void fillPeriodoComboBox() throws SQLException {
        List<String> date;
        List<String> year;
        int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
        Strumento strumento = new Strumento(null, null, null);
        strumento.setId(id_strumento);
        date = controller.getStrumentoDao().getAvailableMonthsForStats(strumento);
        year = controller.getStrumentoDao().getAvailableYearsForStats(strumento);
        date.addAll(year);
        String[] stringDate = date.toArray(new String[0]);
        dateComboBox.setModel(new DefaultComboBoxModel<String> (stringDate));
    }
    public void setStats() throws SQLException {
        int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
        Strumento strumento = new Strumento(null, null, null);
        strumento.setId(id_strumento);

        String data = dateComboBox.getSelectedItem().toString();
            if(data.length()==4) {
                stats.setText(controller.getStrumentoDao().getStatsByYear(strumento, data));
            } else {
                stats.setText(controller.getStrumentoDao().getStatsByMonth(strumento, data));
            }


    }




}
