/**
 * @(#) Generics.class $version 2016. 11. 24
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Generics 
 *
 * @author yongmaroo.kim (yongmaroo.kim@navercorp.com)
 */
public class Generics {
	/*
	 This is based on Toby Lee's LiveCoding tv.
	  https://www.youtube.com/watch?v=PQ58n0hk7DI
	  */

	static <T> void method1(List<T> list) {

	}

	static void unboundTypeWildCard(List<?> list) {
		// only allow add null
		list.add(null);

		// allow list's feature
		list.size();
		list.clear();
		Iterator<?> it = list.iterator();
	}

	// why use, when to use
	static <T> boolean isEmptyT(List<T> list) {
		return list.size() == 0;
	}

	static boolean isEmptyW(List<?> list) {
		return list.size() == 0;
	}

	/*
	 when we use generic methods with T, then we have intention to use type T,
	 at above case, we just need to use List's methods, so, ? is more suitable than T
	  */

	static <T> long frequencyT(List<T> list, T elem) {
		return list.stream().filter(s -> s.equals(elem)).count();
	}

	static long frequencyW(List<?> list, Object elem) {
		return list.stream().filter(s -> s.equals(elem)).count();		// java infer the type ? from Object
	}

	/*
	 Also ? is more suitable than T
	 T : I gonna use the T inside method
	 ? : Every type is fine. I'm not interest in that parameter
	  */

	private static <T extends Comparable<T>> T maxT(List<T> list) {
		return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
	}

	private static <T extends Comparable<? super T>> T maxW(List<? extends T> list) {
		//return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();		// this looks like idea bug ;;
		return null;
	}

	/*
	Comparable<? super T> - lowerbound : This will be used at outside. So any of T's super class fine to assign.
	List<? extends T> upperbound : I have intention to use type T inside method. Inside the method, at least i can use T's method
	 */

	public static void example() {
		List<Integer> list = Arrays.asList(1,2,3,4,5);
		Collections.max(list, (a, b) -> a - b);
		Collections.max(list, (Comparator<Object>) (a, b) -> a.toString().compareTo(b.toString()));

		// Also, Watch the Collections
		// public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll)
	}

	static <T> void reverseT(List<T> list) {
		List<T> temp = new ArrayList<>(list);

		for (int i = 0; i < list.size(); i++) {
			list.set(i, temp.get(list.size() - i - 1));
		}
	}

	static void reverseW(List<?> list) {
		List<?> temp = new ArrayList<>(list);
		for (int i = 0; i < list.size(); i++) {
			// list.set(i, temp.get(list.size() - i - 1));		// capture needed
		}
	}

	// From the outside, this method will use and interest only the list not the what the type is. So this is better design in the Java.
	static void reverseWithHelper(List<?> list) {
		// example with helper
		reverseHelper(list);

		// or rawtype can be used
		List temp = new ArrayList<>(list);
		List list2 = list;
		for (int i = 0; i < list2.size(); i++) {
			list2.set(i, temp.get(list2.size() - i - 1));
		}
	}

	private static <T> void reverseHelper(List<T> list) {
		List<T> temp = new ArrayList<>(list);
		for (int i = 0; i < list.size(); i++) {
			list.set(i, temp.get(list.size() - i - 1));
		}
	}

	public static void main(String[] args) {

	}
}