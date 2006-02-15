package com.guiseframework.test;

import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

import com.garretwilson.util.Debug;
import com.guiseframework.Bookmark;
import com.guiseframework.GuiseSession;
import com.guiseframework.Bookmark.Parameter;
import com.guiseframework.component.*;
import com.guiseframework.component.effect.*;
import com.guiseframework.component.layout.*;
import com.guiseframework.component.layout.ReferenceLayout.Constraints;
import com.guiseframework.demo.DemoUser;
import com.guiseframework.demo.EditUserPanel;
import com.guiseframework.event.*;
import com.guiseframework.geometry.Extent;
import com.guiseframework.model.*;
import com.guiseframework.style.RGBColor;
import com.guiseframework.validator.IntegerRangeValidator;
import com.guiseframework.validator.RegularExpressionStringValidator;
import com.guiseframework.validator.ValidationException;
import com.guiseframework.validator.ValueRequiredValidator;

/**Test panel for a home page.
@author Garret Wilson
*/
public class HomePanel extends DefaultNavigationPanel
{

	private TestFrame frame=null;
	final Label testLabel;

	/**Guise session constructor.
	@param session The Guise session that owns this panel.
	*/
	public HomePanel(final GuiseSession session)
	{
		this(session, null);	//construct the component, indicating that a default ID should be used
	}

	/**ID constructor.
	@param session The Guise session that owns this panel.
	@param id The component identifier, or <code>null</code> if a default component identifier should be generated.
	*/
	public HomePanel(final GuiseSession session, final String id)
	{
		super(session, id, new RegionLayout(session));	//construct the parent using a region layout
		setLabel("Home Panel Test");	//set the panel label

		final LayoutPanel contentPanel=new LayoutPanel(session, new FlowLayout(session, Flow.PAGE)); 

		
		final SelectLink selectLink=new SelectLink(session);
		selectLink.setLabel("This is a select link");
		selectLink.setToggle(true);
		contentPanel.add(selectLink);
		
/*TODO del		
    final GroupPanel cardPanelPanel=new GroupPanel(session, new FlowLayout(session, Flow.PAGE));    //create a panel flowing vertically
    cardPanelPanel.setLabelText("CardTabControl associated with CardPanel");
            //CardPanel
    final CardPanel cardPanel=new CardPanel(session);   //create a card panel
                //page 1
    final Panel<?> cardPanelPage1=new LayoutPanel(session); //create a panel to serve as the page
    final Heading cardPanelPage1Heading=new Heading(session, 0);    //create a top-level heading
    cardPanelPage1Heading.setLabelText("This is page 1.");   //set the text of the heading
    cardPanelPage1.add(cardPanelPage1Heading);  //add the heading to the page
    cardPanel.add(cardPanelPage1, new CardLayout.Constraints(new DefaultLabelModel(session, "Page 1")));    //add the panel with a label
                //page 2
    final Panel<?> cardPanelPage2=new LayoutPanel(session); //create a panel to serve as the page
    final Heading cardPanelPage2Heading=new Heading(session, 0);    //create a top-level heading
    cardPanelPage2Heading.setLabelText("This is page 2.");   //set the text of the heading
    cardPanelPage2.add(cardPanelPage2Heading);  //add the heading to the page
    cardPanel.add(cardPanelPage2, new CardLayout.Constraints(new DefaultLabelModel(session, "Page 2")));    //add the panel with a label
            //CardTabControl
    final CardTabControl cardPanelTabControl=new CardTabControl(session, cardPanel, Flow.LINE); //create a horizontal card tab control to control the existing card panel
    cardPanelPanel.add(cardPanelTabControl);    //place the tab control above the card panel to illustrate common usage
    cardPanelPanel.add(cardPanel);
    cardPanel.getLayout().getConstraints(cardPanelPage2).setEnabled(Boolean.FALSE);
    
    contentPanel.add(cardPanelPanel);
*/
		
/*TODO del		
		final CalendarMonthTableModel calendarMonthTableModel=new CalendarMonthTableModel(session);
		final Table calendarMonthTable=new Table(session, calendarMonthTableModel);
		contentPanel.add(calendarMonthTable);
		
		final CalendarControl calendarControl=new CalendarControl(session);
		contentPanel.add(calendarControl);
		
		
		calendarControl.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractGuisePropertyChangeListener<CalendarMonthTableModel, Calendar>()
				{
					public void propertyChange(final GuisePropertyChangeEvent<CalendarMonthTableModel, Calendar> propertyChangeEvent)
					{
						final Calendar newValue=propertyChangeEvent.getNewValue();	//get the new value
						if(newValue!=null)
						{
							new MessageOptionDialogFrame(session,	//create a new message dialog
									"You selected date: "+newValue.getTime().toString(),
									MessageOptionDialogFrame.Option.OK).open(true);
						}
					}
				});
*/
		
		
		//input panel
		final LayoutPanel inputPanel=new LayoutPanel(session, new FlowLayout(session, Flow.PAGE));	//create the input panel flowing vertically
		inputPanel.setBackgroundColor(RGBColor.AQUA_MARINE);
		final TextControl<Float> inputTextControl=new TextControl<Float>(session, Float.class);	//create a text input control to receive a float
		inputTextControl.setLabel("Input Number");	//add a label to the text input control
		inputTextControl.getModel().setValidator(new ValueRequiredValidator<Float>(session));	//install a validator requiring a value
		inputTextControl.setBackgroundColor(RGBColor.DARK_GOLDEN_ROD);

		inputTextControl.setDescription("This is a description of the first text control.");
		inputTextControl.setFlyoverEnabled(true);	//turn on flyovers

		inputTextControl.getFlyoverStrategy().setPreferredWidth(new Extent(15, Extent.Unit.EM));
		inputTextControl.getFlyoverStrategy().setPreferredHeight(new Extent(10, Extent.Unit.EM));
		
//TODO del when works		inputTextControl.addMouseListener(new TextControl.DefaultFlyoverStrategy<TextControl>(inputTextControl));
		
		inputPanel.add(inputTextControl);	//add the input control to the input panel
		
		final TextControl<Float> outputTextControl=new TextControl<Float>(session, Float.class);	//create a text input control to display the result
		outputTextControl.setLabel("Double the Number");	//add a label to the text output control
		outputTextControl.setEditable(false);	//set the text output control to read-only so that the user cannot modify it
		inputPanel.add(outputTextControl);	//add the output control to the input panel
		inputTextControl.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractGuisePropertyChangeListener<Float>()
				{
					public void propertyChange(final GuisePropertyChangeEvent<Float> propertyChangeEvent)
					{
						final Float newValue=propertyChangeEvent.getNewValue();	//get the new value
						try
						{
							outputTextControl.getModel().setValue(newValue*2);	//update the value
						}
						catch(final ValidationException validationException)	//we have no validator installed in the check control model, so we don't expect changing its value ever to cause any problems
						{
							throw new AssertionError(validationException);
						}							
					}
				});
		final CheckControl checkbox=new CheckControl(session, "checkbox");
		checkbox.setLabel("Enable the button \u278A");
		try
		{
			checkbox.getModel().setValue(Boolean.TRUE);
		}
		catch(final ValidationException validationException)	//we have no validator installed in the check control model, so we don't expect changing its value ever to cause any problems
		{
			throw new AssertionError(validationException);
		}										
		inputPanel.add(checkbox);
		
