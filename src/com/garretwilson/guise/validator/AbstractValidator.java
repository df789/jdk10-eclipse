package com.garretwilson.guise.validator;

/**An abstract implementation of an object that can determine whether a value is valid.
@author Garret Wilson
*/
public abstract class AbstractValidator<V> implements Validator<V>
{
	/**Checks whether a given value is valid, and throws an exception if not
	@param value The value to validate.
	@return <code>true</code> if the value is valid, else <code>false</code>.
	@exception ValidationException if the provided value is not valid.
	*/
	public void validate(final V value) throws ValidationException
	{
		if(!isValid(value))	//if the given value is not valid
		{
			throw new ValidationException("Invalid value: "+value, value);	//TODO i18n
		}
	}

}
