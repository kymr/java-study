/**
 * @(#) PubSub4.class $version 2016. 12. 05
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PubSub4 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class PubSub4 {
	public static void main(String[] args) {
		/*
		pub -> [data1] -> mapPub -> [data2] -> logsub
		<- subscriber(logSub)
		-> onSubscribe(s)
		-> onNext
		-> onNext
		-> onComplete
		 */
		Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
		//Publisher<String> mapPub = mapPub(pub, s -> "[" + s + "]");
		//Publisher<String> reducePub = reducePub(pub, "", (a, b) -> a + "-" + b);
		Publisher<StringBuilder> reducePub = reducePub(pub, new StringBuilder(), (a, b) -> a.append(b + ","));
		reducePub.subscribe(logSub());
	}

	private static <T, R> Publisher<R> reducePub(Publisher<T> pub, R init, BiFunction<R, T, R> bf) {
		return new Publisher<R>() {
			@Override
			public void subscribe(Subscriber<? super R> sub) {
				pub.subscribe(new DelegateSub<T, R>(sub) {
					R result = init;

					@Override
					public void onNext(T i) {
						result = bf.apply(result, i);
					}

					@Override
					public void onComplete() {
						sub.onNext(result);
						sub.onComplete();
					}
				});
			}
		};
	}

	private static <T, R> Publisher<R> mapPub(Publisher<T> pub, Function<T, R> f) {
		return new Publisher<R>() {
			@Override
			public void subscribe(Subscriber<? super R> sub) {
				pub.subscribe(new DelegateSub<T, R>(sub) {
					@Override
					public void onNext(T i) {
						sub.onNext(f.apply(i));
					}
				});
			}
		};
	}

	private static <T> Subscriber<T> logSub() {
		return new Subscriber<T>() {
			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("onSubscribe : ");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(T i) {
				System.out.println("onNext : " + i);
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
	}

	private static Publisher<Integer> iterPub(Iterable<Integer> iter) {
		return new Publisher<Integer>() {
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
	}
}