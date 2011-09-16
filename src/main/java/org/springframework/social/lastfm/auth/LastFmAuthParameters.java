package org.springframework.social.lastfm.auth;

import java.util.List;
import java.util.Map;

import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.support.ParameterMap;
/**
* Parameters for building an LastFm authorize URL.
* @author Michael Lavelle
* @see LastFmAuthOperations#buildAuthorizeUrl(OAuth2Parameters)
*/
public final class LastFmAuthParameters extends ParameterMap {

	private static final String REDIRECT_URI = "cb";

	/**
	 * Creates a new LastFmAuthParameters map that is initially empty. Use the
	 * setter methods to add parameters after construction.
	 * 
	 * @see #setRedirectUri(String)
	 * @see #setScope(String)
	 * @see #setState(String)
	 * @see #set(String, String)
	 */
	public LastFmAuthParameters() {
		super(null);
	}

	/**
	 * Creates a new LastFmAuthParameters populated from the initial parameters
	 * provided.
	 * 
	 * @param parameters
	 *            the initial parameters
	 * @see #setRedirectUri(String)
	 * @see #setScope(String)
	 * @see #setState(String)
	 */
	public LastFmAuthParameters(Map<String, List<String>> parameters) {
		super(parameters);
	}

	/**
	 * The authorization callback url. This value must match the redirectUri
	 * registered with the LastFm. 
	 */
	public String getRedirectUri() {
		return getFirst(REDIRECT_URI);
	}

	/**
	 * Sets the authorization callback url. This value must match the
	 * redirectUri registered with LastFm. 
	 */
	public void setRedirectUri(String redirectUri) {
		set(REDIRECT_URI, redirectUri);
	}

}
