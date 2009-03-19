package scrummer.model;

/**
 * Class through which all models are created and referenced
 */
public class ModelFactory {

	/**
	 * Create connection model
	 * @return created model
	 */
	public ConnectionModel createConnectionModel()
	{
		if (_connectionModel == null)
		{
			_connectionModel = new ConnectionModel();
		}
		return _connectionModel;
	}

	/**
	 * Fetch connection model
	 * @return model
	 */
	public ConnectionModel getConnectionModel()
	{
		if (_connectionModel == null)
		{
			throw new NullPointerException("Connection model was not created!");
		}
		return _connectionModel;
	}
	
	/**
	 * Create project model
	 * @return created model
	 */
	public ProjectModel createProjectModel()
	{
		if (_projectModel == null)
		{
			_projectModel = new ProjectModel();
		}
		return _projectModel;
	}
	
	/**
	 * Fetch project model
	 * @return model
	 */
	public ProjectModel getProjectModel()
	{
		if (_projectModel == null)
		{
			throw new NullPointerException("Project model was not created!");
		}
		return _projectModel;
	}
	
	/**
	 * Create navigation model
	 * @return created model
	 */
	public NavigationModel createNavigationModel()
	{
		if (_navigationModel == null)
		{
			_navigationModel = new NavigationModel();
		}
		return _navigationModel;
	}
	
	/**
	 * Fetch navigation model
	 * @return model
	 */
	public NavigationModel getNavigationModel()
	{
		if (_navigationModel == null)
		{
			throw new NullPointerException("Navigation model was not created!");
		}
		return _navigationModel;
	}
	
	/**
	 * Create property model
	 * @return created model
	 */
	public PropertyModel createPropertyModel(LoggingModel logger)
	{
		if (_propertyModel == null)
		{
			_propertyModel = new PropertyModel(logger);
		}
		return _propertyModel;
	}
	
	/**
	 * Fetch property model
	 * @return model
	 */
	public PropertyModel getPropertyModel()
	{
		if (_propertyModel == null)
		{
			throw new NullPointerException("Property model was not created!");
		}
		return _propertyModel;
	}
	
	/**
	 * Create logging model
	 * @return created model
	 */
	public LoggingModel createLoggingModel()
	{
		if (_loggingModel == null)
		{
			_loggingModel = new LoggingModel();
		}
		return _loggingModel;
	}
	
	/**
	 * Fetch logging model
	 * @return logging model
	 */
	public LoggingModel getLoggingModel()
	{
		if (_loggingModel == null)
		{
			throw new NullPointerException("Logging model was not created!");
		}
		return _loggingModel;
	}
	
	private ConnectionModel _connectionModel = null;
	private ProjectModel    _projectModel = null;
	private NavigationModel _navigationModel = null;
	private PropertyModel   _propertyModel = null;
	private LoggingModel    _loggingModel = null;
}
