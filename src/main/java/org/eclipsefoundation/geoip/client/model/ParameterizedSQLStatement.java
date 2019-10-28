/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.model;

import java.sql.JDBCType;

/**
 * @author Martin Lowe
 *
 */
public class ParameterizedSQLStatement {

	private String sql;
	private Object[] params;
	private JDBCType[] types;

	/**
	 * Builds a loaded parameterized statement to be used in querying dataset.
	 */
	public ParameterizedSQLStatement(String sql, Object[] params, JDBCType[] types) {
		this.sql = sql;
		this.params = params;
		this.types = types;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * @return the types
	 */
	public JDBCType[] getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(JDBCType[] types) {
		this.types = types;
	}

}
