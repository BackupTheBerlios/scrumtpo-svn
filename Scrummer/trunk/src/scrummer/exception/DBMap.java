package scrummer.exception;

/**
 * Database mapping exception - thrown in case something went wrong with database mapping
 */
public class DBMap extends RuntimeException {

	public DBMap() {}

	public DBMap(Object obj)
	{
		super("Database mapping error" + obj.getClass().toString());
	}
	
	public DBMap(String message) {
		super(message);
	}

	public DBMap(Throwable cause) {
		super(cause);
	}

	public DBMap(String message, Throwable cause) {
		super(message, cause);
	}

	/// serialization id
	private static final long serialVersionUID = 8953864326257965166L;
}
