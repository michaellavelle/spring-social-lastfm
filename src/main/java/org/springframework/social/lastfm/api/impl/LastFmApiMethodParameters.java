package org.springframework.social.lastfm.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.social.lastfm.connect.LastFmSignature;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author Michael Lavelle
 */
public class LastFmApiMethodParameters extends
		LinkedMultiValueMap<String, String> {

	
	private static final long serialVersionUID = 1L;

	public LastFmApiMethodParameters(String methodName, String apiKey,
			String token, String secret,Map<String,String> params) {
		this(methodName,apiKey,token,secret,null,params);
	}
	
	public LastFmApiMethodParameters(String methodName, String apiKey,
			String token, String secret,String sessionKey,Map<String,String> params) {
		LastFmSignature lastFmSignature = sessionKey != null ? 
				new LastFmSignature(apiKey,
				methodName, token, secret,sessionKey,params) 
				: new LastFmSignature(apiKey,
						methodName, token, secret,params);
		String apiSignature = lastFmSignature.toString();
		add("format", "json");
		add("api_sig", apiSignature);
		add("api_key", apiKey);
		if (sessionKey != null)
		{
			add("sk", sessionKey);
		}
		add("method", methodName);
		add("token", token);
		for (Map.Entry<String,String> entry : params.entrySet())
		{
			add(entry.getKey(),entry.getValue());
		}
	}
	
	public LastFmApiMethodParameters(String methodName, String apiKey,
			String token, String secret) {
		this(methodName, apiKey, token, secret, new HashMap<String,String>());
		
	}
	
	public LastFmApiMethodParameters(String methodName, String apiKey,
			String token, String secret,String sessionKey) {
		this(methodName,apiKey,token,secret,sessionKey,new HashMap<String,String>());
		
	}
	
	
	

}
