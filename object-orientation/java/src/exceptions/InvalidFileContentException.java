package exceptions;

public class InvalidFileContentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public InvalidFileContentException() {
		super();
		// TODO Auto-generated constructor stub
	}



	public InvalidFileContentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}



	public InvalidFileContentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}



	public InvalidFileContentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}



	public InvalidFileContentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}



	@Override
	public String getMessage() {
		return "File contiene campi non validi!";
	}

	
}
