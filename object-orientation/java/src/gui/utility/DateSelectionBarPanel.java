package gui.utility;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Font;

public class DateSelectionBarPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DatePicker datePicker;
	private int month = 0;
    private int year = 2023;
    private JTextField displayDateTextField;
    private JButton previousMonthButton;
    private JButton previousYearButton;
    private JButton nextYearButton;
    private JButton nextMonthButton;
    
	public DateSelectionBarPanel(DatePicker datePicker) {
		this.datePicker = datePicker;
		
		setPanelSettings();
		generateComponents();
        
	}
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public JTextField getDisplayDateTextField() {
		return displayDateTextField;
	}

	public void setDisplayDateTextField(JTextField displayDateTextField) {
		this.displayDateTextField = displayDateTextField;
	}

	public void displayDate(Calendar calendar, SimpleDateFormat dateFormat) {
		displayDateTextField.setText(dateFormat.format(calendar.getTime()));		
	}
	
	private void setPanelSettings() {
		setLayout(new GridLayout(1, 5));
	}
	
	private void generateComponents() {
		generatePreviousYearButton();
        generatePreviousMonthButton();
        generateTextField();
        generateNextMonthButton();
        generateNextYearButton();
	}
	
	private void generateTextField() {
		displayDateTextField = new JTextField("", JTextField.CENTER);
		displayDateTextField.setFont(new Font("Century Gothic", Font.BOLD, 11));
    	displayDateTextField.setBorder(new LineBorder(Color.WHITE));
        displayDateTextField.setEditable(false);
        displayDateTextField.setBackground(Color.WHITE);
        displayDateTextField.setHorizontalAlignment(SwingConstants.CENTER);
        add(displayDateTextField);
	}
	
	private void generatePreviousMonthButton() {
		previousMonthButton = new JButton("<");
        previousMonthButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                month--;
                datePicker.displayDate();
            }
        });
        previousMonthButton.setBorderPainted(false);
        previousMonthButton.setFocusPainted(false);
        previousMonthButton.setBackground(Color.WHITE);
        add(previousMonthButton);
	}
	
	private void generatePreviousYearButton() {
		previousYearButton = new JButton("<<");
        previousYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                year--;
                datePicker.displayDate();
            }
        });
        previousYearButton.setBorderPainted(false);
        previousYearButton.setFocusPainted(false);
        previousYearButton.setBackground(Color.WHITE);
        add(previousYearButton);
	}
	
	private void generateNextMonthButton() {
		nextMonthButton = new JButton(">");
        nextMonthButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                month++;
                datePicker.displayDate();
            }
        });
        nextMonthButton.setBorderPainted(false);
        nextMonthButton.setFocusPainted(false);
        nextMonthButton.setBackground(Color.WHITE);
        add(nextMonthButton);
	}
	
	private void generateNextYearButton() {
		nextYearButton = new JButton(">>");
        nextYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                year++;
                datePicker.displayDate();
            }
        });
        nextYearButton.setBorderPainted(false);
        nextYearButton.setFocusPainted(false);
        nextYearButton.setBackground(Color.WHITE);
        add(nextYearButton);
	}



}
