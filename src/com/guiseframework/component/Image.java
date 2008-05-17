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

package com.guiseframework.component;

import com.guiseframework.model.*;

/**A simple image component with no descriptory text.
@author Garret Wilson
*/
public class Image extends AbstractImageComponent
{

	/**Default constructor.*/
	public Image()
	{
		this(new DefaultInfoModel(), new DefaultImageModel());	//construct the parent class with default models
	}

	/**Image model constructor.
	@param imageModel The component image model.
	@exception NullPointerException if the given image model is <code>null</code>.
	*/
	public Image(final ImageModel imageModel)
	{
		this(new DefaultInfoModel(), imageModel);	//construct the parent class with a default info model
	}

	/**Info model and image model constructor.
	@param infoModel The component info model.
	@param imageModel The component image model.
	@exception NullPointerException if the given info model and/or image model is <code>null</code>.
	*/
	public Image(final InfoModel infoModel, final ImageModel imageModel)
	{
		super(infoModel, imageModel);	//construct the parent class
	}

}
