package com.javaguise.platform.web;

import java.util.*;

import static com.garretwilson.text.FormatUtilities.*;
import com.garretwilson.util.*;

import com.javaguise.controller.*;

/**A control event indicating that a full or partial form submission occurred.
@author Garret Wilson
*/
public class FormEvent implements ControlEvent
{

	/**Whether this event represents all components on the form.*/
	private final boolean exhaustive;

		/**@return Whether this event represents all components on the form.*/
		public final boolean isExhaustive() {return exhaustive;}

	/**The map of parameter lists.*/
	private final ListMap<String, Object> parameterListMap=new ArrayListHashMap<String, Object>();

		/**@return The map of parameter lists.*/
		public ListMap<String, Object> getParameterListMap() {return parameterListMap;}

	/**Constructor that indicates whether the event is exhaustive.
	@param exhaustive Whether this event represents all components on the form.
	*/
	public FormEvent(final boolean exhaustive)
	{
		this.exhaustive=exhaustive;
	}
		
	/**@return A string representation of this event.*/
	public String toString()
	{
		final StringBuilder stringBuilder=new StringBuilder();	//create a string builder for constructing a string
		if(isExhaustive())	//if the event is exhaustive
		{
			stringBuilder.append("(exhaustive) ");
		}
		final ListMap<String, Object> parameterListMap=getParameterListMap();	//get the request parameter map
		for(final Map.Entry<String, List<Object>> parameterListMapEntry:parameterListMap.entrySet())	//for each entry in the map of parameter lists
		{
			stringBuilder.append("Key: ").append(parameterListMapEntry.getKey()).append(" Value: {");	//Key: key Value: {
			formatList(stringBuilder, ',', parameterListMapEntry.getValue());	//values
			stringBuilder.append('}');	//}
		}
		return stringBuilder.toString();	//return the string we constructed
	}
}