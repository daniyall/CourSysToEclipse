package ca.sfu.ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.sfu.CourSysToEclipse;
import ca.sfu.PrepareSummary;
import ca.sfu.exceptions.UnableToPrepareSubmissionException;
import ca.sfu.ui.gui.FileSelectPanel.FileType;

public class UserInputPanel extends JPanel {

	public static final int LABEL_WIDTH = 150;
	private static final String ZIP_FILE_SELECTOR_TITLE = "Zip file: ";
	private static final String DEST_SELECTOR_TITLE = "Destination folder: ";
	private static final String ASSN_NAME_TITLE = "Assignment name: ";
	private static final String EXTRACT_BTN_TEXT = "Extract";
	private static final long serialVersionUID = 1L;
	private JButton extractBtn = new JButton(EXTRACT_BTN_TEXT);

	private final OutputPanel output;
	private FileSelectPanel zipFileSelector;
	private FileSelectPanel destFolderSelector;
	private JTextField assnNameField;

	public UserInputPanel(OutputPanel output) {
		this.output = output;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		zipFileSelector = new FileSelectPanel(ZIP_FILE_SELECTOR_TITLE,
				FileType.FILE, new FilterFilesWithExtensionsList(
						CourSysToEclipse.SUPPORTED_INPUT_FILES));

		destFolderSelector = new FileSelectPanel(DEST_SELECTOR_TITLE,
				FileType.FOLDER);

		add(zipFileSelector);
		add(destFolderSelector);

		JPanel assnNamePanel = setupAssnNamePanel();
		add(assnNamePanel);

		JPanel btnPanel = setupBtnPanel();

		add(btnPanel);
	}

	private JPanel setupAssnNamePanel() {
		JPanel assnNamePanel = new JPanel();
		assnNamePanel.setLayout(new BorderLayout());
		JLabel promptLbl = new JLabel(ASSN_NAME_TITLE);
		promptLbl.setPreferredSize(new Dimension(LABEL_WIDTH, -1));

		assnNameField = new JTextField();

		assnNamePanel.add(promptLbl, BorderLayout.WEST);
		assnNamePanel.add(assnNameField, BorderLayout.CENTER);
		return assnNamePanel;
	}

	private JPanel setupBtnPanel() {
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(Box.createHorizontalGlue());

		extractBtn.addActionListener(createExtractBtnListener());
		btnPanel.add(extractBtn);
		return btnPanel;
	}

	private ActionListener createExtractBtnListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				output.clear();

				try {
					PrepareSummary summary = CourSysToEclipse.prepareForEclipse(
							zipFileSelector.getSelectedFile(),
							destFolderSelector.getSelectedFile(),
							assnNameField.getText());
					
					output.print(summary.toString());
					output.print("Done.");
					
					
				} catch (UnableToPrepareSubmissionException e) {
					output.print(e.getMessage());
				}
			}
		};
	}
}
