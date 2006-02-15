package com.guiseframework.model;

import java.util.Collection;

import com.guiseframework.validator.ValidationException;

/**A model for selecting one or more values from a collection.
The model must be thread-safe, synchronized on itself. Any iteration over values should include synchronization on the instance of this interface.
@param <V> The type of values contained in the model.
@author Garret Wilson
*/
public interface SelectModel<V> extends ValueModel<V>, Collection<V>
{

	/**Replaces the first occurrence in the of the given value with its replacement.
	This method ensures that another thread does not change the model while the search and replace operation occurs.
	@param oldValue The value for which to search.
	@param newValue The replacement value.
	@return Whether the operation resulted in a modification of the model.
	*/
	public boolean replace(final V oldValue, final V newValue);

	/**Determines the selected value.
	This method delegates to the selection strategy.
	If more than one value is selected, the lead selected value will be returned.
	@return The value currently selected, or <code>null</code> if no value is currently selected.
	*/
	public V getSelectedValue();

	/**Determines the selected values.
	This method delegates to the selection strategy.
	@return The values currently selected.
	*/
	public V[] getSelectedValues();

	/**Sets the selected values.
	If a value occurs more than one time in the model, the first occurrence of the value will be selected.
	Values that do not occur in the select model will be ignored.
	This method delegates to the selection strategy.
	@param values The values to select.
	@exception ValidationException if the provided value is not valid.
	*/
	public void setSelectedValues(final V... values) throws ValidationException;

	/**@return The class representing the type of value this model can hold.*/
	public Class<V> getValueClass();

}