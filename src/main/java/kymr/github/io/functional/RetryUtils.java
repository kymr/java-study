/**
 * @(#) RetryUtils.class $version 2017. 01. 05
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.functional;

import lombok.extern.slf4j.Slf4j;

import java.security.InvalidParameterException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * RetryUtils 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
@Slf4j
public class RetryUtils {
	private static final int RETRY = 3;
	private static final long DELAY = 1000l;

	@FunctionalInterface
	public interface RunnableWithException {
		void run() throws Exception;
	}

	public static <V> V retry(Callable<V> callable, Throwable throwable, String message) {
		return retryLogics(callable, throwable, message);
	}

	public static void retry(RunnableWithException runnable, Throwable throwable, String message) {
		retryLogics(runnable, throwable, message);
	}

	private static <T> T retryLogics(Object functionalInterface, Throwable throwable, String message) {
		int counter = 0;

		while (counter < RETRY) {
			try {
				if (functionalInterface instanceof Callable) {
					return (T) ((Callable) functionalInterface).call();
				} else if (functionalInterface instanceof RunnableWithException) {
					((RunnableWithException) functionalInterface).run();
					return null;
				} else {
					throw new InvalidParameterException();
				}
			} catch (Exception e) {
				counter++;
				log.error("retry {} / {}, {}", counter, RETRY, message, e);

				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		throw new RuntimeException(throwable);
	}
}