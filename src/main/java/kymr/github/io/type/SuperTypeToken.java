/**
 * @(#) SuperTypeToken.class $version 2016. 12. 29
 * <p>
 * Copyright 2007 NAVER Corp. All rights Reserved.
 * NAVER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kymr.github.io.type;

import org.springframework.asm.TypeReference;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SuperTypeToken 
 */
public class SuperTypeToken {
	public static void main(String[] args) throws NoSuchFieldException {
		cannotReturnType();
		canGetType();
		typeReferenceExxample();
		typeSafeMapExample();
		parameterizedTypeReferenceExample();
		improvedTypeSafeMap();
		resolvableTypeExample();
	}

	static class Sup<T> {
		T value;
	}

	private static void cannotReturnType() throws NoSuchFieldException {
		Sup<String> s = new Sup<>();
		s.value = "s";
		System.out.println("Type Erasure");
		System.out.println(s.getClass().getDeclaredField("value").getType());
	}

	// NESTED STATIC CLASS
	static class Sub extends Sup<String> {
	}
	static class Sub2 extends Sup<List<String>> {
	}

	private static void canGetType() {
		Sub sub = new Sub();
		Type t = sub.getClass().getGenericSuperclass();
		ParameterizedType ptype = (ParameterizedType) t;
		System.out.println("REIFICATION");
		System.out.println(ptype.getActualTypeArguments()[0]);
		System.out.println(((ParameterizedType)new Sub2().getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

		class Sub3 extends Sup<String> {
		}
		System.out.println(((ParameterizedType)new Sub3().getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

		Sup b = new Sup<String>() {
		};
		System.out.println(((ParameterizedType)b.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

	}

	static class TypeReference<T> {
		Type type;

		public TypeReference() {
			Type stype = getClass().getGenericSuperclass();
			if (stype instanceof ParameterizedType) {
				this.type = ((ParameterizedType) stype).getActualTypeArguments()[0];
			} else throw new RuntimeException();
		}
	}

	private static void typeReferenceExxample() {
		TypeReference t = new TypeReference<String>() {};
		System.out.println(t.type);
	}

	static class TypesafeMap {
		Map<TypeReference2<?>, Object> map = new HashMap<>();

		<T> void put(TypeReference2<T> tr, T value) {
			map.put(tr, value);
		}

		<T> T get(TypeReference2<T> tr) {
			if (tr.type instanceof Class<?>) {
				return ((Class<T>)tr.type).cast(map.get(tr));
			} else {
				return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr));
			}
		}
	}

	static class TypeReference2<T> {
		Type type;

		public TypeReference2() {
			Type stype = getClass().getGenericSuperclass();

			if (stype instanceof ParameterizedType) {
				this.type = ((ParameterizedType) stype).getActualTypeArguments()[0];
			}
			else throw new RuntimeException();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;

			TypeReference2<?> that = (TypeReference2<?>) o;

			return type.equals(that.type);

		}

		@Override
		public int hashCode() {
			return type.hashCode();
		}
	}

	private static void typeSafeMapExample() {
		TypeReference2 t = new TypeReference2<String>() {};
		System.out.println(t.type);

		System.out.println("== Parameterized Type ==");

		TypesafeMap m = new TypesafeMap();
		m.put(new TypeReference2<String>() {}, "String");
		m.put(new TypeReference2<Integer>() {}, 1);
		m.put(new TypeReference2<List<Integer>>() {}, Arrays.asList(1, 2, 3));
		m.put(new TypeReference2<List<String>>() {}, Arrays.asList("a", "b", "c"));


		System.out.println(m.get(new TypeReference2<String>() {}));
		System.out.println(m.get(new TypeReference2<Integer>() {}));
		System.out.println(m.get(new TypeReference2<List<String>>() {}));
		System.out.println(m.get(new TypeReference2<List<Integer>>() {}));
	}

	private static void parameterizedTypeReferenceExample() {
		ParameterizedTypeReference typeRef = new ParameterizedTypeReference<List<Map<Set<String>, String>>>() {};
		System.out.println(typeRef.getType());
	}


	static class TypesafeMap3 {
		Map<Type, Object> map = new HashMap<>();

		<T> void put(TypeReference3<T> tr, T value) {
			map.put(tr.type, value);
		}

		<T> T get(TypeReference3<T> tr) {
			if (tr.type instanceof Class<?>) {
				return ((Class<T>)tr.type).cast(map.get(tr.type));
			} else {
				return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr.type));
			}
		}
	}

	static class TypeReference3<T> {
		Type type;

		public TypeReference3() {
			Type stype = getClass().getGenericSuperclass();

			if (stype instanceof ParameterizedType) {
				this.type = ((ParameterizedType) stype).getActualTypeArguments()[0];
			}
			else throw new RuntimeException();
		}
	}


	private static void improvedTypeSafeMap() {
		TypeReference3 t = new TypeReference3<String>() {};
		System.out.println(t.type);

		System.out.println("== Improved Parameterized Type ==");

		TypesafeMap3 m = new TypesafeMap3();
		m.put(new TypeReference3<String>() {}, "String");
		m.put(new TypeReference3<Integer>() {}, 1);
		m.put(new TypeReference3<List<Integer>>() {}, Arrays.asList(1, 2, 3));
		m.put(new TypeReference3<List<String>>() {}, Arrays.asList("a", "b", "c"));

		System.out.println(m.get(new TypeReference3<String>() {}));
		System.out.println(m.get(new TypeReference3<Integer>() {}));
		System.out.println(m.get(new TypeReference3<List<String>>() {}));
		System.out.println(m.get(new TypeReference3<List<Integer>>() {}));
	}

	private static void resolvableTypeExample() {
		System.out.println("== resolvable ==");
		ResolvableType rt = ResolvableType.forInstance(new TypeReference3<List<String>>() {});
		System.out.println(rt.getSuperType().getGenerics().length);
		System.out.println(rt.getSuperType().getGeneric(0).getType());
		System.out.println(rt.getSuperType().getGeneric(0).getNested(2).getType());
		System.out.println(rt.getSuperType().hasUnresolvableGenerics());
		System.out.println(ResolvableType.forInstance(new ArrayList<String>()).hasUnresolvableGenerics());
	}

}