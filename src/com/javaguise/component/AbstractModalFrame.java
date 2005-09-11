package com.javaguise.component;

import com.javaguise.component.layout.Layout;
import com.javaguise.model.LabelModel;
import com.javaguise.session.GuiseSession;
import com.garretwilson.lang.ObjectUtilities;

/**Abstract implementation of a modal frame.
@param <R> The type of modal result this modal frame produces.
@author Garret Wilson
*/
public abstract class AbstractModalFrame<R, C extends ModalFrame<R, C>> extends AbstractFrame<C> implements ModalFrame<R, C>
{

	/**The result of this frame's modal interaction, or <code>null</code> if no result is given.*/
	private R result=null;

		/**@return The result of this frame's modal interaction, or <code>null</code> if no result is given.*/
		public R getResult() {return result;}

		/**Sets the modal result.
		This is a bound property that only fires a change event when the new value is different via the <code>equals()</code> method.
		@param newResult The new result of this frame's modal interaction, or <code>null</code> if no result is given.
		@see ModalFrame#RESULT_PROPERTY
		*/
		public void setResult(final R newResult)
		{
			if(!ObjectUtilities.equals(result, newResult))	//if the value is really changing (compare their values, rather than identity)
			{
				final R oldResult=result;	//get the old value
				result=newResult;	//actually change the value
				firePropertyChange(RESULT_PROPERTY, oldResult, newResult);	//indicate that the value changed
			}
		}

	/**Session, ID, layout, and model constructor.
	@param session The Guise session that owns this component.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	@param layout The layout definition for the container.
	@param model The component data model.
	@exception NullPointerException if the given session, layout, and/or model is <code>null</code>.
	@exception IllegalArgumentException if the given identifier is not a valid component identifier.
	*/
	public AbstractModalFrame(final GuiseSession<?> session, final String id, final Layout layout, final LabelModel model)
	{
		super(session, id, layout, model);	//construct the parent class
	}
	/**Ends this frame's modal interaction and navigates either to the previous modal navigation or to this frame's referring URI, if any.
	@param result The result of this frame's modal interaction, or <code>null</code> if no result is given.
	@see #setResult(R)
	@see GuiseSession#endModalNavigation(ModalFrame)
	*/
	public void endModal(final R result)
	{
		setResult(result);	//update the result
		getSession().endModalNavigation(this);	//end modal navigation for this modal frame
	}
}