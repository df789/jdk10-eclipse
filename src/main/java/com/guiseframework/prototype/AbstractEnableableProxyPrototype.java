/*
 * Copyright © 2008 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

import com.guiseframework.model.*;

/**
 * An enableable prototype that is a proxy for another enableable prototype.
 * @param <P> The type of prototype being proxied.
 * @author Garret Wilson
 */
public abstract class AbstractEnableableProxyPrototype<P extends Prototype & InfoModel & Enableable> extends AbstractProxyPrototype<P> implements Enableable {

	/** @return Whether the object is enabled and can receive user input. */
	public boolean isEnabled() {
		return getProxiedPrototype().isEnabled();
	}

	/**
	 * Sets whether the object is enabled and can receive user input. This is a bound property of type <code>Boolean</code>.
	 * @param newEnabled <code>true</code> if the object should indicate and accept user input.
	 * @see #ENABLED_PROPERTY
	 */
	public void setEnabled(final boolean newEnabled) {
		getProxiedPrototype().setEnabled(newEnabled);
	}

	/**
	 * Fires appropriate property change events for the bound properties of the proxied prototype This implementation fires property change events for the
	 * following properties:
	 * <ul>
	 * <li>{@link #ENABLED_PROPERTY}</li>
	 * </ul>
	 * @param oldProxiedPrototype The old proxied prototype.
	 * @param newProxiedPrototype The new proxied prototype.
	 * @throws NullPointerException if the given old proxied prototype and/or new proxied prototype is <code>null</code>.
	 */
	protected void fireProxiedPrototypeBoundPropertyChanges(final P oldProxiedPrototype, final P newProxiedPrototype) {
		super.fireProxiedPrototypeBoundPropertyChanges(oldProxiedPrototype, newProxiedPrototype); //fire the default proxied prototype bound property change events
		firePropertyChange(ENABLED_PROPERTY, oldProxiedPrototype.isEnabled(), newProxiedPrototype.isEnabled());
	}

	/**
	 * Proxied prototype constructor.
	 * @param proxiedPrototype The prototype proxied by this prototype.
	 * @throws NullPointerException if the given proxied prototype is <code>null</code> is <code>null</code>.
	 */
	public AbstractEnableableProxyPrototype(final P proxiedPrototype) {
		super(proxiedPrototype);
	}

}