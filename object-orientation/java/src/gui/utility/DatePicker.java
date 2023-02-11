package gui.utility;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;


public class DatePicker extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private JFormattedTextField linkedTextField;
    private String pickedDate = "";
    private DateSelectionPanel dateSelectionPanel;
    private YearMonthSelectionPanel yearMonthSelectionPanel;

    public DatePicker()
    {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        
        setPanelSettings();
        generateDateSelectionPanel();
        generateYearMonthSelectionPanel();
        
        displayDate();
    }

    public void setLinkedTextField(JFormattedTextField linkedTextField) {
    	this.linkedTextField = linkedTextField;
    }
    
    public void displayDate()
    {
        calendar.set(yearMonthSelectionPanel.getYear(), yearMonthSelectionPanel.getMonth(), 1);
        dateSelectionPanel.displayDate(calendar);
        yearMonthSelectionPanel.displayDate(calendar, new SimpleDateFormat("yyyy-MM"));
        
    }

    public String getPickedDate()
    {
        return pickedDate;
    }
    
    public void pickDate(){
    	if (dateSelectionPanel.getDay().equals("")) {
            pickedDate = dateSelectionPanel.getDay();
    	} else {
	        calendar.set(yearMonthSelectionPanel.getYear(), yearMonthSelectionPanel.getMonth(), Integer.parseInt(dateSelectionPanel.getDay()));
	        pickedDate = dateFormat.format(calendar.getTime());
	        if(linkedTextField != null) {
	        	linkedTextField.setValue(calendar.getTime());
	        }
    	}
    }
    
    private void setPanelSettings() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 0));
    }
    
    private void generateDateSelectionPanel() {
    	dateSelectionPanel = new DateSelectionPanel(this);
        add(dateSelectionPanel, BorderLayout.CENTER);
    }
    
    private void generateYearMonthSelectionPanel() {
    	yearMonthSelectionPanel = new YearMonthSelectionPanel(this);
        add(yearMonthSelectionPanel, BorderLayout.SOUTH);
    }
}