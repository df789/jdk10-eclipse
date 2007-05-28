package com.guiseframework.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.net.URI;
import java.util.*;
import static java.util.Collections.*;
import java.util.concurrent.*;

import javax.naming.OperationNotSupportedException;

import static com.garretwilson.lang.ObjectUtilities.*;
import com.garretwilson.beans.GenericPropertyChangeListener;
import com.garretwilson.beans.TargetedEvent;

import static com.garretwilson.util.CollectionUtilities.*;

import com.garretwilson.lang.ObjectUtilities;
import com.garretwilson.util.Debug;

import static com.guiseframework.GuiseResourceConstants.*;
import static com.guiseframework.Resources.*;
import com.guiseframework.component.effect.Effect;
import com.guiseframework.event.*;
import com.guiseframework.model.LabelModel;
import com.guiseframework.model.Notification;
import com.guiseframework.prototype.*;

/**Abstract implementation of a frame.
This implementation notifies the user when the frame does not validate in {@link #validate()}.
@author Garret Wilson
*/
public abstract class AbstractFrame<C extends Frame<C>> extends AbstractEnumCompositeComponent<AbstractFrame.FrameComponent, C> implements Frame<C>
{

	/**The resource URI for the close image.*/
	public final static URI CLOSE_ICON_RESOURCE_URI=createURIResourceReference("theme.frame.close.icon");	//TODO probably move this to Theme

	/**The enumeration of frame components.*/
	private enum FrameComponent{CONTENT_COMPONENT, MENU_COMPONENT, CLOSE_ACTION_CONTROL};

	/**The state of the frame.*/
	private State state=State.CLOSED;

		/**@return The state of the frame.*/
		public State getState() {return state;}

		/**Sets the state of the frame.
		This is a bound property.
		@param newState The new state of the frame.
		@exception NullPointerException if the given state is <code>null</code>.
		@see Frame#STATE_PROPERTY
		*/
		protected void setState(final State newState)
		{
			if(state!=checkInstance(newState, "State cannot be null."))	//if the value is really changing
			{
				final State oldState=state;	//get the old value
				state=newState;	//actually change the value
				firePropertyChange(STATE_PROPERTY, oldState, newState);	//indicate that the value changed
				setMode(isModal() && newState!=State.CLOSED ? Mode.EXCLUSIVE : null);	//set exclusive modal mode if we are open and modal
			}			
		}

	/**Whether the frame is modal if and when it is open.*/
	private boolean modal=false;

		/**@return Whether the frame is modal if and when it is open.*/
		public boolean isModal() {return modal;}

		/**Sets whether the frame is modal if and when it is open.
		This is a bound property of type <code>Boolean</code>.
		@param newModal <code>true</code> if the frame should be modal, else <code>false</code>.
		@see Frame#MODAL_PROPERTY
		*/
		public void setModal(final boolean newModal)
		{
			if(modal!=newModal)	//if the value is really changing
			{
				final boolean oldModal=modal;	//get the current value
				modal=newModal;	//update the value
				firePropertyChange(MODAL_PROPERTY, Boolean.valueOf(oldModal), Boolean.valueOf(newModal));
				setMode(newModal && getState()!=State.CLOSED ? Mode.EXCLUSIVE : null);	//set exclusive modal mode if we are open and modal
			}
		}

	/**The current mode of interaction, or <code>null</code> if the component is in a modeless state.*/
	private Mode mode=null;

		/**@return The current mode of interaction, or <code>null</code> if the component is in a modeless state.*/
		public Mode getMode() {return mode;}

		/**Sets the mode of interaction.
		This is a bound property.
		@param newMode The new mode of component interaction.
		@see ModalComponent#MODE_PROPERTY 
		*/
		public void setMode(final Mode newMode)
		{
			if(mode!=newMode)	//if the value is really changing
			{
				final Mode oldMode=mode;	//get the old value
				mode=newMode;	//actually change the value
				firePropertyChange(MODE_PROPERTY, oldMode, newMode);	//indicate that the value changed
			}			
		}

