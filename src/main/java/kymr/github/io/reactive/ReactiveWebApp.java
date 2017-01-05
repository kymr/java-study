/**
 * @(#) ReactiveWebApp.class $version 2016. 12. 05
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ReactiveWebApp 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
//@SpringBootApplication
public class ReactiveWebApp {

	@RestController
	public static class Controller {
		@RequestMapping("/hello")
		public Publisher<String> hello(String name) {
			return new Publisher<String>() {
				@Override
				public void subscribe(Subscriber<? super String> s) {
					s.onSubscribe(new Subscription() {
						@Override
						public void request(long n) {
							s.onNext("Hello " + name);
							s.onComplete();
						}

						@Override
						public void cancel() {

						}
					});
				}
			};
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebApp.class, args);
	}
}