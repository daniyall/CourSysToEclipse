package ca.sfu.ui.gui;

import static ca.sfu.ui.Strings.*;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	
	private static int WIDTH = 600;
	private static int HEIGHT = 400;
	
	private GUI() {
		JFrame frame = new JFrame(APP_NAME);
		frame.setSize(WIDTH, HEIGHT);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		OutputPanel output = new OutputPanel();
		mainPanel.add(new UserInputPanel(output), BorderLayout.NORTH);
		mainPanel.add(output, BorderLayout.CENTER);
		frame.add(mainPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
