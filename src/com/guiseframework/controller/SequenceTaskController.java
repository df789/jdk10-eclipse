/*
 * Copyright © 2000-2008 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

package com.guiseframework.controller;

import static com.globalmentor.java.Objects.*;

import com.globalmentor.model.SequenceTask;
import com.guiseframework.event.*;
import com.guiseframework.prototype.ActionPrototype;
import static com.guiseframework.theme.Theme.*;

/**Abstract base class for managing progression of a sequence.
@author Garret Wilson
*/
public class SequenceTaskController
{

	/**The action prototype for starting the sequence.*/
	private final ActionPrototype startActionPrototype;

		/**@return The action prototype for starting the sequence.*/
		public ActionPrototype getStartActionPrototype() {return startActionPrototype;}
	
	/**The action prototype for going to the previous step.*/
	private final ActionPrototype previousActionPrototype;

		/**@return The action prototype for going to the previous step.*/
		public ActionPrototype getPreviousActionPrototype() {return previousActionPrototype;}

	/**The action prototype for going to the next step.*/
	private final ActionPrototype nextActionPrototype;

		/**@return The action prototype for going to the next step.*/
		public ActionPrototype getNextActionPrototype() {return nextActionPrototype;}

	/**The action prototype for finishing the sequence.*/
	private final ActionPrototype finishActionPrototype;

		/**@return The action prototype for finishing the sequence.*/
		public ActionPrototype getFinishActionPrototype() {return finishActionPrototype;}

	/**The action prototype for confirming an action.*/
	private final ActionPrototype confirmActionPrototype;

		/**@return The action prototype for confirming an action.*/
		public ActionPrototype getConfirmActionPrototype() {return confirmActionPrototype;}

	/**The action for advancing; serves as a proxy for the start, next,
		and finish actions, depending on the state of the sequence.
	*/
//TODO fix	private final ProxyAction advanceAction;

		/**The action for advancing; serves as a proxy for the start, next,
			and finish actions, depending on the state of the sequence.
		@see #getStartActionPrototype()
		@see #getNextActionPrototype()
		@see #getFinishActionPrototype()
		*/
//TODO fix		public ProxyAction getAdvanceAction() {return advanceAction;}

	/**The button for staring the sequence; created from the corresponding action.*/
//TODO fix	private final JButton startButton;

		/**@return The action for starting the sequence; created from the corresponding action.
		@see #getStartAction
		*/
//TODO fix		private JButton getStartButton() {return startButton;}		

	/**The button for going to the previous component; created from the corresponding action.*/
//TODO fix	private final JButton previousButton;

		/**@return The action for going to the previous component; created from the corresponding action.
		@see #getPreviousAction
		*/
//TODO fix		private JButton getPreviousButton() {return previousButton;}
		
	/**The button for going to the next component; created from the corresponding action.*/
//TODO fix	private final JButton nextButton;

		/**@return The action for going to the next component; created from the corresponding action.
		@see #getNextAction
		*/
//TODO fix		private JButton getNextButton() {return nextButton;}
		
	/**The button for finishing the sequence; created from the corresponding action.*/
//TODO fix	private final JButton finishButton;

		/**@return The action for finishing the sequence; created from the corresponding action.
		@see #getFinishAction
		*/
//TODO fix		private JButton getFinishButton() {return finishButton;}		

	/**The button for advancing in the sequence; created from the corresponding action.*/
//TODO fix	private final JButton advanceButton;

		/**@return The action for advancing in sequence; created from the corresponding action.
		@see #getAdvanceAction
		*/
//TODO fix		private JButton getAdvanceButton() {return advanceButton;}		

	/**Whether the advance buttons are distinct or dual-duty.*/
	private boolean distinctAdvance;

		/**@return Whether the advance buttons are distinct or dual-duty;
			this defaults to <code>false</code>.
		*/
		public boolean isDistinctAdvance() {return distinctAdvance;}

		/**Sets whether the advance buttons are distinct or dual-duty.
		@param distinct <code>true</code> if there should be distinct buttons for
			start, next, and finish, or <code>false</code> if one button should share
			these responsibilitiese.
		*/
		public void setDistinctAdvance(final boolean distinct) {distinctAdvance=distinct;}

	/**The length of time, in milliseconds, to wait for confirmation when applicable.*/
	protected final static int CONFIRM_DELAY=5000;

	/**The timer that allows confirmation only within a specified time.*/
//TODO fix	private final Timer confirmTimer;

		/**@return The timer that allows confirmation only within a specified time.*/
//G***del if not needed		protected Timer getConfirmTimer() {return confirmTimer;}

	/**The action prototype currently being confirmed and which, if confirmed, will be performed.*/
	private ActionPrototype confirmingActionProtype;

		/**The action prototype currently being confirmed and which, if confirmed, will be performed.*/
		public ActionPrototype getConfirmingActionPrototype() {return confirmingActionProtype;}
	