	/**Whether the frame is movable.*/
	private boolean movable=true;

		/**@return Whether the frame is movable.*/
		public boolean isMovable() {return movable;}

		/**Sets whether the frame is movable.
		This is a bound property of type <code>Boolean</code>.
		@param newMovable <code>true</code> if the frame should be movable, else <code>false</code>.
		@see Frame#MOVABLE_PROPERTY
		*/
		public void setMovable(final boolean newMovable)
		{
			if(movable!=newMovable)	//if the value is really changing
			{
				final boolean oldMovable=movable;	//get the current value
				movable=newMovable;	//update the value
				firePropertyChange(MOVABLE_PROPERTY, Boolean.valueOf(oldMovable), Boolean.valueOf(newMovable));
			}
		}

	/**Whether the frame can be resized.*/
	private boolean resizable=true;

		/**@return Whether the frame can be resized.*/
		public boolean isResizable() {return resizable;}

		/**Sets whether the frame can be resized.
		This is a bound property of type <code>Boolean</code>.
		@param newResizable <code>true</code> if the frame can be resized, else <code>false</code>.
		@see Frame#RESIZABLE_PROPERTY
		*/
		public void setResizable(final boolean newResizable)
		{
			if(resizable!=newResizable)	//if the value is really changing
			{
				final boolean oldResizable=resizable;	//get the current value
				resizable=newResizable;	//update the value
				firePropertyChange(MOVABLE_PROPERTY, Boolean.valueOf(oldResizable), Boolean.valueOf(newResizable));
			}
		}

	/**The related component such as a popup source, or <code>null</code> if the frame is not related to another component.*/
	private Component<?> relatedComponent=null;

		/**@return The related component such as a popup source, or <code>null</code> if the frame is not related to another component.*/
		public Component<?> getRelatedComponent() {return relatedComponent;}

		/**Sets the related component.
		This is a bound property.
		@param newRelatedComponent The new related component, or <code>null</code> if the frame is not related to another component.
		@see Frame#RELATED_COMPONENT_PROPERTY 
		*/
		public void setRelatedComponent(final Component<?> newRelatedComponent)
		{
			if(relatedComponent!=newRelatedComponent)	//if the value is really changing
			{
				final Component<?> oldRelatedComponent=relatedComponent;	//get the old value
				relatedComponent=newRelatedComponent;	//actually change the value
				firePropertyChange(RELATED_COMPONENT_PROPERTY, oldRelatedComponent, newRelatedComponent);	//indicate that the value changed
			}			
		}

	/**Whether the title bar is visible.*/
	private boolean titleVisible=true;

		/**@return Whether the title bar is visible.*/
		public boolean isTitleVisible() {return titleVisible;}

		/**Sets whether the title bar is visible.
		This is a bound property of type <code>Boolean</code>.
		@param newTitleVisible <code>true</code> if the title bar should be visible, else <code>false</code>.
		@see Frame#TITLE_VISIBLE_PROPERTY
		*/
		public void setTitleVisible(final boolean newTitleVisible)
		{
			if(titleVisible!=newTitleVisible)	//if the value is really changing
			{
				final boolean oldTitleVisible=titleVisible;	//get the current value
				titleVisible=newTitleVisible;	//update the value
				firePropertyChange(TITLE_VISIBLE_PROPERTY, Boolean.valueOf(oldTitleVisible), Boolean.valueOf(newTitleVisible));
			}
		}

	/**The effect used for opening the frame, or <code>null</code> if there is no open effect.*/
	private Effect openEffect=null;

		/**@return The effect used for opening the frame, or <code>null</code> if there is no open effect.*/
		public Effect getOpenEffect() {return openEffect;}

