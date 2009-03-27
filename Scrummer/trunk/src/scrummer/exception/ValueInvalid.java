package scrummer.exception;

/**
 * Some value was invalid
 */
public class ValueInvalid extends RuntimeException {
	
	/**
	 * Constrctor
	 * @param value value that vas invalid
	 * @param reason why was the value invalid
	 */
	public ValueInvalid(String value, String reason)
	{
		super("Invalid value: " + value + " - " + reason);
	}
	
	/// serialization id
	private static final long serialVersionUID = 8416745973868729228L;
}