		final ListControl<Float> listControl=new ListControl<Float>(session, Float.class, new SingleListSelectionPolicy<Float>());	//create a list control allowing only single selections
		listControl.setLabel("Pick a Number");	//set the list control label
		listControl.setRowCount(5);
		listControl.getModel().add(new Float(10));
		listControl.getModel().add(new Float(20));
		listControl.getModel().add(new Float(30));
		listControl.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractGuisePropertyChangeListener<Float>()
				{
					public void propertyChange(final GuisePropertyChangeEvent<Float> propertyChangeEvent)
					{
						final Float newValue=propertyChangeEvent.getNewValue();	//get the new value
						try
						{
Debug.trace("list control changed value to", newValue);
							outputTextControl.getModel().setValue(newValue!=null ? newValue*2 : null);	//update the value
						}
						catch(final ValidationException validationException)	//we have no validator installed in the check control model, so we don't expect changing its value ever to cause any problems
						{
							throw new AssertionError(validationException);
						}							
					}
				});
		inputPanel.add(listControl);


		contentPanel.add(inputPanel);	//add the input panel to the temperature panel
		
		
		testLabel=new Label(session, "testLabel");
		testLabel.setDragEnabled(true);
		testLabel.setStyleID("title");
		testLabel.setLabel("This is label text from the model.");
		
		
		final Object testCookie=session.getEnvironment().getProperty("testCookie");
		if(testCookie instanceof String)
		{
			testLabel.setLabel((String)testCookie);
		}
		
		
		contentPanel.add(testLabel);	//add a new label
		
		
		
/*TODO del
		final Flash flash=new Flash(session);
		flash.setFlashURI(URI.create("test.swf"));
		flash.setPreferredWidth(new Extent(564));
		flash.setPreferredHeight(new Extent(474));
		contentPanel.add(flash);
*/
    final ImageSelectActionControl imageAction=new ImageSelectActionControl(session);
    imageAction.setImage(URI.create("http://www.garretwilson.com/photos/2000/february/cowcalf.jpg"));
    imageAction.setRolloverImage(URI.create("slider-thumb.gif"));
    contentPanel.add(imageAction);


		final Text testText=new Text(session);
		testText.setTextContentType(XHTML_FRAGMENT_CONTENT_TYPE);
