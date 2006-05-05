package com.guiseframework.component;

import java.beans.PropertyVetoException;
import java.util.*;
import java.util.concurrent.*;

import static com.garretwilson.lang.ObjectUtilities.*;

import com.garretwilson.beans.AbstractGenericPropertyChangeListener;
import com.garretwilson.beans.GenericPropertyChangeEvent;
import com.guiseframework.GuiseSession;
import com.guiseframework.converter.*;
import com.guiseframework.event.*;
import com.guiseframework.model.*;
import com.guiseframework.validator.*;

/**A table component.
@author Garret Wilson
*/
public class Table extends AbstractCompositeStateControl<TableModel.Cell<?>, Table.CellComponentState, Table> implements TableModel
{

	/**The table model used by this component.*/
	private final TableModel tableModel;

		/**@return The table model used by this component.*/
		protected TableModel getTableModel() {return tableModel;}

	/**Whether the table is editable and the cells will allow the the user to change their values, if their respective columns are designated as editable as well.*/
	private boolean editable=true;

		/**@return Whether the table is editable and the cells will allow the the user to change their values, if their respective columns are designated as editable as well.*/
		public boolean isEditable() {return editable;}

		/**Sets whether the table is editable and the cells will allow the the user to change their values, if their respective columns are designated as editable as well.
		This is a bound property of type <code>Boolean</code>.
		@param newEditable <code>true</code> if the cells should allow the user to change their values if their respective columns are also designated as editable.
		@see TableModel#EDITABLE_PROPERTY
		*/
		public void setEditable(final boolean newEditable)
		{
			if(editable!=newEditable)	//if the value is really changing
			{
				final boolean oldEditable=editable;	//get the old value
				editable=newEditable;	//actually change the value
				firePropertyChange(EDITABLE_PROPERTY, Boolean.valueOf(oldEditable), Boolean.valueOf(newEditable));	//indicate that the value changed
			}			
		}

	/**The map of cell representation strategies for columns.*/
	private final Map<TableColumnModel<?>, CellRepresentationStrategy<?>> columnCellRepresentationStrategyMap=new ConcurrentHashMap<TableColumnModel<?>, CellRepresentationStrategy<?>>();

	/**Installs the given cell representation strategy to produce representation components for the given column.
	@param <V> The type of value the column represents.
	@param column The column with which the strategy should be associated.
	@param cellRepresentationStrategy The strategy for generating components to represent values in the given column.
	@return The representation strategy previously associated with the given column.
	*/	
	@SuppressWarnings("unchecked")	//we check the generic types before putting them in the map, so it's fine to cast the retrieved values
	public <V> CellRepresentationStrategy<? super V> setCellRepresentationStrategy(final TableColumnModel<V> column, CellRepresentationStrategy<V> cellRepresentationStrategy)
	{
		return (CellRepresentationStrategy<? super V>)columnCellRepresentationStrategyMap.put(column, cellRepresentationStrategy);	//associate the strategy with the column in the map
	}

	/**Returns the given cell representation strategy assigned to produce representation components for the given column.
	@param <V> The type of value the column represents.
	@param column The column with which the strategy should be associated.
	@return The strategy for generating components to represent values in the given column, or <code>null</code> if there is no associated representation strategy.
	*/	
	@SuppressWarnings("unchecked")	//we check the generic types before putting them in the map, so it's fine to cast the retrieved values
	public <V> CellRepresentationStrategy<? super V> getCellRepresentationStrategy(final TableColumnModel<V> column)
	{
		return (CellRepresentationStrategy<? super V>)columnCellRepresentationStrategyMap.get(column);	//return the strategy linked to the column in the map
	}

