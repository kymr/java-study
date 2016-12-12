/**
 * @(#) FluxScEx2.class $version 2016. 12. 13
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * FluxScEx2
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
@Slf4j
public class FluxScEx2 {
	public static void main(String[] args) throws InterruptedException {
		Flux.interval(Duration.ofMillis(500))
				.subscribe(s -> log.debug("onNext : {}", s));

		log.debug("exit");
		TimeUnit.SECONDS.sleep(5);

		/*
		Executors.newSingleThreadExecutor().execute(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {}
			System.out.println("Hello");
		});
		*/

		/*
		 User Thread vs Daemon Thread

		 If any of User Thread exists, then jvm doesn't terminate the thread.
		 And If daemon thread only exists while terminating, then jvm terminate threads.

		 Flux uses daemon threads.
		  */

	}
}