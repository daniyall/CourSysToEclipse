package ca.sfu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PrepareSummary {
	
	private List<File> submissionsNotPrepared = new ArrayList<File>();
	
	private int filesFoundInZip = 0;
	private int submissionsPrepared = 0;
	
	public void incrFilesFoundInZip() {
		filesFoundInZip++;
	}
	
	public void incrSubmissionsPrepared() {
		submissionsPrepared++;
	}
	
	public void addSubmissionNotPrepared(File file) {
		submissionsNotPrepared.add(file);
	}

	@Override
	public String toString() {
		String summary = "";
		
		summary += "Submissions found in zip: " + filesFoundInZip + "\n";
		summary += "Submissions prepared: " + submissionsPrepared + "\n";
		
		if(submissionsNotPrepared.size() != 0) {
			summary += "\n";
			summary += "Warning: " + submissionsNotPrepared.size() + " submissions could not be prepared:" + "\n";
			
			for(File file : submissionsNotPrepared) {
				summary += "\t - "+ file.getName() + "\n";
			}
		}
		summary += "\n";
		
		return summary;
	}
	
	
	
}
