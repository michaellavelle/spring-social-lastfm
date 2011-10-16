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