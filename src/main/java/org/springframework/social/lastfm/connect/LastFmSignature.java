package org.springframework.social.lastfm.connect;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Michael Lavelle
 */
public class LastFmSignature {

	private String secret;
	private SortedMap<String,List<String>> sortedMap = new TreeMap<String,List<String>>();

	private void addParameter(String name,String value)
	{
		List<String> values = sortedMap.get(name);
		if (values == null)
		{
			values = new ArrayList<String>();
			sortedMap.put(name, values);
		}
		values.add(value);
	}

	public LastFmSignature(String apiKey, String method, String token,
			String secret,String sessionKey,Map<String,String> params) {
		

		this.secret = secret;
		 
			addParameter("api_key",apiKey);
			addParameter("method",method);
			addParameter("sk",sessionKey);
			addParameter("token",token);

			for (Map.Entry<String, String> entry : params.entrySet())
			{
				addParameter(entry.getKey(),entry.getValue());
			}

			

	}
	
	public LastFmSignature(String apiKey, String method, String token,
			String secret,Map<String,String> params) {
		


		this.secret = secret;
		 
			addParameter("api_key",apiKey);
			addParameter("method",method);
			addParameter("token",token);

			for (Map.Entry<String, String> entry : params.entrySet())
			{
				addParameter(entry.getKey(),entry.getValue());
			}

			

	}
	
	

	public String toString() {
		
		String s = "";
		for (Map.Entry<String,List<String>> entry : sortedMap.entrySet())
		{
			for (String value : entry.getValue())
			{
				s = s + entry.getKey() + value;
			}
		}
		s = s + secret;

		byte[] defaultBytes = s.getBytes();
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(s.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;

		} catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException(nsae);
		}

	}

}
