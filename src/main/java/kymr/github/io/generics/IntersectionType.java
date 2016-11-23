/**
 * @(#) IntersectionType.class $version 2016. 11. 24
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.generics;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * IntersectionType 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class IntersectionType {
	/*
	 This is based on Toby Lee's LiveCoding tv.
	  https://www.youtube.com/watch?v=PQ58n0hk7DI
	  */


	public static void main(String[] args) {
		hello2(s -> s);
	}

	private static void hello1(Function p0) {
		// @FunctionalInterface - interface that has only one abstract method.
	}

	private static void hello2(Function<String, String> p0) {

	}

	public static void anonymousClassExample() {
		Function<String, String> f = new Function<String, String>() {
			@Override
			public String apply(String s) {
				return null;
			}
		};
		hello2(f);
	}

	public static void intersect() {
		hello1((Function) s -> s);
		hello2((Function & Serializable & Cloneable) s -> s);	// intersect with marker interface, still has only one method.
	}

	public static <T extends Function & Serializable & Cloneable> void hello3(T o) {
	}


	// intersect 2
	interface Hello {
		default void hello() {
			System.out.println("Hello");
		}
	}

	interface Hi {
		default void hi() {
			System.out.println("Hi");
		}
	}

	public static void intersect2() {
		intersectHello((Function & Hello & Hi) s -> s);
	}

	private static <T extends Function & Hello & Hi> void intersectHello(T t) {
		t.hello();
		t.hi();
	}


	// intersect 3
	public static void intersect3() {
		run((Function & Hello & Hi) s -> s, o -> {
			o.hello();
			o.hi();
		});
	}

	private static <T extends Function> void run(T t, Consumer<T> consumer) {
		consumer.accept(t);;
	}

	// intersect 4
	interface DelegateTo<T> {
		T delegate();
	}

	interface Hello2 extends DelegateTo<String> {
		default void hello() {
			System.out.println("Hello " + delegate());
		}
	}

	interface UpperCase extends DelegateTo<String> {
		default void upperCase() {
			System.out.println(delegate().toUpperCase());
		}
	}


	public static void intersect4() {
		run2((DelegateTo<String> & Hello & UpperCase)() -> "Yongmaroo Kim", o -> {		// () -> "Yongmaroo Kim", this is implementation of delegate method
			o.hello();
			o.delegate();
		});

	}

	private static <T extends DelegateTo<S>, S> void run2(T t, Consumer<T> consumer) {
		consumer.accept(t);;
	}


	// intersect 5
	interface Pair<T> {
		T getFirst();
		T getSecond();
		void setFirst(T first);
		void setSecond(T second);
	}

	static class Name implements Pair<String> {
		String firstName;
		String lastName;

		public Name(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		public String getFirst() {
			return this.firstName;
		}

		@Override
		public String getSecond() {
			return this.lastName;
		}

		@Override
		public void setFirst(String first) {
			this.firstName = first;
		}

		@Override
		public void setSecond(String second) {
			this.lastName = second;
		}
	}

	interface ForwardingPair<T> extends DelegateTo<Pair<T>>, Pair<T> {
		default T getFirst() { return delegate().getFirst(); }
		default T getSecond() { return delegate().getSecond(); }
		default void setFirst(T first) { delegate().setFirst(first); }
		default void setSecond(T second) { delegate().setSecond(second); }
	}

	interface Convertable<T> extends DelegateTo<Pair<T>> {
		default void convert(Function<T, T> mapper) {
			Pair<T> pair = delegate();
			pair.setFirst(mapper.apply(pair.getFirst()));
			pair.setSecond(mapper.apply(pair.getSecond()));
		}
	}

	static <T> void print(Pair<T> pair) {
		System.out.println(pair.getFirst() + " " + pair.getSecond());
	}

	interface Printable<T> extends DelegateTo<Pair<T>> {
		default void print() {
			System.out.println(delegate().getFirst() + " " + delegate().getSecond());
		}
	}


	public static void intersect5() {
		Pair<String> name = new Name("Yongmaroo", "Kim");
		run2((ForwardingPair<String> & Convertable<String> & Printable<String>)() -> name, o -> {
			System.out.println(o.getFirst() + " " + o.getSecond());
			print(o);
			o.convert(s -> s.toUpperCase());
			print(o);
			o.convert(s -> s.substring(0, 2));
			print(o);

			o.print();
		});

	}
}