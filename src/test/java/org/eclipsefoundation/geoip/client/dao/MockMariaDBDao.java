/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;

import org.eclipsefoundation.geoip.client.model.ParameterizedSQLStatement;

import io.quarkus.test.Mock;

/**
 * Test stub for MariaDB DAO. This sits on top of the real DAO and returns empty
 * results to enable testing of resources without having to use real data or
 * external databases.
 * 
 * @author Martin Lowe
 *
 */
@Mock
@ApplicationScoped
public class MockMariaDBDao extends MariaDBDao {

	@Override
	public <T> List<T> get(ParameterizedSQLStatement stmt, Function<ResultSet, T> p) throws SQLException {
		return Collections.emptyList();
	}
}
