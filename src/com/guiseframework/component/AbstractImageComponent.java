package com.guiseframework.component;

import java.net.URI;

import javax.mail.internet.ContentType;

import static com.garretwilson.io.ContentTypeConstants.*;
import static com.garretwilson.io.ContentTypeUtilities.*;
import static com.garretwilson.net.URIUtilities.*;
import static com.garretwilson.util.ArrayUtilities.*;

import com.garretwilson.lang.ObjectUtilities;
import com.guiseframework.component.transfer.*;
import com.guiseframework.model.LabelModel;

/**An abstract implementation of an image component.
This component installs a default export strategy supporting export of the following content types:
<ul>
	<li><code>text/uri-list</code></li>
	<li>The label content type.</li>
</ul>
@author Garret Wilson
*/
public abstract class AbstractImageComponent<C extends ImageComponent<C>> extends AbstractComponent<C> implements ImageComponent<C>
{

	/**The default export strategy for this component type.*/
	protected final static ExportStrategy<ImageComponent<?>> DEFAULT_EXPORT_STRATEGY=new ExportStrategy<ImageComponent<?>>()
			{
				/**Exports data from the given component.
				@param component The component from which data will be transferred.
				@return The object to be transferred, or <code>null</code> if no data can be transferred.
				*/
				public Transferable<ImageComponent<?>> exportTransfer(final ImageComponent<?> component)
				{
					return new DefaultTransferable(component);	//return a default transferable for this component
				}
			};

	/**The image URI, which may be a resource URI, or <code>null</code> if there is no image URI.*/
	private URI image=null;

		/**@return The image URI, which may be a resource URI, or <code>null</code> if there is no image URI.*/
		public URI getImage() {return image;}

		/**Sets the URI of the image.
		This is a bound property of type <code>URI</code>.
		@param newImage The new URI of the image, which may be a resource URI.
		@see #IMAGE_PROPERTY
		*/
		public void setImage(final URI newImage)
		{
			if(!ObjectUtilities.equals(image, newImage))	//if the value is really changing
			{
				final URI oldImage=image;	//get the old value
				image=newImage;	//actually change the value
				firePropertyChange(IMAGE_PROPERTY, oldImage, newImage);	//indicate that the value changed
			}			
		}

	/**Label model constructor.
	@param labelModel The component label model.
	@exception NullPointerException if the given label or model is <code>null</code>.
	*/
	public AbstractImageComponent(final LabelModel labelModel)
	{
		super(labelModel);	//construct the parent class
		addExportStrategy((ExportStrategy<C>)DEFAULT_EXPORT_STRATEGY);	//install a default export strategy 
	}

	/**The default transferable object for an image.
	@author Garret Wilson
	*/
	protected static class DefaultTransferable extends AbstractTransferable<ImageComponent<?>>
	{
		/**Source constructor.
		@param source The source of the transferable data.
		@exception NullPointerException if the provided source is <code>null</code>.
		*/
		public DefaultTransferable(final ImageComponent<?> source)
		{
			super(source);	//construct the parent class
		}

		/**Determines the content types available for this transfer.
		This implementation returns a URI-list content type and the content type of the label.
		@return The content types available for this transfer.
		*/
		public ContentType[] getContentTypes() {return createArray(new ContentType(TEXT, URI_LIST_SUBTYPE, null), getSource().getLabelContentType());}

		/**Transfers data using the given content type.
		@param contentType The type of data expected.
		@return The transferred data, which may be <code>null</code>.
		@exception IllegalArgumentException if the given content type is not supported.
		*/
		public Object transfer(final ContentType contentType)
		{
			final ImageComponent<?> image=getSource();	//get the image
			if(match(contentType, TEXT, URI_LIST_SUBTYPE))	//if this is a text/uri-list type
			{
				final URI imageURI=image.getImage();	//get the image URI
				return imageURI!=null ? createURIList(image.getSession().resolveURI(imageURI)) : null;	//return the image URI, if there is one
			}
			else if(contentType.match(image.getLabelContentType()))	//if the label has the content type requested
			{
				final String label=image.getLabel();	//get the image label, if any
				return label!=null ? image.getSession().resolveString(image.getLabel()) : null;	//return the resolved label text, if any
			}
			else	//if we don't support this content type
			{
				throw new IllegalArgumentException("Content type not supported: "+contentType);
			}
		}
	}

}