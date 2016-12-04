/**
 * @(#) PubSub1.class $version 2016. 12. 04
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PubSub1
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class PubSub1 {
	public static void main(String[] args) {
		Publisher<Integer> pub = new Publisher<Integer>() {
			Iterable<Integer> iter = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());

			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				sub.onSubscribe(new Subscription() {
					@Override
					public void request(long n) {
						try {
							iter.forEach(s -> sub.onNext(s));
							sub.onComplete();
						} catch (Throwable t) {
							sub.onError(t);
						}
					}

					@Override
					public void cancel() {

					}
				});
			}
		};

		Subscriber<Integer> sub = new Subscriber<Integer>() {
			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("onSubscribe : ");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("onNext : " + integer);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("onError : " +  t);
			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		};

		pub.subscribe(sub);
	}
}