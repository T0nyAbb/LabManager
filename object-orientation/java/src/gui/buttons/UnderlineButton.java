package gui.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import gui.utility.Style;

public class UnderlineButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color defaultColor;
	private Color enteredColor;
	private Color pressedColor;
	
	public UnderlineButton() {
		setDefaultColor(Style.default_color_01);
		setEnteredColor(Style.entered_color_01);
		setPressedColor(Style.pressed_color_01);
		
		setFont(new Font(Style.font_name_01, Font.BOLD, 14));
        setFocusable(false);
		setForeground(defaultColor);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
                setForeground(enteredColor);
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
                setForeground(defaultColor);
        	}
        	
        	@Override
        	public void mousePressed(MouseEvent e) {
                setForeground(pressedColor);
        	}
        	
        	@Override
        	public void mouseReleased(MouseEvent e) {
                setForeground(defaultColor);
        	}
        });
	}
	

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
		setForeground(defaultColor);
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
	
	@Override
	public void setText(String text) {
		super.setText("<HTML><U>"+text+"</U></HTML>");
	}

}