		/**Sets the effect used for opening the frame.
		This is a bound property.
		@param newEffect The new effect used for opening the frame, or <code>null</code> if there should be no open effect.
		@see Frame#OPEN_EFFECT_PROPERTY 
		*/
		public void setOpenEffect(final Effect newOpenEffect)
		{
			if(openEffect!=newOpenEffect)	//if the value is really changing
			{
				final Effect oldOpenEffect=openEffect;	//get the old value
				openEffect=newOpenEffect;	//actually change the value
				firePropertyChange(OPEN_EFFECT_PROPERTY, oldOpenEffect, newOpenEffect);	//indicate that the value changed
			}			
		}

	/**@return The content child component, or <code>null</code> if this frame does not have a content child component.
	@see FrameComponent#CONTENT_COMPONENT
	*/
	public Component<?> getContent() {return getComponent(FrameComponent.CONTENT_COMPONENT);}

	/**Sets the content child component.
	This is a bound property.
	@param newContent The content child component, or <code>null</code> if this frame does not have a content child component.
	@see FrameComponent#CONTENT_COMPONENT
	@see ContentComponent#CONTENT_PROPERTY
	*/
	public void setContent(final Component<?> newContent)
	{
		final Component<?> oldContent=setComponent(FrameComponent.CONTENT_COMPONENT, newContent);	//set the component
		if(oldContent!=newContent)	//if the component really changed
		{
			firePropertyChange(CONTENT_PROPERTY, oldContent, newContent);	//indicate that the value changed
			consumePrototypes();	//merge the produced prototypes of the new content
		}
	}

	/**@return The frame menu, or <code>null</code> if this frame does not have a menu.
	@see FrameComponent#MENU_COMPONENT
	*/
	public Menu<?> getMenu() {return (Menu<?>)getComponent(FrameComponent.MENU_COMPONENT);}

	/**Sets the frame menu.
	This is a bound property.
	@param newMenu The frame menu, or <code>null</code> if this frame does not have a menu.
	@see FrameComponent#MENU_COMPONENT
	@see Frame#MENU_PROPERTY
	*/
	public void setMenu(final Menu<?> newMenu)
	{
		final Menu<?> oldMenu=(Menu<?>)setComponent(FrameComponent.MENU_COMPONENT, newMenu);	//set the component
		if(oldMenu!=newMenu)	//if the component really changed
		{
			firePropertyChange(MENU_PROPERTY, oldMenu, newMenu);	//indicate that the value changed
		}
	}

	/**The set of components that have been used to represent processed prototypes.*/
	private Set<Component<?>> prototypeComponents=new CopyOnWriteArraySet<Component<?>>();
	
	/**Consumes and processes the produced prototypes of this frame and the current content, if either or both implement {@link PrototypeProducer}.
	The produced prototypes will be integrated into the current menu and toolbar, if present.
	@see #getMenu()
	*/
	public void consumePrototypes()	//TODO change to combine produced prototypes in producePrototypeInfos()
	{
			//TODO improve to only remove the components that were created for prototypes that no longer exist
		for(final Component<?> component:prototypeComponents)	//for each prototype component
		{
			final CompositeComponent<?> parent=component.getParent();	//get this component's parent
			if(parent instanceof Container)	//if the component is still installed into a container
			{
				((Container<?>)parent).remove(component);	//remove the component from its parent
			}
		}
		final Set<PrototypeInfo> prototypeInfos=new TreeSet<PrototypeInfo>();	//create a sorted set of produced prototypes
		if(this instanceof PrototypeProducer)	//if this frame produces prototypes
		{
			addAll(prototypeInfos, ((PrototypeProducer)this).producePrototypes());	//collect produced prototypes from the frame
		}
		final Component<?> content=getContent();	//get the current content
		if(content instanceof PrototypeProducer)	//if the content produces prototypes
		{
			addAll(prototypeInfos, ((PrototypeProducer)content).producePrototypes());	//collect produced prototypes from the content
		}
		final Map<Prototype, Component<?>> menuPrototypeComponentMap=new HashMap<Prototype, Component<?>>();	//keep track of which menu components we create for which prototypes
		final Menu<?> menu=getMenu();	//get the current menu
		for(final PrototypeInfo prototypeInfo:prototypeInfos)	//for each produced prototype info
		{
			final Prototype prototype=prototypeInfo.getPrototype();	//get the prototype to add
			if(prototypeInfo.isMenu() && menu!=null)	//if this is a menu prototype and we have a menu
			{
				final Prototype parentPrototype=prototypeInfo.getParentPrototype();	//get the prototype's parent, if any
				final Component<?> parentComponent=parentPrototype!=null ? menuPrototypeComponentMap.get(parentPrototype) : menu;	//if there is a parent prototype, use the component that was created for the parent prototype
				if(parentComponent instanceof Container)	//if there is a parent component that is a container
				{
					final Component<?> component=((Container<?>)parentComponent).add(prototype);	//add this prototype to the parent component
					prototypeComponents.add(component);	//note that we created this component
					menuPrototypeComponentMap.put(prototype, component);	//record temporarily the component we used to represent this prototype
				}
			}
		}
	}	
	
