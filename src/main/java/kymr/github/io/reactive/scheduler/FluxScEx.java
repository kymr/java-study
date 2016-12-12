/**
 * @(#) FluxScEx.class $version 2016. 12. 13
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive.scheduler;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * FluxScEx 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class FluxScEx {
	public static void main(String[] args) {
		Flux.range(1, 10)
				.publishOn(Schedulers.newSingle("pub"))
				.log()
				.subscribeOn(Schedulers.newSingle("sub"))
				.subscribe(System.out::println);

		System.out.println("exit");
	}
}