	/**Ensures the component for a particular row and column exists.
	@param <T> The type of value contained in the cells of the column.
	@param rowIndex The zero-based cell row index.
	@param column The cell column.
	@return The child component representing the given cell.
	*/
	public <T> Component<?> verifyCellComponent(final int rowIndex, final TableColumnModel<T> column)
	{
		final TableModel tableModel=getTableModel();	//get the table model
		final boolean editable=isEditable() && column.isEditable();	//see if the cell is editable (a cell is only editable if both its table and column are editable)
		final TableModel.Cell<T> cell=new TableModel.Cell<T>(rowIndex, column);	//create a cell object representing this row and column
		CellComponentState cellComponentState=getComponentState(cell);	//get the component information for this cell
		if(cellComponentState==null || cellComponentState.isEditable()!=editable)	//if there is no component for this cell, or the component has a different editable status
		{
			final Component<?> valueComponent=getCellRepresentationStrategy(column).createComponent(this, tableModel, rowIndex, column, editable, false, false);	//create a new component for the cell
//TODO del			valueComponent.setParent(this);	//tell this component that this table component is its parent
			cellComponentState=new CellComponentState(valueComponent, editable);	//create a new component state for the cell's component and metadata
			putComponentState(cell, cellComponentState);	//store the component state in the map for next time
		}
		return cellComponentState.getComponent();	//return the representation component
	}

	/**Value class and column names constructor with a default data model.
	@param <C> The type of values in all the cells in the table.
	@param valueClass The class indicating the type of values held in the model.
	@param columnNames The names to serve as label headers for the columns.
	@exception NullPointerException if the given value class is <code>null</code>.
	*/
	public <C> Table(final Class<C> valueClass, final String... columnNames)
	{
		this(new DefaultTableModel(valueClass, null, columnNames));	//construct the class with no default data
	}

	/**Columns constructor with a default data model.
	@param columns The models representing the table columns.
	*/
	public Table(final TableColumnModel<?>... columns)
	{
		this(new DefaultTableModel(null, columns));	//construct the class with no default data
	}

	/**Value class, table data, and column names constructor with a default data model.
	@param <C> The type of values in all the cells in the table.
	@param valueClass The class indicating the type of values held in the model.
	@param rowValues The two-dimensional list of values, where the first index represents the row and the second represents the column, or <code>null</code> if no default values should be given.
	@param columnNames The names to serve as label headers for the columns.
	@exception NullPointerException if the given value class is <code>null</code>.
	@exception IllegalArgumentException if the given number of columns does not equal the number of columns in any given data row.
	@exception ClassCastException if one of the values in a row is not compatible with the type of its column.
	*/
	public <C> Table(final Class<C> valueClass, final C[][] rowValues, final String... columnNames)
	{
		this(new DefaultTableModel(valueClass, rowValues, columnNames));	//construct the class with a default model
	}

	/**Table data and columns constructor with a default data model.
	@param rowValues The two-dimensional list of values, where the first index represents the row and the second represents the column, or <code>null</code> if no default values should be given.
	@param columns The models representing the table columns.
	@exception IllegalArgumentException if the given number of columns does not equal the number of columns in any given data row.
	@exception ClassCastException if one of the values in a row is not compatible with the type of its column.
	*/
	public Table(final Object[][] rowValues, final TableColumnModel<?>... columns)
	{
		this(new DefaultTableModel(rowValues, columns));	//construct the class with a default model
	}