	/**The action listener for closing the frame.*/
	private final ActionListener closeActionListener;
		
	/**@return The action control for closing the frame, or <code>null</code> if this frame does not have a close action control.
	@see FrameComponent#CLOSE_ACTION_CONTROL
	*/
	public ActionControl<?> getCloseActionControl() {return (ActionControl<?>)getComponent(FrameComponent.CLOSE_ACTION_CONTROL);}

	/**Sets the action control for closing the frame.
	This is a bound property.
	@param newCloseActionControl The action control for closing the frame, or <code>null</code> if this frame does not have a close action control.
	@see FrameComponent#CLOSE_ACTION_CONTROL
	@see Frame#CLOSE_ACTION_CONTROL_PROPERTY
	*/
	public void setCloseActionControl(final ActionControl<?> newCloseActionControl)
	{
		final ActionControl<?> oldCloseActionControl=(ActionControl<?>)setComponent(FrameComponent.CLOSE_ACTION_CONTROL, newCloseActionControl);	//set the component
		if(oldCloseActionControl!=newCloseActionControl)	//if the component really changed
		{
			if(oldCloseActionControl!=null)	//if we had an old close action
			{
				oldCloseActionControl.removeActionListener(closeActionListener);	//remove the close action listener from the old control
			}
			if(newCloseActionControl!=null)	//if we have a new close action
			{
				newCloseActionControl.addActionListener(closeActionListener);	//listen for the new action control and close the frame in response
			}
			firePropertyChange(CLOSE_ACTION_CONTROL_PROPERTY, oldCloseActionControl, newCloseActionControl);	//indicate that the value changed
		}
	}

	/**The input focus strategy for this input focus group.*/
	private InputFocusStrategy inputFocusStrategy=new DefaultInputFocusStrategy();

		/**@return The input focus strategy for this input focus group.*/
		public InputFocusStrategy getInputFocusStrategy() {return inputFocusStrategy;}
	
		/**Sets the input focus strategy.
		This is a bound property
		@param newInputFocusStrategy The input focus strategy for this group.
		@exception NullPointerException if the given input focus strategy is <code>null</code>.
		@see #INPUT_FOCUS_STRATEGY_PROPERTY
		*/
		public void setInputFocusStrategy(final InputFocusStrategy newInputFocusStrategy)
		{
			if(!inputFocusStrategy.equals(newInputFocusStrategy))	//if the value is really changing
			{
				final InputFocusStrategy oldInputFocusStrategy=inputFocusStrategy;	//get the old value
				inputFocusStrategy=newInputFocusStrategy;	//actually change the value
				firePropertyChange(INPUT_FOCUS_STRATEGY_PROPERTY, oldInputFocusStrategy, newInputFocusStrategy);	//indicate that the value changed
			}			
		}

	/**The component within this group that has the input focus, or <code>null</code> if no component currently has the input focus.*/ 
	private InputFocusableComponent<?> inputFocusedComponent=null;

