package ca.sfu.ui;

import static ca.sfu.ui.Strings.*;

import java.io.File;

import ca.sfu.CourSysToEclipse;
import ca.sfu.exceptions.UnableToPrepareSubmissionException;

public class CLI {
	public static void main(String[] args) {
		if(args.length != 3) {
			reportMessage(USAGE);
			return;
		}
		
		File sourceZip = new File(args[0]);
		File destFolder = new File(args[1]);
		
		try {
			CourSysToEclipse.prepareForEclipse(sourceZip, destFolder, args[2]);
		} catch (UnableToPrepareSubmissionException e) {
			reportMessage(e.getMessage());
			return;
		}
		
		reportMessage(DONE + destFolder.getAbsolutePath());
	}
	
	private static void reportMessage(String msg) {
		System.out.println(msg);
	}
}
