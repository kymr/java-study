/**
 * @(#) RemoteService.class $version 2017. 01. 14
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RemoteService 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
@SpringBootApplication
public class RemoteService {
	@RestController
	public static class MyController {
		@GetMapping("/service")
		public String service(String req) throws InterruptedException {
			Thread.sleep(2000);
			return req + "/service";
		}

		@GetMapping("/service2")
		public String service2(String req) throws InterruptedException {
			Thread.sleep(2000);
			return req + "/service2";
		}

	}



	public static void main(String[] args) {
		System.setProperty("SERVER_PORT", "8081");
		System.setProperty("server.tomcat.max-threads", "1000");
		SpringApplication.run(RemoteService.class, args);
	}
}