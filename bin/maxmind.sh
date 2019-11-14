#/bin/bash

if [ -z "$1" ]; then
  echo "An argument for location of DB files needs to be set" 
  exit 1
fi


echo "Starting MaxMind data retrieval!"
exec > maxmind.log 2>&1

BINARY_BIN="$1/bin"

echo "Getting data from MaxMind"
wget "https://geolite.maxmind.com/download/geoip/database/GeoLite2-Country-CSV.zip"
wget "https://geolite.maxmind.com/download/geoip/database/GeoLite2-Country.tar.gz"
wget "https://geolite.maxmind.com/download/geoip/database/GeoLite2-City.tar.gz"

echo "Extracting CSV data"
## Copy the Country CSV data to a location on the drive
unzip -o GeoLite2-Country-CSV.zip
echo "Copying data to $1/db to be uploaded"
DIR=$(pwd)
COUNTRY_DIR=$(find $DIR -maxdepth 1 -type d -name "GeoLite2-Country*")
mkdir -p $1/db
cp -ut $1/db $COUNTRY_DIR/GeoLite2-Country-Blocks* $COUNTRY_DIR/GeoLite2-Country-Locations-en.csv

echo "Extracting MaxMind GeoIP binaries"
## Extract the binary geoIP databases
mkdir -p $BINARY_BIN
tar -C $BINARY_BIN -xzvf  GeoLite2-Country.tar.gz --wildcards '*.mmdb'
tar -C $BINARY_BIN -xzvf  GeoLite2-City.tar.gz --wildcards '*.mmdb'
(cd $BINARY_BIN && find . -mindepth 1 -type f -exec mv -u {} $BINARY_BIN \; && find . -mindepth 1 -type d -exec rm -rf {} \;)

echo "Cleaning up extra files"
## Cleanup
rm -rf $COUNTRY_DIR
rm GeoLite2-Country-CSV.zip
rm GeoLite2-Country.tar.gz
rm GeoLite2-City.tar.gz

