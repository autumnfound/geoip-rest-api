/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.model;

import com.opencsv.bean.CsvBindByName;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Representation of the network information for rows in the MaxMind subnet
 * tables.
 * 
 * @author Martin Lowe
 *
 */
@RegisterForReflection
public class SubnetRange {

	@CsvBindByName(column = "geoname_id")
	private String geoname;
	@CsvBindByName(column = "registered_country_geoname_id")
	private String registeredGeoname;
	@CsvBindByName
	private String network;

	/**
	 * 
	 */
	public SubnetRange() {
	}

	/**
	 * @return the geoname
	 */
	public String getGeoname() {
		return geoname;
	}

	/**
	 * @param geoname the geoname to set
	 */
	public void setGeoname(String geoname) {
		this.geoname = geoname;
	}

	/**
	 * @return the registeredGeoname
	 */
	public String getRegisteredGeoname() {
		return registeredGeoname;
	}

	/**
	 * @param registeredGeoname the registeredGeoname to set
	 */
	public void setRegisteredGeoname(String registeredGeoname) {
		this.registeredGeoname = registeredGeoname;
	}

	/**
	 * @return the network
	 */
	public String getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(String network) {
		this.network = network;
	}

}
