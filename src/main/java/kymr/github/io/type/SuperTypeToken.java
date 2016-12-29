/**
 * @(#) SuperTypeToken.class $version 2016. 12. 29
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.type;

/**
 * SuperTypeToken 
 */
public class SuperTypeToken {
	static class Sup<T> {
		T value;
	}

	public static void main(String[] args) throws NoSuchFieldException {
		cannotReturnType();
	}

	private static void cannotReturnType() throws NoSuchFieldException {
		Sup<String> s = new Sup<>();

		System.out.println(s.getClass().getDeclaredField("value").getType());
	}


}