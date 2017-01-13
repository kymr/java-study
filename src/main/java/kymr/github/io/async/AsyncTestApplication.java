/**
 * @(#) AsyncTestApplication.class $version 2017. 01. 14
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.async;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * AsyncTestApplication 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
@SpringBootApplication
public class AsyncTestApplication {
	@RestController
	public static class MyController {
		AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

		@Autowired MyService myService;

		static final String URL1 = "http://localhost:8081/service?req={req}";
		static final String URL2 = "http://localhost:8081/service2?req={req}";

		@GetMapping("/rest")
		public DeferredResult<String> rest(int idx) {
			DeferredResult<String> dr = new DeferredResult<>();

			ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(URL1, String.class, "hello" + idx);
			f1.addCallback(s -> {
				ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(URL2, String.class, s.getBody());
				f2.addCallback(s2 -> {
					ListenableFuture<String> f3 = myService.work(s2.getBody());
					f3.addCallback(s3 -> {
						dr.setResult(s3);
					}, e -> {
						dr.setErrorResult(e.getMessage());
					});
				}, e -> {
					dr.setErrorResult(e.getMessage());
				});
			}, e-> {
				dr.setErrorResult(e.getMessage());
			});

			return dr;
		}
	}

	@Service
	public static class MyService {
		@Async
		public ListenableFuture<String> work(String req) {
			return new AsyncResult<>(req + "/asyncwork");
		}
	}

	@Bean
	ThreadPoolTaskExecutor myThreadPool() {
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(1);
		te.setMaxPoolSize(1);
		te.initialize();
		return te;
	}


	public static void main(String[] args) {
		System.setProperty("SERVER_PORT", "8080");
		System.setProperty("server.tomcat.max-threads", "1");
		SpringApplication.run(AsyncTestApplication.class, args);
	}
}