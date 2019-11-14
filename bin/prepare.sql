CREATE DATABASE geoip;
USE geoip;
CREATE TABLE countries (
	geoname_id VARCHAR(255) NOT NULL,
	locale_code VARCHAR(255) NOT NULL,
	continent_code VARCHAR(255) NOT NULL,
	continent_name VARCHAR(255) NOT NULL,
	country_iso_code VARCHAR(255) NOT NULL,
	country_name VARCHAR(255) NOT NULL,
	is_in_european_union BOOLEAN NOT NULL,
	PRIMARY KEY (geoname_id)
) ENGINE = InnoDB;
CREATE TABLE country_blocks_ipv4 (
  network VARCHAR(255) NOT NULL,
  geoname_id VARCHAR(255) NOT NULL,
  registered_country_geoname_id VARCHAR(255),
  represented_country_geoname_id VARCHAR(255),
  is_anonymous_proxy BOOLEAN,
  is_satellite_provider BOOLEAN,
  PRIMARY KEY (network)
) ENGINE = InnoDB;
CREATE TABLE country_blocks_ipv6 (
  network VARCHAR(255) NOT NULL,
  geoname_id VARCHAR(255) NOT NULL,
  registered_country_geoname_id VARCHAR(255),
  represented_country_geoname_id VARCHAR(255),
  is_anonymous_proxy BOOLEAN,
  is_satellite_provider BOOLEAN,
  PRIMARY KEY (network)
) ENGINE = InnoDB;
LOAD DATA LOCAL INFILE "GeoLite2-Country-Locations-en.csv" INTO TABLE countries FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (geoname_id,locale_code,continent_code,continent_name,country_iso_code,country_name,is_in_european_union);
LOAD DATA LOCAL INFILE "GeoLite2-Country-Blocks-IPv4.csv" INTO TABLE country_blocks_ipv4 FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (network, geoname_id,registered_country_geoname_id,represented_country_geoname_id,is_anonymous_proxy,is_satellite_provider);
LOAD DATA LOCAL INFILE "GeoLite2-Country-Blocks-IPv6.csv" INTO TABLE country_blocks_ipv6 FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (network, geoname_id,registered_country_geoname_id,represented_country_geoname_id,is_anonymous_proxy,is_satellite_provider);

CREATE USER 'geoip'@'%';
GRANT SELECT ON geoip.* TO 'geoip'@'%';