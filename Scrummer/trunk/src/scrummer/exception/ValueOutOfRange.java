package scrummer.exception;

/**
 * Value out of range
 */
public class ValueOutOfRange extends RuntimeException {

	/**
	 * Value out of range exception
	 */
	public ValueOutOfRange() {}

	/**
	 * Constructor
	 * 
	 * @param value wrong value
	 * @param low low bound
	 * @param high high bound
	 */
	public ValueOutOfRange(int value, int low, int high)
	{
		super("Value " + value + " out of range [" + low + "," + high + "].");
	}
	
	/// serialization id
	private static final long serialVersionUID = 1646312224447892912L;
}
