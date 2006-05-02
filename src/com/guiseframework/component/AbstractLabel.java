package com.guiseframework.component;

import javax.mail.internet.ContentType;

import static com.garretwilson.util.ArrayUtilities.*;

import com.guiseframework.component.transfer.*;
import com.guiseframework.model.*;

/**An abstract label component.
This component installs a default export strategy supporting export of the following content types:
<ul>
	<li>The label content type.</li>
</ul>
@author Garret Wilson
*/
public abstract class AbstractLabel<C extends LabelComponent<C>> extends AbstractComponent<C> implements LabelComponent<C>
{

	/**The default export strategy for this component type.*/
	protected final static ExportStrategy<LabelComponent> DEFAULT_EXPORT_STRATEGY=new ExportStrategy<LabelComponent>()
			{
				/**Exports data from the given component.
				@param component The component from which data will be transferred.
				@return The object to be transferred, or <code>null</code> if no data can be transferred.
				*/
				public Transferable<LabelComponent> exportTransfer(final LabelComponent component)
				{
					return new DefaultTransferable(component);	//return a default transferable for this component
				}
			};

	/**Default constructor with a default label model.*/
	public AbstractLabel()
	{
		this(new DefaultLabelModel());	//construct the class with a default label model
	}

	/**Label model constructor.
	@param labelModel The component label model.
	@exception NullPointerException if the given label or model is <code>null</code>.
	*/
	public AbstractLabel(final LabelModel labelModel)
	{
		super(labelModel);	//construct the parent class
		addExportStrategy(DEFAULT_EXPORT_STRATEGY);	//install a default export strategy 
	}

	/**The default transferable object for a label.
	@author Garret Wilson
	*/
	protected static class DefaultTransferable extends AbstractTransferable<LabelComponent>
	{
		/**Source constructor.
		@param source The source of the transferable data.
		@exception NullPointerException if the provided source is <code>null</code>.
		*/
		public DefaultTransferable(final LabelComponent source)
		{
			super(source);	//construct the parent class
		}

		/**Determines the content types available for this transfer.
		This implementation returns the content type of the label.
		@return The content types available for this transfer.
		*/
		public ContentType[] getContentTypes() {return createArray(getSource().getLabelContentType());}

		/**Transfers data using the given content type.
		@param contentType The type of data expected.
		@return The transferred data, which may be <code>null</code>.
		@exception IllegalArgumentException if the given content type is not supported.
		*/
		public Object transfer(final ContentType contentType)
		{
			final LabelComponent source=getSource();	//get the source of the transfer
			if(contentType.match(source.getLabelContentType()))	//if we have the content type requested
			{
				final String label=source.getLabel();	//get the label
				return label!=null ? source.getSession().resolveString(source.getLabel()) : null;	//return the label text, if any
			}
			else	//if we don't support this content type
			{
				throw new IllegalArgumentException("Content type not supported: "+contentType);
			}
		}
	}
}
