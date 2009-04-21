package scrummer.model;

import java.util.Date;

public class DataRow {
	
	/**
	 * @return true if data is valid(no need to be refreshed), false otherwise
	 */
	public boolean isValid(Date current)
	{
		if (_lastChange == null)
		{
			_lastChange = current;
			return true;
		}
		else
		{	
			long currentMs = current.getTime();
			long lastMs = _lastChange.getTime();
			if (currentMs > lastMs)
			{
				if ((currentMs - lastMs) > _dataCached)
				{
					_lastChange = current;
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}

	/// how much time in miliseconds should data be kept
	private long _dataCached = 10000;
	/// 
	private Date _lastChange;
}