//TODO bring back		testText.getModel().setTextResourceKey("test.html");
		testText.setText("this is <strong>good</strong> stuff");
		
		final Label boundLabel=new Label(session);
		boundLabel.setLabel("Button");
		boundLabel.setDescription("This is button flyover.");
		boundLabel.setFlyoverEnabled(true);	//turn on flyovers
		testText.add(boundLabel, new ReferenceLayout.Constraints("boundComponent"));

		contentPanel.add(testText);
		
		final LayoutPanel buttonPanel=new LayoutPanel(session, "testButtonPanel", new FlowLayout(session, Flow.LINE));	//create a panel flowing horizontally

		final Button testButton=new Button(session, "testButton");
		testButton.setLabel("Click here to go to the 'Hello World' demo.");
		testButton.setDescription("This is the hello world button.");
/*TODO fix
		testButton.setFlyoverEnabled(true);	//turn on flyovers
		testButton.getFlyoverStrategy().setPreferredWidth(new Extent(15, Extent.Unit.EM));
		testButton.getFlyoverStrategy().setPreferredHeight(new Extent(10, Extent.Unit.EM));
*/
		
		testButton.setCornerArcSize(Corner.LINE_FAR_PAGE_NEAR, Component.ROUNDED_CORNER_ARC_SIZE);
		testButton.setCornerArcSize(Corner.LINE_FAR_PAGE_FAR, Component.ROUNDED_CORNER_ARC_SIZE);
		
		testButton.addActionListener(new NavigateActionListener("helloworld"));
		buttonPanel.add(testButton);	//add a new button
		
		final Button testButton2=new Button(session, "testButton2");
		testButton2.setLabel("Click this button to change the text.");
		testButton2.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						testLabel.setLabel("You pressed the button!");
/*TODO del test						
						final int MAX_FACTOR=5;
						final Integer[][] multiplicationTableData=new Integer[MAX_FACTOR+1][MAX_FACTOR+1];	//create the table data array
						for(int rowIndex=MAX_FACTOR; rowIndex>=0; --rowIndex)	//for each row
						{
							for(int columnIndex=MAX_FACTOR; columnIndex>=0; --columnIndex)	//for each column
							{
								multiplicationTableData[rowIndex][columnIndex]=new Integer(rowIndex*columnIndex);	//fill this cell with data
							}
						}
						final String[] columnNames=new String[MAX_FACTOR+1];	//create the array of column names
						for(int columnIndex=MAX_FACTOR; columnIndex>=0; --columnIndex)	//for each column
						{
							columnNames[columnIndex]=Integer.toString(columnIndex);	//generate the column name
						}
						final Table multiplicationTable=new Table(session, Integer.class, multiplicationTableData, columnNames);	//create the table component
						multiplicationTable.setLabelText("Multiplication Table");	//give the table a label
*/

					  final DefaultOptionDialogFrame myDialog=new DefaultOptionDialogFrame(session, DefaultOptionDialogFrame.Option.OK);    //show the OK button
						final Heading heading=new Heading(session, 0);

						heading.setLabel("Delete Dialog");

						myDialog.setOptionContent(heading);

						myDialog.open();
						
						session.getEnvironment().setProperty("testCookie", "This is a successful cookie value.");
						
/*TODO bring back
						final Label label=new Label(session, new DefaultLabelModel(session, "Are you sure?"));
						
						final DefaultOptionDialogFrame confirmDialog=new DefaultOptionDialogFrame(session, label, DefaultOptionDialogFrame.Option.OK, DefaultOptionDialogFrame.Option.CANCEL);
						confirmDialog.setLabelText("Confirm your choice");
						confirmDialog.setPreferredWidth(new Extent(20, Extent.Unit.EM));
						confirmDialog.setPreferredHeight(new Extent(10, Extent.Unit.EM));
						confirmDialog.addPropertyChangeListener(ModalComponent.MODE_PROPERTY, new AbstractPropertyValueChangeListener<Mode>()
								{
									public void propertyValueChange(final PropertyValueChangeEvent<Mode> propertyValueChangeEvent)
									{
										if(propertyValueChangeEvent.getNewValue()==null)	//if modality is ended
										{
											testLabel.setLabelText("resulting option is "+confirmDialog.getModel().getValue());											
										}
									}
								});
						confirmDialog.open();
*/
					}
				});
		buttonPanel.add(testButton2);	//add a new button
		final Link testLink=new Link(session);
		testLink.setLabel("This is a link.");
		testLink.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{

						session.getEnvironment().removeProperty("testCookie");

						testLabel.setLabel("The link works.");

					
					
					}
				});
		buttonPanel.add(testLink);	//add a new button
		final Link modalLink=new Link(session);
		modalLink.setLabel("Test modal.");
		modalLink.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						getSession().navigateModal("edituser", new ModalNavigationAdapter()
								{
									/**Called when an a modal panel ends its modality.
									@param modalEvent The event indicating the panel ending modality and the modal value.
									*/
									public void modalEnded(final ModalEvent modalEvent)
									{
										
									}
								}
						
						);
					}
				});
		buttonPanel.add(modalLink);	//add a new button
		
		final Link helloLink=new Link(session);
		helloLink.setLabel("More Hello World.");
		helloLink.addActionListener(new NavigateActionListener("helloworld"));
		buttonPanel.add(helloLink);	//add the link

		final Link frameLink=new Link(session);
		frameLink.setLabel("Frame");
		frameLink.setDescription("This is a flyover for the frame link.");
		frameLink.setFlyoverEnabled(true);	//turn on flyovers
		frameLink.getFlyoverStrategy().setPreferredWidth(new Extent(15, Extent.Unit.EM));
		frameLink.getFlyoverStrategy().setPreferredHeight(new Extent(10, Extent.Unit.EM));
