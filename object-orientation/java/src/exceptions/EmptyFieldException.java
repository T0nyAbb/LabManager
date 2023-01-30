package exceptions;

public class EmptyFieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyFieldException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmptyFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EmptyFieldException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public EmptyFieldException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EmptyFieldException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		return "I campi non possono essere vuoti!";
	}
	
}
