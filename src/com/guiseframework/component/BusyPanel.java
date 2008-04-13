package com.guiseframework.component;

import static com.guiseframework.theme.Theme.GLYPH_BUSY;
import static com.guiseframework.theme.Theme.MESSAGE_BUSY;

import com.guiseframework.component.layout.*;

/**The default panel used to indicate Guise busy status.
@author Garret Wilson
*/
public class BusyPanel extends LayoutPanel
{

	/**Default constructor.*/
	public BusyPanel()
	{
		super(new RegionLayout());	//construct the parent class with a region layout
		final Label label=new Label();	//create a new label
		label.setGlyphURI(GLYPH_BUSY);	//show the busy icon
		label.setLabel(MESSAGE_BUSY);	//show the busy message
		add(label, new RegionConstraints(Region.CENTER));	//put the label in the center
	}

}