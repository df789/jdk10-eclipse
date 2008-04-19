package com.guiseframework;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.mail.*;

import com.globalmentor.beans.PropertyBindable;
import com.globalmentor.io.IOOperation;
import com.globalmentor.net.Resource;
import com.globalmentor.net.URIPath;
import com.globalmentor.net.URIs;
import com.guiseframework.component.ApplicationFrame;
import com.guiseframework.platform.*;
import com.guiseframework.theme.Theme;

import static com.globalmentor.java.Classes.*;

/**An application running Guise.
To enable mail-related functionality, mail must be configured using {@link #setMailProperties(Map)}.
@author Garret Wilson
*/
public interface GuiseApplication extends Resource, PropertyBindable
{

	/**The environment bound property.*/
	public final static String ENVIRONMENT_PROPERTY=getPropertyName(GuiseApplication.class, "environment");
	/**The locales bound property.*/
	public final static String LOCALES_PROPERTY=getPropertyName(GuiseApplication.class, "locales");
	/**The resource bundle base name bound property.*/
	public final static String RESOURCE_BUNDLE_BASE_NAME_PROPERTY=getPropertyName(GuiseApplication.class, "resourceBundleBaseName");
	/**The style URI bound property.*/
	public final static String STYLE_URI_PROPERTY=getPropertyName(GuiseApplication.class, "styleURI");
	/**The theme URI bound property.*/
	public final static String THEME_URI_PROPERTY=getPropertyName(GuiseApplication.class, "themeURI");
	/**The bound property of whether this application applies themes.*/
	public final static String THEMED_PROPERTY=getPropertyName(GuiseApplication.class, "themed");

	/**The application-relative base path reserved for exclusive Guise use.*/
	public final static URIPath GUISE_RESERVED_BASE_PATH=new URIPath("~guise/");
	/**The application-relative base path to access all Guise assets.*/
	public final static URIPath GUISE_ASSETS_BASE_PATH=GUISE_RESERVED_BASE_PATH.resolve("assets/");
	/**The application-relative base path to access all Guise temporary assets.*/
	public final static URIPath GUISE_ASSETS_TEMP_BASE_PATH=GUISE_ASSETS_BASE_PATH.resolve("temp/");
	/**The base path of audio assets, relative to the application.*/
	public final static URIPath GUISE_ASSETS_AUDIO_PATH=GUISE_ASSETS_BASE_PATH.resolve("audio/");
	/**The base path of document assets, relative to the application.*/
	public final static URIPath GUISE_ASSETS_DOCUMENTS_PATH=GUISE_ASSETS_BASE_PATH.resolve("documents/");
	/**The base path of DTD assets, relative to the application.*/
	public final static URIPath GUISE_ASSETS_DTD_PATH=GUISE_ASSETS_BASE_PATH.resolve("dtd/");
	/**The base path of Flash assets, relative to the application.*/
	public final static URIPath GUISE_ASSETS_FLASH_PATH=GUISE_ASSETS_BASE_PATH.resolve("flash/");
	/**The base path of JavaScript assets, relative to the application.*/
	public final static URIPath GUISE_ASSETS_JAVASCRIPT_PATH=GUISE_ASSETS_BASE_PATH.resolve("javascript/");
	/**The base path of theme assets, relative to the application.*/
	public final static URIPath GUISE_ASSETS_THEMES_PATH=GUISE_ASSETS_BASE_PATH.resolve("themes/");
		/**The base path of the default Guise theme, relative to the application.*/
		public final static URIPath GUISE_ROOT_THEME_BASE_PATH=GUISE_ASSETS_THEMES_PATH.resolve("root/");
			/**The path of the root Guise theme, relative to the application.*/
			public final static URIPath GUISE_ROOT_THEME_PATH=GUISE_ROOT_THEME_BASE_PATH.resolve("root.theme.turf");
		/**The base path of the basic Guise theme, relative to the application.*/
		public final static URIPath GUISE_BASIC_THEME_BASE_PATH=GUISE_ASSETS_THEMES_PATH.resolve("basic/");
			/**The path of the basic Guise theme, relative to the application.*/
			public final static URIPath GUISE_BASIC_THEME_PATH=GUISE_BASIC_THEME_BASE_PATH.resolve("basic.theme.turf");
	/**The base path of the default Guise theme cursors, relative to the application.*/
	public final static URIPath GUISE_ROOT_THEME_CURSORS_PATH=GUISE_ROOT_THEME_BASE_PATH.resolve("cursors/");

