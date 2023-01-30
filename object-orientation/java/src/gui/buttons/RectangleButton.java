package gui.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class RectangleButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color defaultColor;
	private Color enteredColor;
	private Color pressedColor;
	
	public RectangleButton() {
		setDefaultColor(Color.DARK_GRAY);
		setEnteredColor(Color.GRAY);
		setPressedColor(Color.LIGHT_GRAY);
		
        setFont(new Font("Arial", Font.BOLD, 18));
        setBackground(defaultColor);
        setForeground(Color.BLACK);
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