//TODO del		frameLink.getFlyoverStrategy().setOpenEffect(new OpacityFadeEffect(session, 1500));	//TODO testing openEffect
		frameLink.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						if(frame==null)
						{
							frame=new TestFrame(session);
							frame.setLabel("Test Frame");
						}
	Debug.trace("ready to set frame visible");
						frame.open();
					}
				});
		buttonPanel.add(frameLink);

		final Link modalFrameLink=new Link(session);
		modalFrameLink.setLabel("Modal Frame");
		modalFrameLink.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						final DefaultDialogFrame<Boolean> dialog=new DefaultDialogFrame<Boolean>(session, Boolean.class);
						dialog.setLabel("Test Dialog");
						
						final TextControl<Float> inputTextControl=new TextControl<Float>(session, Float.class);	//create a text input control to receive a float
						inputTextControl.setLabel("Input Number");	//add a label to the text input control
						inputTextControl.getModel().setValidator(new ValueRequiredValidator<Float>(session));	//install a validator requiring a value
						((Container<?>)dialog.getContent()).add(inputTextControl);	//add the input control to the input panel
						final TextControl<Float> outputTextControl=new TextControl<Float>(session, Float.class);	//create a text input control to display the result
						outputTextControl.setLabel("Double the Number");	//add a label to the text output control
						((Container<?>)dialog.getContent()).add(outputTextControl);	//add the output control to the input panel
						
						dialog.open(true);
					}
				});
		buttonPanel.add(modalFrameLink);

		contentPanel.add(buttonPanel);	//add the button panel to the panel

		
		

		
		final LayoutPanel linkPanel=new LayoutPanel(session, new FlowLayout(session, Flow.LINE));	//create a panel flowing horizontally
		
		final Link nearbyLink=new Link(session);
		nearbyLink.setLabel("Inside");
		nearbyLink.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						session.navigate(URI.create("http://www.cnn.com"));
					}
				});
		linkPanel.add(nearbyLink);
		
		final Link popupLink=new Link(session);
		popupLink.setLabel("Popup");
		popupLink.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						session.navigate(URI.create("http://www.cnn.com"), "another");
					}
				});
		linkPanel.add(popupLink);
	
		contentPanel.add(linkPanel);
		
		final Link listenerPopupLink=new Link(session);
		listenerPopupLink.setLabel("Popup from NavigateActionListener");
		listenerPopupLink.addActionListener(new NavigateActionListener(URI.create("http://www.about.com"), "another"));
		linkPanel.add(listenerPopupLink);
		
		
		final CheckControl check3=new CheckControl(session);	
		check3.setCheckType(CheckControl.CheckType.ELLIPSE);
		check3.setLabel("Third, disconnected check");
		contentPanel.add(check3);
		
		
		
		
		final LayoutPanel bookmarkPanel=new LayoutPanel(session, new FlowLayout(session, Flow.LINE));	//create a panel flowing horizontally

		final Link bookmark1Link=new Link(session);
		bookmark1Link.setLabel("Bookmark1");
		bookmark1Link.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						getSession().setBookmark(new Bookmark(new Bookmark.Parameter("bookmark", "1")));
					}
				});
		bookmarkPanel.add(bookmark1Link);

		final Link bookmark2Link=new Link(session);
		bookmark2Link.setLabel("Bookmark2");
		bookmark2Link.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						getSession().setBookmark(new Bookmark(new Bookmark.Parameter("bookmark", "2")));
					}
				});
		bookmarkPanel.add(bookmark2Link);

		final Link bookmark3Link=new Link(session);
		bookmark3Link.setLabel("Go Bookmark3");
		bookmark3Link.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						getSession().navigate(getSession().getNavigationPath(), new Bookmark(new Bookmark.Parameter("bookmark", "3")));
					}
				});
		bookmarkPanel.add(bookmark3Link);

		contentPanel.add(bookmarkPanel);	//add the bookmark panel to the panel
		
		
		final LayoutPanel sliderPanel=new LayoutPanel(session, new FlowLayout(session, Flow.LINE));

		
		final ValueModel<Integer> sliderModel=new DefaultValueModel<Integer>(session, Integer.class, 100);	//default to 100
		sliderModel.setValidator(new IntegerRangeValidator(session, 0, 100));	//set a range validator for the model
		
		final SliderControl<Integer> horizontalSlider=new SliderControl<Integer>(session, sliderModel, Flow.LINE);
		horizontalSlider.setLabel("Slider Value");
		horizontalSlider.setThumbImage(URI.create("slider-thumb.gif"));
		horizontalSlider.setTrackImage(URI.create("slider-track.gif"));
		sliderPanel.add(horizontalSlider);

		final SliderControl<Integer> verticalSlider=new SliderControl<Integer>(session, sliderModel, Flow.PAGE);
		verticalSlider.setLabel("Slider Value");
		sliderPanel.add(verticalSlider);
		
		final TextControl<Integer> sliderInput=new TextControl<Integer>(session, sliderModel);	//create a text input control
		sliderInput.setLabel("Slider Value");
		sliderPanel.add(sliderInput);

		contentPanel.add(sliderPanel);	//add the slider panel to the panel
		
		final TextControl<String> textInput=new TextControl<String>(session, "textInput", String.class);	//create a text input control
		textInput.setLabel("This is the text input label.");
		textInput.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractGuisePropertyChangeListener<String>()
				{
					public void propertyChange(GuisePropertyChangeEvent<String> propertyChangeEvent)
					{
						testLabel.setLabel(propertyChangeEvent.getNewValue());
						if(frame!=null)
						{
							frame.label.setLabel(propertyChangeEvent.getNewValue());
							frame.setLabel("Updated frame.");
						}
					}
				});