	/**@return The application locale used by default if a new session cannot determine the users's preferred locale.*/
//TODO del	public Locale getDefaultLocale();

	/**Sets the application locale used by default if a new session cannot determine the users's preferred locale.
	This is a bound property.
	@param newDefaultLocale The new default application locale.
	@see #DEFAULT_LOCALE_PROPERTY
	*/
//TODO del	public void setDefaultLocale(final Locale newDefaultLocale);

	/**@return The thread-safe set of locales supported by this application.*/
//TODO del	public Set<Locale> getSupportedLocales();

	/**@return The read-only non-empty list of locales supported by the application, with the first locale the default used if a new session cannot determine the users's preferred locale.*/
	public List<Locale> getLocales();

	/**Sets the list of supported locales.
	This is a bound property.
	@param newLocales The new supported application locales.
	@exception NullPointerException if the given list of locales is <code>null</code>.
	@exception IllegalArgumentException if the given list of locales is empty.
	@see #LOCALES_PROPERTY
	*/
	public void setLocales(final List<Locale> newLocales);

	/**@return The base name of the resource bundle to use for this application, or <code>null</code> if no custom resource bundle is specified for this application..*/
	public String getResourceBundleBaseName();

	/**Changes the resource bundle base name.
	This is a bound property.
	@param newResourceBundleBaseName The new base name of the resource bundle, or <code>null</code> if no custom resource bundle is specified for this application.
	@see #RESOURCE_BUNDLE_BASE_NAME_PROPERTY
	*/
	public void setResourceBundleBaseName(final String newResourceBundleBaseName);

	/**@return The application local environment.*/
	public Environment getEnvironment();

	/**Sets the application local environment.
	This method will not normally be called directly from applications.
	This is a bound property.
	@param newEnvironment The new application local environment.
	@exception NullPointerException if the given environment is <code>null</code>.
	@see #ENVIRONMENT_PROPERTY
	*/
	public void setEnvironment(final Environment newEnvironment);

	/**Returns the properties of the mail manager.
	This method is guaranteed to return a non-<code>null</code> value after the application is installed.
	@return The properties of the mail manager.
	@exception IllegalStateException if the application is installed into a container but the mail properties has not been configured. 
	*/
	public Map<?, ?> getMailProperties();

	/**Sets properties of the mail manager.
	@param mailProperties The new properties of the mail manager
	@exception NullPointerException if the given properties is <code>null</code>.
	@exception IllegalStateException if the application has already been installed into a container. 
	*/
	public void setMailProperties(final Map<?, ?> mailProperties);

	/**Retrieves the current mail session.
	@return This application's mail session.
	@exception IllegalStateException if the application has not yet been installed into a container.
	@exception IllegalStateException if mail has not been configured for this application.
	*/
	public Session getMailSession();

	/**Retrieves the queue used to send mail.
	Mail added to this queue will be sent use the application's configured mail protocols.
	@return The queue used for to send mail.
	@exception IllegalStateException if the application has not yet been installed into a container.
	@exception IllegalStateException if mail has not been configured for this application.
	*/
	public Queue<Message> getMailSendQueue();

	/**@return Whether the application applies themes.*/
	public boolean isThemed();

	/**Sets whether the application applies themes.
	This is a bound property of type <code>Boolean</code>.
	@param newThemed <code>true</code> if the application should apply themes, else <code>false</code>.
	@see #THEMED_PROPERTY
	*/
	public void setThemed(final boolean newThemed);

	/**@return The absolute or application-relative URI of the application style, or <code>null</code> if the default style should be used.*/
	public URI getStyleURI();

	/**Sets the URI of the style of the application.
	This is a bound property.
	@param newStyle The URI of the application style, or <code>null</code> if the default style should be used.
	@see #STYLE_URI_PROPERTY
	*/
	public void setStyleURI(final URI newStyle);

