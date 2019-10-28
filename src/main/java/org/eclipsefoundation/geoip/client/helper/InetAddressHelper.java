/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.net.InetAddresses;

/**
 * Helper class centralizing checks of IP addresses.
 * 
 * @author Martin Lowe
 */
public class InetAddressHelper {

	public static final List<String> INVALID_ADDRESSES_IPv4 = Collections
			.unmodifiableList(Arrays.asList("127.0.0.1", "0.0.0.0"));
	public static final List<String> INVALID_ADDRESSES_IPv6 = Collections.unmodifiableList(Arrays.asList("::1", "::"));

	/**
	 * Centralized IP address check that blacklists loopback and unspecified
	 * 
	 * @param address the IP address to check
	 * @return true if address is a valid IP address, false otherwise
	 */
	public static boolean validInetAddress(String address) {
		// return immediately if null
		if (address == null) {
			return false;
		}
		
		// check lightweight options first for invalid addresses
		if (INVALID_ADDRESSES_IPv4.contains(address) || INVALID_ADDRESSES_IPv6.contains(address)) {
			return false;
		}
		// Use Google Guava as larger more intensive check of string values
		return InetAddresses.isInetAddress(address);
	}

	// hide constructor
	private InetAddressHelper() {
	}
}
