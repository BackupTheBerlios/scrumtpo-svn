package scrummer.util;

import scrummer.model.ConnectionModel;

/**
 * Query that returns something
 * @param <ResultType>
 */
public class ResultQuery<ResultType> extends Query {

	/**
	 * Constructor 
	 * @param connectionModel connectino model
	 */
	public ResultQuery(ConnectionModel connectionModel) {
		super(connectionModel);
	}
	
	/**
	 * Fetch result 
	 * @return
	 */
	public ResultType getResult()
	{
		return _result;
	}
	
	/**
	 * Set result
	 * @param value value to set
	 */
	public void setResult(ResultType value)
	{
		_result = value;
	}
	
	private ResultType _result;

}