	/**@return The URI of the application theme, to be resolved against the application base path.*/
	public URI getThemeURI();

	/**Sets the URI of the application theme.
	This is a bound property.
	@param newThemeURI The URI of the new application theme.
	@exception NullPointerException if the given theme URI is <code>null</code>.
	@see #THEME_URI_PROPERTY
	*/
	public void setThemeURI(final URI newThemeURI);

	/**@return The identifier for logging to a Data Collection System such as WebTrends, or <code>null</code> if no DCS ID is known.*/
	public String getDCSID();

	/**Sets the Data Collection Server log identifier.
	@param dcsID The identifier for logging to a Data Collection System such as WebTrends, or <code>null</code> if no DCS ID is known.
	*/
	public void setDCSID(final String dcsID);

	/**Determines the logical navigation path based upon a requested depict path.
	This method must preserve paths beginning with {@value #GUISE_RESERVED_BASE_PATH}.
	@param depictURI The plain absolute depict URI.
	@param depictPath The application-relative depict path.
	@return The application-relative logical navigation path.
	@throws NullPointerException if the given depict URI and/or depict path is <code>null</code>.
	@see #GUISE_RESERVED_BASE_PATH
	*/
	public URIPath getNavigationPath(final URI depictURI, final URIPath depictPath);

	/**Determines the depict URI based upon a navigation URI.
	This method must preserve paths beginning with {@value #GUISE_RESERVED_BASE_PATH}.
	@param depictURI The plain absolute depict URI.
	@param navigationURI The logical navigation URI, either absolute or relative to the application.
	@return The depict URI, either absolute or relative to the application.
	@throws NullPointerException if the given depict URI and/or logical URI is <code>null</code>.
	@see #GUISE_RESERVED_BASE_PATH
	*/
	public URI getDepictURI(final URI depictURI, final URI navigationURI);

	/**Registers a destination so that it can be matched against one or more paths.
	Any existing destinations for the path or path pattern is replaced.
	Existing destinations will take priority if a path matches multiple destination path patterns.
	@param destination The description of the destination at the appplication context-relative path or path pattern.
	@exception NullPointerException if the destination is <code>null</code>.
	*/
	public void addDestination(final Destination destination);

	/**Registers a destination so that it can be matched against one or more paths.
	Any existing destinations for the path or path pattern is replaced.
	@param destination The description of the destination at the appplication context-relative path or path pattern.
	@param priority Whether this destination takes priority over other destinations when there are multiple matches;
		if this destination has no path pattern, this parameter is ignored.
	@exception NullPointerException if the destination is <code>null</code>.
	*/
	public void addDestination(final Destination destination, final boolean priority);

	/**Associates multiple destinations with application context-relative paths or path patterns.
	All destinations are first cleared.
	Any existing destinations for the given context-relative paths are replaced.
	@param destinations The destinations to set.
	*/
	public void setDestinations(final List<Destination> destinations);

	/**Determines the destination associated with the given application context-relative path.
	This method first checks for a destination that matches the exact path as given;
	if no matching path is found, all destinations with path patterns are searched for a match.
	@param path The address for which a destination should be retrieved.
	@return The destination associated with the given path, or <code>null</code> if no destination is associated with the path. 
	@exception IllegalArgumentException if the provided path is absolute.
	*/
	public Destination getDestination(final URIPath path);

	/**Returns an iterable of destinations.
	Any changes to the iterable will not necessarily be reflected in the destinations available to the application.
	@return An iterable to the application's destinations.
	*/
	public Iterable<Destination> getDestinations();

	/**Determines if there is a destination associated with the given appplication context-relative path.
	This method first checks for a destination that matches the exact path as given;
	if no matching path is found, all destinations with path patterns are searched for a match.
	@param path The appplication context-relative path.
	@return <code>true</code> if there is destination associated with the given path, or <code>false</code> if no destination is associated with the given path.
	@exception NullPointerException if the path is <code>null</code>.
	@exception IllegalArgumentException if the provided path is absolute.
	*/
	public boolean hasDestination(final URIPath path);