		/**Starts the confirmation timer and, if confirmation is received within
		 	the required amount of time, the given action is taken. Alternatively,
		 	if no action is given, the confirmation process is stopped. If the action
		 	is already waiting for confirmation, no action is taken.
		@param newConfirmingActionPrototype The action to perform if confirmation is received,
			or <code>null</code> if no action should be pending confirmation.
		*/
		public void setConfirmingActionPrototype(final ActionPrototype newConfirmingActionPrototype)
		{
			final ActionPrototype oldConfirmingActionPrototype=confirmingActionProtype;	//get the action currently waiting for confirmation
			if(oldConfirmingActionPrototype!=newConfirmingActionPrototype)	//if the pending action is really changing
			{
//TODO fix				confirmTimer.stop();	//stop any confirmations currently pending
				confirmingActionProtype=newConfirmingActionPrototype;	//update the confirming action prototype
				if(newConfirmingActionPrototype!=null)	//if there is a new action waiting to be confirmed
				{
//TODO fix					confirmTimer.restart();	//start the confirmation countdown				
				}
//TODO fix				updateStatus();	//update the status to show whether an action is waiting to be confirmed
			}
		}

	/**Whether each navigation of the sequence must be confirmed.*/
	private boolean confirmNavigation=false;
		
		/**@return <code>true</code> if each navigation should be confirmed.*/
		public boolean isConfirmNavigation() {return confirmNavigation;}
	
		/**Sets whether each navigation must be confirmed.
		@param confirmNavigation <code>true</code> if each navigation must be confirmed.
		*/
		public void setConfirmNavigation(final boolean confirmNavigation) {this.confirmNavigation=confirmNavigation;}

	/**The sequence task being controlled.*/
	private final SequenceTask task;

		/**@return The sequence task being controlled.*/
		public SequenceTask getTask() {return task;}

	/**Sequence task constructor.
	@param task The sequence task being controlled.
	@throws NullPointerException if the given task is <code>null</code>.
	*/
	public SequenceTaskController(final SequenceTask task)
	{
		this.task=checkInstance(task, "Task cannot be null.");
		startActionPrototype=new ActionPrototype(LABEL_START, GLYPH_START);
		startActionPrototype.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent actionEvent)	//if the start action is performed
					{
						if(!isConfirmNavigation() || getConfirmingActionPrototype()==startActionPrototype)	//if this action is waiting to be confirmed
						{
							getTask().goStart(); //start the sequence
							setConfirmingActionPrototype(null);	//show that we're not waiting for confirmation on anything
						}
						else	//if we should confirm this action
						{
							setConfirmingActionPrototype(startActionPrototype);	//perform this action subject to confirmation
						}
					};
				});
		previousActionPrototype=new ActionPrototype(LABEL_PREVIOUS, GLYPH_PREVIOUS);
		previousActionPrototype.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent actionEvent)	//if the previous action is performed
					{
						if(!isConfirmNavigation() || getConfirmingActionPrototype()==previousActionPrototype)	//if this action is waiting to be confirmed
						{
							getTask().goPrevious();  //go to the previous step
							setConfirmingActionPrototype(null);	//show that we're not waiting for confirmation on anything
						}
						else	//if we should confirm this action
						{
							setConfirmingActionPrototype(previousActionPrototype);	//perform this action subject to confirmation
						}
					};
				});
		nextActionPrototype=new ActionPrototype(LABEL_NEXT, GLYPH_NEXT);
		nextActionPrototype.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent actionEvent)	//if the next action is performed
					{
						if(!isConfirmNavigation() || getConfirmingActionPrototype()==nextActionPrototype)	//if this action is waiting to be confirmed
						{
							getTask().goNext();  //go to the next step
							setConfirmingActionPrototype(null);	//show that we're not waiting for confirmation on anything
						}
						else	//if we should confirm this action
						{
							setConfirmingActionPrototype(nextActionPrototype);	//perform this action subject to confirmation
						}
					};
				});
		finishActionPrototype=new ActionPrototype(LABEL_FINISH, GLYPH_FINISH);
		finishActionPrototype.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent actionEvent)	//if the finish action is performed
					{
						if(!isConfirmNavigation() || getConfirmingActionPrototype()==finishActionPrototype)	//if this action is waiting to be confirmed
						{
							getTask().goFinish(); //try to finish the sequence
							setConfirmingActionPrototype(null);	//show that we're not waiting for confirmation on anything
						}
						else	//if we should confirm this action
						{
							setConfirmingActionPrototype(finishActionPrototype);	//perform this action subject to confirmation
						}
					}
				});
		
//TODO fix		advanceAction=new ProxyAction(startActionPrototype);	//the advance action will initially proxy the start action
		confirmActionPrototype=new ActionPrototype(LABEL_CONFIRM, GLYPH_CONFIRM);
		confirmActionPrototype.addActionListener(new ActionListener()
				{
					@Override public void actionPerformed(final ActionEvent actionEvent)
					{
						final ActionPrototype confirmingActionProtype=getConfirmingActionPrototype();	//see if there is an action waiting to be confirmed
						if(confirmingActionProtype!=null)	//if there is an action waiting to be confirmed
						{
//TODO fix							confirmTimer.stop();	//the action is confirmed; suspend waiting for confirmation
							confirmingActionProtype.performAction();	//perform the confirming action
						}
					}
				});