		/**Indicates the component within this group that has the input focus.
		The focused component may be another {@link InputFocusGroupComponent}, which in turn will have its own focused component.
		@return The component within this group that has the input focus, or <code>null</code> if no component currently has the input focus.
		*/ 
		public InputFocusableComponent<?> getInputFocusedComponent() {return inputFocusedComponent;}
	
		/**Sets the focused component within this input focus group.
		This is a bound property.
		@param newInputFocusedComponent The component to receive the input focus.
		@exception PropertyVetoException if the given component is not a focusable component within this input focus group, the component cannot receive the input focus, or the input focus change has otherwise been vetoed.
		@see #getInputFocusStrategy()
		@see #INPUT_FOCUSED_COMPONENT_PROPERTY
		*/
		public void setInputFocusedComponent(final InputFocusableComponent<?> newInputFocusedComponent) throws PropertyVetoException 
		{
			if(!ObjectUtilities.equals(inputFocusedComponent, newInputFocusedComponent))	//if the value is really changing
			{
				final InputFocusStrategy oldInputFocusedComponent=inputFocusStrategy;	//get the old value
				inputFocusedComponent=newInputFocusedComponent;	//actually change the value
				firePropertyChange(INPUT_FOCUSED_COMPONENT_PROPERTY, oldInputFocusedComponent, newInputFocusedComponent);	//indicate that the value changed
			}			
		}

	/**Component constructor.
	@param component The single child component, or <code>null</code> if this frame should have no child component.
	*/
	public AbstractFrame(final Component<?> component)
	{
		super(FrameComponent.values());	//construct the parent class
		closeActionListener=new ActionListener()	//create an action listener for closing
				{
					public void actionPerformed(final ActionEvent actionEvent)	//if the close action is initiated
					{
						close();	//close the frame
					}
				};
		setComponent(FrameComponent.CONTENT_COMPONENT, component);	//set the component directly, because child classes may prevent the setContent() method from changing the component 
		final Link closeButton=new Link();	//create a close action control
		closeButton.setIcon(CLOSE_ICON_RESOURCE_URI);	//indicate to the close action control the resource key for its icon
		setCloseActionControl(closeButton);	//set the close action control
	}

	/**Opens the frame with the currently set modality.
	Opening the frame registers the frame with the application frame.
	If the frame is already open, no action occurs.
	@see #getState() 
	@see Frame#STATE_PROPERTY
	*/
	public void open()
	{
		if(getState()==State.CLOSED)	//if the state is closed
		{
			final ApplicationFrame<?> applicationFrame=getSession().getApplicationFrame();	//get the application frame
			if(this!=applicationFrame)	//if this is not the application frame
			{
				getSession().getApplicationFrame().addChildFrame(this);	//add the frame to the application frame
			}
			setState(State.OPEN);	//change the state
		}
	}

	/**Opens the frame, specifying modality.
	Opening the frame registers the frame with the session.
	If the frame is already open, no action occurs.
	@param modal <code>true</code> if the frame should be opened as a modal frame, else <code>false</code>.
	@see #getState() 
	@see Frame#STATE_PROPERTY
	*/
	public void open(final boolean modal)
	{
		setModal(modal);	//update the modality
		open();	//open the frame normally
	}

	/**Opens the frame as modal and installs the given property change listener to listen for the mode changing.
	This is a convenience method that adds the {@link ModalComponent#MODE_PROPERTY} change listener using {@link #addPropertyChangeListener(String, PropertyChangeListener)} and then calls {@link #open(boolean)} with a value of <code>true</code>.
	@param modeChangeListener The mode property change listener to add.
	@see ModalComponent#MODE_PROPERTY 
	*/
	public void open(final GenericPropertyChangeListener<Mode> modeChangeListener)
	{
		addPropertyChangeListener(MODE_PROPERTY, modeChangeListener);	//add the mode property change listener
		open(true);	//open modally
	}

	/**Determines whether the frame should be allowed to close.
	This implementation returns <code>true</code>.
	This method is called from {@link #close()}.
	@return <code>true</code> if the frame should be allowed to close.
	*/
	public boolean canClose()
	{
		return true;	//by default always allow the frame to be closed
	}

