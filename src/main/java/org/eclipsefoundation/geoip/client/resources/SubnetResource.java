/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.resources;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipsefoundation.geoip.client.dao.MariaDBDao;
import org.eclipsefoundation.geoip.client.model.ParameterizedSQLStatement;

/**
 * Provides subnets for given country codes, in both ipv4 and ipv6 formats upon
 * request.
 * 
 * @author Martin Lowe
 */
@Path("/subnets")
@Produces(MediaType.APPLICATION_JSON)
public class SubnetResource {

	@Inject
	MariaDBDao dao;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{ipv: (ipv[46])}/{isoLocale: ([a-zA-Z]{2})}")
	public Response get(@PathParam("isoLocale") String isoLocale, @PathParam("ipv") String ipVersion) {
		// check the ipversion in the URI string to find table name
		String ipTableName;
		if ("ipv4".equals(ipVersion)) {
			ipTableName = "country_blocks_ipv4";
		} else {
			ipTableName = "country_blocks_ipv6";
		}
		// build a parameterized statement to pass to DAO for query
		ParameterizedSQLStatement stmt = new ParameterizedSQLStatement("SELECT network FROM " + ipTableName
				+ " b JOIN countries c ON (b.geoname_id = c.geoname_id OR b.registered_country_geoname_id = c.geoname_id) "
				+ "WHERE country_iso_code = ?", new Object[] { isoLocale }, new JDBCType[] { JDBCType.VARCHAR });

		// get the results
		try {
			List<String> results = dao.get(stmt, rs -> {
				try {
					return rs.getString("network");
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			});
			return Response.ok(results).build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
