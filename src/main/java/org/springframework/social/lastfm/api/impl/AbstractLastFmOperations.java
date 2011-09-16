package org.springframework.social.lastfm.api.impl;

import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public abstract class AbstractLastFmOperations {

	protected final RestTemplate restTemplate;
	protected final boolean isAuthorizedForUser;

	public AbstractLastFmOperations(RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		this.restTemplate = restTemplate;
		this.isAuthorizedForUser = isAuthorizedForUser;
	}

}