	/**Table model constructor.
	@param tableModel The component data model.
	@exception NullPointerException if the given table model is <code>null</code>.
	*/
	public Table(final TableModel tableModel)
	{
		this.tableModel=checkInstance(tableModel, "Table model cannot be null.");	//save the table model
		this.tableModel.addPropertyChangeListener(getRepeatPropertyChangeListener());	//listen and repeat all property changes of the table model
		this.tableModel.addVetoableChangeListener(getRepeatVetoableChangeListener());	//listen and repeat all vetoable changes of the table model
			//TODO listen to and repeat table model events
		for(final TableColumnModel<?> column:tableModel.getColumns())	//install a default cell representation strategy for each column
		{
			installDefaultCellRepresentationStrategy(column);	//create and install a default representation strategy for this column
		}
		getSession().addPropertyChangeListener(GuiseSession.LOCALE_PROPERTY, new AbstractGenericPropertyChangeListener<Locale>()	//listen for the session locale changing
				{
					public void propertyChange(GenericPropertyChangeEvent<Locale> propertyChangeEvent)	//if the locale changes
					{
						clearComponentStates();	//clear all the components and component states in case they are locale-related TODO probably transfer this up to the abstract composite state class
					}			
				});
		if(tableModel instanceof ListListenable)	//if this table model allows list listeners TODO improve this; create a table model listener---maybe that will implement ListListener
		{
			final ListListenable<Object> listListenable=(ListListenable<Object>)tableModel;	//get the list listenable
			listListenable.addListListener(new ListListener<Object>()	//listen for table modifications
					{
						public void listModified(final ListEvent<Object> listEvent)	//if the table list is modified
						{
							clearComponentStates();	//clear all the components and component states TODO probably do this on a component-by-component basis
							getView().setUpdated(false);	//TODO fix hack; add a table listener and have the view listen to that
						};
					});
		}
	}

		//TableModel delegations

	/**Determines the logical index of the given table column.
	@param column One of the table columns.
	@return The zero-based logical index of the column within the table, or -1 if the column is not one of the model's columns.
	*/
	public int getColumnIndex(final TableColumnModel<?> column) {return getTableModel().getColumnIndex(column);}

	/**@return A read-only list of table columns in physical order.*/ 
	public List<TableColumnModel<?>> getColumns() {return getTableModel().getColumns();}

	/**@return The number of rows in this table.*/
	public int getRowCount() {return getTableModel().getRowCount();}

	/**@return The number of columns in this table.*/
	public int getColumnCount() {return getTableModel().getColumnCount();}

	/**Returns the cell value for the given cell.
	@param <C> The type of cell value.
	@param cell The cell containing the row index and column information.
	@return The value in the cell at the given row and column, or <code>null</code> if there is no value in that cell.
	@exception IndexOutOfBoundsException if the given row index represents an invalid location for the table.
	@exception IllegalArgumentException if the given column is not one of this table's columns.
	*/
	public <C> C getCellValue(final Cell<C> cell) {return getTableModel().getCellValue(cell);}

	/**Returns the cell value at the given row and column.
	@param <C> The type of cell values in the given column.
	@param rowIndex The zero-based row index.
	@param column The column for which a value should be returned.
	@return The value in the cell at the given row and column, or <code>null</code> if there is no value in that cell.
	@exception IndexOutOfBoundsException if the given row index represents an invalid location for the table.
	@exception IllegalArgumentException if the given column is not one of this table's columns.
	*/
	public <C> C getCellValue(final int rowIndex, final TableColumnModel<C> column) {return getTableModel().getCellValue(rowIndex, column);}

	/**Sets the cell value for the given cell.
	@param <C> The type of cell value.
	@param cell The cell containing the row index and column information.
	@param newCellValue The value to place in the cell at the given row and column, or <code>null</code> if there should be no value in that cell.
	@exception IndexOutOfBoundsException if the given row index represents an invalid location for the table.
	@exception IllegalArgumentException if the given column is not one of this table's columns.
	*/
	public <C> void setCellValue(final Cell<C> cell, final C newCellValue) {getTableModel().setCellValue(cell, newCellValue);}

	/**Sets the cell value at the given row and column.
	@param <C> The type of cell values in the given column.
	@param rowIndex The zero-based row index.
	@param column The column for which a value should be returned.
	@param newCellValue The value to place in the cell at the given row and column, or <code>null</code> if there should be no value in that cell.
	@exception IndexOutOfBoundsException if the given row index represents an invalid location for the table.
	@exception IllegalArgumentException if the given column is not one of this table's columns.
	*/
	public <C> void setCellValue(final int rowIndex, final TableColumnModel<C> column, final C newCellValue) {getTableModel().setCellValue(rowIndex, column, newCellValue);}

