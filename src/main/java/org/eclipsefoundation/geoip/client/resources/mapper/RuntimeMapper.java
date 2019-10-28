/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.resources.mapper;

import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip2.exception.GeoIp2Exception;

/**
 * Captures exceptions and generates responses based on their causes.
 * 
 * @author Martin Lowe
 */
@Provider
public class RuntimeMapper implements ExceptionMapper<RuntimeException> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeMapper.class);

	@Override
	public Response toResponse(RuntimeException exception) {
		LOGGER.error(exception.getMessage(), exception);

		// respond to error
		Throwable cause = exception.getCause();
		Response out;
		if (cause instanceof UnknownHostException) {
			out = Response.status(Status.BAD_REQUEST).entity("Passed IP address was not a valid address").build();
		} else if (cause instanceof GeoIp2Exception) {
			out = Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("An error occured while interacting with the geo IP databases: " + cause.getMessage())
					.build();
		} else if (cause instanceof SQLException) {
			out = Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("An error occured while interacting with the subnet dataset: " + cause.getMessage())
					.build();
		} else {
			out = Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Unknown server error occured while processing request").build();
		}
		return out;
	}

}