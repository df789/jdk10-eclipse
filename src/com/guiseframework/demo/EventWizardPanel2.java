package com.guiseframework.demo;

import com.globalmentor.beans.AbstractGenericPropertyChangeListener;
import com.globalmentor.beans.GenericPropertyChangeEvent;
import com.guiseframework.component.*;

/**Event wizard Guise demonstration panel using URF PLOOP.
Copyright © 2006 GlobalMentor, Inc.
Demonstrates sequence card panels, task card constraints, task status select links,
	action card couplers, link select card couplers, and action prototypes.
@author Garret Wilson
*/
public class EventWizardPanel2 extends LayoutPanel
{

	/**Initializes the component after construction.*/
	public void initialize()
	{
		final SequenceCardPanel wizardCardPanel=(SequenceCardPanel)getComponentByName(this, "wizardCardPanel");
		final Panel personalAgePanel=(Panel)getComponentByName(this, "personalAgePanel");
		final CheckControl ageCheckControl=(CheckControl)getComponentByName(this, "ageCheckControl");
		ageCheckControl.addPropertyChangeListener(CheckControl.VALUE_PROPERTY, new AbstractGenericPropertyChangeListener<Boolean>()
				{
					public void propertyChange(GenericPropertyChangeEvent<Boolean> propertyChangeEvent)	//if the age checkbox changes
					{
						wizardCardPanel.setDisplayed(personalAgePanel, propertyChangeEvent.getNewValue());	//show or hide the age panel based upon the state of the age checkbox
					}			
				});
		final LayoutPanel wizardButtonPanel=(LayoutPanel)getComponentByName(this, "wizardButtonPanel");
		final Button previousButton=new Button(wizardCardPanel.getPreviousActionPrototype());
		wizardButtonPanel.add(previousButton);
		final Button nextButton=new Button(wizardCardPanel.getNextActionPrototype());
		wizardButtonPanel.add(nextButton);
		final Button finishButton=new Button(wizardCardPanel.getFinishActionPrototype());
		wizardButtonPanel.add(finishButton);
		wizardCardPanel.resetSequence();
	}

}
