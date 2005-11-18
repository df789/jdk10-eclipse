package com.javaguise.component.layout;

import com.garretwilson.beans.PropertyValueChangeEvent;
import static com.garretwilson.lang.ObjectUtilities.*;
import com.javaguise.component.Component;

/**An event indicating that a property of layout constraints changed.
The source is always the layout object. The component and constraints are also provided.
@param <T> The type of layout constraints associated with the component.
@param <V> The type of property value.
@author Garret Wilson
*/
public class LayoutConstraintsPropertyChangeEvent<T extends Layout.Constraints, V> extends PropertyValueChangeEvent<V>
{

	/**@return The layout in which the event occurred.*/
	public Layout getSource() {return (Layout)super.getSource();}

	/**The component for which a constraint value changed.*/
	private final Component<?> component;

		/**@return The component for which a constraint value changed.*/
		public Component<?> getComponent() {return component;}

	/**The constraints for which a value changed.*/
	private final T constraints;

		/**@return The constraints for which a value changed.*/
		public T getConstraints() {return constraints;}

	/**Source, component, constraint, property name, with old and new value constructor.
	@param source The layout that fired the event.
	@param component The component for which a constraint value changed.
	@param constraints The constraints for which a value changed.
	@param propertyName The programmatic name of the property that was changed.
	@param oldValue The old value of the property, or <code>null</code> if no old value is not available.
	@param newValue The new value of the property, or <code>null</code> if the new value is not available.
	@exception NullPointerException if the given component and/or constraints is <code>null</code>.
	*/
	public LayoutConstraintsPropertyChangeEvent(final Layout<T> source, final Component<?> component, final T constraints, final String propertyName, final V oldValue, V newValue)
	{
		super(source, propertyName, oldValue, newValue);	//construct the parent class
		this.component=checkNull(component, "Component cannot be null.");
		this.constraints=checkNull(constraints, "Constraints cannot be null.");
	}

}