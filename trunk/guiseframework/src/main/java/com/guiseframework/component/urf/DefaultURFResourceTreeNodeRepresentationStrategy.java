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

package com.guiseframework.component.urf;

import com.globalmentor.urf.*;

/**A default tree node representation strategy representing an URF resource.
@author Garret Wilson
*/
public class DefaultURFResourceTreeNodeRepresentationStrategy extends AbstractURFResourceTreeNodeRepresentationStrategy<URFResource>
{

	/**Default constructor with a default namespace label manager.*/
	public DefaultURFResourceTreeNodeRepresentationStrategy()
	{
		this(new TURFNamespaceLabelManager());	//create the class with a default namespace label manager
	}

	/**RDF XMLifier constructor.
	@param namespaceLabelManager The manager responsible for generating namespace labels..
	@exception NullPointerException if the given label manager is <code>null</code>.
	*/
	public DefaultURFResourceTreeNodeRepresentationStrategy(final TURFNamespaceLabelManager namespaceLabelManager)
	{
		super(namespaceLabelManager);	//construct the parent class
	}

}
