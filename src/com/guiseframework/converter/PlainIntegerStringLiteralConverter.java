package com.guiseframework.converter;

import static java.text.MessageFormat.*;

/**A converter that converts an {@link Integer} from and to a string literal with no delimiters.
@author Garret Wilson
@see Integer
*/
public class PlainIntegerStringLiteralConverter extends AbstractStringLiteralConverter<Integer>
{

	/**Converts a literal representation of a value from the lexical space into a value in the value space.
	@param literal The literal value in the lexical space to convert.
	@return The converted value in the value space, or <code>null</code> if the given literal is <code>null</code>.
	@exception ConversionException if the literal value cannot be converted.
	*/ 
	public Integer convertLiteral(final String literal) throws ConversionException
	{
		try
		{
			return literal!=null && literal.length()>0 ? Integer.valueOf(Integer.parseInt(literal)) : null;	//if there is a literal, convert it to an Integer			
		}
		catch(final NumberFormatException numberFormatException)	//if the string does not contain a valid Integer
		{
			throw new ConversionException(format(getSession().dereferenceString(getInvalidValueMessage()), literal), literal);
		}
	}
}