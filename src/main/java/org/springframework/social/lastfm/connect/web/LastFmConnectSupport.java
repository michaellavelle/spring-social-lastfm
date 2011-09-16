package org.springframework.social.lastfm.connect.web;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.social.connect.Connection;
import org.springframework.social.lastfm.auth.AccessGrant;
import org.springframework.social.lastfm.auth.LastFmAuthOperations;
import org.springframework.social.lastfm.auth.LastFmAuthParameters;
import org.springframework.social.lastfm.connect.LastFmConnectionFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Provides common LastFm connect support and utilities for Java web/servlet
 * environments. Used by
 * {@link LastFmProviderSignInController}.
 * 
 * @author Michael Lavelle
 */
public class LastFmConnectSupport {

	private URL applicationUrl;

	/**
	 * Configures the base secure URL for the application this controller is
	 * being used in e.g. <code>https://myapp.com</code>. Defaults to null. If
	 * specified, will be used to generate OAuth callback URLs. If not
	 * specified, LastFm callback URLs are generated from
	 * {@link HttpServletRequest HttpServletRequests}. You may wish to set this
	 * property if requests into your application flow through a proxy to your
	 * application server. In this case, the HttpServletRequest URI may contain
	 * a scheme, host, and/or port value that points to an internal server not
	 * appropriate for an external callback URL. If you have this problem, you
	 * can set this property to the base external URL for your application and
	 * it will be used to construct the callback URL instead.
	 * 
	 * @param applicationUrl
	 *            the application URL value
	 */
	public void setApplicationUrl(URL applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	/**
	 * Complete the connection to the OAuth2 provider.
	 * 
	 * @param connectionFactory
	 *            the 
	 *            LastFmConnectionFactory
	 * @param request
	 *            the current web request
	 * @return a new connection to the service provider
	 */
	public Connection<?> completeConnection(
			LastFmConnectionFactory connectionFactory, NativeWebRequest request) {
		String code = request.getParameter("token");
		AccessGrant accessGrant = connectionFactory.getLastFmAuthOperations()
				.exchangeForAccess(code, null);
		return connectionFactory.createConnection(accessGrant);
	}

	public String buildAuthUrl(LastFmConnectionFactory connectionFactory,
			NativeWebRequest request,
			MultiValueMap<String, String> additionalParameters) {
		LastFmAuthOperations oauthOperations = connectionFactory
				.getLastFmAuthOperations();
		LastFmAuthParameters parameters = getLastFmAuthParameters(request,
				additionalParameters);

		return oauthOperations.buildAuthorizeUrl(parameters);
	}

	private LastFmAuthParameters getLastFmAuthParameters(
			NativeWebRequest request,
			MultiValueMap<String, String> additionalParameters) {
		LastFmAuthParameters parameters = new LastFmAuthParameters(
				additionalParameters);
		parameters.setRedirectUri(callbackUrl(request));

		return parameters;
	}

	private String callbackUrl(NativeWebRequest request) {
		HttpServletRequest nativeRequest = request
				.getNativeRequest(HttpServletRequest.class);
		if (applicationUrl != null) {
			return applicationUrl + connectPath(nativeRequest);
		} else {
			return nativeRequest.getRequestURL().toString();
		}
	}

	private String connectPath(HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		return request.getServletPath() + (pathInfo != null ? pathInfo : "");
	}

}