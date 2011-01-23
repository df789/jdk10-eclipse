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

package com.guiseframework.event;

/**An abstract event providing information on input such as a keystroke or a command.
@author Garret Wilson
*/
public abstract class AbstractInputEvent extends AbstractGuiseEvent implements InputEvent
{

	/**Whether the input associated with this event has been consumed.*/
	private boolean consumed=false;

		/**@return Whether the input associated with this event has been consumed.*/
		public boolean isConsumed() {return consumed;}

		/**Consumes the input associated with this event.
		The event is marked as consumed so that other listeners will be on notice not to consume the input.
		*/
		public void consume() {consumed=true;}

	/**Source constructor.
	@param source The object on which the event initially occurred.
	@exception NullPointerException if the given source is <code>null</code>.
	*/
	public AbstractInputEvent(final Object source)
	{
		super(source);	//construct the parent class
	}

}