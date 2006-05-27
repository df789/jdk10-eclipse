package com.guiseframework.platform.web;

import static com.garretwilson.lang.ObjectUtilities.*;
import static com.garretwilson.net.URIConstants.*;
import static com.garretwilson.net.URIUtilities.*;
import static com.garretwilson.servlet.ServletConstants.*;
import static com.garretwilson.servlet.http.HttpServletUtilities.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

import java.io.InputStream;
import java.net.*;
import java.security.Principal;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.garretwilson.util.Debug;
import com.guiseframework.*;

/**A Guise container for Guise HTTP servlets.
There will be one servlet Guise container per {@link ServletContext}, which usually corresponds to a single web application on a JVM.
@author Garret Wilson
*/
public class HTTPServletGuiseContainer extends AbstractGuiseContainer
{

	/**The absolute path, relative to the servlet context, of the resources directory.*/
	public final static String RESOURCES_DIRECTORY_PATH=ROOT_PATH+WEB_INF_DIRECTORY_NAME+PATH_SEPARATOR+"guise-application-resources"+PATH_SEPARATOR;

	/**The static, synchronized map of Guise containers keyed to servlet contexts.*/
	private final static Map<ServletContext, HTTPServletGuiseContainer> servletContextGuiseContainerMap=synchronizedMap(new HashMap<ServletContext, HTTPServletGuiseContainer>());

	/**Retrieves the Guise container associated with the given servlet context.
	Because the Java Servlet architecture does not provide the context path to the servlet context, this method can only be called after the first request, which will provide the context path.
	If no Guise container is associated with the servlet context, one is created.
	@param servletContext The servlet context with which this container is associated.
	@param baseURI The base URI of the container, an absolute URI that ends with the base path, which ends with a slash ('/'), indicating the base path of the application base paths.
	@return The Guise container associated with the given servlet context.
	@exception NullPointerException if the servlet context and/or base URI is <code>null</code>.
	@exception IllegalArgumentException if the base URI is not absolute or does not end with a slash ('/') character.
	*/
	public static HTTPServletGuiseContainer getGuiseContainer(final ServletContext servletContext, final URI baseURI)
	{
		synchronized(servletContextGuiseContainerMap)	//don't allow the map to be used while we do the lookup
		{
			HTTPServletGuiseContainer guiseContainer=servletContextGuiseContainerMap.get(servletContext);	//get the Guise container for this servlet context
			if(guiseContainer==null)	//if there is no Guise container
			{
				guiseContainer=new HTTPServletGuiseContainer(baseURI, servletContext);	//create a new Guise container for this servlet context, specifying the base URI
			}
			return guiseContainer;	//return the Guise container
		}
	}

	/**The servlet context with which this container is associated.*/
	private final ServletContext servletContext;

		/**@return The servlet context with which this container is associated.*/
		protected final ServletContext getServletContext() {return servletContext;}

	/**Servlet contains and container base URI constructor.
	@param baseURI The base URI of the container, an absolute URI that ends with the base path, which ends with a slash ('/'), indicating the base path of the application base paths.
	@param servletContext The servlet context with which this container is associated.
	@exception NullPointerException if the base URI and/or servlet context is <code>null</code>.
	@exception IllegalArgumentException if the base URI is not absolute or does not end with a slash ('/') character.
	*/
	public HTTPServletGuiseContainer(final URI baseURI, final ServletContext servletContext)
	{
		super(baseURI);	//construct the parent class
		this.servletContext=checkInstance(servletContext, "Servlet context cannot be null.");
	}

	/**Installs the given application at the given context path.
	This version is provided to expose the method to the servlet.
	@param contextPath The context path at which the application is being installed.
	@exception NullPointerException if either the application or context path is <code>null</code>.
	@exception IllegalArgumentException if the context path is not absolute and does not end with a slash ('/') character.
	@exception IllegalStateException if the application is already installed in some container.
	@exception IllegalStateException if there is already an application installed in this container at the given context path.
	*/
	protected void installApplication(final AbstractGuiseApplication application, final String contextPath)
	{
		super.installApplication(application, contextPath);	//delegate to the parent class
	}

	/**Uninstalls the given application.
	This version is provided to expose the method to the servlet.
	@exception NullPointerException if the application is <code>null</code>.
	@exception IllegalStateException if the application is not installed in this container.
	*/
	protected void uninstallApplication(final AbstractGuiseApplication application)
	{
		super.uninstallApplication(application);	//delegate to the parent class			
	}