//TODO del		textInput.getModel().setValidator(new RegularExpressionStringValidator("[a-z]*"));
		contentPanel.add(textInput);
	
	
	
		final LayoutPanel horizontalPanel=new LayoutPanel(session, new FlowLayout(session, Flow.LINE));	//create a panel flowing horizontally
	
	
		final GroupPanel booleanPanel=new GroupPanel(session, new FlowLayout(session, Flow.PAGE));	//create a panel flowing vertically
//		booleanPanel.setDragEnabled(true);
		booleanPanel.setLabel("Check one of these");
		final CheckControl check1=new CheckControl(session, "check1");
		check1.setCheckType(CheckControl.CheckType.ELLIPSE);
		check1.setLabel("First check");
		booleanPanel.add(check1);	
		final CheckControl check2=new CheckControl(session, "check2");	
		check2.setCheckType(CheckControl.CheckType.ELLIPSE);
		check2.setLabel("Second check");
//		check2.getModel().setEnabled(false);	//TODO fix
		booleanPanel.add(check2);
		final ModelGroup<ValueModel<Boolean>> booleanGroup=new MutualExclusionPolicyModelGroup();
		booleanGroup.add(check1.getModel());
		booleanGroup.add(check2.getModel());
		booleanGroup.add(check3.getModel());
	
		horizontalPanel.add(booleanPanel);

		final Button testButtona=new Button(session, "testButton");
		testButtona.setLabel("Nuther button.");
//		testButtona.setDragEnabled(true);
		horizontalPanel.add(testButtona);	//add a new button
/*TODO fix		
		final Panel booleanPanela=new Panel(session, new FlowLayout(Axis.Y));	//create a panel flowing vertically
		booleanPanela.setLabelText("Check one of these");
		final CheckControl check1a=new CheckControl(session, "check1");
		check1a.setCheckType(CheckControl.CheckType.ELLIPSE);
		check1a.setLabelText("First check");
		booleanPanela.add(check1a);	
		final CheckControl check2a=new CheckControl(session, "check2");	
		check2a.setCheckType(CheckControl.CheckType.ELLIPSE);
		check2a.setLabelText("Second check");
		booleanPanela.add(check2a);	
		final ModelGroup<ValueModel<Boolean>> booleanGroupa=new MutualExclusionModelGroup();
		booleanGroupa.add(check1a.getModel());
		booleanGroupa.add(check2a.getModel());

		horizontalPanel.add(booleanPanela);
*/
		
		final Image image=new Image(session);
		image.setImage(URI.create("http://www.garretwilson.com/photos/2000/february/cowcalf.jpg"));
