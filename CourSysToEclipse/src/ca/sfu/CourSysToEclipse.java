package ca.sfu;

import static ca.sfu.ui.Strings.*;

import java.io.File;

import ca.sfu.exceptions.UnableToPrepareSubmissionException;

public class CourSysToEclipse {

	public static void prepareForEclipse(File sourceZip, File destFolder, String assnName) throws UnableToPrepareSubmissionException {
		if (!sourceZip.exists()) {
			throw new UnableToPrepareSubmissionException(ZIP_NOT_FOUND);
		}
		
		if(!destFolder.exists()) {
			if(!destFolder.mkdir()) {
				throw new UnableToPrepareSubmissionException(COULD_NOT_CREATE_DEST);
			}
		}
		
		RecursiveZipExtractor extractor = new RecursiveZipExtractor(sourceZip, destFolder);
		try {
			extractor.extract();
		} catch (ZipExtractionException e) {
			throw new UnableToPrepareSubmissionException(e.getMessage());
		}
		
		Renamer renamer = new Renamer(destFolder, assnName);
		renamer.rename();
		
		EclipseProjectRenamer projectRenamer = new EclipseProjectRenamer(destFolder);
		projectRenamer.renameProjects();		
	}
}
