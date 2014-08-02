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

import static com.globalmentor.java.Objects.*;

import com.guiseframework.input.Command;
import com.guiseframework.input.CommandInput;

/**A focused event providing information on an input user command.
@author Garret Wilson
*/
public class CommandEvent extends AbstractFocusedInputEvent
{

	/**The command.*/
	private final Command command;

		/**The command.*/
		public Command getCommand() {return command;}

	/**Command constructor.
	@param source The object on which the event initially occurred.
	@param command The command.
	@throws NullPointerException if the given source and/or command is <code>null</code>.
	*/
	public CommandEvent(final Object source, final Command command)
	{
		super(source);	//construct the parent class
		this.command=checkInstance(command, "Command cannot be null.");
	}

	/**Command input constructor.
	@param source The object on which the event initially occurred.
	@param commandInput The command input the properties of which will be copied.
	@throws NullPointerException if the given source and/or input is <code>null</code>.
	*/
	public CommandEvent(final Object source, final CommandInput commandInput)
	{
		this(source, commandInput.getCommand());	//construct the new event with the specified source
	}

	/**Copy constructor that specifies a different source.
	@param source The object on which the event initially occurred.
	@param commandEvent The event the properties of which will be copied.
	@throws NullPointerException if the given source and/or event is <code>null</code>.
	*/
	public CommandEvent(final Object source, final CommandEvent commandEvent)
	{
		this(source, commandEvent.getCommand());	//construct the new event with the specified source
	}

	/**@return The command input associated with this event.*/
	public CommandInput getInput()
	{
		return new CommandInput(getCommand());	//return new command input
	}

}
