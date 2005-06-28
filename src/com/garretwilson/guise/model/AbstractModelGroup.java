package com.garretwilson.guise.model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**An abstract implementation of a group of similar models for providing such functions as communication or mutual exclusion.
This class is thread safe.
@param <M> The type of model contained in the group.
@author Garret Wilson.
*/
public abstract class AbstractModelGroup<M extends Model> implements ModelGroup<M>
{

	/**The set of models.*/
	private final Set<M> modelSet=new CopyOnWriteArraySet<M>();	//create a thread-safe set that is very efficient on reads because it works on a copy of the set (which we don't mind; changing to the set occurs infrequently)

		/**@return The set of models.*/
		protected Set<M> getModelSet() {return modelSet;}

	/**Determines whether this group contains the given model.
	@param model The model being checked for group inclusion.
	@return <code>true</code> if the model is contained in this group, else <code>false</code>.
	*/
	public boolean contains(final Model model)
	{
		return modelSet.contains(model);	//see if the set of models contains this model TODO check for class cast exception
	}

	/**Adds a model to the group.
	If the model is already included in the group, no action occurs.
	This version delegates to {@link #addImpl(M)}.
	@param model The model to add to the group.	
	*/
	public void add(final M model)
	{
		if(!contains(model))	//if the group doesn't already contain the model
		{
			addImpl(model);	///actually add the model to the model set
		}
	}

	/**Actual implementation of adding a model to the group.
	@param model The model to add to the group.	
	*/
	protected void addImpl(final M model)
	{
		modelSet.add(model);	//add this model to the model set
	}

	/**Removes a model from the group.
	If the model is not included in this group, no action occurs.
	This version delegates to {@link #removeImpl(M)}.
	@param model The model to remove from the group.
	*/
	public void remove(final M model)
	{
		if(contains(model))	//if the group contains the model
		{
			removeImpl(model);	///actually remove the model from the model set
		}
	}

	/**Actual implementation of removing a model from the group.
	@param model The model to remove from the group.
	*/
	protected void removeImpl(final M model)
	{
		modelSet.remove(model);	//remove this model from the model set
	}

}