	/**@return The Guise container into which this application is installed, or <code>null</code> if the application is not yet installed.*/
	public GuiseContainer getContainer();

	/**Creates a new session for the application on the given platform.
	@param platform The platform on which this session's objects are depicted.
	@return A new session for the application
	@exception NullPointerException if the given platform is <code>null</code>.
	*/
	public GuiseSession createSession(final Platform platform);

	/**Registers a session with this application.
	The Guise session has not yet been initialized when this method is called.
	@param guiseSession The Guise session to register with this Guise application.
	@exception IllegalStateException if the given session has alreaady been registered with this application.
	*/
	public void registerSession(final GuiseSession guiseSession);

	/**Unregisters a session from this application.
	The Guise session has already been uninitialized when this method is called. 
	@param guiseSession The Guise session to unregister from this Guise application.
	@exception IllegalStateException if the given session is not registered with this application.
	*/
	public void unregisterSession(final GuiseSession guiseSession);

	/**Retrieves a Guise session for the given UUID.
	@param uuid The UUID of the Guise session to retrieve. 
	@return The Guise session associated with the given UUID, or <code>null</code> if no Guise session is associated with the given UUID.
	@exception NullPointerException if the given UUID is <code>null</code>.
	*/
	public GuiseSession getSession(final UUID uuid);

	/**Creates a frame for the application.
	@return A new frame for the application.
	*/
	public ApplicationFrame createApplicationFrame();

	/**Reports the base path of the application.
	The base path is an absolute path that ends with a slash ('/'), indicating the base path of the navigation panels.
	@return The base path representing the Guise application, or <code>null</code> if the application is not yet installed.
	*/
	public URIPath getBasePath();

	/**Returns the home directory shared by all sessions of this application.
	This value is not available before the application is installed.
	@return The home directory of the application.
	@exception IllegalStateException if the application has not yet been installed into a container. 
	*/
	public File getHomeDirectory();

	/**Returns the log directory shared by all sessions of this application.
	This value is not available before the application is installed.
	@return The log directory of the application.
	@exception IllegalStateException if the application has not yet been installed into a container. 
	*/
	public File getLogDirectory();

	/**Returns the temprary directory shared by all sessions of this application.
	This value is not available before the application is installed.
	@return The temporary directory of the application.
	@exception IllegalStateException if the application has not yet been installed into a container. 
	*/
	public File getTempDirectory();

	/**Retrieves a writer suitable for recording log information for the application.
	The given base filename is appended with a representation of the current date.
	If a log writer for the same date is available, it is returned; otherwise, a new log writer is created.
	If the current date is a different day than that used for the current log writer for a given base filename, a new writer is created for the current date.
	@param baseFilename The base filename (e.g. "base.log") that will be used in generating a log file for the current date (e.g. "base 2003-02-01.log").
	@param initializer The encapsulation of any initialization that should be performed on any new writer, or <code>null</code> if no initialization is requested.
	@param uninitializer The encapsulation of any uninitialization that should be performed on any new writer, or <code>null</code> if no uninitialization is requested.
	@see GuiseApplication#getLogDirectory()
	*/
	public Writer getLogWriter(final String baseFilename, /*TODO fix final CalendarResolution calendarResolution, */final IOOperation<Writer> initializer, final IOOperation<Writer> uninitializer) throws IOException;

	/**@return Whether this application has been installed into a container at some base path.
	@see #getContainer()
	@see #getBasePath()
	*/
	public boolean isInstalled();

	/**Checks to ensure that this application is installed.
	@exception IllegalStateException if the application is not installed.
	@see #isInstalled()
	*/
	public void checkInstalled();

	/**Installs the application into the given container at the given base path.
	This method is called by {@link GuiseContainer} and should not be called directly by applications.
	@param container The Guise container into which the application is being installed.
	@param basePath The base path at which the application is being installed.
	@param homeDirectory The home directory of the application.
	@param logDirectory The log directory of the application.
	@param tempDirectory The temporary directory of the application.
	@exception NullPointerException if the container, base path, home directory, log directory, and/or temporary directory is <code>null</code>.
	@exception IllegalArgumentException if the context path is not absolute and does not end with a slash ('/') character.
	@exception IllegalStateException if the application is already installed.
	*/
	public void install(final AbstractGuiseContainer container, final URIPath basePath, final File homeDirectory, final File logDirectory, final File tempDirectory);

