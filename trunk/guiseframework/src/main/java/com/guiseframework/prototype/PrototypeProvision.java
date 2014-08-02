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

package com.guiseframework.prototype;

import static com.globalmentor.java.Objects.*;

import com.globalmentor.log.Log;
import com.globalmentor.model.AbstractHashObject;

/**Prototype provision information indicating order, hierarchy, and location for generated components.
This description is usually used to generate components and place them in menus and/or toolbars. 
@param <P> The type of prototype being provided.
@author Garret Wilson
*/
public class PrototypeProvision<P extends Prototype> extends AbstractHashObject implements Comparable<PrototypeProvision<P>>
{

	/**No order.*/
	public final static int NO_ORDER=-1;
	/**The minimum allowed order.*/
	public final static int MIN_ORDER=0;
	/**The maximum allowed order.*/
	public final static int MAX_ORDER=Integer.MAX_VALUE;

		//top-level prototype orders
	public final static int RESOURCE_MENU_ORDER=1000000;
	public final static int EDIT_MENU_ORDER=2000000;
	public final static int INSERT_MENU_ORDER=3000000;
	public final static int VIEW_MENU_ORDER=4000000;
	public final static int TOOL_MENU_ORDER=5000000;
	public final static int CONFIGURE_MENU_ORDER=6000000;
	public final static int WINDOW_MENU_ORDER=7000000;
	public final static int HELP_MENU_ORDER=99000000;

		//resource menu order
	public final static int RESOURCE_MENU_NEW_ORDER=RESOURCE_MENU_ORDER+100;
	public final static int RESOURCE_MENU_ADD_ORDER=RESOURCE_MENU_ORDER+200;
	public final static int RESOURCE_MENU_OPEN_ORDER=RESOURCE_MENU_ORDER+300;
	public final static int RESOURCE_MENU_CLOSE_ORDER=RESOURCE_MENU_ORDER+400;
	public final static int RESOURCE_MENU_EDIT_ORDER=RESOURCE_MENU_ORDER+500;
	public final static int RESOURCE_MENU_EDIT_CANCEL=RESOURCE_MENU_ORDER+550;
	public final static int RESOURCE_MENU_SAVE_ORDER=RESOURCE_MENU_ORDER+600;
	public final static int RESOURCE_MENU_SAVE_AS_ORDER=RESOURCE_MENU_ORDER+700;
	public final static int RESOURCE_MENU_SAVE_ALL_ORDER=RESOURCE_MENU_ORDER+800;
	public final static int RESOURCE_MENU_REVERT_ORDER=RESOURCE_MENU_ORDER+900;
	public final static int RESOURCE_MENU_MOVE_ORDER=RESOURCE_MENU_ORDER+1000;
	public final static int RESOURCE_MENU_RENAME_ORDER=RESOURCE_MENU_ORDER+1100;
	public final static int RESOURCE_MENU_REFRESH_ORDER=RESOURCE_MENU_ORDER+1200;
	public final static int RESOURCE_MENU_DELETE_ORDER=RESOURCE_MENU_ORDER+1300;
	public final static int RESOURCE_MENU_PREVIOUS_ORDER=RESOURCE_MENU_ORDER+1400;
	public final static int RESOURCE_MENU_RECEDE_ORDER=RESOURCE_MENU_ORDER+1500;
	public final static int RESOURCE_MENU_PLAY_ORDER=RESOURCE_MENU_ORDER+1600;
	public final static int RESOURCE_MENU_PAUSE_ORDER=RESOURCE_MENU_ORDER+1700;
	public final static int RESOURCE_MENU_RECORD_ORDER=RESOURCE_MENU_ORDER+1800;
	public final static int RESOURCE_MENU_STOP_ORDER=RESOURCE_MENU_ORDER+1900;
	public final static int RESOURCE_MENU_ADVANCE_ORDER=RESOURCE_MENU_ORDER+2000;
	public final static int RESOURCE_MENU_NEXT_ORDER=RESOURCE_MENU_ORDER+2100;
	public final static int RESOURCE_MENU_RETRIEVE_ORDER=RESOURCE_MENU_ORDER+2200;
	public final static int RESOURCE_MENU_ANNOTATE_ORDER=RESOURCE_MENU_ORDER+2300;
	public final static int RESOURCE_MENU_PROPERTIES_ORDER=RESOURCE_MENU_ORDER+2400;
	public final static int RESOURCE_MENU_EXIT_ORDER=RESOURCE_MENU_ORDER+9900;

