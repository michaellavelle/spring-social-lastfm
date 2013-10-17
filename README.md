This project is a work in progress with a few methods of the LastFm Api implemented so far. These include:

* Retrieving a LastFm user's basic profile info
* Retrieving a LastFm user's Loved, Recent, or Top Tracks
* Retrieving a LastFm user's friends and neighbours lists 
* Retrieving a LastFm user's shouts
* Performing a shout
* Loving and unloving a track
* Performing a basic track search
* Updating a LastFm users' now listening status
* Simple scrobbling

[![endorse](https://api.coderwall.com/michaellavelle/endorsecount.png)](https://coderwall.com/michaellavelle)


Please note that as LastFm's auth scheme is *neither* OAuth1 or OAuth2, there are a few constraints concerning the use 
of this module:

In order to use Spring-Social's existing ProviderSignInController or ConnectController (which require OAuth2 scheme)
the LastFmPseudoOAuth2ConnectionFactory (not the standard LastFmConnectionFactory) 
must be registered with the ConnectionFactoryRegistry *AND* the LastFmPseudoOAuth2Filter must be registered in 
your filter chain in web.xml.

The LastFmPseudoOAuth2ConnectionFactory conforms the LastFmConnectionFactory to OAuth2 spec, and the LastFmPseudoOAuth2Filter
modifies the auth-callback from LastFm to that required by ProviderSignInController or ConnectController.



