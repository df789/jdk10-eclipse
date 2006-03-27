package com.guiseframework.component.layout;

import com.guiseframework.GuiseSession;
import com.guiseframework.model.*;

/**Constraints on a component in a container control.
Each component can be specified as bing displayed and/or enabled.
@author Garret Wilson
*/
public class ControlConstraints extends AbstractConstraints implements Displayable, Enableable
{

	/**Whether the component is displayed or has no representation, taking up no space.*/
	private boolean displayed=true;

		/**@return Whether the component is displayed or has no representation, taking up no space.*/
		public boolean isDisplayed() {return displayed;}

		/**Sets whether the component is displayed or has no representation, taking up no space.
		This is a bound property of type <code>Boolean</code>.
		@param newDisplayed <code>true</code> if the component should be displayed, else <code>false</code> if the component should take up no space.
		@see #DISPLAYED_PROPERTY
		*/
		public void setDisplayed(final boolean newDisplayed)
		{
			if(displayed!=newDisplayed)	//if the value is really changing
			{
				final boolean oldDisplayed=displayed;	//get the current value
				displayed=newDisplayed;	//update the value
				firePropertyChange(DISPLAYED_PROPERTY, Boolean.valueOf(oldDisplayed), Boolean.valueOf(newDisplayed));
			}
		}

	/**Whether the component is enabled.*/
	private boolean enabled=true;

		/**@return Whether the component is enabled.*/
		public boolean isEnabled() {return enabled;}

		/**Sets whether the the component is enabled.
		This is a bound property of type <code>Boolean</code>.
		@param newEnabled <code>true</code> if the corresponding component is enabled.
		@see #ENABLED_PROPERTY
		*/
		public void setEnabled(final boolean newEnabled)
		{
			if(enabled!=newEnabled)	//if the value is really changing
			{
				final boolean oldEnabled=enabled;	//get the old value
				enabled=newEnabled;	//actually change the value
				firePropertyChange(ENABLED_PROPERTY, Boolean.valueOf(oldEnabled), Boolean.valueOf(newEnabled));	//indicate that the value changed
			}			
		}

	/**Session constructor.
	@param session The Guise session that owns this model.
	@exception NullPointerException if the given session is <code>null</code>.
	*/
	public ControlConstraints(final GuiseSession session)
	{
		this(session, true);	//construct the class with no label
	}

	/**Session and enabled constructor.
	@param session The Guise session that owns this model.
	@param enabled Whether the component is enabled.
	@exception NullPointerException if the given session is <code>null</code>.
	*/
	public ControlConstraints(final GuiseSession session, final boolean enabled)
	{
		super(session);	//construct the parent class 
		this.enabled=enabled;	//save the enabled state
	}

}