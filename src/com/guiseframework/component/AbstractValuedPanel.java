/*
 * Copyright © 2005-2008 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guiseframework.component;

import static com.globalmentor.java.Objects.*;

import com.guiseframework.component.layout.*;

/**An abstract panel that represents a value.
@param <V> The type of value displayed within the component.
@author Garret Wilson
*/
public abstract class AbstractValuedPanel<V> extends AbstractPanel implements ValuedComponent<V>
{

	/**The class representing the type of value displayed within the component.*/
	private final Class<V> valueClass;

		/**@return The class representing the type of value displayed within the component.*/
		public Class<V> getValueClass() {return valueClass;}

	/**Value class and layout constructor.
	@param valueClass The class indicating the type of value displayed within the component.
	@param layout The layout definition for the container.
	@exception NullPointerException if the given value class and/or layout is <code>null</code>.
	*/
	public AbstractValuedPanel(final Class<V> valueClass, final Layout<? extends Constraints> layout)
	{
		super(layout);	//construct the parent class
		this.valueClass=checkInstance(valueClass, "Value class cannot be null.");	//store the value class
	}

}
