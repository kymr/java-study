/**
 * @(#) IntegerDelegateSub.class $version 2016. 12. 05
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * IntegerDelegateSub 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class IntegerDelegateSub implements Subscriber<Integer> {
	Subscriber sub;

	public IntegerDelegateSub(Subscriber sub) {
		this.sub = sub;
	}

	@Override
	public void onSubscribe(Subscription s) {
		sub.onSubscribe(s);
	}

	@Override
	public void onNext(Integer i) {
		sub.onNext(i);
	}

	@Override
	public void onError(Throwable t) {
		sub.onError(t);
	}

	@Override
	public void onComplete() {
		sub.onComplete();
	}
}