/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.lastfm.pseudooauth2.connect.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * ServletFilter which intercepts calls to LastFm's auth callbackurl, adding a
 * new "code" parameter to the request with the same value as the "token"
 * parameter.
 * 
 * Allows existing ProviderSignInController and ConnectController
 * implementations from Spring Social to process LastFm auth callbacks, as both
 * classes expect a "code" parameter in the callback request
 * 
 * Note that the LastFmPseudoOAuth2ConnectionFactory (as opposed to the default
 * LastFmConnectionFactory) must be registered with the
 * ConnectionFactoryRegistry in order for this strategy to work, as
 * ProviderSignInController and ConnectController cannot be used with non-oauth
 * connection factories
 * 
 * @author Michael Lavelle
 */
public class LastFmPseudoOAuth2Filter implements Filter {

	/**
	 * The default signin callback path - can be overridden via filter config
	 * parameter "signinCallbackPath"
	 */
	private String signinCallbackPath = "/signin/lastfm";

	/**
	 * The default connect callback path - can be overridden via filter config
	 * parameter "connectCallbackPath"
	 */
	private String connectCallbackPath = "/connect/lastfm";

	@Override
	public void destroy() {

	}

	/**
	 * @param request
	 * @return The token passed back from a LastFm auth callback
	 */
	private String getToken(ServletRequest request) {
		return request.getParameter("token");
	}

	/**
	 * Are we processing a LastFm callback? Checks request uri and presence of
	 * token parameter
	 * 
	 * @param request
	 * @return true if processing a LastFm callback
	 */
	private boolean isLastFmCallback(HttpServletRequest request) {
		return (request.getMethod().toLowerCase().equals("get") && (request
				.getRequestURI().equals(signinCallbackPath) || request
				.getRequestURI().equals(connectCallbackPath)))
				&& getToken(request) != null;
	}

	/**
	 * Proceed normally if we are not processing a LastFm callback Otherwise,
	 * add an additional "code" parameter to the request with the same value as
	 * the "token" parameter
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		if (isLastFmCallback(request)) {
			chain.doFilter(new AddParameterRequestWrapper(request, "code",
					getToken(req)), resp);
		} else {
			chain.doFilter(req, resp);
		}
	}

	/**
	 * Allows callback path to be specified - if not specified defaults to
	 * "/signin/lastfm"
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {

		String signinCallbackPath = config
				.getInitParameter("signinCallbackPath");
		if (signinCallbackPath != null) {
			this.signinCallbackPath = signinCallbackPath;
		}

		String connectCallbackPath = config
				.getInitParameter("connectCallbackPath");
		if (connectCallbackPath != null) {
			this.connectCallbackPath = connectCallbackPath;
		}
	}

}
