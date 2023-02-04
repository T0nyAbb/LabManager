package gui.panels.accessframe;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JLabel backgroundLabel;

	public BackgroundPanel() {
        setPanelSettings();
        generateBackgroundLabel();
	}
	
	private void setPanelSettings() {
		setOpaque(true);
        setLayout(null);
	}
	
	private void generateBackgroundLabel() {
		backgroundLabel = new JLabel();
		
		ImageIcon backgroundImage = new ImageIcon(BackgroundPanel.class.getResource("/background/bg03.jpg"));
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        add(backgroundLabel);
	}
}
