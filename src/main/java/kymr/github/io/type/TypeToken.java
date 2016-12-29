/**
 * @(#) TypeToken.class $version 2016. 12. 29
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TypeToken 
 */
public class TypeToken {
	public static void main(String[] args) throws Exception {
		createInstance();
		createInstanceWithGeneric1();
		createInstanceWithGeneric2();
		typeSafeMapExample1();
		typeSafeMapExample2();
	}

	static <T> T create(Class<T> clazz) throws Exception {
		return clazz.newInstance();
	}

	private static void createInstance() throws Exception {
		Integer o = create(Integer.class);
		System.out.println(o.getClass());
	}

	private static void createInstanceWithGeneric1() {
		Generic<String> s = new Generic<>();
		s.value = "String";

		Generic<Integer> i = new Generic<>();
		i.value = 1;
		i.set(10);
	}

	static class Generic<T> {
		T value;
		void set(T t) {}
		T get() { return null; };
	}

	private static void createInstanceWithGeneric2() {
		/* this is wrong;
		Object o = "String";
		Integer i = (Integer) o;
		 */
	}

	static <T> T create2(Class<T> clazz) throws IllegalAccessException, InstantiationException {
		return (T) clazz.newInstance();
	}

	static class TypesafeMap1 {
		Map<String, Object> map = new HashMap<>();

		void dangerousUseCase() {
			map.put("a", "a");
			map.put("b", 1);
			Integer i = (Integer) map.get("b");
			String s = (String) map.get("a");
		}
	}

	static class TypesafeMap2 {
		Map<Class<?>, Object> map = new HashMap<>();

		<T> void put(Class<T> clazz, T value) {
			map.put(clazz, value);
		}

		<T> T get(Class<T> clazz) {
			//return (T) map.get(clazz); 		// this is not typesafe
			return clazz.cast(map.get(clazz));
		}
	}

	// TYPE TOKEN

	private static void typeSafeMapExample1() {
		TypesafeMap2 m = new TypesafeMap2();
		m.put(String.class, "String");
		// m.put(Integer.class, "Value")	// this is wrong
		m.put(Integer.class, 1);
		m.put(List.class, Arrays.asList(1, 2, 3));

		System.out.println(m.get(List.class));
	}

	private static void typeSafeMapExample2() {
		/*
		TypesafeMap2 m = new TypesafeMap2();
		m.put(List<Integer>.class, Arrays.asList(1, 2, 3));			// List<Integer>
		m.put(List<String>.class, Arrays.asList("a", "b", "c"));	// List<String>

		class literal cannot be used
		*/

		System.out.println("So Super Type Token !!! .. by Neal Gafter");
		System.out.println("ERASURE, REIFICATION");
		System.out.println("Java had to choice ERASURE not REIFICATION. Because Java should be comparable with previous version");

		System.out.println("*** GO TO SuperTypeToken.java ***");
	}
}