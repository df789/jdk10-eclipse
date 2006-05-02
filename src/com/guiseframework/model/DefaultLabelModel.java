package com.guiseframework.model;

import static com.garretwilson.lang.ObjectUtilities.*;
import static com.garretwilson.text.TextUtilities.*;

import java.net.URI;

import javax.mail.internet.ContentType;

import com.garretwilson.lang.ObjectUtilities;

/**A default implementation of a model for an identifier such as text and/or an icon.
@author Garret Wilson
*/
public class DefaultLabelModel extends AbstractModel implements LabelModel
{

	/**The icon URI, which may be a resource URI, or <code>null</code> if there is no icon URI.*/
	private URI icon=null;

		/**@return The icon URI, which may be a resource URI, or <code>null</code> if there is no icon URI.*/
		public URI getIcon() {return icon;}

		/**Sets the URI of the icon.
		This is a bound property of type <code>URI</code>.
		@param newIcon The new URI of the icon, which may be a resource URI.
		@see #ICON_PROPERTY
		*/
		public void setIcon(final URI newIcon)
		{
			if(!ObjectUtilities.equals(icon, newIcon))	//if the value is really changing
			{
				final URI oldIcon=icon;	//get the old value
				icon=newIcon;	//actually change the value
				firePropertyChange(ICON_PROPERTY, oldIcon, newIcon);	//indicate that the value changed
			}			
		}

	/**The label text, which may include a resource reference, or <code>null</code> if there is no label text.*/
	private String label=null;

		/**@return The label text, which may include a resource reference, or <code>null</code> if there is no label text.*/
		public String getLabel() {return label;}

		/**Sets the text of the label.
		This is a bound property.
		@param newLabelText The new text of the label, which may include a resource reference.
		@see #LABEL_PROPERTY
		*/
		public void setLabel(final String newLabelText)
		{
			if(!ObjectUtilities.equals(label, newLabelText))	//if the value is really changing
			{
				final String oldLabel=label;	//get the old value
				label=newLabelText;	//actually change the value
				firePropertyChange(LABEL_PROPERTY, oldLabel, newLabelText);	//indicate that the value changed
			}			
		}

	/**The content type of the label text.*/
	private ContentType labelContentType=PLAIN_TEXT_CONTENT_TYPE;

		/**@return The content type of the label text.*/
		public ContentType getLabelContentType() {return labelContentType;}

		/**Sets the content type of the label text.
		This is a bound property.
		@param newLabelTextContentType The new label text content type.
		@exception NullPointerException if the given content type is <code>null</code>.
		@exception IllegalArgumentException if the given content type is not a text content type.
		@see #LABEL_CONTENT_TYPE_PROPERTY
		*/
		public void setLabelContentType(final ContentType newLabelTextContentType)
		{
			checkInstance(newLabelTextContentType, "Content type cannot be null.");
			if(labelContentType!=newLabelTextContentType)	//if the value is really changing
			{
				final ContentType oldLabelTextContentType=labelContentType;	//get the old value
				if(!isText(newLabelTextContentType))	//if the new content type is not a text content type
				{
					throw new IllegalArgumentException("Content type "+newLabelTextContentType+" is not a text content type.");
				}
				labelContentType=newLabelTextContentType;	//actually change the value
				firePropertyChange(LABEL_CONTENT_TYPE_PROPERTY, oldLabelTextContentType, newLabelTextContentType);	//indicate that the value changed
			}			
		}

	/**Default constructor.*/
	public DefaultLabelModel()
	{
		this(null);	//construct the class with no label
	}

	/**Label constructor.
	@param label The text of the label.
	*/
	public DefaultLabelModel(final String label)
	{
		super();	//construct the parent class
		this.label=label;	//save the label
	}
}