	/**The synchronized map of Guise sessions keyed to HTTP sessions.*/
	private final Map<HttpSession, GuiseSession> guiseSessionMap=synchronizedMap(new HashMap<HttpSession, GuiseSession>());

	/**Retrieves a Guise session for the given HTTP session.
	A Guise session will be created if none is currently associated with the given HTTP session.
	When a Guise session is first created, its locale will be updated to match the language, if any, accepted by the HTTP request.
	This method can only be accessed by classes in the same package.
	This method should only be called by HTTP Guise session manager.
	@param guiseApplication The Guise application that will own the Guise session.
	@param httpRequest The HTTP request with which the Guise session is associated. 
	@param httpSession The HTTP session for which a Guise session should be retrieved. 
	@return The Guise session associated with the provided HTTP session.
	@see HTTPGuiseSessionManager
	*/
	protected GuiseSession getGuiseSession(final GuiseApplication guiseApplication, final HttpServletRequest httpRequest, final HttpSession httpSession)
	{
		synchronized(guiseSessionMap)	//don't allow anyone to modify the map of sessions while we access it
		{
			GuiseSession guiseSession=guiseSessionMap.get(httpSession);	//get the Guise session associated with the HTTP session
			if(guiseSession==null)	//if no Guise session is associated with the given HTTP session
			{
Debug.trace("+++creating Guise session", httpSession.getId());
/*TODO del
final Enumeration headerNames=httpRequest.getHeaderNames();	//TODO del
while(headerNames.hasMoreElements())
{
	final String headerName=(String)headerNames.nextElement();
	Debug.info("request header:", headerName, httpRequest.getHeader(headerName));
}
*/
				guiseSession=guiseApplication.createSession();	//ask the application to create a new Guise session
				final GuiseEnvironment environment=guiseSession.getEnvironment();	//get the new session's environment
				final Cookie[] cookies=httpRequest.getCookies();	//get the cookies in the request
				if(cookies!=null)	//if a cookie array was returned
				{
					for(final Cookie cookie:cookies)	//for each cookie in the request
					{
						final String cookieName=cookie.getName();	//get the name of this cookie
//					TODO del Debug.trace("Looking at cookie", cookieName, "with value", cookie.getValue());
						if(!"jsessionid".equalsIgnoreCase(cookieName))	//ignore the session ID TODO use a constant; testing
						{
							environment.setProperty(cookieName, decode(cookie.getValue()));	//put this cookie's decoded value into the session's environment
						}
					}
				}
				environment.setProperties(getUserAgentProperties(httpRequest));	//initialize the Guise environment user agent information
/*TODO del if can't be salvaged for Firefox
					//The "Accept: application/x-shockwave-flash" header is only sent in the first request from IE.
					//Unfortunately Firefox 1.5.0.3 doesn't send it at all.
					//see http://www.sitepoint.com/article/techniques-unearthed
					//see http://www.adobe.com/support/flash/releasenotes/player/rn_6.html
				environment.setProperty(CONTENT_APPLICATION_SHOCKWAVE_FLASH_ACCEPTED_PROPERTY,	//content.application.shockwave.flash.accepted
						Boolean.valueOf(isAcceptedContentType(httpRequest, APPLICATION_SHOCKWAVE_FLASH_CONTENT_TYPE, false)));	//see if Flash is installed
*/
				addGuiseSession(guiseSession);	//add and initialize the Guise session
				final Locale[] clientAcceptedLanguages=getAcceptedLanguages(httpRequest);	//get all languages accepted by the client
				guiseSession.requestLocale(asList(clientAcceptedLanguages));	//ask the Guise session to change to one of the accepted locales, if the application supports one
				guiseSessionMap.put(httpSession, guiseSession);	//associate the Guise session with the HTTP session
			}
			return guiseSession;	//return the Guise session
		}
	}

	/**Removes the Guise session for the given HTTP session.
	This method can only be accessed by classes in the same package.
	This method should only be called by HTTP Guise session manager.
	@param httpSession The HTTP session which should be removed along with its corresponding Guise session. 
	@return The Guise session previously associated with the provided HTTP session, or <code>null</code> if no Guise session was associated with the given HTTP session.
	@see HTTPGuiseSessionManager
	*/
	protected GuiseSession removeGuiseSession(final HttpSession httpSession)
	{
Debug.trace("+++removing Guise session", httpSession.getId());
		GuiseSession guiseSession=guiseSessionMap.remove(httpSession);	//remove the HTTP session and Guise session association
		if(guiseSession!=null)	//if there is a Guise session associated with the HTTP session
		{
			removeGuiseSession(guiseSession);	//remove the Guise session
//TODO del			guiseSession.destroy();	//let the Guise session know it's being destroyed so that it can clean up and release references to the application
		}
		return guiseSession;	//return the associated Guise session
	}

