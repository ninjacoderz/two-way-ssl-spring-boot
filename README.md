# 2 way SSL with spring boot

Example client (nt-gateway) and service (nt-ms) code to show how to get 2 way SSL setup with self signed certificate.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

* Java 1.8
* Spring boot 2.1.2
* Java keytool utility


### Installing

Download nt-gateway and nt-ms projects in to your STS or Eclipse workspace. 

### Generate key command

keytool -genkeypair -alias nt-gateway -keyalg RSA -keysize 2048 -storetype JKS -keystore nt-gateway.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1 

keytool -genkeypair -alias nt-ms -keyalg RSA -keysize 2048 -storetype JKS -keystore nt-ms.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1

keytool -export -alias nt-gateway -file nt-gateway.crt -keystore nt-gateway.jks

keytool -export -alias nt-ms -file nt-ms.crt -keystore nt-ms.jks

keytool -import -alias nt-gateway -file nt-gateway.crt -keystore nt-ms.jks

keytool -import -alias nt-ms -file nt-ms.crt -keystore nt-gateway.jks

#### To generate pkc12
keytool -importkeystore -srckeystore browser.jks -destkeystore browser.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass changeit -dests
torepass changeit -srcalias browser -destalias browser -srckeypass changeit -destkeypass changeit -noprompt

### Password
changeit


## Reference: 
https://techexpertise.medium.com/exploring-key-stores-and-public-certificates-jks-723eb6b32a21
https://medium.com/@niral22/2-way-ssl-with-spring-boot-microservices-2c97c974e83
