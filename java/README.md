# Microservices using spring-zuul-ribbon-feign-repo

Following technologies has been used:
-Spring Microservices
-Ribbon
-Feign
-zipkin
-zuul api gateway
-Eureka naming server
-JpaRepository

Environment:
IDE: Eclipse
Build: Maven
JDK: 1.8

The application is a simple currency exchange service build on above technologies. 

Two services are used:
1. currency-exchange-service : used to calculate the exchange rates of currencies. Three currencies exchange are being used. 
USD->INR
AUD->INR
EUR->INR
Currencies are configured in h2 in memory db.

2. currency-conversion-service : this service calls currency-exchange-service through Zuul api gateway. Thats the whole intention to build microservices using 
naming server and api gateway.

Let get started.

Import all the four projects using eclipse and build them.
After successful build, run the following:
	1. netflix-eureka-naming-server
		After successful run, hit the eureka server url to see in console.
		http://localhost:8761/
	2. Now run the netflix-zuul-api-gateway-server
		After successful run, hit the zuul server url to see its up in console.
		http://localhost:8765/actuator/health
		Zuul also register itself with eureka server so that it can help services to interact with each other.
	3. Start currency-exchange-service and hit url to see its status on console:
		localhost:8000/actuator/health
	4. Do the same thing for currency-conversion-service and hit the below url to see its status:
		http://localhost:8100/actuator/health

Now as all the services are up, you can check the eureka url: http://localhost:8761/ . All services will appear on eureka as they have registered themselves 
with it.
Once you see all services are avaiable on Eureka server hit the below server urls to see the exchange rates of currencies:
http://localhost:8765/currency-exchange-service/currency-exchange/from/USD/to/INR
http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR
http://localhost:8765/currency-exchange-service/currency-exchange/from/AUD/to/INR

The currency-conversion-service is calling currency-exchange-service through zuul api gateway configured on Eureka server to get the currency exchange rates and calculating quantites on top.

for example when you hit one of above:
http://localhost:8765/currency-exchange-service/currency-exchange/from/USD/to/INR
{"id":10001,"from":"USD","to":"INR","conversionMultiple":65.00,"port":8002}

Thats it. 