	/**Determines if the container has a resource available stored at the given resource path.
	The provided path is first normalized.
	@param resourcePath A container-relative path to a resource in the resource storage area.
	@return <code>true</code> if a resource exists at the given resource path.
	@exception IllegalArgumentException if the given resource path is absolute.
	@exception IllegalArgumentException if the given path is not a valid path.
	*/
	protected boolean hasResource(final String resourcePath)
	{
		try
		{
			return getServletContext().getResource(getContextAbsoluteResourcePath(resourcePath))!=null;	//determine whether we can get a URL to that resource
		}
		catch(final MalformedURLException malformedURLException)	//if the path is malformed
		{
			throw new IllegalArgumentException(malformedURLException);
		}
	}

	/**Retrieves an input stream to the resource at the given path.
	The provided path is first normalized.
	@param resourcePath A container-relative path to a resource in the resource storage area.
	@return An input stream to the resource at the given resource path, or <code>null</code> if no resource exists at the given resource path.
	@exception IllegalArgumentException if the given resource path is absolute.
	*/
	protected InputStream getResourceInputStream(final String resourcePath)
	{
		return getServletContext().getResourceAsStream(getContextAbsoluteResourcePath(resourcePath));	//try to get an input stream to the resource
	}

	/**Determines the servlet context-relative absolute path of the given container-relative path.
	The provided path is first normalized.
	@param containerRelativeResourcePath A container-relative path to a resource in the resource storage area.
	@return The absolute path to the resource relative to the servlet context.
	@exception IllegalArgumentException if the given resource path is absolute.
	*/
	protected String getContextAbsoluteResourcePath(final String containerRelativeResourcePath)
	{
		final String normalizedPath=normalizePath(containerRelativeResourcePath);	//normalize the path
		if(isAbsolutePath(normalizedPath))	//if the given path is absolute
		{
			throw new IllegalArgumentException("Resource path "+normalizedPath+" is not a relative path.");
		}
		return RESOURCES_DIRECTORY_PATH+normalizedPath;	//construct the absolute context-relative path to the resource
	}

	/**Looks up an application principal from the given ID.
	This version is provided to allow package access.
	@param application The application for which a principal should be returned for the given ID.
	@param id The ID of the principal.
	@return The principal corresponding to the given ID, or <code>null</code> if no principal could be determined.
	*/
	protected Principal getPrincipal(final AbstractGuiseApplication application, final String id)
	{
		return super.getPrincipal(application, id);	//delegate to the parent class
	}

	/**Looks up the corresponding password for the given principal.
	This version is provided to allow package access.
	@param application The application for which a password should e retrieved for the given principal.
	@param principal The principal for which a password should be returned.
	@return The password associated with the given principal, or <code>null</code> if no password is associated with the given principal.
	*/
	protected char[] getPassword(final AbstractGuiseApplication application, final Principal principal)
	{
		return super.getPassword(application, principal);	//delegate to the parent class			
	}

	/**Determines the realm applicable for the resource indicated by the given URI.
	This version is provided to allow package access.
	@param application The application for a realm should be returned for the given resouce URI.
	@param resourceURI The URI of the resource requested.
	@return The realm appropriate for the resource, or <code>null</code> if the given resource is not in a known realm.
	@see GuiseApplication#relativizeURI(URI)
	*/
	protected String getRealm(final AbstractGuiseApplication application, final URI resourceURI)
	{
		return super.getRealm(application, resourceURI);	//delegate to the parent class
	}

	/**Checks whether the given principal is authorized to access the resouce at the given application path.
	This version is provided to allow package access.
	@param application The application for which a principal should be authorized for a given resouce URI.
	@param resourceURI The URI of the resource requested.
	@param principal The principal requesting authentication, or <code>null</code> if the principal is not known.
	@param realm The realm with which the resource is associated, or <code>null</code> if the realm is not known.
	@return <code>true</code> if the given principal is authorized to access the resource represented by the given resource URI.
	*/
	protected boolean isAuthorized(final AbstractGuiseApplication application, final URI resourceURI, final Principal principal, final String realm)
	{
		return super.isAuthorized(application, resourceURI, principal, realm);	//delegate to the parent class
	}

}