/*TODO fix
		image.setLabelText("Cow and Calf");
		image.getModel().setMessage("A cow and her minutes-old calf.");
*/
		image.setLabel("\u0622\u067E");
		image.setDescription("\u0628\u0627\u062A");
		image.setDragEnabled(true);
		horizontalPanel.add(image);

		sliderModel.addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractGuisePropertyChangeListener<Integer>()
				{
					public void propertyChange(GuisePropertyChangeEvent<Integer> propertyChangeEvent)
					{
						final Integer newValue=propertyChangeEvent.getNewValue();	//get the new value
						if(newValue!=null)	//if there is a new value
						{
							testLabel.setOpacity(newValue.floatValue()/100);	//update the label opacity
							image.setOpacity(newValue.floatValue()/100);	//update the image opacity
						}
					}
				});

		
		contentPanel.add(horizontalPanel);
		
/*TODO del		
		final Heading resourceHeading=new Heading(session, 2);
		resourceHeading.getModel().setLabelResourceKey("test.resource");
		add(resourceHeading);
*/

		final Label afterImageLabel=new Label(session);
		afterImageLabel.setLabel("This is a lot of text. ;alsjfd ;lkjas ;ljag ;lkjas g;lkajg; laksgj akjlshf lkjashd flkjsdhlksahlsadkhj asldkhjf ;sgdh a;lgkh a;glkha s;dglh asgd;");
		contentPanel.add(afterImageLabel);

		final ListControl<String> listSelectControl=new ListControl<String>(session, String.class, new SingleListSelectionPolicy<String>());
		listSelectControl.setLabel("Choose an option.");
		listSelectControl.getModel().add("The first option");
//TODO fix		listSelectControl.getModel().add(null);
		listSelectControl.getModel().add("The second option");
		listSelectControl.getModel().add("The third option");
		listSelectControl.getModel().add("The fourth option");
		
/*TODO fix
		listSelectControl.setValueRepresentationStrategy(new ListControl.DefaultValueRepresentationStrategy<String>(session)
				{
					public Label createComponent(final ListSelectModel<String> model, final String value, final int index, final boolean selected, final boolean focused)
					{
						return value!=null	//if there is a value
								? super.createComponent(model, value, index, selected, focused)	//return the default component
								: new Label(session, new DefaultLabelModel(session, "-"));	//return a component with the custom representation
					}
				});
*/
		
		contentPanel.add(listSelectControl);

		final TextAreaControl textAreaControl=new TextAreaControl(session, 25, 100, true);
		textAreaControl.setLabel("Type some text.");
/*TODO bring back
		try
		{
			textAreaControl.getModel().setValue("This is some text\nand some more on another line.\n\nSkipping two lines down, we find a line that is really long, is really, really, ;lkjas;lfk alkg; ;alkghj;alg lkjahq glkjh flkjhasdflkjhasdfl kjhasdf lkjh lkadhf lkshd flksadhf lksadhlskdqah slhjfg sd long.");
		}
		catch (ValidationException e)
		{
			throw new AssertionError(e);
		}
*/
		textAreaControl.getModel().setValidator(new RegularExpressionStringValidator(session, ".{0,10}", true));
		contentPanel.add(textAreaControl);
/*TODO del
		final Text text=new Text(session);
		//TODO del text.getModel().setText("This is some cool text! This is some text\nand some more on another line.\n\nSkipping two lines down, we find a line that is really long, is really, really, ;lkjas;lfk alkg; ;alkghj;alg lkjahq glkjh flkjhasdflkjhasdfl kjhasdf lkjh lkadhf lkshd flksadhf lksadhlskdqah slhjfg sd long. This is some text\nand some more on another line.\n\nSkipping two lines down, we find a line that is really long, is really, really, ;lkjas;lfk alkg; ;alkghj;alg lkjahq glkjh flkjhasdflkjhasdfl kjhasdf lkjh lkadhf lkshd flksadhf lksadhlskdqah slhjfg sd long.");
		text.getModel().setTextResourceKey("sample.html");
		text.getModel().setTextContentType(TextModel.XHTML_CONTENT_TYPE);
		contentPanel.add(text);
*/

