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

import com.guiseframework.model.*;

/**Abstract implementation of a button.
@author Garret Wilson
*/
public abstract class AbstractButtonControl extends AbstractActionControl implements ButtonControl
{

	/**Label model, action model, and enableable object constructor.
	@param labelModel The component label model.
	@param actionModel The component action model.
	@param enableable The enableable object in which to store enabled status.
	@exception NullPointerException if the given label model, action model, and/or enableable object is <code>null</code>.
	*/
	public AbstractButtonControl(final LabelModel labelModel, final ActionModel actionModel, final Enableable enableable)
	{
		super(labelModel, actionModel, enableable);	//construct the parent class
	}
}
