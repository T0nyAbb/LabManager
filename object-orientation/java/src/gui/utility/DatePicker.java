package gui.utility;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.*;
import control.Controller;


public class DatePicker extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private JFormattedTextField formattedTextField;
    private String pickedDate = "";
    private CalendarPanel calendarPanel;
    private DateSelectionBarPanel selectionBarPanel;

    public DatePicker()
    {
        
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        
        setPanelSettings();
        generateCalendarPanel();
        generateSelectionBarPanel();
        
        displayDate();
    }

    public void setFormattedTextField(JFormattedTextField ftf) {
    	formattedTextField = ftf;
    }
    
    public void displayDate()
    {
        calendar.set(selectionBarPanel.getYear(), selectionBarPanel.getMonth(), 1);
        calendarPanel.displayDate(calendar);
        selectionBarPanel.displayDate(calendar, new SimpleDateFormat("yyyy-MM"));
        
    }

    public String getPickedDate()
    {
        return pickedDate;
    }
    
    public void pickDate(){
    	if (calendarPanel.getDay().equals("")) {
            pickedDate = calendarPanel.getDay();
    	} else {
	        calendar.set(selectionBarPanel.getYear(), selectionBarPanel.getMonth(), Integer.parseInt(calendarPanel.getDay()));
	        pickedDate = dateFormat.format(calendar.getTime());
	        setVisible(false);
	        if(formattedTextField != null) {
	        	formattedTextField.setValue(calendar.getTime());
	        }
    	}
    }
    
    private void setPanelSettings() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 0));
    }
    
    private void generateCalendarPanel() {
    	calendarPanel = new CalendarPanel(this);
        add(calendarPanel, BorderLayout.CENTER);
    }
    
    private void generateSelectionBarPanel() {
    	selectionBarPanel = new DateSelectionBarPanel(this);
        add(selectionBarPanel, BorderLayout.SOUTH);
    }
}