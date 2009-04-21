package scrummer.exception;

/**
 * Report invalid null object fields through this exception class
 */
public class NullField extends RuntimeException {

	public NullField() {}

	public NullField(String message) {
		super(message);
	}

	public NullField(Throwable cause) {
		super(cause);
	}

	public NullField(String message, Throwable cause) {
		super(message, cause);
	}
	
	/// serialization id
	private static final long serialVersionUID = -5995095702641201337L;
}
