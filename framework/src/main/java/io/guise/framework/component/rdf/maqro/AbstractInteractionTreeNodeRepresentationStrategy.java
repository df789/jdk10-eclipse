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

package io.guise.framework.component.rdf.maqro;

import java.util.List;

import org.urframework.maqro.*;

import static com.globalmentor.rdf.dublincore.RDFDublinCore.*;

import com.globalmentor.rdf.*;

import io.guise.framework.component.TreeControl;
import io.guise.framework.component.rdf.AbstractRDFResourceTreeNodeRepresentationStrategy;
import io.guise.framework.model.*;
import io.guise.framework.model.rdf.maqro.AbstractInteractionTreeNodeModel;

/**
 * Abstract functionality for a tree node representation strategy representing a MAQRO interaction.
 * @param <V> The type of value the strategy is to represent.
 * @author Garret Wilson
 */
public abstract class AbstractInteractionTreeNodeRepresentationStrategy<V extends Interaction> { //TODO fix extends AbstractRDFResourceTreeNodeRepresentationStrategy<V>

	/** Default constructor with a default RDF XMLifier. */
	public AbstractInteractionTreeNodeRepresentationStrategy() {
		this(new RDFXMLGenerator()); //create the class with a default RDF XMLifier
	}

	/**
	 * RDF XMLifier constructor.
	 * @param rdfXMLifier The RDF XMLifier to use for creating labels.
	 * @throws NullPointerException if the given RDF XMLifier is <code>null</code>.
	 */
	public AbstractInteractionTreeNodeRepresentationStrategy(final RDFXMLGenerator rdfXMLifier) {
		//TODO fix		super(rdfXMLifier);	//construct the parent
	}

	/**
	 * Builds the label to be used for a tree node. This version adds information on the interaction's subject followup evaluation, if any.
	 * @param stringBuilder The string builder to hold the label text.
	 * @param treeControl The component containing the model.
	 * @param model The model containing the value.
	 * @param treeNode The node containing the value.
	 * @param value The value contained in the node.
	 * @return The string builder used to construct the label.
	 */
	//TODO fix	protected <N extends V> StringBuilder buildLabelText(final StringBuilder stringBuilder, final TreeControl treeControl, final TreeModel model, final TreeNodeModel<N> treeNode, final N value)	//TODO fix public
	protected StringBuilder buildLabelText(final StringBuilder stringBuilder, final TreeControl treeControl, final TreeModel model,
			final TreeNodeModel<? extends V> treeNode, final V value) { //TODO later put this method hierarchy in a custom label model
	//TODO del Log.trace("building label text for type:", value.getClass());
		final TreeNodeModel<?> parentTreeNode = treeNode.getParent(); //get the parent node
		final Object parentValue = parentTreeNode != null ? parentTreeNode.getValue() : null; //get the parent value if any
		if(treeNode instanceof AbstractInteractionTreeNodeModel && parentValue instanceof Question) { //if the tree node is an interaction tree node and there is a parent question
			final Question question = (Question)parentValue; //get the question
			final FollowupEvaluation followupEvaluation = ((AbstractInteractionTreeNodeModel<?>)treeNode).getFollowupEvaluation(); //get the followup evaluation, if any, associated with the RDF object
			/*TODO fix
						final String condition=followupEvaluation!=null ? followupEvaluation.getCondition() : null;	//get the evaluation condition, if any
						if(condition!=null) {	//if the evaluation has a condition
							final String[] conditionTokens=condition.split("\\s");	//split the condition on whitespace
							if(conditionTokens.length>0) {	//if there are condition tokens
								final String conditionID=conditionTokens[conditionTokens.length-1];	//get the last condition token
								if(Boolean.TRUE.toString().equals(conditionID)) {	//if the value is "true" TODO improve; check the expected response type
									stringBuilder.append('[').append("Yes").append(']').append(' ');	//append "[Yes] "						
								}
								else if(Boolean.FALSE.toString().equals(conditionID)) {	//if the value is "false" TODO improve; check the expected response type
									stringBuilder.append('[').append("No").append(']').append(' ');	//append "[No] "						
								}
								else {	//if this is a condition for a choice-based question
									final List<RDFObject> choices=question.getChoices();	//get the choices
									if(choices!=null) {	//if there are choices
										for(final RDFObject choiceObject:choices) {	//for each choice
											if(choiceObject instanceof Dialogue	//if this is the choice dialog the condition mentioned
													&& conditionID.equals(((Dialogue)choiceObject).getURI().toString())) 
											{
												final RDFLiteral choiceValue=((Dialogue)choiceObject).getValue();	//get the choice value
												if(choiceValue!=null) {	//if there is a choice value
													stringBuilder.append('[').append(choiceValue).append(']').append(' ');	//append "[choice] "
												}
											}
										}
									}
								}
							}
						}
			*/
		}
		if(value != null) { //if there is no value
		/*TODO fix
					final RDFObject title=getTitle(value);	//get the title, if any
					if(title!=null) {	//if there is a title
						stringBuilder.append(title);	//TODO testing; improve
					}
		*/
		}
		//TODO del		super.buildLabelText(stringBuilder, treeControl, model, treeNode, value);	//do the default label text building
		return stringBuilder; //return the string builder
	}
}