//TODO del		getSession().setLocale(Locale.FRANCE);
//TODO del		getSession().setLocale(new Locale("ar"));

		final Integer[][] multiplicationTableData=new Integer[2][2];
		for(int row=0; row<2; ++row)
		{
			for(int column=0; column<2; ++column)
			{
				multiplicationTableData[row][column]=new Integer(row*column);
			}
		}
		final Table multiplicationTable=new Table(session, Integer.class, multiplicationTableData, "0", "1");
		multiplicationTable.setLabel("Multiplication Table");
		for(final TableColumnModel<?> column:multiplicationTable.getModel().getColumns())
		{
			column.setEditable(true);
		}
		contentPanel.add(multiplicationTable);

		final TreeControl treeControl=new TreeControl(session);
		final TreeNodeModel<String> firstItem=new DefaultTreeNodeModel<String>(session, String.class, "First Item");
		firstItem.add(new DefaultTreeNodeModel<String>(session, String.class, "Sub Item A"));
		firstItem.add(new DefaultTreeNodeModel<String>(session, String.class, "Sub Item B"));
		treeControl.getModel().getRootNode().add(firstItem);
		treeControl.getModel().getRootNode().add(new DefaultTreeNodeModel<String>(session, String.class, "Second Item"));
		treeControl.getModel().getRootNode().add(new DefaultTreeNodeModel<String>(session, String.class, "Third Item"));

		contentPanel.add(treeControl);

		final TabbedPanel tabbedPanel=new TabbedPanel(session);
		//input panel
		final LayoutPanel temperaturePanel=new LayoutPanel(session, new FlowLayout(session, Flow.PAGE));	//create the input panel flowing vertically
		final TextControl<Float> temperatureInput=new TextControl<Float>(session, Float.class);	//create a text input control to receive a float
		temperatureInput.setLabel("Input Temperature");	//add a label to the text input control
		temperatureInput.getModel().setValidator(new ValueRequiredValidator<Float>(session));	//install a validator requiring a value
		temperaturePanel.add(temperatureInput);	//add the input control to the input panel
		final TextControl<Float> temperatureOutput=new TextControl<Float>(session, Float.class);	//create a text input control to display the result
		temperatureOutput.setLabel("Output Temperature");	//add a label to the text output control
		temperatureOutput.setEditable(false);	//set the text output control to read-only so that the user cannot modify it
		temperaturePanel.add(temperatureOutput);	//add the output control to the input panel
		tabbedPanel.add(temperaturePanel, new CardLayout.Constraints("Temperature"));
	
		final LayoutPanel helloPanel=new LayoutPanel(session);
		final Heading helloWorldHeading=new Heading(session, 0);	//create a top-level heading
		helloWorldHeading.setLabel("Hello World!");	//set the text of the heading, using its model
		helloPanel.add(helloWorldHeading);
		tabbedPanel.add(helloPanel, new CardLayout.Constraints("Hello"));
		
		contentPanel.add(tabbedPanel);

		final TabControl<String> stringTabControl=new TabControl<String>(session, String.class, Flow.LINE);
		stringTabControl.getModel().add("First tab");
		stringTabControl.getModel().add("Second tab");
		stringTabControl.getModel().add("Third tab");
		contentPanel.add(stringTabControl);
		try
		{
			stringTabControl.getModel().setSelectedValues("First tab");
		}
		catch (ValidationException e)
		{
			throw new AssertionError(e);
		}

		
		
		final CardTabControl remoteTabControl=new CardTabControl(session, tabbedPanel, Flow.LINE);
		contentPanel.add(remoteTabControl);
		

		checkbox.getModel().addPropertyChangeListener(ValueModel.VALUE_PROPERTY, new AbstractGuisePropertyChangeListener<Boolean>()
				{
					public void propertyChange(final GuisePropertyChangeEvent<Boolean> propertyChangeEvent)
					{
						final Boolean newValue=propertyChangeEvent.getNewValue();	//get the new value
//TODO del						testButton.setDisplayed(newValue);	//update the button enabled state
						testButton.setVisible(newValue);	//update the button enabled state
//TODO del						testButton.setVisible(newValue);	//update the button enabled state
//TODO bring back						testButton.getModel().setEnabled(newValue);	//update the button enabled state
//TODO del Debug.trace("ready to set tabbed panel enabled to ", newValue);
//TODO del						tabbedPanel.getLayout().getConstraints(helloPanel).setEnabled(newValue);	//TODO testing
						remoteTabControl.getModel().setValueEnabled(helloPanel, newValue);	//TODO testing
					}
				});


		add(contentPanel, RegionLayout.CENTER_CONSTRAINTS);	//add the content panel in the center

		add(createMenu(session, Flow.LINE), RegionLayout.PAGE_START_CONSTRAINTS);	//add the pulldown menu at the top

