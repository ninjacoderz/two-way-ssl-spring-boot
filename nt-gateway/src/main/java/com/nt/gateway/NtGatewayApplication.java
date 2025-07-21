package com.nt.gateway;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class NtGatewayApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.debug", "ssl:handshake");
		KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance("jks");
			ClassPathResource classPathResource = new ClassPathResource("nt-gateway.jks");

			InputStream inputStream = classPathResource.getInputStream();
			keyStore.load(inputStream, "changeit".toCharArray());

			// print key and cert
			Key key = keyStore.getKey("nt-gateway", "changeit".toCharArray());
			Certificate cert = keyStore.getCertificate("nt-gateway");
			System.out.println("Private Key:");
			System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
			System.out.println("Certificate:");
			System.out.println(cert.toString());
			PublicKey publicKey = cert.getPublicKey();
			System.out.println("Public key:");
			System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));

		} catch (Exception exception) {
			System.out.println("Exception Occured while creating restTemplate "+exception);
			exception.printStackTrace();
		}
		SpringApplication.run(NtGatewayApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		KeyStore keyStore;
		HttpComponentsClientHttpRequestFactory requestFactory = null;

		try {
			keyStore = KeyStore.getInstance("jks");
			ClassPathResource classPathResource = new ClassPathResource("nt-gateway.jks");

			InputStream inputStream = classPathResource.getInputStream();
			keyStore.load(inputStream, "changeit".toCharArray());

			// print key and cert
			Key key = keyStore.getKey("nt-gateway", "changeit".toCharArray());
			Certificate cert = keyStore.getCertificate("nt-gateway");
			System.out.println("Private Key:");
			System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
			System.out.println("Certificate:");
			System.out.println(cert.toString());

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
					.loadTrustMaterial(null, new TrustSelfSignedStrategy())
					.loadKeyMaterial(keyStore, "changeit".toCharArray()).build(),
					NoopHostnameVerifier.INSTANCE);

			HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
					.setMaxConnTotal(Integer.valueOf(5))
					.setMaxConnPerRoute(Integer.valueOf(5))
					.build();

			requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			requestFactory.setReadTimeout(Integer.valueOf(10000));
			requestFactory.setConnectTimeout(Integer.valueOf(10000));

			restTemplate.setRequestFactory(requestFactory);
		} catch (Exception exception) {
			System.out.println("Exception Occured while creating restTemplate "+exception);
			exception.printStackTrace();
		}
		return restTemplate;
	}

}