/*TODO fix
		confirmTimer=new Timer(CONFIRM_DELAY, new ActionListener()	//create a new action listener that will remove any confirming action after a delay
				{
					public void actionPerformed(final ActionEvent actionEvent) {setConfirmingActionPrototype(null);}	//if the timer runs out, show that there is no confirmation action
				});
		confirmTimer.setRepeats(false);	//we only have one waiting period for confirmation
*/
		confirmingActionProtype=null;	//there is currently no action being confirmed
		distinctAdvance=false;	//default to shared actions for advancing
	}

	/**Initializes actions in the action manager.
	@param actionManager The implementation that manages actions.
	*/
/*TODO fix
	protected void initializeActions(final ActionManager actionManager)
	{
		super.initializeActions(actionManager);	//do the default initialization
		if(isDistinctAdvance())	//if we should have distinct advance, use separate actions
		{
			actionManager.addToolAction(getStartActionPrototype());
			actionManager.addToolAction(new ActionManager.SeparatorAction());
			actionManager.addToolAction(getPreviousActionPrototype());
			actionManager.addToolAction(getNextActionPrototype());
		}
		else	//if we should not have distinct advance, use a dual-use action
		{
			actionManager.addToolAction(getPreviousActionPrototype());
			actionManager.addToolAction(getAdvanceAction());
		} 
		actionManager.addToolAction(new ActionManager.SeparatorAction());
		actionManager.addToolAction(getConfirmActionPrototype());
	}
*/

	/**Initializes the user interface.*/
/*TODO fix
	protected void initializeUI()
	{
		if(getToolBar()!=null)	//if we have a toolbar
			getToolBar().setButtonTextVisible(true);	//show text on the toolbar buttons
		super.initializeUI();	//do the default initialization
		previousButton.setHorizontalTextPosition(SwingConstants.LEADING);	//change the text position of the previous button
		setContentComponent(getDefaultComponent());	//start with the default component		
		setPreferredSize(new Dimension(300, 200));	//set an arbitrary preferred size
	}
*/

	/**Updates the states of the prototypes, including enabled/disabled status, proxied actions, etc.
	*/
/*TODO fix; decide in which class to place
	public void updateStates()
	{
		super.updateStatus(); //update the default actions
		getStartActionPrototype().setEnabled(getAdvanceAction().getProxiedAction()!=getStartActionPrototype()); //only allow starting if we haven't started, yet
		getPreviousActionPrototype().setEnabled(hasPrevious()); //only allow going backwards if we have a previous step
		getNextActionPrototype().setEnabled(hasNext()); //only allow going backwards if we have a next step
		getFinishActionPrototype().setEnabled(!hasNext()); //only allow finishing if there are no next components
		getConfirmActionPrototype().setEnabled(isConfirmNavigation() && getConfirmingActionPrototype()!=null); //only allow confirmation if confirmation is enabled and there is an action waiting to be confirmed
		if(getToolBar()!=null)	//if we have a toolbar
		{
			final Component confirmComponent=getToolBar().getComponent(getConfirmActionPrototype());	//see if the confirm action is on the toolbar
			if(confirmComponent!=null)	//if the action has a corresponding component on the toolbar
			{
				confirmComponent.setVisible(isConfirmNavigation());	//only show the confirm action if navigation confirmation is enabled
			}
		}
		if(getAdvanceAction().getProxiedAction()!=getStartActionPrototype())	//if we've already started
		{
				//determine if advancing should go to the next item in the sequence or finish
			getAdvanceAction().setProxiedAction(hasNext() ? getNextActionPrototype() : getFinishActionPrototype());			
		}
		final JRootPane rootPane=getRootPane();	//get the ancestor root pane, if there is one
		if(rootPane!=null)	//if there is a root pane
		{
			final JButton defaultButton;	//determind the default button
			if(isDistinctAdvance())	//if we're using distinct buttons for advance
			{
				defaultButton=hasNext() ? getNextButton() : getFinishButton();	//set the next button as the default unless we're finished; in that case, set the finish button as the default
			}
			else	//if we're using a dual-use button for advance
			{					
				defaultButton=getAdvanceButton();	//set the advance button as the default	
			}
			rootPane.setDefaultButton(defaultButton);	//update the default button	
		}
		final Action confirmingAction=getConfirmingActionPrototype();	//see if there is an action waiting to be confirmed
		if(confirmingAction!=null)	//if there is an action waiting to be confirmed
		{
			confirmingAction.setEnabled(false);	//disable the confirming action, because it will be accessed indirectly through the confirmation action
		}
	}
*/

}