//TODO fix		add(createMenu(session, Orientation.Flow.PAGE), RegionLayout.LINE_START_CONSTRAINTS);	//add the menu at the left

		add(createAccordionMenu(session, Flow.PAGE), RegionLayout.LINE_START_CONSTRAINTS);	//add the menu at the left
	}

	protected DropMenu createMenu(final GuiseSession session, final Flow flow)
	{
		final DropMenu menu=new DropMenu(session, flow);

		final DropMenu fileMenu=new DropMenu(session, "fileMenu", Flow.PAGE);
		fileMenu.setLabel("File");
		final Link openMenuLink=new Link(session, "openMenuItem");
		openMenuLink.setLabel("Open");
		fileMenu.add(openMenuLink);
		final Link closeMenuLink=new Link(session, "closeMenuItem");
		closeMenuLink.setLabel("Close");
		fileMenu.add(closeMenuLink);
		menu.add(fileMenu);

		final DropMenu editMenu=new DropMenu(session, "editMenu", Flow.PAGE);
		editMenu.setLabel("Edit");
		final Link copyMenuLink=new Link(session, "copyMenuItem");
		copyMenuLink.setLabel("Copy");
		editMenu.add(copyMenuLink);
		final Link cutMenuLink=new Link(session, "cutMenuItem");
		cutMenuLink.setLabel("Cut");
		editMenu.add(cutMenuLink);
		final Link pasteMenuLink=new Link(session, "pasteMenuItem");
		pasteMenuLink.setLabel("Paste");
		editMenu.add(pasteMenuLink);
		menu.add(editMenu);

		final DropMenu windowMenu=new DropMenu(session, "windowMenu", Flow.PAGE);
		windowMenu.setLabel("Window");

		final DropMenu arrangeMenu=new DropMenu(session, "arrangeMenu", Flow.PAGE);
		arrangeMenu.setLabel("Arrange");
		
		final Link tileMenuLink=new Link(session, "tileMenuItem");
		tileMenuLink.setLabel("Tile");
		arrangeMenu.add(tileMenuLink);
		final Link cascadeMenuLink=new Link(session, "cascadeMenuItem");
		cascadeMenuLink.setLabel("Cascade");
		arrangeMenu.add(cascadeMenuLink);
		windowMenu.add(arrangeMenu);
		menu.add(windowMenu);

			//GlobalMentor
		final Link globalmentorLink=new Link(session);
		globalmentorLink.setLabel("GlobalMentor");
		globalmentorLink.addActionListener(new NavigateActionListener(URI.create("http://www.globalmentor.com/")));
		menu.add(globalmentorLink);
		
		return menu;
	}


	protected AccordionMenu createAccordionMenu(final GuiseSession session, final Flow flow)
	{
		final AccordionMenu menu=new AccordionMenu(session, flow);

		final AccordionMenu fileMenu=new AccordionMenu(session, Flow.PAGE);
		fileMenu.setLabel("File");
		final Link openMenuLink=new Link(session);
		openMenuLink.setLabel("Open");
		fileMenu.add(openMenuLink);
		final Link closeMenuLink=new Link(session);
		closeMenuLink.setLabel("Close");
		fileMenu.add(closeMenuLink);
		menu.add(fileMenu);

		final AccordionMenu editMenu=new AccordionMenu(session, Flow.PAGE);
		editMenu.setLabel("Edit");
		final Message message1=new Message(session);
		message1.setMessage("This is a message to show.");
		editMenu.add(message1);
		menu.add(editMenu);
		
		editMenu.addActionListener(new ActionListener()	//testing accordion menu action
				{
					public void actionPerformed(ActionEvent actionEvent)
					{
						testLabel.setLabel("You pressed the accordion edit menu!");
					}
				});

		final AccordionMenu stuffMenu=new AccordionMenu(session, Flow.PAGE);
		stuffMenu.setLabel("Stuff");
		final Message message2=new Message(session);
		message2.setMessage("This is a message to show.");
		stuffMenu.add(message2);
		menu.add(stuffMenu);

		return menu;
	}

	protected static class TestFrame extends DefaultFrame
	{
		protected final Label label;
		
		public TestFrame(final GuiseSession session)
		{
			super(session);
//TODO del			final LayoutPanel contentPanel=new LayoutPanel(session, new FlowLayout(session, Flow.PAGE)); 
			label=new Label(session);
			label.setLabel("This is frame content");
			setContent(label);
/*TODO del; testing scrolled flyovers			
			contentPanel.add(label);
			
			final Text text=new Text(session);
			text.getModel().setText("This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
			);
			contentPanel.add(text);

			final Link frameLink=new Link(session);
			frameLink.setLabelText("Frame");
			frameLink.getModel().setDescription("This is a flyover for the frame link.");
			frameLink.setFlyoverEnabled(true);	//turn on flyovers
			frameLink.getFlyoverStrategy().setPreferredWidth(new Extent(15, Extent.Unit.EM));
			frameLink.getFlyoverStrategy().setPreferredHeight(new Extent(10, Extent.Unit.EM));
			contentPanel.add(frameLink);

			final Text otherText=new Text(session);
			otherText.getModel().setText("This is some text. It is added so that it will make the frame wrap and keep going."
					+" This is some text. It is added so that it will make the frame wrap and keep going."
			);
			contentPanel.add(otherText);

			setContent(contentPanel);
		
			setPreferredWidth(new Extent(15, Extent.Unit.EM));
			setPreferredHeight(new Extent(10, Extent.Unit.EM));
*/
		}
	}
}