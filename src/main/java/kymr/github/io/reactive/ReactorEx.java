/**
 * @(#) ReactorEx.class $version 2016. 12. 05
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive;

import reactor.core.publisher.Flux;

/**
 * ReactorEx 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class ReactorEx {
	public static void main(String[] args) {
		Flux.<Integer>create(e -> {
			e.next(1);
			e.next(2);
			e.next(3);
			e.complete();
		})
		.log()
		.map(s -> s * 10)
		.reduce(0, (a, b) -> a + b)
		.log()
		.subscribe(System.out::println);
	}
}