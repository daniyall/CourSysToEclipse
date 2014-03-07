package ca.sfu;

/**
 * Indicate an error extracting a file.
 */
public class ZipExtractionException extends Exception {
	private static final long serialVersionUID = 1L;

	public ZipExtractionException(String message) {
		super(message);
	}
}
