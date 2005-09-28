package com.javaguise.event;

import com.javaguise.component.Container;

/**An object that listens for container modification events.
@author Garret Wilson
*/
public interface ContainerListener extends GuiseEventListener<Container<?>>
{

	/**Called when a container is modified.
	@param containerEvent The event indicating the source of the event and the container modifications.
	*/
	public void containerModified(final ContainerEvent containerEvent);

}
