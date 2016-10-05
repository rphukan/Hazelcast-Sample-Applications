# Shops Near You API
Gives you the facility to add a shop with address. Stores the longitude and latitude of your shop using Google [Geocoding API](https://developers.google.com/maps/documentation/geocoding/intro). You can request for a shop mentioning your current latitude and longitude. The API would return you the shop nearest to you.

To add a resource
```
Resource			: /shop
Description			: Adds a shop. The API would find out the latitude and longitude of the 
					  shop address using Google Maps API and store it with the address.
Method				: POST
Request Content-type: application/json
Example Request		:	{
						  "shopName": "Amphitheatre Parkway",
						  "shopAddress": {
						    "number": "1600",
						    "addressLine1" : "Mountain View",
						    "addressLine2" : "CA",
						    "postCode": "94043"
						  }
						}
Success Response	: 200 OK
```

To get a resource
```
Resource				: /shop/{latitude}/{longitude}
Description				: Gives you the nearest shop from your latitude and longitude.
Method					: GET
Response Content-type	: application/json;charset=UTF-8
Example Response		: 200 OK	
							{
							  "shopName": "Amphitheatre Parkway",
							  "shopAddress": {
							    "number": "1600",
							    "addressLine1": "Mountain View",
							    "addressLine2": "CA",
							    "postCode": "94043"
							  },
							  "shopLatitude": 37.422364,
							  "shopLongitude": -122.084364
							}
```
## Hazelcast as Distributed data grid

## Hazelcast as Distributed caching


## How to run
You need Java 8 for running this project.


**Maven :**
If using maven you can use below command to run it

`mvn spring-boot:run -Dserver.port=8082`

**Gradle :**
If using gradle use below command to run it

`gradle bootRun`

**As jar :**
You can directly run it as a jar using below command. Change the path to the jar accordingly

`java -jar -Dserver.port=8082 shops-near-you.jar`

If you do not change the port using `-Dserver.port=8082` by default the embedded Tomcat runs on port `8080`

## If you are behind a proxy server
If you are running it behind a proxy server, set the proxy setting in `application.properties`. e.g.
```
config.proxy=true
config.proxyaddress=<your proxy host address>
config.proxyport=<your proxy port>
config.proxyuser=<user name>
config.proxypassword=<proxy password>
```

Else update the file as `config.proxy=false`.

