package com.nt.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NtMsApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.debug", "ssl:handshake");
		SpringApplication.run(NtMsApplication.class, args);
	}

}
