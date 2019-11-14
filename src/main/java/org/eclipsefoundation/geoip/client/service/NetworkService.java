/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.service;

import java.util.List;

import org.eclipsefoundation.geoip.client.model.IPVersion;

/**
 * @author martin
 *
 */
public interface NetworkService {

	/**
	 * Retrieve a list of subnets for the current country for the given IPVersion
	 * 
	 * @param countryCode the ISO country code to retrieve subnets for
	 * @param ipv         the IPVersion to retrieve subnets for
	 * @return a list of subnets that match the criteria given, or an empty list if
	 *         no matches are found.
	 */
	List<String> getSubnets(String countryCode, IPVersion ipv);
}
