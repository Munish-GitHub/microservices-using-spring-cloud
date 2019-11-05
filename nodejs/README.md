Implementing Eureka and Zuul for Service Discovery and Dynamic Routing in JavaScript Microservices Running on Node.js. 
This app is simple Super hero and threat app. Hero and Threat are two services where Threat service call hero service to assign the threat to it.

In Microservices architecture, Threat service call Hero service through Zuul api gateway cloud which is configured on Eureka naming service. Other two services are also registered on Eureka server while they starts up.

Eureka server and Zuul services are created with Spring boot cloud. You can go to Java folder in repo and import the repo and projects. Start eureka server and zuul api gateway as mentioned in README.md of Java folder.

Lets start with Node (npm should be installed on machine):
After cloning repo, import node js projects in Visual Studio code.
Open three terminals, one for hero service, second for threat service and third for Eureka
On hero service and threat service terminals hit the below commands:
>npm install -y
>npm install --save express body-parser

On Eureka terminal, hit the below commands:
npm init -y
npm install request 
npm install ip

Now lets start the services:
Start hero service: 
node .\heroes.js 3838
Start threat service:
node .\threats.js 3131

The hero service, threat service and Zuul api gateway will registered there on Eureka console on browser. I hope, you have started eureka server and zuul api gateway already as mentioned above.

After succesful start of services go on Eureka server IP:
http://localhost:8761/

Now if everything works as expected then to test Open Rest client/Postman and hit below URL with post request and content type as application/json:
http://localhost:8765/threats-service/assignment
Request post
body: {"heroId": 1, "threatId": 1}
content-type: application/json

The respons should be below:
Status Code: 202 
response: {"id":1,"displayName":"Pisa tower is about to collapse.","necessaryPowers":["flying"],"img":"tower.jpg","assignedHero":1}

Thats it. You have configued node js service on Eureka naming server and the request of threat service is going through the zuul api gateway to Heores service.


	
Blog followed for Microservices with node js:
https://www.twilio.com/blog/building-javascript-microservices-node-js
