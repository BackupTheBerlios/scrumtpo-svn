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
	public Models(LoggingModel logger) {
		_loggingModel = logger;
		createPropertyModel(getLoggingModel());
		
		String languageClassFile = _propertyModel.getProperty("language.default");
		Scrummer.setLanguageClassFile(languageClassFile);
		
		createConnectionModel(getPropertyModel());
		createProjectModel();
		createNavigationModel();
		createResourceModel();
		createDeveloperModel();
		createProductBacklogModel();
		createMetricModel();
		
		createSprintBacklogModel();
		createImpedimentModel();
		
		_sprintbacklogModel.setImpedimentTableModel(_impedimentModel.getImpedimentTableModel());
		
		getProductBacklogModel().setSprintBacklogModel(getSprintBacklogModel());
		
		createDBSchemaModel();
		createAdminDaysModel();
		createAbsenceTypeModel();
		createTaskTypeModel();
		createTaskStatusModel();
		createTaskModel();
	}
	
	/**
	 * Create connection model
	 * @param logger logger
	 * @return created model
	 */
	private ConnectionModel createConnectionModel(PropertyModel properties) {
		if (_connectionModel == null) {
			_connectionModel = new ConnectionModel(getLoggingModel(), properties);
		}
		return _connectionModel;
	}

	/**
	 * Fetch connection model
	 * @return model
	 */
	public ConnectionModel getConnectionModel() {
		if (_connectionModel == null) {
			throw new NullPointerException("Connection model was not created!");
		}
		return _connectionModel;
	}
	
	/**
	 * Create project model
	 * @return created model
	 */
	private ProjectModel createProjectModel() {
		if (_projectModel == null) {
			_projectModel = new ProjectModel(getConnectionModel());
		}
		return _projectModel;
	}
	
	/**
	 * Fetch project model
	 * @return model
	 */
	public ProjectModel getProjectModel() {
		if (_projectModel == null) {
			throw new NullPointerException("Project model was not created!");
		}
		return _projectModel;
	}
	
	/**
	 * Create navigation model
	 * @return created model
	 */
	private NavigationModel createNavigationModel() {
		if (_navigationModel == null) {
			_navigationModel = new NavigationModel(getProjectModel());
		}
		return _navigationModel;
	}
	
	/**
	 * Fetch navigation model
	 * @return model
	 */
	public NavigationModel getNavigationModel() {
		if (_navigationModel == null) {
			throw new NullPointerException("Navigation model was not created!");
		}
		return _navigationModel;
	}
	
	/**
	 * Create property model
	 * @return created model
	 */
	private PropertyModel createPropertyModel(LoggingModel logger) {
		if (_propertyModel == null) {
			_propertyModel = new PropertyModel(logger);
		}
		return _propertyModel;
	}
	
	/**
	 * Fetch property model
	 * @return model
	 */
	public PropertyModel getPropertyModel() {
		if (_propertyModel == null) {
			throw new NullPointerException("Property model was not created!");
		}
		return _propertyModel;
	}
	
	/**
	 * Create logging model
	 * @return created model
	 */
	private LoggingModel createLoggingModel() {
		if (_loggingModel == null) {
			_loggingModel = new LoggingModel();
		}
		return _loggingModel;
	}
	
	/**
	 * Fetch logging model
	 * @return logging model
	 */
	public LoggingModel getLoggingModel() {
		if (_loggingModel == null) {
			throw new NullPointerException("Logging model was not created!");
		}
		return _loggingModel;
	}
	
	/**
	 * Create resource model
	 * @return created resource model
	 */
	private ResourceModel createResourceModel() {
		if (_resourceModel == null)	 {
			_resourceModel = new ResourceModel(getLoggingModel());
		}
		return _resourceModel;
	}
	
	/**
	 * @return resource model
	 */
	public ResourceModel getResourceModel() {
		if (_resourceModel == null) {
			throw new NullPointerException("Resource model was not yet created!");
		}
		return _resourceModel;
	}
	
	/**
	 * Create developer model
	 * @return created developer model
	 */
	private DeveloperModel createDeveloperModel() {
		if (_developerModel == null) {
			_developerModel = new DeveloperModel(getConnectionModel());
		}
		return _developerModel;
	}
	
	/**
	 * @return developer model
	 */
	public DeveloperModel getDeveloperModel() {
		if (_developerModel == null) {
			throw new NullPointerException("Developer model was not yet created!");
		}
		return _developerModel;
	}
	
	/**
	 * Create impediment model
	 * @return created impediment model
	 */
	private ImpedimentModel createImpedimentModel() {
		if (_impedimentModel == null) {
			_impedimentModel = new ImpedimentModel(getConnectionModel(), getSprintBacklogModel());
		}
		return _impedimentModel;
	}
	
	/**
	 * @return impediment model
	 */
	public ImpedimentModel getImpedimentModel() {
		if (_impedimentModel == null) {
			throw new NullPointerException("Impediment model was not yet created!");
		}
		return _impedimentModel;
	}
	
	/**
	 * Create product backlog model
	 * @return created product backlog model
	 */
	private ProductBacklogModel createProductBacklogModel() {
		if (_productbacklogModel == null) {
			_productbacklogModel = 
				new ProductBacklogModel(getConnectionModel(), getProjectModel());
		}
		return _productbacklogModel;
	}
	
	/**
	 * @return product backlog model
	 */
	public ProductBacklogModel getProductBacklogModel() {
		if (_productbacklogModel == null) {
			throw new NullPointerException("Product Backlog model was not yet created!");
		}
		return _productbacklogModel;
	}
	
	/**
	 * Create sprint backlog model
	 * @return created sprint backlog model
	 */
	private SprintBacklogModel createSprintBacklogModel() {
		if (_sprintbacklogModel == null) {
			_sprintbacklogModel = new SprintBacklogModel(getConnectionModel(), getProjectModel());
		}
		return _sprintbacklogModel;
	}
	
	/**
	 * @return product backlog model
	 */
	public SprintBacklogModel getSprintBacklogModel() {
		if (_sprintbacklogModel == null) {
			throw new NullPointerException("Sprint Backlog model was not yet created!");
		}
		return _sprintbacklogModel;
	}
	
	/**
	 * Create database schema model
	 * @return created model
	 */
	private DBSchemaModel createDBSchemaModel() {
		if (_dbschemaModel == null)	{
			_dbschemaModel = new DBSchemaModel(getConnectionModel());
		}
		return _dbschemaModel;
	}
	
	/**
	 * @return database schema model
	 */
	public DBSchemaModel getDBSchemaModel() {
		if (_dbschemaModel == null) {
			throw new NullPointerException("Database schema model was not yet created!");
		}
		return _dbschemaModel;
	}
	
	/**
	 * Create administrative days model
	 * @return created model
	 */
	private AdminDaysModel createAdminDaysModel() {
		if (_admindaysModel == null) {
			_admindaysModel = new AdminDaysModel(getConnectionModel());
		}
		return _admindaysModel;
	}
	
	/**
	 * @return administrative days model
	 */
	public AdminDaysModel getAdminDaysModel() {
		if (_admindaysModel == null) {
			throw new NullPointerException("Administrative days model was not yet created!");
		}
		return _admindaysModel;
	}
	
	private AbsenceTypeModel createAbsenceTypeModel() {
		if (_absencetypeModel == null) {
			_absencetypeModel = new AbsenceTypeModel(getConnectionModel());
		}
		return _absencetypeModel;
	}
	
	public AbsenceTypeModel getAbsenceTypeModel() {
		if (_absencetypeModel == null) {
			throw new NullPointerException("Absence type model was not yet created!");
		}
		return _absencetypeModel;
	}
	
	private TaskStatusModel createTaskStatusModel() {
		if (_taskstatusModel == null) {
			_taskstatusModel = new TaskStatusModel(getConnectionModel());
		}
		return _taskstatusModel;
	}
	
	public TaskStatusModel getTaskStatusModel() {
		if (_taskstatusModel == null) {
			throw new NullPointerException("Task status model was not yet created!");
		}
		return _taskstatusModel;
	}
	
	private TaskTypeModel createTaskTypeModel() {
		if (_tasktypeModel == null)	{
			_tasktypeModel = new TaskTypeModel(getConnectionModel());
		}
		return _tasktypeModel;
	}
	
	public TaskTypeModel getTaskTypeModel()  {
		if (_tasktypeModel == null) {
			throw new NullPointerException("Task type model was not yet created!");
		}
		return _tasktypeModel;
	}
	
	private TaskModel createTaskModel() {
		if (_taskModel == null)	 {
			_taskModel = 
				new TaskModel(getConnectionModel(), getProjectModel(), getSprintBacklogModel());
		}
		return _taskModel;
	}
	
	public TaskModel getTaskModel() {
		if (_taskModel == null) {
			throw new NullPointerException("Task model was not yet created!");
		}
		return _taskModel;
	}
	
	public MetricModel createMetricModel() {
		if (_metricModel == null) {
			_metricModel = new MetricModel(getConnectionModel(), getProjectModel(), getSprintBacklogModel());
		}
		return _metricModel;
	}
	
	public MetricModel getMetricModel() {
		if (_metricModel == null) {
			throw new NullPointerException("Metric model was not created!");
		}
		return _metricModel;
	}
	
	private ConnectionModel _connectionModel = null;
	private ProjectModel    _projectModel = null;
	private NavigationModel _navigationModel = null;
	private PropertyModel   _propertyModel = null;
	private LoggingModel    _loggingModel = null;
	private ResourceModel   _resourceModel = null;
	private DeveloperModel  _developerModel = null;
	private ImpedimentModel _impedimentModel = null;
	private MetricModel _metricModel = null;
	private SprintBacklogModel  _sprintbacklogModel = null;
	private ProductBacklogModel _productbacklogModel = null;
	/// schema model contains database schema related data
	private DBSchemaModel _dbschemaModel = null;
	private AdminDaysModel _admindaysModel = null;
	private AbsenceTypeModel _absencetypeModel = null;
	private TaskStatusModel _taskstatusModel = null;
	private TaskTypeModel _tasktypeModel = null;
	private TaskModel _taskModel = null;
}