	/**Closes the frame.
	Closing the frame unregisters the frame with the session.
	If the frame is already closed, no action occurs.
	This method calls {@link #canClose()} and only performs closing functionality if that method returns <code>true</code>.
	This method delegates actual closing to {@link #closeImpl()}, and that method should be overridden rather than this one.
	@see #getState() 
	@see Frame#STATE_PROPERTY
	*/
	public final void close()
	{
		if(getState()!=State.CLOSED)	//if the frame is not already closed
		{
			if(canClose())	//if the frame can close
			{
				closeImpl();	//actually close the frame
			}
		}
	}

	/**Implementation of frame closing.*/
	protected void closeImpl()
	{
//TODO del Debug.trace("ready to remove frame");
		final ApplicationFrame<?> applicationFrame=getSession().getApplicationFrame();	//get the application frame
		if(this!=applicationFrame)	//if this is not the application frame
		{
			getSession().getApplicationFrame().removeChildFrame(this);	//remove the frame from the application frame
		}
		setState(State.CLOSED);	//change the state
	}

	/**Validates the user input of this component and all child components.
	The component will be updated with error information.
	The user is also notified of any error, using this component's notification, the first notification in the content component, or a default message.
	@return The current state of {@link #isValid()} as a convenience.
	*/
	public boolean validate()
	{
		if(!super.validate())	//validate the component normally; if the component does not validate
		{
			Notification notification=getNotification();	//see if this panel has any notification
			if(notification==null)	//if we don't have a notification
			{
				final Component<?> contentComponent=getContent();	//get the content component
				if(contentComponent!=null)	//if there is a content component
				{
					final List<Notification> notifications=getNotifications(contentComponent);	//get the notifications from the content component
					if(!notifications.isEmpty())	//if there are notifications
					{
						notification=notifications.get(0);	//use the first notification
					}
				}
			}
			if(notification==null)	//if we didn't find a custom notification
			{
				notification=new Notification(VALIDATION_FALSE_MESSAGE_RESOURCE_REFERENCE, Notification.Severity.ERROR);	//use a general validation notification
			}
			getSession().notify(notification);	//indicate that there was a validation error
		}
		return isValid();	//return the current valid state
	}

	/**Dispatches an input event to this component and all child components, if any.
	If this is a {@link FocusedInputEvent} the event will be directed towards the focused component, if any.
	This version fires any unconsumed {@link FocusedInputEvent} and then, if the event is still unconsumed, dispatches the event to the focused component, if any.
	Other events are dispatched normally.
	@param inputEvent The input event to dispatch.
	@see #fireInputEvent(InputEvent)
	@see InputEvent#isConsumed()
	@see FocusedInputEvent
	*/
	public void dispatchInputEvent(final InputEvent inputEvent)
	{
//Debug.trace("in frame", this, "with label", getLabel(), "ready to dispatch input event");
		if(inputEvent instanceof FocusedInputEvent)	//if this is a focused input event
		{
//Debug.trace("this is an input event");
			if(!inputEvent.isConsumed())	//if the input has not been consumed
			{
//Debug.trace("in frame", this, "with label", getLabel(), "firing unconsumed focused input event", inputEvent);
				fireInputEvent(inputEvent);	//fire the event to any listeners
			}
			if(!inputEvent.isConsumed())	//if the input has still not been consumed
			{
//Debug.trace("in frame", this, "with label", getLabel(), "event still not consumed", inputEvent);
				final Component<?> inputFocusedComponent=getInputFocusedComponent();	//get the component with the input focus
//Debug.trace("input focused component:", inputFocusedComponent);
				if(inputFocusedComponent!=null)	//if we have a focused component in this group
				{
					inputFocusedComponent.dispatchInputEvent(inputEvent);	//have the input focused component dispatch the event
				}
			}
		}
		else	//if this is not a focused input event
		{
			super.dispatchInputEvent(inputEvent);	//dispatch the event normally
		}
	}

}
