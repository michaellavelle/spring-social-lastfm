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
package org.springframework.social.lastfm.connect;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWithHeaders extends RestTemplate {
	 
	private HttpHeaders headers = new HttpHeaders();
	 
	public HttpHeaders getRequestHttpHeaders() {
	     return headers;
	}
	
	
	public RestTemplateWithHeaders(ClientHttpRequestFactory requestFactory,HttpHeaders headers) {
		super(requestFactory);
	}


	@Override
	protected <T> T doExecute(URI url, HttpMethod method,
			RequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor) throws RestClientException {
	

	      RequestCallbackDecorator requestCallbackDecorator =
	                 new RequestCallbackDecorator(requestCallback);
	 
	     return super.doExecute(url, method,
	              requestCallbackDecorator, responseExtractor);
		
	}



	private class RequestCallbackDecorator implements RequestCallback
	{
	 
	   public RequestCallbackDecorator(
	      RequestCallback targetRequestCallback) {
	         this.targetRequestCallback = targetRequestCallback;
	   }
	 
	   private RequestCallback targetRequestCallback;
	 
	   @Override
	   public void doWithRequest(ClientHttpRequest request)
	         throws IOException {
	 
	      request.getHeaders().putAll(headers);
	       
	      if (null != targetRequestCallback) {
	          targetRequestCallback.doWithRequest(request);
	      }
	   }
	}
	}