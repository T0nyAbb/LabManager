package gui.panels.mainpageframe;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.GroupLayout.Alignment;

import dao.StrumentoDao;
import dto.Strumento;
import control.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StrumentoStatsPanel extends JPanel {
    private Controller controller;
    private JComboBox<String> strumentoComboBox;
    private JTextField textField;
    private JLabel strumentoLabel;

    private JLabel prenotazioneListLabel;

    private JList<String> prenotazioni;

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
        prova();






    }
    public void setPanelSettings() {
        setBackground(Color.WHITE);
    }



    public void fillStrumentoComboBox() throws SQLException {
        List<Strumento> strumenti;

        StrumentoDao sDao = new StrumentoDao(controller.getDatabaseConnection());
        strumenti = sDao.getAll();


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
    private void generateLabels() {
        strumentoLabel = new JLabel("Seleziona Strumento");
        strumentoLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        strumentoLabel.setForeground(new Color(0, 68, 140));

    }
    private void generateComboBox() throws SQLException {
        strumentoComboBox = new JComboBox<String>();
        strumentoComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        fillStrumentoComboBox();

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
    public void prova() {
        int id_strumento = Integer.parseInt(strumentoComboBox.getSelectedItem().toString().split(":")[0]);
        Strumento strumento = new Strumento(null, null, null);
        strumento.setId(id_strumento);
        textField.setText(strumentoComboBox.getSelectedItem().toString());
    }
}