	/**An encapsulation of a component for a cell along with other metadata, such as whether the component was editable when created.
	@author Garret Wilson
	*/ 
	protected static class CellComponentState extends AbstractCompositeStateComponent.ComponentState
	{
		/**Whether the component is for a cell that was editable when the component was created.*/
		private final boolean editable;

			/**@return Whether the component is for a cell that was editable when the component was created.*/
			public boolean isEditable() {return editable;}

		/**Constructor
		@param component The component for a cell.
		@param editable Whether the component is for a cell that was editable when the component was created.
		@exception NullPointerException if the given component is <code>null</code>.
		*/
		public CellComponentState(final Component<?> component, final boolean editable)
		{
			super(component);	//construct the parent class
			this.editable=editable;
		}
	}

	/**Installs a default cell representation strategy for the given column.
	@param <T> The type of value contained in the column.
	@param column The table column for which a default cell representation strategy should be installed.
	*/
	private <T> void installDefaultCellRepresentationStrategy(final TableColumnModel<T> column)
	{
		setCellRepresentationStrategy(column, new DefaultCellRepresentationStrategy<T>(AbstractStringLiteralConverter.getInstance(column.getValueClass())));	//create a default cell representation strategy
	}

	/**A strategy for generating components to represent table cell model values.
	The component ID should reflect a unique identifier for the cell.
	@param <V> The type of value the strategy is to represent.
	@author Garret Wilson
	*/
	public interface CellRepresentationStrategy<V>
	{
		/**Creates a component to represent the given cell.
		@param <C> The type of value contained in the column.
		@param table The component containing the model.
		@param model The model containing the value.
		@param rowIndex The zero-based row index of the value.
		@param column The column of the value.
		@param editable Whether values in this column are editable.
		@param selected <code>true</code> if the value is selected.
		@param focused <code>true</code> if the value has the focus.
		@return A new component to represent the given value.
		*/
		public <C extends V> Component<?> createComponent(final Table table, final TableModel model, final int rowIndex, final TableColumnModel<C> column, final boolean editable, final boolean selected, final boolean focused);
	}

	/**A default table cell representation strategy.
	Component values will be represented as themselves.
	For non-editable cells, a message component will be generated using the cell's value as its message.
	Editable cells will be represented using a checkbox for boolean values and a text control for all other values.
	The message's ID will be in the form "<var>tableID</var>.cell-<var>rowIndex</var>-<var>columnIndex</var>".
	@param <V> The type of value the strategy is to represent.
	@see Message
	@see Converter
	@author Garret Wilson
	*/
	public static class DefaultCellRepresentationStrategy<V> implements CellRepresentationStrategy<V>
	{

		/**The converter to use for displaying the value as a string.*/
		private final Converter<V, String> converter;
			
			/**@return The converter to use for displaying the value as a string.*/
			public Converter<V, String> getConverter() {return converter;}

		/**Converter constructor.
		@param converter The converter to use for displaying the value as a string.
		@exception NullPointerException if the given converter is <code>null</code>.
		*/
		public DefaultCellRepresentationStrategy(final Converter<V, String> converter)
		{
			this.converter=checkInstance(converter, "Converter cannot be null.");	//save the converter
		}

