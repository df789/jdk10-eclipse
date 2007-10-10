package com.guiseframework.model.urf;

import java.net.URI;
import com.garretwilson.urf.*;
import com.guiseframework.model.*;

/**Abstract dynamic functionality for all tree node models representing URF.
@param <V> The type of value contained in the tree node.
@author Garret Wilson
*/
public abstract class AbstractURFDynamicTreeNodeModel<V> extends DynamicTreeNodeModel<V>
{

	/**Value class constructor with no initial value.
	@param valueClass The class indicating the type of value held in the model.
	*/
	public AbstractURFDynamicTreeNodeModel(final Class<V> valueClass)
	{
		this(valueClass, null);	//construct the class with no initial value
	}

	/**Initial value constructor.
	@param valueClass The class indicating the type of value held in the model.
	@param initialValue The initial value, which will not be validated.
	*/
	public AbstractURFDynamicTreeNodeModel(final Class<V> valueClass, final V initialValue)
	{
		super(valueClass, initialValue);	//construct the parent class
	}

	/**Creates a child node to represent a property object resource and optional property.
	This version returns a {@link URFResourceDynamicTreeNodeModel}.
	@param propertyURI The URI of the URF property of which this URF resource is an object, or <code>null</code> if this resource should not be considered the object of any property.
	@param resource The resource to represent in the new node.
	@return A child node to represent the given property object resource.
	@exception NullPointerException if the given resource is <code>null</code>.
	*/
	@SuppressWarnings("unchecked")	//the class of the resource should always be its type, so the cast is logically correct
	protected <T extends URFResource> URFResourceDynamicTreeNodeModel<?> createURFResourceTreeNode(final URI propertyURI, final T resource)
	{
		return new URFResourceDynamicTreeNodeModel<T>((Class<T>)resource.getClass(), propertyURI, resource);	//create a new tree node to represent the property and value
	}

}
