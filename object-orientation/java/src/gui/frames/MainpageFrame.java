package gui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import control.Controller;
import javax.swing.JPanel;

public class MainpageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Controller controller;
	
	public MainpageFrame(Controller controller) {
		this.controller = controller;
		
		setFrameSettings();
	}
	
	private void setFrameSettings() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setSize(1000, 600);
    	setMinimumSize(new Dimension(1000, 600));
        setResizable(true);
        setTitle("LabManager");
        Image icon = null;
        try {
        	icon = Toolkit.getDefaultToolkit().getImage(AccessFrame.class.getResource("/icone/labmanager-website-favicon-grey.png"));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        setIconImage(icon);
        getContentPane().setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 600));
        panel.setBackground(new Color(0, 40, 83));
        getContentPane().add(panel, BorderLayout.EAST);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        getContentPane().add(panel_1, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        
    }

}
