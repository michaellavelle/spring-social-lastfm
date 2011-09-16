package org.springframework.social.lastfm.connect;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Michael Lavelle
 */
public class LastFmSignature {

	private String apiKey;
	private String method;
	private String token;
	private String secret;


	public LastFmSignature(String apiKey, String method, String token,
			String secret) {
		try {

			this.apiKey = URLEncoder.encode(apiKey, "UTF-8");
			this.method = URLEncoder.encode(method, "UTF-8");
			this.token = URLEncoder.encode(token, "UTF-8");
			this.secret = URLEncoder.encode(secret, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		String s = "api_key" + apiKey + "method" + method + "token" + token
				+ secret;

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
