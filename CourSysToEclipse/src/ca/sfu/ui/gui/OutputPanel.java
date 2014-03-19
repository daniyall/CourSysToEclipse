package ca.sfu.ui.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Output: "; 
	
	private JLabel titleLabel;
	private JTextArea displayArea;
	
	public OutputPanel() {
		titleLabel = new JLabel(TITLE);
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		
		setLayout(new BorderLayout());
		add(titleLabel, BorderLayout.NORTH);
		add(new JScrollPane(displayArea), BorderLayout.CENTER);
	}
	
	public void print(String s) {
		displayArea.append(s);
	}

	public void clear() {
		displayArea.setText("");
	}
}
