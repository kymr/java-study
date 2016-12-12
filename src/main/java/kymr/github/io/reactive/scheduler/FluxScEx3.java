/**
 * @(#) FluxScEx3.class $version 2016. 12. 13
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * FluxScEx3 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
@Slf4j
public class FluxScEx3 {
	public static void main(String[] args) throws InterruptedException {
		Flux.interval(Duration.ofMillis(200))
				.take(10)
				.subscribe(s -> log.debug("onNext : {}", s));

		TimeUnit.SECONDS.sleep(10);
	}
}