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
package org.springframework.social.lastfm.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.ServerDownException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * Subclass of {@link DefaultResponseErrorHandler} that handles errors from
 * LastFm's API, interpreting them into appropriate exceptions.
 * 
 * @author Michael Lavelle
 */
class LastFmErrorHandler extends DefaultResponseErrorHandler {

	private void handleUncategorizedError(ClientHttpResponse response,
			Map<Integer, String> errorDetails) {
		try {
			super.handleError(response);
		} catch (Exception e) {
			if (errorDetails != null && errorDetails.size() > 0) {
				String m = errorDetails.get(errorDetails.values().iterator()
						.next());
				throw new UncategorizedApiException(m, e);
			} else {
				throw new UncategorizedApiException(
						"No error details from LastFm", e);
			}
		}
	}

	void handleLastFmError(HttpStatus statusCode,
			Map<Integer, String> errorDetails) {

		String message = errorDetails.values().iterator().next();
		if (statusCode == HttpStatus.OK) {
			// TODO I've just put a single error code in here for now - need to
			// complete with other error codes
			if (errorDetails.containsKey(3)) {
				throw new ResourceNotFoundException(message);
			}
			if (errorDetails.containsKey(6)) {
				throw new ResourceNotFoundException(message);
			}

		} else if (statusCode == HttpStatus.BAD_REQUEST) {
			throw new ResourceNotFoundException(message);

		} else if (statusCode == HttpStatus.UNAUTHORIZED) {

			throw new NotAuthorizedException(message);
		} else if (statusCode == HttpStatus.FORBIDDEN) {

			throw new OperationNotPermittedException(message);
		} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
			throw new InternalServerErrorException(message);
		} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new ServerDownException(message);
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		Map<Integer, String> errorDetails = extractErrorDetailsFromResponse(response);

		if (errorDetails == null || errorDetails.size() == 0) {
			handleUncategorizedError(response, errorDetails);
		}

		handleLastFmError(response.getStatusCode(), errorDetails);

		// if not otherwise handled, do default handling and wrap with
		// UncategorizedApiException
		handleUncategorizedError(response, errorDetails);
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {

		if (super.hasError(response)) {
			return true;
		}
		// only bother checking the body for errors if we get past the default
		// error check
		String content = readFully(response.getBody());
		return content.startsWith("{\"error\":");

	}

	/*
	 * Attempts to extract SoundCloud error details from the response. Returns
	 * null if the response doesn't match the expected JSON error response.
	 */
	private Map<Integer, String> extractErrorDetailsFromResponse(
			ClientHttpResponse response) throws IOException {

		ObjectMapper mapper = new ObjectMapper(new JsonFactory());

		try {
			String json = readFully(response.getBody());
			Map<String, String> responseMap = mapper
					.<Map<String, String>> readValue(json,
							new TypeReference<Map<String, String>>() {
							});
			if (responseMap.containsKey("error")) {
				Map<Integer, String> errorMap = new HashMap<Integer, String>();
				errorMap.put(new Integer(responseMap.get("error")),
						responseMap.get("message"));
				return errorMap;
			} else {
				return null;
			}
		} catch (JsonParseException e) {
			return null;
		}

	}

	private String readFully(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		while (reader.ready()) {
			sb.append(reader.readLine());
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

}