	/**Uninstalls the application from the given container.
	All log writers are closed.
	This method is called by {@link GuiseContainer} and should not be called directly by applications.
	@param container The Guise container into which the application is being installed.
	@exception IllegalStateException if the application is not installed or is installed into another container.
	*/
	public void uninstall(final GuiseContainer container);

	/**Resolves a relative or absolute path against the application base path.
	Relative paths will be resolved relative to the application base path. Absolute paths will be be considered already resolved.
	For an application path "/path/to/application/", resolving "relative/path" will yield "/path/to/application/relative/path",
	while resolving "/absolute/path" will yield "/absolute/path".
	@param path The path to be resolved.
	@return The path resolved against the application base path.
	@exception NullPointerException if the given path is <code>null</code>.
	@exception IllegalArgumentException if the provided path specifies a URI scheme (i.e. the URI is absolute) and/or authority (in which case {@link #resolveURI(URI)} should be used instead).
	@see #getBasePath()
	@see #resolveURI(URI)
	*/
	public URIPath resolvePath(final URIPath path);

	/**Resolves a URI against the application base path.
	Relative paths and {@value URIs#PATH_SCHEME} scheme URIs with relative paths will be resolved relative to the application base path.
	Absolute paths will be considered already resolved, as will absolute URIs.
	For an application base path "/path/to/application/", resolving "path:relative/path" or "relative/path" will yield "/path/to/application/relative/path",
	while resolving "path:/absolute/path" or "/absolute/path" will yield "/absolute/path". Resolving "http://example.com/path" will yield "http://example.com/path".
	@param uri The URI to be resolved.
	@return The URI resolved against the application base path.
	@exception NullPointerException if the given URI is <code>null</code>.
	@see #getBasePath()
	@see #resolvePath(URIPath)
	*/
	public URI resolveURI(URI uri);

	/**Changes an absolute path to an application-relative path.
	For an application base path "/path/to/application/", relativizing "/path/to/application/relative/path" will yield "relative/path"
	@param path The path to be relativized.
	@return The path relativized to the application base path.
	@exception NullPointerException if the given path is <code>null</code>.
	@exception IllegalArgumentException if the provided path specifies a URI scheme (i.e. the URI is absolute) and/or authority (in which case {@link #resolveURI(URI)} should be used instead).
	@see #getBasePath()
	@see #relativizeURI(URI)
	*/
	public URIPath relativizePath(final URIPath path);

	/**Changes a URI to an application-relative path.
	For an application base path "/path/to/application/", relativizing "http://www.example.com/path/to/application/relative/path" will yield "relative/path"
	@param uri The URI to be relativized.
	@return The URI path relativized to the application base path.
	@exception NullPointerException if the given URI is <code>null</code>.
	@see #getBasePath()
	@see #relativizePath(URIPath)
	*/
	public URIPath relativizeURI(final URI uri);

	/**Determines the locale-sensitive path of the given resource path.
	Based upon the provided locale, candidate resource paths are checked in the following order:
	<ol>
		<li> <var>resourceBasePath</var> + "_" + <var>language</var> + "_" + <var>country</var> + "_" + <var>variant</var> </li>
		<li> <var>resourceBasePath</var> + "_" + <var>language</var> + "_" + <var>country</var> </li>
		<li> <var>resourceBasePath</var> + "_" + <var>language</var> </li>
	</ol>	 
	@param resourceBasePath An application-relative base path to a resource in the application resource storage area.
	@param locale The locale to use in generating candidate resource names.
	@return The locale-sensitive path to an existing resource based upon the given locale, or <code>null</code> if no resource exists at the given resource base path or any of its locale candidates.
	@exception NullPointerException if the given resource base path and/or locale is <code>null</code>.
	@exception IllegalArgumentException if the given resource path is absolute.
	@exception IllegalArgumentException if the given path is not a valid path.
	@see #hasResource(String)
	*/
	public String getLocaleResourcePath(final String resourceBasePath, final Locale locale);

