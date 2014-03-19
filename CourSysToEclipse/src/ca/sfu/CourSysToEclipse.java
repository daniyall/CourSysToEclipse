package ca.sfu;

import java.io.File;

public class CourSysToEclipse {

	public static void main(String[] args) {
		if(args.length != 3) {
			printUsage();
			return;
		}
		
		File sourceZip = new File(args[0]);
		if (!sourceZip.exists()) {
			System.out.println("Source file does not exist.");
			return;
		}
		
		File destFolder = new File(args[1]);
		if(!destFolder.exists()) {
			if(!destFolder.mkdir()) {
				System.out.println("Could not create destination directory.");
				return;
			}
		}
		
		RecursiveZipExtractor extractor = new RecursiveZipExtractor(sourceZip, destFolder);
		try {
			extractor.extract();
		} catch (ZipExtractionException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Renamer renamer = new Renamer(destFolder, args[2]);
		renamer.rename();
		
		EclipseProjectRenamer projectRenamer = new EclipseProjectRenamer(destFolder);
		projectRenamer.renameProjects();
		
		System.out.println("Done. All submissions should be in " + destFolder.getAbsolutePath());
	}

	private static void printUsage() {
		System.out.println("Correct usage: CourSysToEclipse [sourceZip] [destinationPath] [assignmentName]");
	}

}
