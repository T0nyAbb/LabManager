package gui.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

public class UnderlineButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnderlineButton() {
		setFont(new Font("Arial", Font.BOLD, 14));
        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(true);
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

}
