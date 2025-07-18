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

1013  keytool -genkeypair -alias nt-gateway -keyalg RSA -keysize 2048 -storetype JKS -keystore nt-gateway.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1 
1014  keytool -genkeypair -alias nt-ms -keyalg RSA -keysize 2048 -storetype JKS -keystore nt-ms.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1
1015  keytool -export -alias nt-gateway -file nt-gateway.crt -keystore nt-gateway.jks
1016  keytool -export -alias nt-ms -file nt-ms.crt -keystore nt-ms.jks
1017  keytool -import -alias nt-gateway -file nt-gateway.crt -keystore nt-ms.jks
1018  keytool -import -alias nt-ms -file nt-ms.crt -keystore nt-gateway.jks

### Password
