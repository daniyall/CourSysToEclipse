package ca.sfu.ui.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FilterFilesWithExtensionsList extends FileFilter{

	private String[] acceptableExtensions;
	
	public FilterFilesWithExtensionsList() {
		this.acceptableExtensions = null;
	}
	
	public FilterFilesWithExtensionsList(String[] acceptableExtensions) {
		this.acceptableExtensions = acceptableExtensions;
	}

	public void setAcceptableExtensions(String[] newAcceptableExtensions) {
		this.acceptableExtensions = newAcceptableExtensions;
	}
	
	@Override
	public boolean accept(File file) {
		if (acceptableExtensions == null || acceptableExtensions.length == 0) {
			return true;
		}
		
		for (String extension : acceptableExtensions) {
			if (file.getName().toLowerCase().endsWith( extension.toLowerCase() ) || file.isDirectory()) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		String s = "";
		if(acceptableExtensions.length != 0) {
			for(String ext : acceptableExtensions) {
				s += ext + ", ";
			}
			s = s.substring(0, s.lastIndexOf(','));
		}
		return s;
	}
}
