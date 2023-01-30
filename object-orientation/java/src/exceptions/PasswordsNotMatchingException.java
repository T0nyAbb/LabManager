package exceptions;

public class PasswordsNotMatchingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordsNotMatchingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordsNotMatchingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PasswordsNotMatchingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PasswordsNotMatchingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PasswordsNotMatchingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		return "Le password inserite non corrispondono!";
		
	}
	
}
