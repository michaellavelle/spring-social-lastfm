package org.springframework.social.lastfm.connect.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.core.GenericTypeResolver;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.AbstractConnection;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.auth.LastFmAuthServiceProvider;
import org.springframework.social.lastfm.connect.LastFmConnectionData;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * An LastFm-Auth-based Connection implementation.
 * @author Michael Lavelle
 */
public class LastFmAuthConnection extends AbstractConnection<LastFm> {

	private final LastFmAuthServiceProvider serviceProvider;

	private String token;

	private String sessionKey;

	public String getToken() {
		return token;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	private LastFm api;

	private LastFm apiProxy;

	/**
	 * Creates a new {@link LastFmAuthConnection} from a access grant response.
	 * Designed to be called to establish a new {@link LastFmAuthConnection}
	 * after receiving an access grant successfully. The providerUserId may be
	 * null in this case: if so, this constructor will try to resolve it using
	 * the service API obtained from the {@link OAuth2ServiceProvider}.
	 * 
	 * @param providerId
	 *            the provider id e.g. "lastfm".
	 * @param providerUserId
	 *            the provider user id (may be null if not returned as part of
	 *            the access grant)
	 * @param token
	 *            the granted access token
	 * @param sessionKey
	 *            the granted session key
	 * @param serviceProvider
	 *            the LastFM Auth-based ServiceProvider
	 * @param apiAdapter
	 *            the ApiAdapter for the ServiceProvider
	 */
	public LastFmAuthConnection(String providerId, String providerUserId,
			String token, String sessionKey,
			LastFmAuthServiceProvider serviceProvider,
			ApiAdapter<LastFm> apiAdapter) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(token, sessionKey);
		initApi();
		initApiProxy();
		initKey(providerId, providerUserId);
	}

	/**
	 * Creates a new {@link LastFmAuthConnection} from the data provided.
	 * Designed to be called when re-constituting an existing {@link Connection}
	 * from {@link LastFmConnectionData}.
	 * 
	 * @param data
	 *            the data holding the state of this connection
	 * @param serviceProvider
	 *            the LastFmAuth-based ServiceProvider
	 * @param apiAdapter
	 *            the ApiAdapter for the LastFmAuth-based ServiceProvider
	 */
	public LastFmAuthConnection(LastFmConnectionData data,
			LastFmAuthServiceProvider serviceProvider,
			ApiAdapter<LastFm> apiAdapter) {
		super(data, apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(data.getToken(), data.getSessionKey());
		initApi();
		initApiProxy();
	}

	// implementing Connection

	public boolean hasExpired() {
		return false;
	}

	public void refresh() {

	}

	public LastFm getApi() {
		if (apiProxy != null) {
			return apiProxy;
		} else {
			synchronized (getMonitor()) {
				return api;
			}
		}
	}

	public ConnectionData createData() {
		synchronized (getMonitor()) {
			return new LastFmConnectionData(getKey().getProviderId(), getKey()
					.getProviderUserId(), getDisplayName(), getProfileUrl(),
					getImageUrl(), getToken(), getSessionKey());
		}
	}

	// internal helpers

	private void initAccessTokens(String token, String sessionKey) {
		this.token = token;
		this.sessionKey = sessionKey;
	}

	private void initApi() {
		api = serviceProvider.getApi(token, sessionKey);
	}

	@SuppressWarnings("unchecked")
	private void initApiProxy() {
		Class<?> apiType = GenericTypeResolver.resolveTypeArgument(
				serviceProvider.getClass(), ServiceProvider.class);
		if (apiType.isInterface()) {
			apiProxy = (LastFm) Proxy.newProxyInstance(
					apiType.getClassLoader(), new Class[] { apiType },
					new ApiInvocationHandler());
		}
	}

	private class ApiInvocationHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			synchronized (getMonitor()) {
				if (hasExpired()) {
					throw new ExpiredAuthorizationException();
				}
				try {
					return method.invoke(LastFmAuthConnection.this.api, args);
				} catch (InvocationTargetException e) {
					throw e.getTargetException();
				}
			}
		}
	}

}