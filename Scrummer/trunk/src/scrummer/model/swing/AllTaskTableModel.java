package scrummer.model.swing;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.xnap.commons.i18n.I18n;

import scrummer.Scrummer;
import scrummer.model.ConnectionModel;
import scrummer.model.DBSchemaModel;
import scrummer.model.Models;
import scrummer.model.ProjectModel;
import scrummer.model.SprintBacklogModelCommon;
import scrummer.util.ObjectRow;

public class AllTaskTableModel extends DefaultTableModel {
	
	/**
	 * Default constructor
	 * 
	 * @param connectionModel connection model to connect to database
	 */
	public AllTaskTableModel(ConnectionModel connectionModel, 
						  SprintBacklogModelCommon sprintbacklogModelCommon,
						  ProjectModel projectModel) 
	{
		super();
		
		_projectModel = projectModel;
		_sprintbacklogModelCommon = sprintbacklogModelCommon;
				
		_columns.add(i18n.tr("Task id"));
		_columns.add(i18n.tr("PBI"));
		_columns.add(i18n.tr("Employee"));
		_columns.add(i18n.tr("Team"));
		
		_columns.add(i18n.tr("Task description"));
		_columns.add(i18n.tr("Task type"));
		_columns.add(i18n.tr("Task status"));
		
		_columns.add(i18n.tr("Task date"));
		_columns.add(i18n.tr("Task active"));		
		
		for (int i = 0; i < _columns.size(); i++)
			_realColumns.add("");
	}

	/**
	 * Refresh data
	 */
	public void refresh()
	{
		refreshColumnNames();
		refreshTableData();
        fireTableDataChanged();
	}
	
	/**
	 * Refresh real column names
	 */
	private void refreshColumnNames()
	{
		Models m = Scrummer.getModels();
		DBSchemaModel schemam = m.getDBSchemaModel();
		_realColumns = schemam.getColumns(DBSchemaModel.TaskTable);
		_columnCount = _columns.size();
	}
	
	/**
	 * Refresh table data
	 */
	private void refreshTableData()
	{
		_rows = _sprintbacklogModelCommon.fetchTaskTable(_projectModel.getCurrentProjectId(), _sprintId);
        _rowCount = _rows.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{	
		
	}
	
	@Override
	public void removeRow(int row) 
	{
		if (_sprintbacklogModelCommon.removeSprintBacklog(
				_rows.get(row).get(0).toString()))
		{
			refresh();
		}			
	}

	@Override
	public int getColumnCount() {
		return _columnCount;
	}

	@Override
	public int getRowCount() {
		return _rowCount;
	}

	@Override
	public Object getValueAt(int row, int column) {
		return _rows.get(row).get(column);
	}

	@Override
	public String getColumnName(int column) {
		return _columns.get(column);
	}
	
	/**
	 * Set sprint id that will be displayed by this table
	 * @param value value to set
	 */
	public void setSprintId(int value)
	{
		_sprintId = value;
		refresh();
	}
	
	/// project model
	private ProjectModel _projectModel;
	/// sprint id
	private int _sprintId;
	/// column count Sprint PBI
	private int _columnCount = 0;
	/// row count
	private int _rowCount = 0;
	/// column names for Sprint PBI display
	private Vector<String> _columns = new Vector<String>(14);
	/// real column names for UPDATE-ing
	private Vector<String> _realColumns = new Vector<String>();
	/// data rows
	private Vector<ObjectRow> _rows = new Vector<ObjectRow>();
	/// common developer model operations
	private SprintBacklogModelCommon _sprintbacklogModelCommon;
	/// translation class field
	private I18n i18n = Scrummer.getI18n(getClass());
	/// serialization id
	private static final long serialVersionUID = 2334976808166694864L;

}
