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

package com.guiseframework.component.layout;

/**A layout for a menu that flows along an axis.
@author Garret Wilson
*/
public class MenuLayout extends AbstractFlowLayout<MenuConstraints>	//TODO probably move this into the menu class
{

	/**@return The class representing the type of constraints appropriate for this layout.*/
	public Class<? extends MenuConstraints> getConstraintsClass() {return MenuConstraints.class;}

	/**Flow constructor.
	@param flow The logical axis (line or page) along which information is flowed.
	@exception NullPointerException if the flow axis is <code>null</code>.
	*/
	public MenuLayout(final Flow flow)
	{
		super(flow, false);	//construct the parent class, specifying no wrapping
	}

	/**Creates default constraints for the container.
	@return New default constraints for the container.
	@exception IllegalStateException if this layout does not support default constraints.
	*/
	public MenuConstraints createDefaultConstraints()
	{
		return new MenuConstraints();	//return a default constraints object
	}

}
