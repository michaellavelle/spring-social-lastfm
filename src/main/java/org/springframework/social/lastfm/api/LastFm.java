package org.springframework.social.lastfm.api;

import org.springframework.social.lastfm.api.impl.LastFmTemplate;

/**
 * Interface specifying a basic set of operations for interacting with LastFm.
 * Implemented by {@link LastFmTemplate}.
 * 
 * @author Michael Lavelle
 */
public interface LastFm {

	public UserOperations userOperations();

}
