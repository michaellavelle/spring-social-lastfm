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
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.social.lastfm.auth.LastFmAuthServiceProvider;
import org.springframework.social.lastfm.connect.LastFmConnectionData;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * An LastFm-Auth-based Connection implementation.
 * 
 * @author Michael Lavelle
 */
public class LastFmAuthConnection extends AbstractConnection<LastFm> {

	private final LastFmAuthServiceProvider serviceProvider;

	private LastFmAccessGrant lastFmAccessGrant;

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
			LastFmAccessGrant lastFmAccessGrant,
			LastFmAuthServiceProvider serviceProvider,
			ApiAdapter<LastFm> apiAdapter) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessGrant(lastFmAccessGrant);
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
		initAccessGrant(data.getLastFmAccessGrant());
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
					getImageUrl(), lastFmAccessGrant);
		}
	}

	// internal helpers

	private void initAccessGrant(LastFmAccessGrant lastFmAccessGrant) {
		this.lastFmAccessGrant = lastFmAccessGrant;
	}

	private void initApi() {
		api = serviceProvider.getApi(lastFmAccessGrant);
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