	/**Determines if the application has a resource available stored at the given resource path.
	The provided path is first normalized.
	@param resourcePath An application-relative path to a resource in the application resource storage area.
	@return <code>true</code> if a resource exists at the given resource path.
	@exception IllegalArgumentException if the given resource path is absolute.
	@exception IllegalArgumentException if the given path is not a valid path.
	*/
	public boolean hasResource(final String resourcePath);

	/**Retrieves an input stream to the resource at the given path.
	The provided path is first normalized.
	@param resourcePath An application-relative path to a resource in the application resource storage area.
	@return An input stream to the resource at the given resource path, or <code>null</code> if no resource exists at the given resource path.
	@exception IllegalArgumentException if the given resource path is absolute.
	@exception IllegalArgumentException if the given path is not a valid path.
	*/
	public InputStream getResourceInputStream(final String resourcePath);

	/**Retrieves an input stream to the entity at the given URI.
	The URI is first resolved to the application base path.
	If the URI represents one of this application's public resources, this implementation will return an input stream directly from that resource if possible rather than issuing a separate server request.
	This method supports read access to temporary public resources.
	@param uri A URI to the entity; either absolute or relative to the application.
	@return An input stream to the entity at the given resource URI, or <code>null</code> if no entity exists at the given resource path.
	@exception NullPointerException if the given URI is <code>null</code>.
	@exception IllegalStateException if a Guise public temporary resource was requested that requires a particular Guise session, and the request was not made from the required session.
	@exception IOException if there was an error connecting to the entity at the given URI.
	@see #resolveURI(URI)
	*/
	public InputStream getInputStream(final URI uri) throws IOException;

	/**Retrieves an input stream to the entity at the given path.
	If the URI represents one of this application's public resources, this implementation will return an input stream directly from that resource if possible rather than issuing a separate server request.
	This method supports read access to temporary public resources.
	@param path A path that is either relative to the application context path or is absolute.
	@return An input stream to the entity at the given resource path, or <code>null</code> if no entity exists at the given resource path.
	@exception NullPointerException if the given path is <code>null</code>.
	@exception IllegalArgumentException if the provided path specifies a URI scheme (i.e. the URI is absolute) and/or authority (in which case {@link #getInputStream(URI)} should be used instead).
	@exception IllegalStateException if a Guise public temporary resource was requested that requires a particular Guise session, and the request was not made from the required session.
	@exception IOException if there was an error connecting to the entity at the given path.
	@see #getInputStream(URI)
	*/
	public InputStream getInputStream(final URIPath path) throws IOException;

	/**Retrieves an output stream to the entity at the given URI.
	The URI is first resolved to the application base path.
	This method supports write access to temporary public resources.
	Write access to resources other than Guise public temporary files is currently unsupported. 
	@param uri A URI to the entity; either absolute or relative to the application.
	@return An output stream to the entity at the given resource URI.
	@exception NullPointerException if the given URI is <code>null</code>.
	@exception IllegalStateException if a Guise public temporary resource was requested that requires a particular Guise session, and the request was not made from the required session.
	@exception FileNotFoundException if a URI to a temporary file was passed before the file was created using {@link #createTempAsset(String, String, boolean)}.
	@exception IOException if there was an error connecting to the entity at the given URI.
	@see #resolveURI(URI)
	@see #createTempAsset(String, String, boolean)
	*/
	public OutputStream getOutputStream(final URI uri) throws IOException;

	/**Retrieves an output stream to the entity at the given path.
	This method supports write access to temporary public resources.
	Write access to resources other than Guise public temporary files is currently unsupported. 
	@param path A path that is either relative to the application context path or is absolute.
	@return An output stream to the entity at the given resource path.
	@exception NullPointerException if the given path is <code>null</code>.
	@exception IllegalArgumentException if the provided path specifies a URI scheme (i.e. the URI is absolute) and/or authority (in which case {@link #getOutputStream(URI)} should be used instead).
	@exception IllegalStateException if a Guise public temporary resource was requested that requires a particular Guise session, and the request was not made from the required session.
	@exception FileNotFoundException if a path to a temporary file was passed before the file was created using {@link #createTempAsset(String, String, boolean)}.
	@exception IOException if there was an error connecting to the entity at the given URI.
	@see #getOutputStream(URI)
	@see #createTempAsset(String, String, boolean)
	*/
	public OutputStream getOutputStream(final URIPath path) throws IOException;

