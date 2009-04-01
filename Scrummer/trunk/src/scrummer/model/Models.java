package scrummer.model;

import scrummer.Scrummer;

/**
 * Class through which all models are accessable
 */
public class Models {
	
	/**
	 * Constructor
	 * 
	 * Constructs all models.
	 * 
	 * @param logger logger instance
	 */
	public Models(LoggingModel logger)
	{
		_loggingModel = logger;
		createPropertyModel(getLoggingModel());
		
		String languageClassFile = _propertyModel.getProperty("language.default");
		Scrummer.setLanguageClassFile(languageClassFile);
		
		createConnectionModel(getPropertyModel());
		createProjectModel();
		createNavigationModel();
		createResourceModel();
		createDeveloperModel();
		createImpedimentModel();
		createProductBacklogModel();
		createSprintBacklogModel();
		createDBSchemaModel();
	}
	
	/**
	 * Create connection model
	 * @param logger logger
	 * @return created model
	 */
	private ConnectionModel createConnectionModel(PropertyModel properties)
	{
		if (_connectionModel == null)
		{
			_connectionModel = new ConnectionModel(getLoggingModel(), properties);
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
	private ProjectModel createProjectModel()
	{
		if (_projectModel == null)
		{
			_projectModel = new ProjectModel(getConnectionModel());
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
	private NavigationModel createNavigationModel()
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
	private PropertyModel createPropertyModel(LoggingModel logger)
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
	private LoggingModel createLoggingModel()
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
	
	/**
	 * Create resource model
	 * @return created resource model
	 */
	private ResourceModel createResourceModel()
	{
		if (_resourceModel == null)	
		{
			_resourceModel = new ResourceModel(getLoggingModel());
		}
		return _resourceModel;
	}
	
	/**
	 * @return resource model
	 */
	public ResourceModel getResourceModel()
	{
		if (_resourceModel == null)
		{
			throw new NullPointerException("Resource model was not yet created!");
		}
		return _resourceModel;
	}
	
	/**
	 * Create developer model
	 * @return created developer model
	 */
	private DeveloperModel createDeveloperModel()
	{
		if (_developerModel == null)	
		{
			_developerModel = new DeveloperModel(getConnectionModel());
		}
		return _developerModel;
	}
	
	/**
	 * @return developer model
	 */
	public DeveloperModel getDeveloperModel()
	{
		if (_developerModel == null)
		{
			throw new NullPointerException("Developer model was not yet created!");
		}
		return _developerModel;
	}
	
	/**
	 * Create impediment model
	 * @return created impediment model
	 */
	private ImpedimentModel createImpedimentModel()
	{
		if (_impedimentModel == null)	
		{
			_impedimentModel = new ImpedimentModel(getConnectionModel());
		}
		return _impedimentModel;
	}
	
	/**
	 * @return impediment model
	 */
	public ImpedimentModel getImpedimentModel()
	{
		if (_impedimentModel == null)
		{
			throw new NullPointerException("Impediment model was not yet created!");
		}
		return _impedimentModel;
	}
	
	/**
	 * Create product backlog model
	 * @return created product backlog model
	 */
	private ProductBacklogModel createProductBacklogModel()
	{
		if (_productbacklogModel == null)	
		{
			_productbacklogModel = new ProductBacklogModel(getConnectionModel());
		}
		return _productbacklogModel;
	}
	
	/**
	 * @return product backlog model
	 */
	public ProductBacklogModel getProductBacklogModel()
	{
		if (_productbacklogModel == null)
		{
			throw new NullPointerException("Product Backlog model was not yet created!");
		}
		return _productbacklogModel;
	}
	
	/**
	 * Create product backlog model
	 * @return created product backlog model
	 */
	private SprintBacklogModel createSprintBacklogModel()
	{
		if (_sprintbacklogModel == null)	
		{
			_sprintbacklogModel = new SprintBacklogModel(getConnectionModel());
		}
		return _sprintbacklogModel;
	}
	
	/**
	 * @return product backlog model
	 */
	public SprintBacklogModel getSprintBacklogModel()
	{
		if (_sprintbacklogModel == null)
		{
			throw new NullPointerException("Product Backlog model was not yet created!");
		}
		return _sprintbacklogModel;
	}
	
	/**
	 * Create database schema model
	 * @return created model
	 */
	private DBSchemaModel createDBSchemaModel()
	{
		if (_dbschemaModel == null)	
		{
			_dbschemaModel = new DBSchemaModel(getConnectionModel());
		}
		return _dbschemaModel;
	}
	
	/**
	 * @return database schema model
	 */
	public DBSchemaModel getDBSchemaModel()
	{
		if (_dbschemaModel == null)
		{
			throw new NullPointerException("Database schema model was not yet created!");
		}
		return _dbschemaModel;
	}
	
	
	private ConnectionModel _connectionModel = null;
	private ProjectModel    _projectModel = null;
	private NavigationModel _navigationModel = null;
	private PropertyModel   _propertyModel = null;
	private LoggingModel    _loggingModel = null;
	private ResourceModel   _resourceModel = null;
	private DeveloperModel  _developerModel = null;
	private ImpedimentModel _impedimentModel = null;
	private SprintBacklogModel  _sprintbacklogModel = null;
	private ProductBacklogModel _productbacklogModel = null;
	/// schema model contains database schema related data
	private DBSchemaModel _dbschemaModel = null;
}
