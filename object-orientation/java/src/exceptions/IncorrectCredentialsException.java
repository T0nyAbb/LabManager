package exceptions;

public class IncorrectCredentialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectCredentialsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IncorrectCredentialsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public IncorrectCredentialsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IncorrectCredentialsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IncorrectCredentialsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		return "I dati inseriti sono incorretti!";
	}
	
}
