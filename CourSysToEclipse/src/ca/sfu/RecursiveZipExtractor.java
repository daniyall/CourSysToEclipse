package ca.sfu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Supports recursively extracting a ZIP file and warning about configurable file extensions.
 */
public class RecursiveZipExtractor {
	private File sourceZip;
	private File targetFolder;
	private FileFilter warningFilter = createWarningFileFilter("");
	
	private List<File> extractedFilesCausingWarning = new ArrayList<File>();

	public RecursiveZipExtractor(File source, File target) {
		this.sourceZip = source;
		this.targetFolder = target;
	}

	/**
	 * Set the file extensions which will generate warnings while extracting.
	 * Used to find compressed files which are not handled by this tool.
	 * Accepts a space separated string of file extensions. Each extension
	 * must start with a period. 
	 * Example:
	 * 	".tar .gz .rar"
	 * @param warnExtensions Space separated string of new exceptions to warn for.
	 * @throws IllegalArgumentException If an extension is missing the period.
	 */
	public void setWarningExtensions(String warnExtensions) throws IllegalArgumentException {
		warningFilter = createWarningFileFilter(warnExtensions);
	}

	public void extract() throws ZipExtractionException {
		if (!isValidZipFile(sourceZip)) {
			throw new ZipExtractionException("Source file (" + sourceZip.getAbsolutePath() + ") does not exist.");
		}

		extractedFilesCausingWarning = new ArrayList<File>();
		try {
			extractZipFile(sourceZip, targetFolder);
		} catch (Exception exception) {
			// exception.printStackTrace();
			throw new ZipExtractionException(sourceZip.getAbsolutePath() + " - " + exception.getMessage());
		}
	}
	
	/**
	 * Returns an iterator to a set of File objects that were identified during the
	 * extraction as generating warnings (based on the extension types).
	 */
	public Iterator<File> getWarningFileIterator() {
		return extractedFilesCausingWarning.iterator();
	}
	

	private FileFilter createWarningFileFilter(String extensionsString) {
		final List<String> warnList = convertExtensionsStringToList(extensionsString);
		
		return new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName();
				boolean isFile = pathname.isFile();
				return isFile && extensionInList(fileName);
			}

			private boolean extensionInList(String fileName) {
				for (String extension : warnList) {
					if (fileName.toLowerCase().endsWith(extension)) {
						return true;
					}
				}
				return false;
			}
		};
	}
	private List<String> convertExtensionsStringToList(String extensionsString) {
		String[] extensionsSplit = extensionsString.split(" ");
		final List<String> warnList = new ArrayList<String>();
		for (String extension : extensionsSplit) {
			extension = extension.trim().toLowerCase();
			if (extension.length() == 0) {
				continue;
			}
			if (!extension.startsWith(".")) {
				throw new IllegalArgumentException("Invalid file extension: must be comma separated and start with a period.");
			}
			warnList.add(extension);
		}
		return warnList;
	}

	/**
	 * Recursively extract a ZIP file to a targetFolder folder.
	 * @param zipFilePath The sourceZip ZIP file to extract.
	 * @param targetFolderPath The targetFolder directory of where to place extracted files.
	 * @throws IOException
	 */
	private void extractZipFile(File sourceFile, File targetFolder) throws IOException {
		// Algorithm inspiration:
		// http://stackoverflow.com/questions/981578/how-to-unzip-files-recursively-in-java
		
		if(sourceFile.getName().equalsIgnoreCase("MassiveFiles.zip")) {
			return;
		}
		
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(sourceFile);
		} catch (Exception e) {
			System.out.println(sourceFile.getAbsolutePath() + e.getMessage());
			return;
		}
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			File target = new File(targetFolder, entry.getName());

			makeFolder(entry.isDirectory(), target);

			if (!entry.isDirectory()) {
				InputStream stream = zipFile.getInputStream(entry);
				writeStreamToFile(target, stream);

				if (warningFilter.accept(target)) {
					extractedFilesCausingWarning.add(target);
				}
				
				// Recurse into contained ZIP files:
				if (isValidZipFile(target)) {
					extractZipFile(target, target.getParentFile());
				}
			}
		}
		zipFile.close();
	}

	private void makeFolder(boolean isDirectory, File writeLocation) {
		if (isDirectory) {
			writeLocation.mkdir();
		} else {
			writeLocation.getParentFile().mkdirs();
		}
	}

	private boolean isValidZipFile(File file) {
		return file.exists()
				&& file.isFile()
				&& file.getName().toLowerCase().endsWith(".zip");
	}

	private void writeStreamToFile(File target, InputStream stream)
			throws FileNotFoundException, IOException {
		int numbBytesRead = 0;
		final int BUFFER_SIZE = 2048;
		byte[] bytes = new byte[BUFFER_SIZE];

		// Target:
		FileOutputStream outputStream = new FileOutputStream(target);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

		// Copy data
		while ((numbBytesRead = stream.read(bytes, 0, BUFFER_SIZE)) != -1) {
			bufferedOutputStream.write(bytes, 0, numbBytesRead);
		}

		// Cleanup
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		stream.close();
	}
}
