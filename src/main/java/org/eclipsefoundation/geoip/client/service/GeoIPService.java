/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.service;

import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;

/**
 * @author martin
 *
 */
public interface GeoIPService {

	City getCity(String ipAddr);
	
	Country getCountry(String ipAddr);
}
