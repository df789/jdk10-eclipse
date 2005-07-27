package com.javaguise.demo;

import com.javaguise.component.*;
import com.javaguise.component.layout.FlowLayout;
import com.javaguise.component.layout.Orientation;
import com.javaguise.event.ActionEvent;
import com.javaguise.event.ActionListener;
import com.javaguise.model.ActionModel;
import com.javaguise.session.GuiseSession;

/**Restricted Guise demonstration frame.
Copyright � 2005 GlobalMentor, Inc.
Demonstrates restricted access to navigation paths and user logout.
@author Garret Wilson
*/
public class RestrictedFrame extends DefaultFrame
{

	/**Guise session constructor.
	@param session The Guise session that owns this frame.
	*/
	public RestrictedFrame(final GuiseSession<?> session)
	{
		super(session);	//construct the parent class, defaulting to a region layout
		getModel().setLabel("Guise\u2122 Demonstration: Restricted");	//set the frame title
		
		final Panel restrictionPanel=new Panel(session, new FlowLayout(Orientation.Flow.PAGE));	//create the authorization panel flowing vertically
		
			//heading
		final Heading heading=new Heading(session, 0);	//create a top-level heading
		heading.getModel().setLabel("Access Granted.");	//set the text of the heading, using its model
		restrictionPanel.add(heading);	//add the heading to the panel
		
			//conversion button
		final Button logoutButton=new Button(session);	//create a button for logging out
		logoutButton.getModel().setLabel("Log out");	//set the button label
		logoutButton.getModel().addActionListener(new ActionListener<ActionModel>()	//when the logout button is pressed
				{
					public void actionPerformed(ActionEvent<ActionModel> actionEvent)	//set the session's user to null
					{
						session.setPrincipal(null);	//log out the user
					}
				});
		restrictionPanel.add(logoutButton);	//add the button to the panel
		

		add(restrictionPanel);	//add the panel to the frame in the default center
	}

}
