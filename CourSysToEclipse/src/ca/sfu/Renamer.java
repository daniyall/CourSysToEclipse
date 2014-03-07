package ca.sfu;

import java.io.File;

public class Renamer {
	
	private File folder;
	private String assignmentName;
	
	public Renamer(File folder, String assignmentName) {
		this.folder = folder;
		this.assignmentName = assignmentName;
	}
	
	public void rename() {
		assert folder.exists() && folder.isDirectory();

		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					if (f.isDirectory()) {
						f.renameTo(new File(folder.getAbsolutePath()+ File.separator
								+ file.getName() + "_" + assignmentName));
//						System.out.println(f.getAbsolutePath());
//						System.out.println(folder.getAbsolutePath() + File.separator
//								+ file.getName() + "_" + f.getName() + "\n");
					} else {
						f.delete();
					}
				}

				file.delete();
			}
		}
	}
}
