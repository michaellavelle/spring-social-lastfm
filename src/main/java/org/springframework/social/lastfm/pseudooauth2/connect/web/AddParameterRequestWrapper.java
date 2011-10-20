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
package org.springframework.social.lastfm.pseudooauth2.connect.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServletRequestWrapper whic adding a new parameter to the request, with
 * parameterName and parameterValue as specified in the constructor
 * 
 * @author Michael Lavelle
 */
public class AddParameterRequestWrapper extends HttpServletRequestWrapper {

	private String parameterName;
	private String parameterValue;

	public AddParameterRequestWrapper(HttpServletRequest request,
			String parameterName, String parameterValue) {
		super(request);
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}

	@Override
	public String getQueryString() {
		String query = super.getQueryString();
		if (query != null && query.length() > 0) {
			query = query + "&" + parameterName + "=" + parameterValue;
		} else {
			query = parameterName + "=" + parameterValue;
		}
		return query;
	}

	@Override
	public String getParameter(String name) {
		if (parameterName.equals(name)) {
			return parameterValue;
		} else {
			return super.getParameter(name);
		}
	}

	@Override
	public Map getParameterMap() {

		Map parameterMap = new HashMap();
		parameterMap.putAll(super.getParameterMap());
		parameterMap.put(parameterName, parameterValue);
		return parameterMap;

	}

	@Override
	public Enumeration getParameterNames() {

		final Enumeration existingEnumeration = super.getParameterNames();
		Enumeration newEnumeration = new Enumeration() {
			boolean first = true;

			@Override
			public boolean hasMoreElements() {

				return existingEnumeration.hasMoreElements();
			}

			@Override
			public Object nextElement() {
				if (first) {
					return parameterName;
				} else {
					first = false;
					return existingEnumeration.nextElement();
				}
			}

		};
		return newEnumeration;
	}

	@Override
	public String[] getParameterValues(String name) {
		if (parameterName.equals(name)) {
			String[] values = new String[1];
			values[0] = parameterValue;
			return values;
		} else {
			return super.getParameterValues(name);
		}
	}

}
