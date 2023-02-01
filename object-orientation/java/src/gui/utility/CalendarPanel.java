package gui.utility;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import gui.buttons.RectangleButton;

public class CalendarPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int BUTTONS_MAX_LENGTH = 49;
	private DatePicker datePicker;
	private String day = "";
	private List<JButton> dateButtons;
	
	public CalendarPanel(DatePicker datePicker) {
		this.datePicker = datePicker;
		
		setPanelSettings();
		generateDateButtons();
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void displayDate(Calendar calendar) {
		for (int x = 7; x < dateButtons.size(); x++)
			dateButtons.get(x).setText("");
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
        	dateButtons.get(x).setText("" + day);
		
	}
	
	private void setPanelSettings() {
		setLayout(new GridLayout(7, 7));
        setPreferredSize(new Dimension(420, 200));
        setBackground(Color.WHITE);
        
	}
	
	private void generateDateButtons() {
		dateButtons = new ArrayList<>();
		String[] header = { "Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab" };
		Color enteredColor = new Color(90, 120, 200);
		Color pressedColor = new Color(90, 120, 200);
		Color defaultColor = Color.white;
        for (int x = 0; x < BUTTONS_MAX_LENGTH; x++)
        {
        	RectangleButton newButton = new RectangleButton(defaultColor, enteredColor, pressedColor);
            newButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
            newButton.setBorder(new LineBorder(Color.DARK_GRAY));
            newButton.setBackground(Color.white);
            newButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if (x > 6) {
                newButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        setDay(newButton.getActionCommand());
                        datePicker.pickDate();
                    }
                });
            }
	        else {
                newButton.setText(header[x]);
                newButton.setForeground(new Color(0, 68, 140));
                newButton.setEnteredColor(Color.white);
                newButton.setPressedColor(Color.white);
            }
            dateButtons.add(newButton);
            add(dateButtons.get(x));
        }
	}
	
}
