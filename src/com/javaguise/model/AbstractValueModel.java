package com.javaguise.model;

import com.javaguise.session.GuiseSession;
import com.javaguise.validator.*;

import static com.garretwilson.lang.ObjectUtilities.*;

/**An abstract implementation of a model representing a value.
A derived class need only implement the value access methods.
@param <V> The type of value contained in the model.
@author Garret Wilson
*/
public abstract class AbstractValueModel<V> extends AbstractControlModel implements ValueModel<V>
{

	/**Whether the model's value is editable and the corresponding control will allow the the user to change the value.*/
	private boolean editable=true;

		/**@return Whether the model's value is editable and the corresponding control will allow the the user to change the value.*/
		public boolean isEditable() {return editable;}

		/**Sets whether the model's value is editable and the corresponding control will allow the the user to change the value.
		This is a bound property of type <code>Boolean</code>.
		@param newEditable <code>true</code> if the corresponding control should allow the user to change the value.
		@see ValueModel#EDITABLE_PROPERTY
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

	/**The validator for this model, or <code>null</code> if no validator is installed.*/
	private Validator<V> validator;

		/**@return The validator for this model, or <code>null</code> if no validator is installed.*/
		public Validator<V> getValidator() {return validator;}

		/**Sets the validator.
		This is a bound property
		@param newValidator The validator for this model, or <code>null</code> if no validator should be used.
		@see ValueModel#VALIDATOR_PROPERTY
		*/
		public void setValidator(final Validator<V> newValidator)
		{
			if(validator!=newValidator)	//if the value is really changing
			{
				final Validator<V> oldValidator=validator;	//get the old value
				validator=newValidator;	//actually change the value
				firePropertyChange(VALIDATOR_PROPERTY, oldValidator, newValidator);	//indicate that the value changed
			}
		}

	/**The class representing the type of value this model can hold.*/
	private final Class<V> valueClass;

		/**@return The class representing the type of value this model can hold.*/
		public Class<V> getValueClass() {return valueClass;}

	/**Constructs a value model indicating the type of value it can hold.
	@param session The Guise session that owns this model.
	@param valueClass The class indicating the type of value held in the model.
	@exception NullPointerException if the given session and/or class object is <code>null</code>.
	*/
	public AbstractValueModel(final GuiseSession session, final Class<V> valueClass)
	{
		super(session);	//construct the parent class
		this.valueClass=checkNull(valueClass, "Value class cannot be null.");	//store the value class
	}

	/**Determines whether the contents of this model are valid.
	This version delegates to the validator, if one is installed.
	@return Whether the contents of this model are valid.
	@see #getValidator()
	@see #getValue()
	*/
	public boolean isValid()
	{
		if(super.isValid())	//if the super class thinks we're valid
		{
			final Validator<V> validator=getValidator();	//get the current validator
			return validator!=null ? validator.isValid(getValue()) : true;	//if we have a validator, make sure it thinks the value is valid
		}
		else	//if the super class doesn't think we're valid
		{
			return false;	//indicate that we're not valid
		}
	}

	/**Validates the contents of this model, throwing an exception if the model is not valid.
	This version validates the current value, if there is a validator installed.
	@exception ValidationException if the contents of this model are not valid.	
	*/
	public void validate() throws ValidationException
	{
		super.validate();	//do the default validation
		final Validator<V> validator=getValidator();	//get the currently installed validator, if there is one
		if(validator!=null)	//if a validator is installed
		{
			validator.validate(getValue());	//validate the current value, throwing an exception if anything is wrong
		}
	}

}
