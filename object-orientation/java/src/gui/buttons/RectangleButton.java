package gui.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import gui.utility.Style;

public class RectangleButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color defaultColor;
	private Color enteredColor;
	private Color pressedColor;
	
	public RectangleButton() {
		setDefaultColor(Style.default_color_01);
		setEnteredColor(Style.entered_color_01);
		setPressedColor(Style.pressed_color_01);
		
        setFont(new Font(Style.font_name_01, Font.BOLD, 18));
        setBackground(defaultColor);
        setForeground(Style.foreground_color_02);
		setBorderPainted(false);
		setFocusable(false);
		setContentAreaFilled(false);
        setOpaque(true);
        setBounds(145, 400, 150, 37);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
                setBackground(enteredColor);
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
        	}
        	
        	@Override
        	public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
        	}
        	
        	@Override
        	public void mouseReleased(MouseEvent e) {
                setBackground(defaultColor);
        	}
        });
        
	}

	public RectangleButton(String string) {
		this();
		setText(string);
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
		setBackground(defaultColor);
	}

	public Color getEnteredColor() {
		return enteredColor;
	}

	public void setEnteredColor(Color enteredColor) {
		this.enteredColor = enteredColor;
	}

	public Color getPressedColor() {
		return pressedColor;
	}

	public void setPressedColor(Color pressedColor) {
		this.pressedColor = pressedColor;
	}


}