		/**Creates a component for the given cell.
		This implementation returns a message with string value of the given value using the object's <code>toString()</code> method.
		@param <C> The type of value contained in the column.
		@param table The component containing the model.
		@param model The model containing the value.
		@param rowIndex The zero-based row index of the value.
		@param column The column of the value.
		@param editable Whether values in this column are editable.
		@param selected <code>true</code> if the value is selected.
		@param focused <code>true</code> if the value has the focus.
		@return A new component to represent the given value.
		*/
		@SuppressWarnings("unchecked")	//we check the type of the column value class, so the casts are safe
		public <C extends V> Component<?> createComponent(final Table table, final TableModel model, final int rowIndex, final TableColumnModel<C> column, final boolean editable, final boolean selected, final boolean focused)
		{
			final TableModel.Cell<C> cell=new TableModel.Cell<C>(rowIndex, column);	//create a cell to represent the row and column
			final Class<C> valueClass=column.getValueClass();	//get the value class of the column
			if(Component.class.isAssignableFrom(valueClass))	//if a component is being represented
			{
				return (Component<?>)model.getCellValue(cell);	//return the value as a component TODO find a way to update the cached component if it changes
			}
			final int columnIndex=model.getColumnIndex(column);	//get the logical index of the given column
			if(editable)	//if the component should be editable
			{
				final ValueModel<C> valueModel=new DefaultCellValueModel<C>(model, cell);	//create a new value model for the cell
				if(Boolean.class.isAssignableFrom(valueClass))	//if the value class is subclass of Boolean
				{
					return new CheckControl((ValueModel<Boolean>)(Object)valueModel);	//create a new check control for the Boolean value model TODO find out why JDK 1.5.0_03 requires the intermediate Object cast
				}
				else	//for all other values
				{
					return new TextControl<C>(valueModel);	//generate a text input control for the value model
				}
			}
			else	//if the component should not be editable, return a message component
			{
				return new DefaultCellMessage<C>(model, cell, getConverter());	//create a message component containing a message model representing the value's string value				
			}
		}
	}

	/**A message model that returns a default representation of the cell in a message.
	@param <C> The type of value in the cell.
	@author Garret Wilson
	*/
	public static class DefaultCellMessage<C> extends Message	//TODO convert this to a DefaultCellText component
	{
		/**The table model of the cell.*/
		private final TableModel tableModel;

			/**@return The table model of the cell.*/
			protected TableModel getTableModel() {return tableModel;}

		/**The cell being represented*/
		private TableModel.Cell<C> cell;

			/**@return The cell being represented*/
			protected TableModel.Cell<C> getCell() {return cell;}

		/**The converter to use for displaying the value as a string.*/
		private final Converter<? super C, String> converter;
			
			/**@return The converter to use for displaying the value as a string.*/
			public Converter<? super C, String> getConverter() {return converter;}

		/**Constructs a default message for a cell.
		@param tableModel The table model of the cell.
		@param cell The cell being represented.
		@param converter The converter to use for displaying the value as a string.
		@exception NullPointerException if the given session, table model and/or cell is <code>null</code>.
		*/
		public DefaultCellMessage(final TableModel tableModel, final TableModel.Cell<C> cell, final Converter<? super C, String> converter)
		{
			this.tableModel=checkInstance(tableModel, "Table model cannot be null.");
			this.cell=checkInstance(cell, "Cell cannot be null.");
			this.converter=checkInstance(converter, "Converter cannot be null.");
		}

		/**Determines the message text of the cell.
		This implementation returns a message with a string value of the given value using the installed converter, if no message has been explicitly set.
		@return The message text of the cell.
		@see #getConverter()
		*/
		public String getMessage()
		{
			String message=super.getMessage();	//get the message explicitly set
			if(message==null)	//if no message has been explicitly set
			{
				final TableModel.Cell<C> cell=getCell();	//get our current cell
				final C value=getTableModel().getCellValue(cell.getRowIndex(), cell.getColumn());	//get the value from the table model
				try
				{
					message=getConverter().convertValue(value);	//return the literal value of the value
				}
				catch(final ConversionException conversionException)	//we don't expect a value-to-string conversion to result in an error
				{
					throw new AssertionError(conversionException);
				}
			}
			return message;	//return the message
		}

	}

	/**A value model that returns and updates a the value of the cell.
	@param <C> The type of value in the cell.
	@author Garret Wilson
	*/
	public static class DefaultCellValueModel<C> extends DefaultValueModel<C>
	{
		/**The table model of the cell.*/
		private final TableModel model;

			/**@return The table model of the cell.*/
			protected TableModel getModel() {return model;}

