/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.service.impl;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.eclipsefoundation.geoip.client.dao.MariaDBDao;
import org.eclipsefoundation.geoip.client.model.IPVersion;
import org.eclipsefoundation.geoip.client.model.ParameterizedSQLStatement;
import org.eclipsefoundation.geoip.client.service.NetworkService;

/**
 * Loads Network subnet information via DB connections configured in the
 * application.properties.
 * 
 * @author Martin Lowe
 *
 */
public class SqlNetworkService implements NetworkService {

	@Inject
	MariaDBDao dao;

	@Override
	public List<String> getSubnets(String countryCode, IPVersion ipv) {
		Objects.requireNonNull(countryCode);
		// get the current table name for querying
		String ipTableName;
		if (IPVersion.IPV6.equals(ipv)) {
			ipTableName = "country_blocks_ipv6";
		} else {
			ipTableName = "country_blocks_ipv4";
		}
		// build a parameterized statement to pass to DAO for query
		ParameterizedSQLStatement stmt = new ParameterizedSQLStatement("SELECT network FROM " + ipTableName
				+ " b JOIN countries c ON (b.geoname_id = c.geoname_id OR b.registered_country_geoname_id = c.geoname_id) "
				+ "WHERE country_iso_code = ?", new Object[] { countryCode }, new JDBCType[] { JDBCType.VARCHAR });

		// get the results
		try {
			return dao.get(stmt, rs -> {
				try {
					return rs.getString("network");
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
