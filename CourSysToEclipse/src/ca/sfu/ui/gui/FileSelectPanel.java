package ca.sfu.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FileSelectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String BROWSE_BUTTON_TEXT = "Browse";
	
	private FileFilter filter;
	
	public enum FileType {
		FILE, FOLDER
	}
	
	private JLabel promptLbl;
	private JTextField pathTxtField;
	private JButton browseBtn;
	private JFileChooser fileSelector = new JFileChooser();
	
	public FileSelectPanel(String prompt, FileType selectionType) {
		this(prompt, selectionType, new FilterFilesWithExtensionsList());		
	}
	
	public FileSelectPanel(String prompt, FileType selectionType, FileFilter filter) {
		this.filter = filter;
		
		promptLbl = new JLabel(prompt);
		promptLbl.setPreferredSize(new Dimension(UserInputPanel.LABEL_WIDTH, -1)); // -1 leaves height to default  
		
		pathTxtField = new JTextField();
		
		browseBtn = new JButton(BROWSE_BUTTON_TEXT);
		browseBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				browse();
			}
		});
		
		if (selectionType.equals(FileType.FILE)) {
			fileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileSelector.setFileFilter(this.filter);
		} else {
			fileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		setUpPanel();		
	}

	private void setUpPanel() {
		setLayout(new BorderLayout());
		add(promptLbl, BorderLayout.WEST);
		add(pathTxtField, BorderLayout.CENTER);
		add(browseBtn, BorderLayout.EAST);
	}

	private void browse() {
		int selection = fileSelector.showOpenDialog(this);
		
		if(selection == JFileChooser.APPROVE_OPTION) {
			pathTxtField.setText(fileSelector.getSelectedFile().getAbsolutePath());
		}
	}
	
	public File getSelectedFile() {
		String path = pathTxtField.getText().trim();
		if(path.isEmpty()) {
			return null;
		}
		
		return new File(path);
	}
}
