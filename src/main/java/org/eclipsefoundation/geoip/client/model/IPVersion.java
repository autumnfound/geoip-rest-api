/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.model;

/**
 * Enumerations representing IP versions for usage in subnet calls that can
 * return one result or the other.
 * 
 * @author Martin Lowe
 *
 */
public enum IPVersion {
	IPV4, IPV6;

	/**
	 * Returns the most appropriate IPVersion by matching on name ignoring case.
	 * 
	 * @param version the string to match to an IPVersion name
	 * @return the matching IPVersion, or null if none match.
	 */
	public static IPVersion getByName(String version) {
		for (IPVersion v : values()) {
			if (v.name().equalsIgnoreCase(version)) {
				return v;
			}
		}
		return null;
	}
}
