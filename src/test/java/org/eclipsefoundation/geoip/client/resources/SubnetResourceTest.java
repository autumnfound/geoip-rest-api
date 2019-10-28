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
 * Tests for the Subnets resource, which checks and promises the given endpoints
 * with some parameter checking.
 * 
 * @author Martin Lowe
 *
 */
@QuarkusTest
public class SubnetResourceTest {

	@Test
	public void testSubnetsEndpoint() {
		given().when().get("/subnets/ipv4/ca").then().statusCode(200);
		given().when().get("/subnets/ipv6/ca").then().statusCode(200);
	}

	@Test
	public void testSubnetsBadLocaleEndpoint() {
		// bad ipv4 calls
		given().when().get("/subnets/ipv4/").then().statusCode(500);
		given().when().get("/subnets/ipv4/can").then().statusCode(500);
		given().when().get("/subnets/ipv4/01").then().statusCode(500);

		// bad ipv6 calls
		given().when().get("/subnets/ipv6/").then().statusCode(500);
		given().when().get("/subnets/ipv6/can").then().statusCode(500);
		given().when().get("/subnets/ipv6/01").then().statusCode(500);

		// check other permutations (regex endpoint)
		given().when().get("/subnets/ipv5/ca").then().statusCode(500);
		given().when().get("/subnets/ipvfour/ca").then().statusCode(500);
		given().when().get("/subnets/ipv/ca").then().statusCode(500);

	}
}