		//help prototype orders
	public final static int HELP_MENU_ABOUT_ORDER=HELP_MENU_ORDER+100;

	/**The prototype description's parent, or <code>null</code> if the prototype description has no parent.*/
	private final PrototypeProvision<?> parentPrototypeProvision;

		/**@return The prototype's parent, or <code>null</code> if the prototype description has no parent.*/
		public PrototypeProvision<?> getParentPrototypeProvision() {return parentPrototypeProvision;}

	/**The prototype being described.*/
	private final P prototype;

		/**@return The prototype being described.*/
		public P getPrototype() {return prototype;}

	/**The order of the prototype.*/
	private final int order;

		/**@return The order of the prototype.*/
		public int getOrder() {return order;}

	/**Whether this prototype should be used in a menu if available.*/ 
	private final boolean isMenu;

		/**@return Whether this prototype should be used in a menu if available.*/ 
		public boolean isMenu() {return isMenu;}

	/**Whether this prototype should be used in a toolbar if available.*/ 
	private final boolean isTool;

		/**@return Whether this prototype should be used in a toolbar if available.*/ 
		public boolean isTool() {return isTool;}

	/**Prototype constructor with no prototype description parent.
	@param prototype The prototype.
	@param order The order of the prototype.
	@param isMenu Whether this prototype should be used in a menu if available.
	@param isTool Whether this prototype should be used in a toolbar if available.
	@throws NullPointerException if the given prototype is <code>null</code>.
	@throws IllegalArgumentException if the given order is not {@value #NO_ORDER} and does not come between {@value #MIN_ORDER} and {@value #MAX_ORDER}, inclusive.
	*/
	public PrototypeProvision(final P prototype, final int order, final boolean isMenu, final boolean isTool)
	{
		this(null, prototype, order, isMenu, isTool);	//construct the class with no parent prototype description
	}

	/**Parent prototype description and prototype constructor.
	@param parentPrototypeProvision The prototype description's parent, or <code>null</code> if the prototype description has no parent.
	@param prototype The prototype.
	@param order The order of the prototype.
	@param isMenu Whether this prototype should be used in a menu if available.
	@param isTool Whether this prototype should be used in a toolbar if available.
	@throws NullPointerException if the given prototype is <code>null</code>.
	@throws IllegalArgumentException if the given order is not {@value #NO_ORDER} and does not come between {@value #MIN_ORDER} and {@value #MAX_ORDER}, inclusive.
	*/
	public PrototypeProvision(final PrototypeProvision<?> parentPrototypeProvision, final P prototype, final int order, final boolean isMenu, final boolean isTool)
	{
		super(parentPrototypeProvision, checkInstance(prototype, "Prototype cannot be null."), Integer.valueOf(order));	//construct the parent class
		if(order!=NO_ORDER && (order<MIN_ORDER || order>MAX_ORDER))	//if the order is invalid
		{
			throw new IllegalArgumentException("Invalid order: "+order);
		}
		this.parentPrototypeProvision=parentPrototypeProvision;
		this.prototype=prototype;
		this.order=order;
		this.isMenu=isMenu;
		this.isTool=isTool;
	}

	/**Compares this object with the specified object for order.
	@param prototypeDescription The object to be compared.
	@return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	*/
	public int compareTo(final PrototypeProvision<P> prototypeDescription)
	{
		int value=getOrder()-prototypeDescription.getOrder();	//compare the orders
		if(value==0 && !equals(prototypeDescription))	//if the orders were the same but the objects are not equal
		{
				//TODO compare the labels
			value=hashCode()-prototypeDescription.hashCode();	//create an arbitrary ordering based upon the hash code
			if(value==0)	//if the hash codes are the same but the objects are not equal, the JVM must have assigned two distinct objects identical hash codes; this shouldn't bring down the application, but if it happens frequently this logic should be updated
			{
				Log.warn("Distinct prototype description not comparing correctly.");	//TODO add creation order property to all prototypes to aid in comparison
			}
		}
		return value;	//return the comparation value
	}
}