		/**The cell being represented*/
		private TableModel.Cell<C> cell;

			/**@return The cell being represented*/
			protected TableModel.Cell<C> getCell() {return cell;}

		/**Constructs a default value model for a cell.
		@param model The table model of the cell.
		@param cell The cell being represented.
		@exception NullPointerException if the given table model and/or cell is <code>null</code>.
		*/
		public DefaultCellValueModel(final TableModel model, final TableModel.Cell<C> cell)
		{
			super(checkInstance(cell, "Cell cannot be null.").getColumn().getValueClass());	//construct the parent class
			this.model=checkInstance(model, "Table model cannot be null.");
			this.cell=cell;
		}

		/**@return Whether the model's value is editable and the corresponding control will allow the the user to change the value.
		This version returns <code>true</code> if the model and column are both editable.*/
//TODO important fix		public boolean isEditable() {return getModel().isEditable() && getCell().getColumn().isEditable();}

		/**Sets whether the model's value is editable and the corresponding control will allow the the user to change the value. This version throws an exception, as the editable status is read-only.
		@param newEditable <code>true</code> if the corresponding control should allow the user to change the value.
		*/
//TODO important fix		public void setEditable(final boolean newEditable) {throw new UnsupportedOperationException("Editable is read-only.");}

		/**@return Whether the model is enabled and and the corresponding control can receive user input.
		This version returns <code>true</code> if the model and column are both enabled.*/
//TODO update once enabled is moved		public boolean isEnabled() {return getModel().isEnabled() && getCell().getColumn().isEnabled();}

		/**Sets whether the model is enabled and and the corresponding control can receive user input. This version throws an exception, as the enabled status is read-only.
		@param newEnabled <code>true</code> if the corresponding control should indicate and accept user input.
		*/
//TODO update		public void setEnabled(final boolean newEnabled) {throw new UnsupportedOperationException("Enabled is read-only.");}

		/**@return The validator for this model, or <code>null</code> if no validator is installed.*/
		public Validator<C> getValidator() {return getCell().getColumn().getValidator();}	//return the validator from the column

		/**Sets the validator. This version throws an exception, as the validator is read-only.
		@param newValidator The validator for this model, or <code>null</code> if no validator should be used.
		*/
		public void setValidator(final Validator<C> newValidator) {throw new UnsupportedOperationException("Validator is read-only.");}

		/**@return The value from the table model cell, or <code>null</code> if there is no value in the cell.*/
		public C getValue() {return getModel().getCellValue(getCell());}	//return the value from the table model

		/**Sets the value in the cell.
		If the value change is vetoed by the installed validator, the validation exception will be accessible via {@link PropertyVetoException#getCause()}.
		@param newValue The new value of the cell.
		@exception PropertyVetoException if the provided value is not valid or the change has otherwise been vetoed.
		@see #getValidator()
		@see #VALUE_PROPERTY
		*/
		public void setValue(final C newValue) throws PropertyVetoException
		{
			final C oldValue=getValue();	//get the old value
			final Validator<C> validator=getValidator();	//get the currently installed validator, if there is one
			if(validator!=null)	//if a validator is installed, always validate the value, even if it isn't changing, so that an initial value that may not be valid will throw an error when it's tried to be set to the same, but invalid, value
			{
				try
				{
					validator.validate(newValue);	//validate the new value, throwing an exception if anything is wrong
				}
				catch(final ValidationException validationException)	//if the new value doesn't pass validation
				{
					throw createPropertyVetoException(this, validationException, VALUE_PROPERTY, oldValue, newValue);	//throw a property veto exception representing the validation error
				}					
			}
			getModel().setCellValue(getCell(), newValue);	//set the value in the table model
		}

		/**Resets the value to a default value, which may be invalid according to any installed validators.
		No validation occurs.
		@see ValueModel#VALUE_PROPERTY
		*/
		public void resetValue()
		{
			getModel().setCellValue(getCell(), null);	//set a null value in the table model
		}

	}

}