	/**Creates a temporary asset available at an application navigation path.
	The file will be created in the application's temporary file directory.
	If the asset is restricted to the current Guise session, the asset will be deleted when the current Guise session ends.
	@param baseName The base filename to be used in generating the filename.
	@param extension The extension to use for the temporary file.
	@param restrictionSession The Guise session to which access access to the temporary file should be restricted, or <code>null</code> if there should be no access restriction.
	@return An application navigation path that can be used to access the asset.
	@exception NullPointerException if the given base name and/or extension is <code>null</code>.
	@exception IllegalArgumentException if the base name is the empty string.
	@exception IllegalStateException if the given restriction session is not registered with this application.
	@exception IOException if there is a problem creating the temporary asset.
	@see #getTempDirectory()
	@see #hasAsset(URIPath)
	*/
	public URIPath createTempAsset(final String baseName, final String extension, final GuiseSession restrictionSession) throws IOException;

	/**Determines whether this application has an asset at the given path.
	The path is first normalized. 
	This method supports Guise assets and temporary application assets.
	@param path The application-relative path of the asset.
	@return <code>true</code> if an asset exists at the given path.
	@exception IOException if there was an error accessing the asset.
	@see #createTempAsset(String, String, boolean)
	@see Guise#hasAsset(String)
	*/
	public boolean hasAsset(final URIPath path) throws IOException;

	/**Returns a URL to the asset at the given path.
	The path is first normalized. 
	This method supports Guise assets and temporary application assets.
	The returned URL represents internal access to the asset and should normally not be presented to users. 
	@param path The application-relative path of the asset.
	@param session The Guise session requesting the asset, or <code>null</code> if there is no session associated with the request.
	@return A URL to the asset, or <code>null</code> if there is no such asset.
	@exception IllegalStateException if an asset was requested that requires a particular Guise session different from the given Guise session.
	@exception IOException if there was an error accessing the asset.
	@see #createTempAsset(String, String, boolean)
	@see Guise#getAssetURL(String)
	*/
	public URL getAssetURL(final URIPath path, final GuiseSession guiseSession) throws IOException;

	/**Retrieves a resource bundle for the given theme in the given locale.
	The resource bundle retrieved will allow hierarchical resolution in the following priority:
	<ol>
		<li>Any resource defined by the application.</li>
		<li>Any resource defined by the theme.</li>
		<li>Any resource defined by a parent theme, including the default theme.</li>
		<li>Any resource defined by default by Guise.</li>
	</ol>
	@param theme The current theme in effect.
	@param locale The locale for which resources should be retrieved.
	@return A resolving resource bundle based upon the locale.
	@exception IOException if there was an error loading a resource bundle.
	@see #getResourceBundleBaseName()
	*/
	public ResourceBundle loadResourceBundle(final Theme theme, final Locale locale) throws IOException;

	/**Loads a theme from the given URI.
	All relative URIs are considered relative to the application.
	If the theme specifies no parent theme, the default parent theme will be assigned unless the theme is the default theme.
	@param themeURI The URI of the theme to load.
	@return A loaded theme with resolving parents loaded as well.
	@exception NullPointerException if the given theme URI is <code>null</code>.
	@throws IOException if there is an error loading the theme or one of its parents.
	*/
	public Theme loadTheme(final URI themeURI) throws IOException;

	/**Loads properties from a file in the home directory.
	The properties can be stored in XML or in the traditional properties format.
	@param propertiesPath The path to the properties file, relative to the application home directory.
	@return The properties loaded from the file at the given path.
	@exception NullPointerException if the given properties path is <code>null</code>.
	@exception IllegalArgumentException if the type of properties file is not recognized.
	@exception IOException if there is an error loading the properties.
	@see #getHomeDirectory()
	*/
	public Properties loadProperties(final String propertiesPath) throws IOException;

}
