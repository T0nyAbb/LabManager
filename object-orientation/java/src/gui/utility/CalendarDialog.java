package gui.utility;

import javax.swing.*;
import javax.swing.border.LineBorder;

import control.Controller;
import dto.Prenotazione;
import dto.Strumento;
import gui.panels.mainpageframe.MakeReservationDatePanel;

import java.awt.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class CalendarDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private MakeReservationDatePanel panel;

    private Controller controller;

    private JList<String> list;

    private JScrollPane listScroller;

    private int idStrumento;
    private List<Prenotazione> prenotazioni;

    public CalendarDialog(MakeReservationDatePanel panel, Controller ctrl,  int idStrumento) {
        this.panel = panel;
        controller = ctrl;
        this.idStrumento = idStrumento;
        setDialogSettings();
        generateList();
        setLocationRelativeTo(panel);
        setVisible(true);

    }
    private void setDialogSettings() {
        setModal (true);
        setAlwaysOnTop (true);
        setModalityType (ModalityType.APPLICATION_MODAL);
        setBounds(0, 0, 850, 345);
        setTitle("Calendario strumento");
        setResizable(false);
        getContentPane().setBackground(Style.background_color_01);
        getContentPane().setLayout(new BorderLayout());
    }

    private void loadList(int idStrumento) {
        try {
            Strumento s = new Strumento(idStrumento, null, null);
            prenotazioni =  controller.getPrenotazioneDao().getCalendarByStrumento(s);
            if(prenotazioni.size()>0) {
                DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String[] stringArr = new String[prenotazioni.size()];
                for(int x=0; x<prenotazioni.size(); ++x) {
                    stringArr[x] = timeFormat.format(prenotazioni.get(x).getDataInizio()) +
                            " [" + prenotazioni.get(x).getDurata() + " ore] - " + prenotazioni.get(x).getStrumento().getId() + ": "+ prenotazioni.get(x).getStrumento().getDescrizione() +
                            " - " + prenotazioni.get(x).getStrumento().getPostazione().getSede().getIndirizzo() + ", Postazione " +
                            prenotazioni.get(x).getStrumento().getPostazione().getNome();
                }
                list.setListData(stringArr);
            }else {
                String [] vuoto = {"Non sono presenti prenotazioni"};
                list.setListData(vuoto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void generateList() {
        list = new JList<>();
        list.setFont(new Font("Century Gothic", Font.PLAIN, 17));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        loadList(idStrumento);

        listScroller = new JScrollPane(list);
        listScroller.setViewportBorder(new LineBorder(Style.background_color_03, 1, true));
        listScroller.setViewportView(list);
        listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setContentPane(listScroller);
    }
}

