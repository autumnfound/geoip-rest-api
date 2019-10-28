/* Copyright (c) 2019 Eclipse Foundation and others.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0
 * which is available at http://www.eclipse.org/legal/epl-v20.html,
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipsefoundation.geoip.client.resources;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * Test the listing resource endpoint, using fake data points to test solely the
 * responsiveness of the endpoint.
 * 
 * @author Martin Lowe
 */
@QuarkusTest
public class CountryResourceTest {

	// Toronto IP address range
	private static final String VALID_IPV4_ADDRESS = "72.137.192.0";
	// Google IE server address
	private static final String VALID_IPV6_ADDRESS = "2a00:1450:400a:804::2004";
	
	@Test
	public void testCityIPEndpoint() {
		given().when().get("/countries/"+VALID_IPV4_ADDRESS).then().statusCode(200);
		given().when().get("/countries/"+VALID_IPV6_ADDRESS).then().statusCode(200);
	}

	@Test
	public void testCityBadIPEndpoint() {
		// IPv4 tests
		given().when().get("/countries/bad.ip.add.res").then().statusCode(400);
		given().when().get("/countries/300.0.0.0").then().statusCode(400);
		given().when().get("/countries/1.300.0.0").then().statusCode(400);
		given().when().get("/countries/1.0.300.0").then().statusCode(400);
		given().when().get("/countries/1.0.0.300").then().statusCode(400);
		given().when().get("/countries/sample").then().statusCode(400);
		given().when().get("/countries/"+VALID_IPV4_ADDRESS+":8080").then().statusCode(400);
		// seems to be an issue with Google Guava code, only gets detected by MaxMind
		given().when().get("/countries/0.1.1.1").then().statusCode(500);
		// loopback + unspecified address
		given().when().get("/countries/127.0.0.1").then().statusCode(400);
		given().when().get("/countries/0.0.0.0").then().statusCode(400);
		
		// IPv6 tests
		given().when().get("/countries/bad:ip:add::res").then().statusCode(400);
		// loopback + unspecified address
		given().when().get("/countries/::").then().statusCode(400);
		given().when().get("/countries/::1").then().statusCode(400);
	}
}
