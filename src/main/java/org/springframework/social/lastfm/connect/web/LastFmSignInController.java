package org.springframework.social.lastfm.connect.web;

import javax.inject.Inject;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ExtensibleProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.lastfm.connect.LastFmConnectionFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
* Spring MVC Controller for handling the LastFm user sign-in flow.  We cannot use the existing ProviderSignInController,
* as LastFm's Auth scheme is neither OAuth1 or OAuth2.
* <ul>
* <li>POST /signin/{providerId} - Initiate user sign-in with {providerId}.</li>
* <li>GET /signin/{providerId}?oauth_token&oauth_verifier||code - Receive {providerId} authentication callback and establish the connection.</li>
* </ul>
* @author Michael Lavelle
*/
public class LastFmSignInController extends ExtensibleProviderSignInController {

	private final LastFmConnectSupport lastFmWebSupport = new LastFmConnectSupport();

	@Inject
	public LastFmSignInController(
			ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository usersConnectionRepository,
			SignInAdapter signInAdapter) {
		super(connectionFactoryLocator, usersConnectionRepository,
				signInAdapter);
	}

	@RequestMapping(value = "/{providerId}", method = RequestMethod.GET, params = {
			"!code", "!oauth_token" })
	public View lastFmAuthCallback(@PathVariable String providerId,
			@RequestParam("token") String token, NativeWebRequest request) {
		LastFmConnectionFactory connectionFactory = (LastFmConnectionFactory) connectionFactoryLocator
				.getConnectionFactory(providerId);
		Connection<?> connection = lastFmWebSupport.completeConnection(
				connectionFactory, request);
		return handleSignIn(connection, request);
	}

	/**
	 * Process a sign-in form submission by commencing the process of
	 * establishing a connection to the provider on behalf of the user. For
	 * OAuth1, fetches a new request token from the provider, temporarily stores
	 * it in the session, then redirects the user to the provider's site for
	 * authentication authorization. For OAuth2, redirects the user to the
	 * provider's site for authentication authorization.
	 */
	@RequestMapping(value = "/{providerId}", method = RequestMethod.POST)
	public RedirectView signIn(@PathVariable String providerId,
			NativeWebRequest request) {

		ConnectionFactory<?> connectionFactory = connectionFactoryLocator
				.getConnectionFactory(providerId);
		if (connectionFactory instanceof LastFmConnectionFactory) {
			return new RedirectView(lastFmWebSupport.buildAuthUrl(
					(LastFmConnectionFactory) connectionFactory, request, null));
		} else {
			return super.signIn(providerId, request);

		}
	}

}
