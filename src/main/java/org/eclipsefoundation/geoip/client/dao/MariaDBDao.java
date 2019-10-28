/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipsefoundation.geoip.client.model.ParameterizedSQLStatement;

import io.agroal.api.AgroalDataSource;

/**
 * Access layer for retrieving data from the MariaDB database. This dataset is
 * read-only, so only a get is required.
 * 
 * @author Martin Lowe
 *
 */
@ApplicationScoped
public class MariaDBDao {

	@Inject
	AgroalDataSource connectionPool;

	/**
	 * Creates a connection to the database, and queries using the passed data.
	 * 
	 * @param sql query to post to the database
	 * @return a resultset wrapped in an optional if results are found, or an empty
	 *         optional.
	 */
	public <T> List<T> get(ParameterizedSQLStatement stmt, Function<ResultSet, T> p) throws SQLException {
		if (stmt.getParams().length != stmt.getTypes().length) {
			throw new IllegalStateException(
					"Prepared statement parameter count does not equal statement types count, cannot continue");
		}
		try (Connection c = connectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(stmt.getSql())) {
			// prepare the statement for parameterized values
			for (int i = 0; i < stmt.getParams().length; i++) {
				Object param = stmt.getParams()[i];

				// check for the types present in DB table
				switch (stmt.getTypes()[i]) {
				case VARCHAR:
					ps.setString(i + 1, (String) param);
					break;
				case BOOLEAN:
					ps.setBoolean(i + 1, (boolean) param);
					break;
				default:
					// ignore, others unused
					break;
				}
			}
			// execute the statement, and return the results if they exist
			if (ps.execute()) {
				List<T> results = new LinkedList<>();
				// try w/ resource to auto close result set when done
				try (ResultSet rs = ps.getResultSet()) {
					// convert all results to the desired output
					while (rs.next()) {
						results.add(p.apply(rs));
					}
				}
				return results;
			}
		}
		// return empty list for no results
		return Collections.emptyList();
	}

}
