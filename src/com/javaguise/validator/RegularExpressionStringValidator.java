package com.javaguise.validator;

import java.util.regex.Pattern;
import com.javaguise.session.GuiseSession;

/**A string validator that can validate against regular expressions.
@author Garret Wilson
*/
public class RegularExpressionStringValidator extends AbstractRegularExpressionValidator<String>
{

	/**Constructs a string regular expression validator from a regular expression string, without requiring a non-<code>null</code> value..
	@param session The Guise session that owns this validator.
	@param regularExpression The regular expression against which to validate string values.
	@exception NullPointerException if the given session and/or regular expression is <code>null</code>.
	*/
	public RegularExpressionStringValidator(final GuiseSession<?> session, final String regularExpression)
	{
		super(session, regularExpression);	//construct the parent class
	}

	/**Constructs a string regular expression validator from a regular expression string.
	@param session The Guise session that owns this validator.
	@param regularExpression The regular expression against which to validate string values.
	@param valueRequired Whether the value must be non-<code>null</code> in order to be considered valid.
	@exception NullPointerException if the given session and/or regular expression is <code>null</code>.
	*/
	public RegularExpressionStringValidator(final GuiseSession<?> session, final String regularExpression, final boolean valueRequired)
	{
		super(session, regularExpression, valueRequired);	//construct the parent class
	}

	/**Constructs a string regular expression validator from a regular expression pattern, without requiring a non-<code>null</code> value.
	@param session The Guise session that owns this validator.
	@param pattern The regular expression pattern against which to validate string values.
	@exception NullPointerException if the given session and/or regular expression is <code>null</code>.
	*/
	public RegularExpressionStringValidator(final GuiseSession<?> session, final Pattern pattern)
	{
		super(session, pattern);	//construct the parent class
	}

	/**Constructs a string regular expression validator from a regular expression pattern.
	@param session The Guise session that owns this validator.
	@param pattern The regular expression pattern against which to validate string values.
	@param valueRequired Whether the value must be non-<code>null</code> in order to be considered valid.
	@exception NullPointerException if the given session and/or regular expression is <code>null</code>.
	*/
	public RegularExpressionStringValidator(final GuiseSession<?> session, final Pattern pattern, final boolean valueRequired)
	{
		super(session, pattern, valueRequired);	//construct the parent class
	}
	/**Returns a string representation of the given value so that it may be validated against the regular expression.
	This version returns the value itself.
	@param value The value being validated.
	@return A string representation of the given value, or <code>null</code> if the value is <code>null</code>.
	*/
	protected String toString(final String value)
	{
		return value;	//return the value, which is already a string
	}
}
