package org.springframework.social.lastfm.api.impl;

import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * Subclass of {@link DefaultResponseErrorHandler} that handles errors from
 * LastFm's API, interpreting them into appropriate exceptions.
 * 
 * @author Michael Lavelle
 */
class LastFmErrorHandler extends DefaultResponseErrorHandler {

}
