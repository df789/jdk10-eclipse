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

import com.guiseframework.component.layout.*;
import com.guiseframework.event.MouseEvent;
import com.guiseframework.event.MouseListener;
import com.guiseframework.geometry.Point;
import com.guiseframework.geometry.Rectangle;
import com.guiseframework.model.ValueModel;

/**A spinner control.
@param <V> The type of value the spinner represents.
@author Garret Wilson
*/
public abstract class SpinnerControl<V> extends AbstractContainer implements ValueControl<V>	//TODO finish  
{

	/**@return The layout definition for the component.*/
	public FlowLayout getLayout() {return (FlowLayout)super.getLayout();}

	/**Layout and value model constructor.
	@param layout The layout definition for the container.
	@param valueModel The component value model.
	@throws NullPointerException if the given layout and/or value model is <code>null</code>.
	*/
	public SpinnerControl(final FlowLayout layout, final ValueModel<V> valueModel)
	{
		super(layout/*TODO fix, model*/);	//construct the parent class
	}

//TODO fix to use one of new abstract composite component classes; make sure determineValid() is implemented correctly and that updateValid() is called at the appropriate time
}
