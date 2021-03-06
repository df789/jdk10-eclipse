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

package io.guise.framework.model.rdf.maqro;

import java.util.Collections;
import java.util.List;

import org.urframework.maqro.FollowupEvaluation;
import org.urframework.maqro.Group;
import org.urframework.maqro.Question;

import com.globalmentor.rdf.RDFObject;
import com.globalmentor.rdf.RDFResource;

import io.guise.framework.model.TreeNodeModel;
import io.guise.framework.model.rdf.AbstractRDFResourceTreeNodeModel;

/**
 * Abstract functionality for a tree node model that represents a group.
 * @param <V> The type of value contained in the tree node.
 * @author Garret Wilson
 */
public abstract class AbstractGroupTreeNodeModel<V extends Group> extends AbstractInteractionTreeNodeModel<V> {

	/**
	 * Value class constructor with no initial value.
	 * @param valueClass The class indicating the type of value held in the model.
	 */
	public AbstractGroupTreeNodeModel(final Class<V> valueClass) {
		this(valueClass, null); //construct the class with no initial value
	}

	/**
	 * Initial value constructor.
	 * @param valueClass The class indicating the type of value held in the model.
	 * @param initialValue The initial value, which will not be validated.
	 */
	public AbstractGroupTreeNodeModel(final Class<V> valueClass, final V initialValue) {
		this(valueClass, null, initialValue); //construct the class with no property
	}

	/**
	 * Property and initial value constructor.
	 * @param valueClass The class indicating the type of value held in the model.
	 * @param followupEvaluation The followup evaluation which considers this interaction a followup in this context, or <code>null</code> if there is no followup
	 *          evaluation subject in this context.
	 * @param initialValue The initial value, which will not be validated.
	 */
	public AbstractGroupTreeNodeModel(final Class<V> valueClass, final FollowupEvaluation followupEvaluation, final V initialValue) {
		super(valueClass, followupEvaluation, initialValue); //construct the parent class
	}

	/**
	 * Dynamically determines whether this node is a leaf. This version determines if there is one or more child interactions.
	 * @return Whether this node should be considered a leaf with no children.
	 */
	protected boolean determineLeaf() {
		/*TODO fix
				if(!super.determineLeaf()) {	//if the base class doesn't think this is a leaf
					return false;	//this isn't a leaf
				}
				final Group group=getValue();	//get the group
				if(group!=null) {	//if we have a group
					final List<RDFObject> interactionList=group.getInteractions();	//get the child interactions
					if(interactionList!=null && !interactionList.isEmpty()) {	//if there are child interactions
						return false;	//this is not a leaf because there is at least one child interaction
					}
				}
		*/
		return true; //we couldn't find child interactions, so this is a leaf 
	}

	/**
	 * Dynamically determines children. This version includes child interactions.
	 * @return The dynamically loaded list of children.
	 */
	protected List<TreeNodeModel<?>> determineChildren() {
		/*TODO fix
				final List<TreeNodeModel<?>> children=super.determineChildren();	//determine the default children
				final Group group=getValue();	//get the group
				if(group!=null) {	//if we have a group
					final List<RDFObject> interactionList=group.getInteractions();	//get the child interactions
					if(interactionList!=null) {	//if there are child interactions
						for(final RDFObject interactionResource:interactionList) {	//for each interaction
							//TODO important; fix blind RDFResource cast
							children.add(createRDFResourceTreeNode(null, (RDFResource)interactionResource));	//create a child node for this interaction resource
						}
					}
				}
				return children;	//return the determined children
		*/
		return Collections.emptyList();
	}

	/**
	 * Creates a child node to represent a property object resource and optional property. This version returns a {@link GroupTreeNodeModel} for a {@link Group}.
	 * This version returns a {@link QuestionTreeNodeModel} for a {@link Question}. All other resource types will result in a default RDF tree node model being
	 * returned.
	 * @param rdfProperty The property of which the object is a resource, or <code>null</code> if this object should not be considered the object of any property.
	 * @param rdfResource The resource to represent in the new node.
	 * @return A child node to represent the given property object resource.
	 */
	protected AbstractRDFResourceTreeNodeModel createRDFResourceTreeNode(final RDFResource rdfProperty, final RDFResource rdfResource) {
		/*TODO fix
				if(rdfResource instanceof Group) {	//if the interaction is a group
					return new GroupTreeNodeModel(null, (Group)rdfResource);	//create a group tree node model
				}
				else if(rdfResource instanceof Question) {	//if the interaction is a question
					return new QuestionTreeNodeModel(null, (Question)rdfResource);	//create a question tree node model
				}
				else {	//if we don't recognize the resource type
					return super.createRDFResourceTreeNode(rdfProperty, rdfResource);	//create a default RDF tree node
				}
		*/throw new UnsupportedOperationException("TODO convert to URF");
	}

}
